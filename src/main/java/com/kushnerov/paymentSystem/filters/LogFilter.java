package com.kushnerov.paymentSystem.filters;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import  com.kushnerov.paymentSystem.entities.Client;



public class LogFilter implements Filter {
	private static final Logger logger = Logger.getLogger(LogFilter.class.getName());
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getServletPath();
        Client client = (Client) req.getSession().getAttribute("user");

        if (!"/jsp/login.jsp".equals(url)) {
            if (client==null) {
            	logger.log(Level.INFO, req.getRemoteHost() + " tried to access "
                        + req.getRequestURL() +
                        " on " + new Date() + ". ");   	
            } else {
            	logger.log(Level.INFO, client.getEmail() + " access to "
                        + req.getParameter("command") + " from "
                        + req.getRemoteHost() + " on " + new Date() + ". ");
            }
        }
        chain.doFilter(request, response);
    }


	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	
}
