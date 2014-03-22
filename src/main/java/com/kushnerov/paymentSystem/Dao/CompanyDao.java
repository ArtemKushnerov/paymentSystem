package  com.kushnerov.paymentSystem.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import  com.kushnerov.paymentSystem.entities.Company;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class CompanyDao extends AbstractDao<Company> {

	
	private static class CompanyDaoHolder {

        public static final CompanyDao instance = new CompanyDao();
    }

    public static CompanyDao getInstance() {
        return CompanyDaoHolder.instance;
    }
	@Override
	public int create(Company bean) throws MyDAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Company read(int id) throws MyDAOException {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs= null;
		Company company = null;
		try {
			 pst = connection.prepareStatement(Resource
					.getStr("sql.get.company.by.id"));
			pst.setInt(1, id);
			 rs = pst.executeQuery();
			if (rs.next()) {
				company = new Company();
				company.setId(rs.getInt(1));
				company.setName(rs.getString(2));
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in CompanyDao.read", e);
            throw new MyDAOException("ERROR_COMPANY_READ", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}
		return company;
	}

	@Override
	public List<Company> readAll() throws MyDAOException {
		List<Company> companyList = new ArrayList<Company>();
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.all.companies"));
			 rs = pst.executeQuery();
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt(1));
				company.setName(rs.getString(2));
				companyList.add(company);
			}
		} catch (SQLException e) {
            logger.log(Level.ERROR, "Exception in CompanyDao.readAll", e);
            throw new MyDAOException("ERROR_COMPANY_READ_ALL", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}
		return companyList;
	}

	@Override
	public int update(Company bean) throws MyDAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) throws MyDAOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
