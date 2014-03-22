package com.kushnerov.paymentSystem.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.Dao.AccountDao;
import  com.kushnerov.paymentSystem.entities.Account;
import  com.kushnerov.paymentSystem.exceptions.MyCommandException;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;

public class RealeseBlockCommand implements Command {

    private static final Logger logger = Logger.getLogger(RealeseBlockCommand.class.getName());

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws MyCommandException {
		int accountId = Integer.parseInt(request.getParameter("accountid"));
		AccountDao accDao = AccountDao.getInstance();
		
		try {
			Account acc = accDao.read(accountId);
			acc.setBlocked(false);
			accDao.update(acc);
		} catch (MyDAOException ex) {
			logger.log(Level.ERROR, "Exeption in CommandAccountUnLock.execute", ex);
            throw new MyCommandException(ex.getMessage(), ex);		}
		
		return CommandFactory.getCommand("adminlogin").execute(request, response);
	}
}
