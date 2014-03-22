package com.kushnerov.paymentSystem.command;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.Dao.CardDao;
import  com.kushnerov.paymentSystem.Dao.PaymentDao;
import  com.kushnerov.paymentSystem.entities.Account;
import  com.kushnerov.paymentSystem.entities.Card;
import  com.kushnerov.paymentSystem.entities.Client;
import  com.kushnerov.paymentSystem.entities.Payment;
import  com.kushnerov.paymentSystem.entities.Service;
import  com.kushnerov.paymentSystem.exceptions.MyCommandException;
import  com.kushnerov.paymentSystem.exceptions.MyDAOException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class MakePaymentCommand implements Command {
	private static final Logger logger = Logger
			.getLogger(MakePaymentCommand.class.getName());

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws MyCommandException {
		Payment payment = new Payment();
		String messagePay = Resource.getStr("forward.message_pay");
		Client client = (Client) request.getSession().getAttribute("user");
		int cardId = Integer.parseInt(request.getParameter("Card"));
		if (request.getParameter("sum").equals("")) {
			return Resource.getStr("forward.payments3");
		}
		double amount = Double.parseDouble(request.getParameter("sum"));
		System.out.println(amount);
		for (Card card : client.getCards()) {
			if (cardId == card.getId()) {
				payment.setRemitter(card.getAccount());
				Account account = card.getAccount();
				if (account.isBlocked()) {
					request.setAttribute("message", "account_is_locked");
					return messagePay;
				}
				if (account.getBalance() < amount) {
					request.setAttribute("message", "not_enough_money");
					return messagePay;
				}
			}
		}
		try {
			payment.setAmount(amount);
		} catch (NumberFormatException ex) {
			logger.log(Level.INFO, "Exception validate summ", ex);
			return Resource.getStr("forward.payments3");
		}
		Service selectedService = (Service) request.getSession().getAttribute(
				"selectedService");
		payment.setReceiver(selectedService.getAccount());
		payment.setPaymentDetails((selectedService.getCompany().getName() + "/" + selectedService
				.getName()));
		payment.setDate(new Date(System.currentTimeMillis()));
		PaymentDao paymentDao = PaymentDao.getInstance();
		try {
			paymentDao.create(payment);
			CardDao cardDAO = CardDao.getInstance();
			client.setCards(cardDAO.readAll(client));
			request.getSession().setAttribute("user", client);
		} catch (MyDAOException e) {
			logger.log(Level.ERROR, "Exception in MakePaymentCommand.execute",
					e);
			throw new MyCommandException(e.getMessage(), e);
		}
		request.setAttribute("message", "successful_payment");
		return messagePay;
	}
}
