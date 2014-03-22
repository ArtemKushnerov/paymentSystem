package com.kushnerov.paymentSystem.command;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

	public static Map<String, Command> commands = new HashMap<String, Command>();

	static {
		commands.put("login", new LoginCommand());
		commands.put("adminlogin", new AdminLoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("accountblock", new AccountBlockCommand());
		commands.put("accountblock2", new AccountBlockCommand2());
		commands.put("home", new HomeCommand());
		commands.put("paymentshistory", new PaymentHistoryCommand());
		commands.put("paymentsshow", new ShowPaymentsPageCommand());
		commands.put("selectService", new SelectServiceCommand());
		commands.put("startPayment", new StartPaymentCommand());
		commands.put("makePayment", new MakePaymentCommand());
		commands.put("releaseblock", new RealeseBlockCommand());
		commands.put("topupaccount", new TopUpAccountCommand());
		commands.put("topupaccount2", new TopUpAccountCommand2());
	}

	public static Command getCommand(String command) {

		return commands.get(command);
	}

}
