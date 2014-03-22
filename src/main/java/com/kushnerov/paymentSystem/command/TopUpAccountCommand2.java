package com.kushnerov.paymentSystem.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.Dao.AccountDao;
import  com.kushnerov.paymentSystem.entities.Account;
import  com.kushnerov.paymentSystem.exceptions.MyCommandException;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class TopUpAccountCommand2 implements Command {
	private static final Logger logger = Logger
			.getLogger(MakePaymentCommand.class.getName());

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws MyCommandException {
		int accountId = Integer.parseInt(request.getParameter("Account"));
		if (request.getParameter("sum").equals("")) {
			return CommandFactory.getCommand("topupaccount").execute(request, response);
		}
		double sum = Double.parseDouble(request.getParameter("sum"));
		System.out.println(sum);
		
		AccountDao accDao = AccountDao.getInstance();
		try {
			Account acc = accDao.read(accountId);
			acc.setBalance(acc.getBalance() + sum);
			accDao.update(acc);
		} catch (MyDAOException e) {
			logger.log(Level.ERROR, "Exception in TopUpAccount2.execute", e);
			throw new MyCommandException(e.getMessage(), e);
		}
		request.setAttribute("message", "successful_top_up");
		return Resource.getStr("forward.message_pay");
	}

}
