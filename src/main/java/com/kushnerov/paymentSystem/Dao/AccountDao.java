package  com.kushnerov.paymentSystem.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import  com.kushnerov.paymentSystem.entities.Account;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class AccountDao extends AbstractDao<Account> {

	private AccountDao() {
	}

	private static class AccountDaoHolder {

		public static final AccountDao instance = new AccountDao();
	}

	public static AccountDao getInstance() {
		return AccountDaoHolder.instance;
	}

	@Override
	public List<Account> readAll() throws MyDAOException {
		List<Account> accList = new ArrayList<Account>();
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.all.accounts"));
			rs = pst.executeQuery();
			while (rs.next()) {
				Account acc = new Account();
				acc.setId(rs.getInt(1));
				acc.setBalance(rs.getDouble(2));
				acc.setBlocked(rs.getBoolean(3));
				accList.add(acc);
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in AccountDao.readAll", e);
			throw new MyDAOException("ERROR_ACCOUNT_READ", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}

		return accList;
	}

	@Override
	public Account read(int id) throws MyDAOException {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Account acc = null;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.account.by.id"));
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				acc = new Account();
				acc.setId(rs.getInt(1));
				acc.setBalance(rs.getDouble(2));
				acc.setBlocked(rs.getBoolean(3));
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in AccountDao.read", e);
			throw new MyDAOException("ERROR_ACCOUNT_READ", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}
		return acc;
	}

	@Override
	public int create(Account bean) throws MyDAOException {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		int res = 0;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.create.account"));
			// pst.setInt(1, bean.getId());
			pst.setDouble(1, bean.getBalance());
			pst.setBoolean(2, bean.isBlocked());
			res = pst.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in AccountDao.create", e);
			throw new MyDAOException("ERROR_ACCOUNT_CREATE", e);
		} finally {
			close(pst);
			close(connection);
		}
		return res;
	}

	@Override
	public int update(Account bean) throws MyDAOException {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		int res = 0;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.update.account"));
			pst.setDouble(1, bean.getBalance());
			pst.setBoolean(2, bean.isBlocked());
			pst.setInt(3, bean.getId());
			res = pst.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in AccountDao.update", e);
			throw new MyDAOException("ERROR_ACCOUNT_UPDATE", e);
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
					.getStr("sql.delete.account"));
			pst.setInt(1, id);
			res = pst.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in AccountDao.delete", e);
			throw new MyDAOException("ERROR_ACCOUNT_DELETE", e);
		} finally {
			close(pst);
			close(connection);
		}
		return res;
	}

}
