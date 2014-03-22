package com.kushnerov.paymentSystem.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import  com.kushnerov.paymentSystem.exceptions.MyCommandException;

public interface Command {

	String execute(HttpServletRequest request, HttpServletResponse response) throws MyCommandException;
}
