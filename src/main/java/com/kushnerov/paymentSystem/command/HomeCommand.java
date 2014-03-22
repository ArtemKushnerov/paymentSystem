package com.kushnerov.paymentSystem.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.Dao.CardDao;
import  com.kushnerov.paymentSystem.entities.Client;
import com.kushnerov.paymentSystem.exceptions.MyCommandException;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class HomeCommand implements Command {
	private static final Logger logger = Logger.getLogger(LoginCommand.class
			.getName());

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws MyCommandException {
		Client client = (Client) request.getSession().getAttribute("user");
		CardDao cardDao = CardDao.getInstance();
		try {
			client.setCards(cardDao.readAll(client));
		} catch (MyDAOException e) {
			logger.log(Level.ERROR, "Exception in HomeCommand.execute", e);
			throw new MyCommandException(e.getMessage(), e);
		}
		request.getSession().setAttribute("user", client);
		return Resource.getStr("forward.index_user");
	}

}
