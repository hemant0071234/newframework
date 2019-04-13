package utils;

import constants.Browser;
import org.testng.log4testng.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {

	private static Logger log = Logger.getLogger(PropertyUtils.class);
	private static final String PROPERTY_FILE_NAME = "config.properties";
	private static final Properties PROPERTIES = getProperties();

	private static synchronized Properties getProperties()
	{
		try
		{
			InputStream inputStream = new BufferedInputStream(new FileInputStream(PROPERTY_FILE_NAME));
			Properties properties = new Properties();
			properties.load(inputStream);
			return properties;
		}
		catch(IOException e)
		{

			log.warn("Error reading the properties file", e);
			throw new RuntimeException("Error loading the properties file "+e);
		}
	}

	public static Browser getBrowser()
	{
		return Browser.valueOf((System.getProperty("browser", PROPERTIES.getProperty("browser")).toUpperCase()));
	}


	public static String getsAppUrl()
	{
		return System.getProperty("appsUrl", PROPERTIES.getProperty("appsUrl"));
	}


	public static long getDefaultTimeOutForElement()
	{
		return Long.parseLong(System.getProperty("defaultTimeoutForElement", PROPERTIES.getProperty("defaultTimeoutForElement")));
	}

	public static long getRetryCount()
	{
		return Long.parseLong(System.getProperty("retyCount", PROPERTIES.getProperty("retyCount")));
	}

}
