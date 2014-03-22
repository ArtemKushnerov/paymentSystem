package com.kushnerov.paymentSystem.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.Dao.PaymentDao;
import  com.kushnerov.paymentSystem.entities.Client;
import  com.kushnerov.paymentSystem.entities.Payment;
import  com.kushnerov.paymentSystem.exceptions.MyCommandException;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class PaymentHistoryCommand implements Command {

	static final Logger logger = Logger.getLogger(PaymentHistoryCommand.class
			.getName());

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws MyCommandException {
		Client client = (Client) request.getSession().getAttribute("user");
		PaymentDao pDao = PaymentDao.getInstance();
		/*int resultsPerPage = Integer.parseInt(request
				.getParameter("resultsPerPage"));
		int page = Integer.parseInt(request.getParameter("page"));
		int numOfPages = 0;*/
		List<Payment> payments = null;
		try {
			payments = pDao.readAllByClient(client);
			/*numOfPages = payments.size() / resultsPerPage
					+ (payments.size() % resultsPerPage == 0 ? 0 : 1);
			int startIndex = page == 1 ? 0 : ((page-1) * resultsPerPage + 1);
			int n = resultsPerPage * page;
			int endIndex = n < (payments.size()-1) ? n : (payments.size()-1);
			payments = payments.subList(startIndex, endIndex);*/
		} catch (MyDAOException e) {
			logger.log(Level.ERROR,
					"Exception in PaymentHistoryCommand.execute", e);
			throw new MyCommandException(e.getMessage(), e);
		}
	/*	request.setAttribute("currentPage", page);
		request.setAttribute("numOfPages", numOfPages);*/
		request.setAttribute("payments", payments);
		return Resource.getStr("forward.payments_history");
	}
}
