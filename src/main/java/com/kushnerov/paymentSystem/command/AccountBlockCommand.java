package com.kushnerov.paymentSystem.command;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.kushnerov.paymentSystem.Dao.AccountDao;
import com.kushnerov.paymentSystem.entities.Account;
import com.kushnerov.paymentSystem.entities.Card;
import  com.kushnerov.paymentSystem.entities.Client;
import  com.kushnerov.paymentSystem.exceptions.MyCommandException;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class AccountBlockCommand implements Command {

	private static final Logger logger = Logger
			.getLogger(AccountBlockCommand.class.getName());

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws MyCommandException {
		Client client = (Client) request.getSession().getAttribute("user");
		Set<Account> accounts = new HashSet<Account>();
		AccountDao accDao = AccountDao.getInstance();
		for (Card card : client.getCards()) {

			try {
				accounts.add(accDao.read(card.getAccount().getId()));
			} catch (MyDAOException e) {
				logger.log(Level.ERROR,
						"Exception in AccountBlockCommand2.execute", e);
				throw new MyCommandException(e.getMessage(), e);
			}
			///
		}
		request.setAttribute("accounts", accounts);
		return Resource.getStr("forward.account_block");
	}
}
