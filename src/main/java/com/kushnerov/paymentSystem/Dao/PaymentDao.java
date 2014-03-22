package  com.kushnerov.paymentSystem.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import  com.kushnerov.paymentSystem.entities.Client;
import  com.kushnerov.paymentSystem.entities.Payment;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class PaymentDao extends AbstractDao<Payment> {

	private PaymentDao() {
	}

	private static class PaymentDaoHolder {

		public static final PaymentDao instance = new PaymentDao();
	}

	public static PaymentDao getInstance() {
		return PaymentDaoHolder.instance;
	}

	private List<Payment> setEntity(ResultSet rs) throws MyDAOException {
		List<Payment> payments = new ArrayList<Payment>();
		try {
			while (rs.next()) {
				Payment payment = new Payment();
				payment.setId(rs.getInt(1));
				payment.setDate(rs.getDate(2));
				AccountDao accDao = AccountDao.getInstance();
				payment.setRemitter(accDao.read(rs.getInt(3)));
				payment.setReceiver(accDao.read(rs.getInt(4)));
				payment.setAmount(rs.getDouble(5));
				payment.setPaymentDetails((rs.getString(6)));
				payments.add(payment);
			}
			return payments;
		} catch (SQLException ex) {
			ex.printStackTrace();

			logger.log(Level.ERROR, "Exception in PaymentDAO.setEntity", ex);
			throw new MyDAOException("ERROR_PAYMENT_READ", ex);
		}
	}

	@Override
	public List<Payment> readAll() throws MyDAOException {
		List<Payment> payments = new ArrayList<Payment>();
		Statement statement = null;
		Connection connection = getConnection();
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("sql.get.all.payments");
			payments = setEntity(rs);
		} catch (SQLException ex) {
			logger.log(Level.ERROR, "Exception in PaymentDao.readAll", ex);
			throw new MyDAOException("ERROR_PAYMENT_READ", ex);
		} finally {
			close(rs);
			close(statement);
			close(connection);
		}
		return payments;
	}

	@Override
	public Payment read(int id) throws MyDAOException {
		Payment payment = null;
		PreparedStatement pst = null;
		Connection connection = getConnection();
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.payment.by.id"));
			pst.setInt(1, id);
			rs = pst.executeQuery();
			List<Payment> payments = setEntity(rs);
			if (payments.size() > 0) {
				payment = payments.get(0);
			}
		} catch (SQLException ex) {
			logger.log(Level.ERROR, "Exception in PaymentDAO.read", ex);
			throw new MyDAOException("ERROR_PAYMENT_READ", ex);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}
		return payment;
	}

	@Override
	public int create(Payment payment) throws MyDAOException {
		PreparedStatement pst = null;
		Connection connection = getConnection();
		int res = 0;
		try {
			System.out.println(connection.getTransactionIsolation());
			System.out.println(Connection.TRANSACTION_REPEATABLE_READ);
			connection.setAutoCommit(false); // new tranzaction
			//update client account
			pst = connection.prepareStatement(Resource
					.getStr("sql.update.account.balance.client"));
			pst.setDouble(1, payment.getAmount());
			pst.setInt(2, payment.getRemitter().getId());
			pst.executeUpdate();
			pst.close();
			//update company account
			pst = connection.prepareStatement(Resource
					.getStr("sql.update.account.balance.company"));
			pst.setDouble(1, payment.getAmount());
			pst.setInt(2, payment.getReceiver().getId());
			pst.executeUpdate();
			pst.close();
			//insert data to payments table
			pst = connection.prepareStatement(Resource
					.getStr("sql.create.payment"));
			pst.setDate(1, payment.getDate());
			pst.setInt(2, payment.getRemitter().getId());
			pst.setInt(3, payment.getReceiver().getId());
			pst.setDouble(4, payment.getAmount());
			pst.setString(5, payment.getPaymentDetails());
			res = pst.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException ex) {
			logger.log(Level.ERROR, "Exception in PaymentsDAO.update", ex);
			ex.printStackTrace();
			try {
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException e2) {
				logger.log(Level.ERROR,
						"Exception in PaymentsDAO.update while rollback", ex);
				throw new MyDAOException("ERROR_CREATE_PAYMENT", ex);
			}
			throw new MyDAOException("ERROR_CREATE_PAYMENT", ex);
		} finally {
			close(pst);
			close(connection);
		}
		return res;
	}

	@Override
	public int update(Payment payment) {
		return 0;

	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Payment> readAllByClient(Client client) throws MyDAOException {
		List<Payment> payments = new ArrayList<Payment>();
		Connection connection = getConnection();
		PreparedStatement pst = null;

		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.all.payments.by.client"));
			pst.setInt(1, client.getId());
			rs = pst.executeQuery();
			payments = setEntity(rs);
		} catch (SQLException ex) {
			ex.printStackTrace();

			logger.log(Level.ERROR, "Exception in PaymentDao.readAllByCLient",
					ex);
			throw new MyDAOException("ERROR_PAYMENT_READ_BY_CLIENT", ex);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}
		return payments;
	}

}
