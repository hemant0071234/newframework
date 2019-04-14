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

public class DriverManager {
    private static Logger log = Logger.getLogger(DriverManager.class);

    public static WebDriver getWebDriver() {
        Browser browser = PropertyUtils.getBrowser();
        WebDriver driver = getWebdriver(browser);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.setProperty("webdriver.timeouts.implicitlywait", "30");

        return driver;
    }

    private static WebDriver getWebdriver(Browser browser) {
        log.info(String.format("Starting '%s' browser", browser));

        switch (browser) {
            case FIREFOX:

                WebDriverManager.firefoxdriver().setup();

                return new FirefoxDriver();

            case OPERA:

                WebDriverManager.operadriver().setup();

                return new OperaDriver();

            case EDGE:

                if (System.getProperty("os.name").contains("Windows")) {
                    log.info("Client Machine is windows");
                    WebDriverManager.edgedriver().setup();

                    return new EdgeDriver();
                } else {
                    log.info("Client Machine is not windows");
                }

            case CHROME:

                WebDriverManager.chromedriver().setup();

                return new ChromeDriver();

            default:
                throw new UnsupportedOperationException("Attempt to start invalid browser " + browser);
        }
    }

}