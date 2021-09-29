package com.constants;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pardis.common.ConstantManager;

public class Constants {

	private static Logger logger = Logger.getLogger(Constants.class.getName());

	public static ConstantManager constantManager= ConstantManager.getInstance();

	public static final String PROPERTIES_FILE = "/notebook/notebook.properties";

	public static Integer CUSTOMER_NO_LENGTH = null;
	public static String PERSON_CUSTOMER_NO_BASE_OPTION = null;
	public static String PERSON_CUSTOMER_NO_TYPE = null;
	public static String SYSTEM_DOAMIN_NAME = null;
	public static String SYSTEM_APPLICATION_NAME = null;
	public static String CORRESPONDING_USER_APPLICATION_NAME = null;
	
	public static class SQL_CONNECTION {
	/*	
		public static String JDBC_CONNECTION_URL = constantManager
				.getString(
						PROPERTIES_FILE,
						CommonConstantsPropkeys.COM.PARDIS.ORGAN.JDBC_CONNECTION_URL);*/
		
		public static String DATASOURCENAME = constantManager
				.getString(
						PROPERTIES_FILE,
						CommonConstantsPropkeys.COM.PARDIS.ORGAN.DATASOURCENAME);
		
		public static String WL_DOMAIN = constantManager
				.getString(
						PROPERTIES_FILE,
						CommonConstantsPropkeys.COM.PARDIS.ORGAN.WL_DOMAIN);
		
	}

	public static class REPORT_TEMPLATE_PATH {
		public static String REPORT_TEMPLATE_PATH = constantManager
				.getString(
						PROPERTIES_FILE,
						CommonConstantsPropkeys.COM.PARDIS.REPORT.JASPER_REPORT_TEMPLATE_PATH);
		public static String GENERALREPORT_LIST = constantManager
				.getString(
						PROPERTIES_FILE,
						CommonConstantsPropkeys.COM.PARDIS.REPORT.GENERALREPORT_LIST);
		public static String FILE_UPLOAD_PATH = constantManager
				.getString(
						PROPERTIES_FILE,
						CommonConstantsPropkeys.COM.PARDIS.REPORT.FILE_UPLOAD_PATH);
		public static String MERCHANTREPORT_LIST = constantManager
				.getString(
						PROPERTIES_FILE,
						CommonConstantsPropkeys.COM.PARDIS.REPORT.MERCHANTREPORT_LIST);
		public static String GROUPREPORT_LIST = constantManager
				.getString(
						PROPERTIES_FILE,
						CommonConstantsPropkeys.COM.PARDIS.REPORT.GROUPREPORT_LIST);
	/*	public static String JDBC_CONNECTION_URL = constantManager
				.getString(
						PROPERTIES_FILE,
						CommonConstantsPropkeys.COM.PARDIS.ORGAN.JDBC_CONNECTION_URL);*/
		public static String GROUPCARDREPORT_LIST = constantManager
				.getString(
						PROPERTIES_FILE,
						CommonConstantsPropkeys.COM.PARDIS.REPORT.GROUPCARDREPORT_LIST);

		

	}
	
	
	static {

		constantManager.addObserver(CommonConstants.PROPERTIES_FILE,
				new Observer() {

			public void update(Observable o, Object arg) {
				logger.log(Level.INFO,
						"Reinitializing Org Constants from " + arg);
				initailizeConstantsFromConfigFile();
			}
		});
	}

	public static void initailizeConstantsFromConfigFile() {


	}


	private static Object loadConstant(String propName, Class clazz) {
		Object constant = null;
		if (String.class.equals(clazz))
			constant = constantManager.getString(
					CommonConstants.PROPERTIES_FILE, propName);
		else if (Integer.class.equals(clazz))
			constant = constantManager.getInteger(
					CommonConstants.PROPERTIES_FILE, propName);
		else if (Long.class.equals(clazz))
			constant = constantManager.getLong(
					CommonConstants.PROPERTIES_FILE, propName);
		if (constant == null) {
			logger.log(Level.WARNING, "Can't load constant \"" + propName
					+ "\" from file \""
					+  CommonConstants.PROPERTIES_FILE+ "\"");
		}
		return constant;
	}


	public static String getCorrespondingUserApplicationName(){
		if (CORRESPONDING_USER_APPLICATION_NAME == null)
			CORRESPONDING_USER_APPLICATION_NAME = (String) loadConstant(
					CommonConstantsPropkeys.COM.PARDIS.ORGAN.CORRESPONDING_USER__APPLICATION_NAME,
					String.class);
		return CORRESPONDING_USER_APPLICATION_NAME;
	}
	public static String getSystemDomainName(){
		if (SYSTEM_DOAMIN_NAME == null)
			SYSTEM_DOAMIN_NAME = (String) loadConstant(
					CommonConstantsPropkeys.COM.PARDIS.ORGAN.SYSTEM_DOAMIN_NAME,
					String.class);
		return SYSTEM_DOAMIN_NAME;
	}


	public static final String DEFAULT_PASSWORD = "123456";

	public static String leftPad(String source, int width, char replacement) {
		return String.format("%" + width + "s", source).replace(' ',
				replacement);
	}

}
