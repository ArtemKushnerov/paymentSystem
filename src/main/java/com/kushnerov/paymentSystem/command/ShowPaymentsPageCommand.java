package com.kushnerov.paymentSystem.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.Dao.CompanyDao;
import  com.kushnerov.paymentSystem.entities.Company;
import  com.kushnerov.paymentSystem.exceptions.MyCommandException;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class ShowPaymentsPageCommand implements Command{
	 private static final Logger logger = Logger.getLogger(ShowPaymentsPageCommand.class.getName());
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws MyCommandException {
		List<Company> companies;
		CompanyDao companyDao = CompanyDao.getInstance();
		try {
			companies = companyDao.readAll();
		} catch (MyDAOException e) {
			 logger.log(Level.ERROR, "Exception in PaymentsShowCommand.execute", e);
	            throw new MyCommandException(e.getMessage(), e);
		}
		request.setAttribute("companies", companies);
		return Resource.getStr("forward.payments");
	}

}
