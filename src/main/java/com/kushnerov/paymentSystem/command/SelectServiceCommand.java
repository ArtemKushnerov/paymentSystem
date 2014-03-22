package com.kushnerov.paymentSystem.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.Dao.ServiceDao;
import  com.kushnerov.paymentSystem.entities.Service;
import  com.kushnerov.paymentSystem.exceptions.MyCommandException;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class SelectServiceCommand implements Command {
	static final Logger logger = Logger.getLogger(SelectServiceCommand.class
			.getName());

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws MyCommandException {
		if (request.getParameter("Company") == null) {
			return "jsp/payments.jsp";
		}
		int id = Integer.parseInt(request.getParameter("Company"));
		ServiceDao servDao = ServiceDao.getInstance();
		List<Service> services = null;
		try {
			services = servDao.readAllByCompany(id);
			System.out.println(services);
		} catch (MyDAOException e) {
			logger.log(Level.ERROR,
					"Exception in SelectServiceCommand.execute", e);
			throw new MyCommandException(e.getMessage(), e);
		}
		request.setAttribute("services", services);
		return Resource.getStr("forward.payments2");
	}

}
