package com.kushnerov.paymentSystem.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.Dao.ServiceDao;
import  com.kushnerov.paymentSystem.entities.Service;
import  com.kushnerov.paymentSystem.exceptions.MyCommandException;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class StartPaymentCommand implements Command {

	static final Logger logger = Logger.getLogger(SelectServiceCommand.class
			.getName());

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws MyCommandException {
		int serviceId = Integer.parseInt(request.getParameter("Service"));
		ServiceDao servDao = ServiceDao.getInstance();
		Service service = null;
		try {
			service = servDao.read(serviceId);
		} catch (MyDAOException e) {
			logger.log(Level.ERROR, "Exception in StartPaymentCommand.execute",
					e);
			throw new MyCommandException(e.getMessage(), e);
		}
		request.getSession().setAttribute("selectedService", service);
		return Resource.getStr("forward.payments3");
	}

}
