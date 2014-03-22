package  com.kushnerov.paymentSystem.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import  com.kushnerov.paymentSystem.entities.Card;
import  com.kushnerov.paymentSystem.entities.Client;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class CardDao extends AbstractDao<Card> {

	private CardDao() {
	}

	private static class CardDaoHolder {

		public static final CardDao instance = new CardDao();
	}

	public static CardDao getInstance() {
		return CardDaoHolder.instance;
	}

	private List<Card> setEntity(ResultSet rs) throws MyDAOException {
		List<Card> cards = new ArrayList<Card>();
		try {
			while (rs.next()) {
				Card card = new Card();
				card.setId(rs.getInt(1));
				ClientDao clientDao = ClientDao.getInstance();
				card.setCardHolder(clientDao.read(rs.getInt(2)));
				card.setNumber(rs.getLong(3));
				card.setExpirationDate(rs.getDate(4).toString());
				AccountDao accDao = AccountDao.getInstance();
				card.setAccount(accDao.read(rs.getInt(5)));
				PaymentSystemDao pstDao =  PaymentSystemDao.getInstance();
				card.setPaymentSystem(pstDao.read(rs.getInt(6)));
				card.setType(rs.getString(7));
				cards.add(card);
			}
			return cards;
		} catch (SQLException ex) {
			logger.log(Level.ERROR, "Exception in CardDao.setEntity", ex);
			throw new MyDAOException("ERROR_CARD_INITIALIZE", ex);
		}
	}

	@Override
	public int create(Card bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Card bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Card read(int id) throws MyDAOException {
		Card card = null;

		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection connection = getConnection();
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.card.by.id"));
			pst.setInt(1, id);
			rs = pst.executeQuery();
			List<Card> cards = setEntity(rs);
			if (cards.size() > 0) {
				card = cards.get(0);
			}
		} catch (SQLException ex) {
			logger.log(Level.ERROR, "Exception in CardDAO.read", ex);
			throw new MyDAOException("ERROR_CARD_INITIALIZE", ex);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}
		return card;
	}

	@Override
	public List<Card> readAll() throws MyDAOException {
		List<Card> cardList = new ArrayList<Card>();
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.all.cards"));
			rs = pst.executeQuery();
			cardList = setEntity(rs);
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in CardDao.readAll", e);
			throw new MyDAOException("ERROR_CARD_READ_ALL", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}

		return cardList;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Card> readAll(Client client) throws MyDAOException {
		List<Card> cardList = new ArrayList<Card>();
		Connection connection = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(Resource
					.getStr("sql.get.all.cards.by.client"));
			pst.setInt(1, client.getId());
			rs = pst.executeQuery();
			cardList = setEntity(rs);
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Exception in CardDao.readAllByClient", e);
			throw new MyDAOException("ERROR_CARD_READ_ALL_BY_CLIENT", e);
		} finally {
			close(rs);
			close(pst);
			close(connection);
		}

		return cardList;
	}

}
