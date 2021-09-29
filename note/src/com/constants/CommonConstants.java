package com.constants;

import com.pardis.cfg.RegistryInterface;
import com.pardis.common.ConstantManager;


/**
 * Keeps the common constants of this project. All constants of this project are
 * moved into properties file of this project. Only the path of project's
 * properties file is remained(the path is specified from root path of
 * registry).
 */
public class CommonConstants {
	public static final String PROPERTIES_FILE = "/notebook/notebook.properties";

	public static void createPropertiesFile() {
		RegistryInterface reg = ConstantManager.getInstance().getRegistry();

	}

	public static void main(String[] args) {
		createPropertiesFile();
	}

	

}
