package  com.kushnerov.paymentSystem.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Level;

import  com.kushnerov.paymentSystem.entities.PaymentSystem;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class PaymentSystemDao extends AbstractDao<PaymentSystem> {

private PaymentSystemDao(){}
	
	private static class PaymentSystemDaoHolder {

        public static final PaymentSystemDao instance = new PaymentSystemDao();
    }

    public static PaymentSystemDao getInstance() {
        return PaymentSystemDaoHolder.instance;
    }
	@Override
	public List<PaymentSystem> readAll() throws MyDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(int id) throws MyDAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int create(PaymentSystem bean) throws MyDAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(PaymentSystem bean) throws MyDAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PaymentSystem read(int id) throws MyDAOException {
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs= null;
		PaymentSystem pSyst = null;
		try {
			 pst = connection.prepareStatement(Resource.getStr("sql.get.paymentSystem.by.id"));
			pst.setInt(1, id);
			 rs = pst.executeQuery();
			if (rs.next()) {
				pSyst = new PaymentSystem();
				pSyst.setId(rs.getInt(1));
				pSyst.setName(rs.getString(2));;
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in PaymentSystemDao.read", e);
            throw new MyDAOException("ERROR_PAYMENTSYSTEM_READ", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}
		return pSyst;
	}

}
