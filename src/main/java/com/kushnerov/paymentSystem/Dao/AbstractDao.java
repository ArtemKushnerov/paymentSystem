package  com.kushnerov.paymentSystem.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.entities.Identity;
import  com.kushnerov.paymentSystem.exceptions.MyConnectionException;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.pool.ConnectionPool;

abstract public class AbstractDao<T extends Identity> {

	protected Connection connection;
	protected final static Logger logger = Logger.getLogger(AbstractDao.class.getName());

	public abstract int create(T bean) throws MyDAOException;

	public abstract T read(int id) throws MyDAOException;

	public abstract List<T> readAll() throws MyDAOException;

	public abstract int update(T bean) throws MyDAOException;

	public abstract int delete(int id) throws MyDAOException;

	

	 protected Connection getConnection() throws MyDAOException {
	        try {
	            return ConnectionPool.getInstance().getConnection();
	        } catch (MyConnectionException ex) {
	            logger.log(Level.ERROR, "Exception in DAO.getConnection", ex);
	            throw new MyDAOException(ex);
	        }
	    }

	   
	    protected void close(Connection connection) {
	        ConnectionPool pool = ConnectionPool.getInstance();
	        pool.release(connection);
	    }

	   
	    protected void close(PreparedStatement preparedStatement) {
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException ex) {
	                logger.log(Level.ERROR, "Exception in DAO.closePreparedStatement:", ex);
	            }
	        }
	    }

	   
	    protected void close(Statement statement) {
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException ex) {
	                logger.log(Level.ERROR, "Exception in DAO.closeStatement:", ex);
	            }
	        }
	    }
	    protected void close(ResultSet rs) {
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException ex) {
	                logger.log(Level.ERROR, "Exception in DAO.closeResultSet:", ex);
	            }
	        }
	    }
}
