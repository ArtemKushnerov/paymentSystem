package com.kushnerov.paymentSystem.filters;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kushnerov.paymentSystem.entities.Client;

public class AccessFilter implements Filter {

	private HashMap<String, String> commands = new HashMap<String, String>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		commands.put("login", "all");
		commands.put("adminlogin", "admin");
		commands.put("logout", "all");
		commands.put("accountblock", "user");
		commands.put("accountblock2", "user");
		commands.put("home", "user");
		commands.put("paymentshistory", "user");
		commands.put("paymentsshow", "user");
		commands.put("selectService", "user");
		commands.put("startPayment", "user");
		commands.put("makePayment", "user");
		commands.put("releaseblock", "admin");
		commands.put("topupaccount", "user");
		commands.put("topupaccount2", "user");
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		Client client = (Client) request.getSession().getAttribute("user");
		//String url = request.getServletPath();
		String command = request.getParameter("command");
        String url = request.getRequestURI().substring(request.getContextPath().length());
		if (url.equals("/login.jsp") || url.equals("/")
				|| url.equals("/css/bootstrap/css/bootstrap.min.css")
				|| command.equals("login")) {
			chain.doFilter(request, response);
			return;
		}
		if (client != null) {
			String expectedRole = commands.get(command);
			String realRole = client.getRole().getDescription();
			if (realRole.equals(expectedRole) || expectedRole.equals("all")) {
				chain.doFilter(request, response);
			} else
				//response.sendRedirect("/epam-paymentSystem/login.jsp");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
		} else
			//response.sendRedirect("/epam-paymentSystem/login.jsp");
        response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	public void destroy() {
	}

}
