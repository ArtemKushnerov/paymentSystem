package  com.kushnerov.paymentSystem.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.DBinit.PropertiesHolder;
import  com.kushnerov.paymentSystem.exceptions.MyConnectionException;

public class ConnectionPool {

	private static final Logger LOG = Logger.getLogger(ConnectionPool.class.getName());
	private static final ConnectionPool instance = new ConnectionPool();
	private LinkedBlockingDeque<Connection> connections = new LinkedBlockingDeque<Connection>();
	PropertiesHolder propertiesHolder;


	private ConnectionPool() {
	}

	public static ConnectionPool getInstance() {
		return instance;
	}
	
	private Connection createConnection() throws MyConnectionException {
		try {
			return DriverManager.getConnection(
					propertiesHolder.getUrl(), propertiesHolder.getUser(),
					propertiesHolder.getPassword());
		} catch (SQLException ex) {
			LOG.log(Level.ERROR, "Exception in DatabasePool.createConnection",
					ex);
			throw new MyConnectionException("ERROR_CONNECTION_TO_DB", ex);
		}
	}

	
	public void init(PropertiesHolder propertiesHolder)
			throws MyConnectionException {
		this.propertiesHolder=propertiesHolder;
		try {
			Class.forName(propertiesHolder.getDriver());
		} catch (ClassNotFoundException ex) {
			LOG.log(Level.ERROR, "Exception in DatabasePool.init:", ex);
			throw new MyConnectionException("ERROR_LOAD_DB_DRIVER", ex);
		}
		for (int i = 0; i < propertiesHolder.getInitConnections(); i++) {
			connections.add(createConnection());
		}
	}

	
	public Connection getConnection() throws MyConnectionException {
		try {
			Connection connection = connections.poll(500, TimeUnit.MILLISECONDS);
			if (connection == null) {
				connections.add(createConnection());
				connection = connections.poll();
			}
			return connection;
		} catch (InterruptedException ex) {
			LOG.log(Level.ERROR, "Exception in DatabasePool.getConnection:", ex);
			return getConnection();
		} catch (MyConnectionException ex1) {
			LOG.log(Level.ERROR, "Exception in DatabasePool.getConnection:",
					ex1);
			throw new MyConnectionException("ERROR_CONNECTION_TO_DB", ex1);
		}
	}

	
	public void release(Connection connection) {
		try {
			if (!connection.isClosed()) {
				connections.add(connection);
			}
		} catch (SQLException ex) {
			LOG.log(Level.ERROR, "Exception in Databasepool.release", ex);
		}
	}

	
	public void destroy() {
		for (Iterator<Connection> it = connections.iterator(); it.hasNext();) {
			Connection c = it.next();
			try {
				c.close();
			} catch (SQLException ex) {
				LOG.log(Level.ERROR, "Exception in DatabasePool.destroy:", ex);
			}
		}
		connections.removeAll(connections);
	}

}
