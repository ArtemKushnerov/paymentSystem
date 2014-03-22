package com.kushnerov.paymentSystem.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.Dao.AccountDao;
import  com.kushnerov.paymentSystem.entities.Account;
import  com.kushnerov.paymentSystem.exceptions.MyCommandException;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class AdminLoginCommand implements Command {
	private static final Logger logger = Logger.getLogger(AdminLoginCommand.class
			.getName());

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws MyCommandException {
		AccountDao accDao = AccountDao.getInstance();
		List<Account> accounts = null;
		try {
			accounts = accDao.readAll();
		} catch (MyDAOException e) {
            logger.log(Level.ERROR, "Exception in AdminLoginCommand.execute", e);
            throw new MyCommandException(e.getMessage(), e);
		}
		request.setAttribute("accounts", accounts);
		return Resource.getStr("forward.index_admin");
	}

}
