package  com.kushnerov.paymentSystem.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import  com.kushnerov.paymentSystem.entities.Role;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class RoleDao extends AbstractDao<Role> {

	private RoleDao() {
	}

	private static class RoleDaoHolder {

		public static final RoleDao instance = new RoleDao();
	}

	public static RoleDao getInstance() {
		return RoleDaoHolder.instance;
	}

	@Override
	public int create(Role bean) throws MyDAOException {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		int res = 0;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.create.role"));
			pst.setString(1, bean.getDescription());
			res = pst.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in RoleDao.create", e);
			throw new MyDAOException("ERROR_ROLE_CREATE", e);
		} finally {
			close(pst);
			close(connection);
		}
		return res;
	}

	@Override
	public Role read(int id) throws MyDAOException {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Role role = null;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.role.by.id"));
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				role = new Role();
				role.setId(rs.getInt(1));
				role.setDescription(rs.getString(2));
				;
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in RoleDao.read", e);
			throw new MyDAOException("ERROR_ROLE_READ", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}
		return role;
	}

	@Override
	public List<Role> readAll() throws MyDAOException {
		List<Role> roleList = new ArrayList<Role>();
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.all.roles"));
			rs = pst.executeQuery();
			while (rs.next()) {
				Role role = new Role();
				role.setId(rs.getInt(1));
				role.setDescription(rs.getString(2));
				roleList.add(role);
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in RoleDao.readAll", e);
			throw new MyDAOException("ERROR_ROLE_READ_ALL", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}

		return roleList;
	}

	@Override
	public int update(Role bean) throws MyDAOException {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		int res = 0;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.update.role"));
			pst.setString(1, bean.getDescription());
			pst.setInt(2, bean.getId());
			res = pst.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in RoleDao.update", e);
			throw new MyDAOException("ERROR_ROLE_UPDATE", e);
		} finally {
			close(pst);
			close(connection);
		}

		return res;
	}

	@Override
	public int delete(int id) throws MyDAOException {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		int res = 0;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.delete.role"));
			pst.setInt(1, id);
			res = pst.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in RoleDao.delete", e);
			throw new MyDAOException("ERROR_ROLE_DELETE", e);
		} finally {
			close(pst);
			close(connection);
		}
		return res;
	}

}
