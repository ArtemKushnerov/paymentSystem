package com.kushnerov.paymentSystem.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.Dao.AccountDao;
import  com.kushnerov.paymentSystem.Dao.CardDao;
import  com.kushnerov.paymentSystem.entities.Account;
import  com.kushnerov.paymentSystem.entities.Card;
import  com.kushnerov.paymentSystem.entities.Client;
import  com.kushnerov.paymentSystem.exceptions.MyCommandException;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;

public class AccountBlockCommand2 implements Command {

    private static final Logger logger = Logger.getLogger(AccountBlockCommand2.class.getName());

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws MyCommandException {
		Client client = (Client) request.getSession().getAttribute("user");
		int accountId = Integer.parseInt(request.getParameter("accountid"));
		AccountDao accDao =  AccountDao.getInstance();
		CardDao cardDao = CardDao.getInstance();
		try {
			Account acc = accDao.read(accountId);
			acc.setBlocked(true);
			accDao.update(acc);
			List<Card> cards = cardDao.readAll(client);
			for (Card card : cards ) {
			System.out.println(card.getAccount().isBlocked());	
			}
			client.setCards(cards);
			request.getSession().setAttribute("user", client);
		} catch (MyDAOException e) {
			logger.log(Level.ERROR, "Exception in AccountBlockCommand2.execute ",e);
			throw new MyCommandException(e.getMessage(),e);
		}
		return CommandFactory.getCommand("accountblock").execute(request, response);
	}

}
