package com.kushnerov.paymentSystem.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import  com.kushnerov.paymentSystem.exceptions.MyCommandException;
import  com.kushnerov.paymentSystem.resource.Resource;

public class LogoutCommand implements Command {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws MyCommandException {
		request.getSession().removeAttribute("user");
//		request.getSession().invalidate();
		return Resource.getStr("forward.login");
	}

}
