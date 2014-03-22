package  com.kushnerov.paymentSystem.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import  com.kushnerov.paymentSystem.entities.Client;
import  com.kushnerov.paymentSystem.entities.Role;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class ClientDao extends AbstractDao<Client> {

private ClientDao(){}
	
	private static class ClientDaoHolder {

        public static final ClientDao instance = new ClientDao();
    }

    public static ClientDao getInstance() {
        return ClientDaoHolder.instance;
    }
	@Override
	public List<Client> readAll() throws MyDAOException {
		List<Client> clientList = new ArrayList<Client>();
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.all.clients"));
			rs = pst.executeQuery();
			while (rs.next()) {
				Client cl = new Client();
				cl.setId(rs.getInt(1));
				cl.setFirstName(rs.getString(2));
				cl.setLastName(rs.getString(3));
				cl.setEmail(rs.getString(4));
				cl.setPassword(rs.getString(5));
				RoleDao rdao = RoleDao.getInstance();
				Role role = rdao.read(rs.getInt(6));
				cl.setRole(role);
				clientList.add(cl);
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in ClientDao.readAll", e);
			throw new MyDAOException("ERROR_CLIENT_READ_ALL", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}

		return clientList;
	}

	@Override
	public Client read(int id) throws MyDAOException {
		Client client = null;
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			PreparedStatement st = connection.prepareStatement(Resource
					.getStr("sql.get.client.by.id"));
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				client = new Client();
				client.setId(rs.getInt(1));
				client.setFirstName(rs.getString(2));
				client.setLastName(rs.getString(3));
				client.setEmail(rs.getString(4));
				client.setPassword(rs.getString(5));
				RoleDao rdao = RoleDao.getInstance();
				Role role = rdao.read(rs.getInt(6));
				client.setRole(role);
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in ClientDao.read", e);
			throw new MyDAOException("ERROR_CLIENT_READ", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}
		return client;
	}
	
	public Client read(String email, String pass) throws MyDAOException {
		Connection connection = getConnection();
		Client client = null;
		try {
			PreparedStatement st = connection.prepareStatement(Resource
					.getStr("sql.get.client.by.email.and.password"));
			st.setString(1, email);
			st.setString(2, pass);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				client = new Client();
				client.setId(rs.getInt(1));
				client.setFirstName(rs.getString(2));
				client.setLastName(rs.getString(3));
				client.setEmail(rs.getString(4));
				client.setPassword(rs.getString(5));
				RoleDao rdao = RoleDao.getInstance();
				Role role = rdao.read(rs.getInt(6));
				client.setRole(role);
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in ClientDao.read", e);
			throw new MyDAOException("ERROR_CLIENT_READ_BY_EMAIL_AND_PASSWORD", e);
		} finally {
		}
		return client;
	}

	@Override
	public int create(Client bean) throws MyDAOException {
		

		return 0;
	}

	@Override
	public int update(Client bean) throws MyDAOException {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		int res = 0;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.update.client"));
			pst.setString(1, bean.getFirstName());
			pst.setString(2, bean.getLastName());
			pst.setString(3, bean.getEmail());
			pst.setString(4, bean.getPassword());
			pst.setInt(5, bean.getRole().getId());
			pst.setInt(6, bean.getId());
			res = pst.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in ClientDao.update", e);
            throw new MyDAOException("ERROR_CLIENT_UPDATE", e);
		}finally {
			close(pst);
			close(connection);
		}
		return res;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
