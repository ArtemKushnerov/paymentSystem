package  com.kushnerov.paymentSystem.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import  com.kushnerov.paymentSystem.entities.Account;
import  com.kushnerov.paymentSystem.entities.Service;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class ServiceDao extends AbstractDao<Service> {

	private ServiceDao() {
	}

	private static class ServiceDaoHolder {

		public static final ServiceDao instance = new ServiceDao();
	}

	public static ServiceDao getInstance() {
		return ServiceDaoHolder.instance;
	}

	@Override
	public int create(Service bean) throws MyDAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Service read(int id) throws MyDAOException {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Service service = null;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.service.by.id"));
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				service = new Service();
				service.setId(rs.getInt(1));
				service.setName(rs.getString(2));
				CompanyDao companyDao = new CompanyDao();
				service.setCompany(companyDao.read(rs.getInt(3)));
				AccountDao accDao = AccountDao.getInstance();
				service.setAccount(accDao.read(rs.getInt(4)));
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in ServiceDao.read", e);
			throw new MyDAOException("ERROR_SERVICE_READ", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}
		return service;
	}

	@Override
	public List<Service> readAll() throws MyDAOException {
		List<Service> serviceList = new ArrayList<Service>();
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.all.services"));
			rs = pst.executeQuery();
			while (rs.next()) {
				Service service = new Service();
				service.setId(rs.getInt(1));
				service.setName(rs.getString(2));
				serviceList.add(service);
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in ServiceDao.readAll", e);
			throw new MyDAOException("ERROR_SERVICE_READ_ALL", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}
		return serviceList;
	}

	@Override
	public int update(Service bean) throws MyDAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) throws MyDAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Service readByRecieverAccount(Account receiver) throws MyDAOException{
		// TODO Auto-generated method stub
		return null;
	}

	public List<Service> readAllByCompany(int id) throws MyDAOException {
		List<Service> serviceList = new ArrayList<Service>();
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.all.services.by.company.id"));
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				Service service = new Service();
				service.setId(rs.getInt(1));
				service.setName(rs.getString(2));
				serviceList.add(service);
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in ServiceDao.readAllByCompany", e);
			throw new MyDAOException("ERROR_SERVICE_READ_ALL_BY_COMPANY_ID", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}
		return serviceList;
	}

}
