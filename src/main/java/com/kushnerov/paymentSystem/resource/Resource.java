package  com.kushnerov.paymentSystem.resource;

import java.util.ResourceBundle;

public class Resource {

	public static final String RESOURCE_PATH = "i18n.Resource";

	private static ResourceBundle resource = ResourceBundle
			.getBundle(RESOURCE_PATH);

	public static String getStr(String key) {
		return resource.getString(key);
	}

}
