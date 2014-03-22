package  com.kushnerov.paymentSystem.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import  com.kushnerov.paymentSystem.DBinit.PropertiesHolder;
import  com.kushnerov.paymentSystem.DBinit.PropertiesManager;
import  com.kushnerov.paymentSystem.command.Command;
import  com.kushnerov.paymentSystem.command.CommandFactory;
import  com.kushnerov.paymentSystem.exceptions.PaySysException;
import  com.kushnerov.paymentSystem.pool.ConnectionPool;
import  com.kushnerov.paymentSystem.resource.Resource;

public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(Controller.class
			.getName());

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			Command command = CommandFactory.getCommand(request
					.getParameter("command"));
			page = command.execute(request, response);
		} catch (PaySysException e) {
			logger.log(Level.ERROR, "PaySysException:", e);
			request.setAttribute("errorMessage", e.getMessage());
			page = Resource.getStr("forward.errorex");
		}
		if (page.equals("/login.jsp"))
			response.sendRedirect("");
		else
			request.getRequestDispatcher(page).forward(request, response);
	}

	@Override
	public void init() throws ServletException {
		//configure log4j
        ServletContext context = getServletContext();
        PropertiesManager propertiesManager = PropertiesManager.getInstance();
        //DOMConfigurator.configure(context.getRealPath("log4j.xml"));
		try {
            PropertiesHolder propertiesHolder = propertiesManager.getProperty(context);
			ConnectionPool.getInstance().init(propertiesHolder);
		} catch (PaySysException e) {
			logger.log(Level.ERROR, "Exception in Servlet.init:", e);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	 public void destroy() {
	        super.destroy(); 
	        ConnectionPool databasePool = ConnectionPool.getInstance();
	        databasePool.destroy();
	    }
}
