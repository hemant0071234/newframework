package driver;

import constants.Browser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.internal.ElementScrollBehavior;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.PropertyUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DriverManager
{
	private static Logger log = Logger.getLogger(DriverManager.class);

	public static WebDriver getWebDriver()
	{
		Browser browser = PropertyUtils.getBrowser();
		WebDriver driver = getWebdriver(browser);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.setProperty("webdriver.timeouts.implicitlywait", "30");

		return driver;
	}

	private static WebDriver getWebdriver(Browser browser)
	{
		log.info(String.format("Starting '%s' browser", browser));

		switch (browser) {
			case FIREFOX:

				WebDriverManager.firefoxdriver().setup();
				DesiredCapabilities cap = getFireFoxDesiredCapabilities();

				return new FirefoxDriver(cap);

			case OPERA:

				WebDriverManager.operadriver().setup();

				return new OperaDriver();

			case EDGE:

				if (System.getProperty("os.name").contains("Windows")) {
					log.info("Client Machine is windows");
					WebDriverManager.edgedriver().setup();

					return new EdgeDriver();
				} else{
					log.info("Client Machine is not windows");
				}

			case CHROME:

				WebDriverManager.chromedriver().setup();

				DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
				ChromeOptions option = new ChromeOptions();
				Map<String,Object> pref = new HashMap<>();
				pref.put("download.prompt_for_download", false);
				pref.put("download.default_directory", getDownloadDirectory());
				option.setExperimentalOption("prefs", pref);
				option.addArguments("--start-maximized");
				chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, option);

				return new ChromeDriver(chromeCapabilities);

			default:
				throw new UnsupportedOperationException("Attempt to start invalid browser " + browser);
		}
	}


	private static DesiredCapabilities getFireFoxDesiredCapabilities()
	{
		String neverAskSaveToDiskAndOpenFileValues = "application/octet-stream, application/x-zip-compressed, " +
				"application/zip-compressed, application/zip, multipart/x-zip, application/x-compressed, " +
				"application/msword, text/plain, image/gif, image/png, application/pdf, application/excel, " +
				"application/vnd.ms-excel, application/x-excel, application/x-msexcel, text/csv";
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("app.update.enabled", false);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.dir", getDownloadDirectory());
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.panel.shown", true);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", neverAskSaveToDiskAndOpenFileValues);
		profile.setPreference("browser.helperApps.neverAsk.openFile", neverAskSaveToDiskAndOpenFileValues);
		DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
		firefoxCapabilities.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, ElementScrollBehavior.BOTTOM);
		firefoxCapabilities.setCapability(FirefoxDriver.PROFILE, profile);
		return firefoxCapabilities;
	}

	private static String getDownloadDirectory() {
		return System.getProperty("user.dir") + File.separator + "target";
	}
}