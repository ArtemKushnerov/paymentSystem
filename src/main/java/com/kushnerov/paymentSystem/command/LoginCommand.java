package com.kushnerov.paymentSystem.command;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.Dao.CardDao;
import  com.kushnerov.paymentSystem.Dao.ClientDao;
import  com.kushnerov.paymentSystem.entities.Client;
import  com.kushnerov.paymentSystem.exceptions.MyCommandException;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class LoginCommand implements Command {
	private static final Logger logger = Logger.getLogger(LoginCommand.class
			.getName());
	

	private String encryptPassword(String pass) throws MyCommandException {
		MessageDigest md;
		String passwordHash = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(pass.getBytes());
			passwordHash = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException ex) {
			logger.log(Level.ERROR,
					"Exception encrypt in LoginCommand.execute", ex);
			throw new MyCommandException("ERROR_ENCRYPT_SYSTEM", ex);
		}
		return passwordHash;
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws MyCommandException {
		Client client = null;
		String passwordHash = encryptPassword(request.getParameter("password"));
		String username = request.getParameter("login");
		ClientDao dao = ClientDao.getInstance();
		try {
			client = dao.read(username, passwordHash);
		} catch (MyDAOException ex) {
			logger.log(Level.ERROR, "Exception in LoginCommand.execute", ex);
			throw new MyCommandException(ex.getMessage(), ex);
		}
		String page = null;
		if (client == null) {
			page = Resource.getStr("forward.login");
//			request.setAttribute("login_message", "wrong_username_or_password");
		} else if (client.getRole().getDescription().equals("user")) {
			page = Resource.getStr("forward.index_user");
			CardDao cardDao = CardDao.getInstance();
			try {
				client.setCards(cardDao.readAll(client));
			} catch (MyDAOException e) {
				logger.log(Level.ERROR, "Exception in LoginCommand.execute", e);
				throw new MyCommandException(e.getMessage(), e);
			}
		} else if (client.getRole().getDescription().equals("admin")) {
            Command command = CommandFactory.getCommand("adminlogin");
            page = command.execute(request, response);
		}
		request.getSession().setAttribute("user", client);
		return page;
	}
}
