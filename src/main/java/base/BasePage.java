package base;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.PropertyUtils;

public abstract class BasePage<T extends BasePage> {
    protected WebDriver wd;
    String appsUrl = PropertyUtils.getsAppUrl();
    protected Logger log = Logger.getLogger(getClass());

    public abstract String getURL();


    public BasePage(WebDriver driver) {
        this(driver, false);
    }

    public BasePage(WebDriver driver, boolean waitForLoad) {
        wd = driver;
        PageFactory.initElements(wd, this);

    }

    public void acceptAlertIfPresent(WebDriver wd) {
        try {

            wd.switchTo().alert().accept();
        } catch (NoAlertPresentException ignored) {
            // TODO: handle exception
        }
    }

    public T navigateTo() {
        log.info(String.format("Navigating to url - '%s'", getURL()));
        String url = PropertyUtils.getsAppUrl() + getURL();
        if (wd.getCurrentUrl().equals(url)) {
            wd.navigate().refresh();
        }
        wd.get(url);
        acceptAlertIfPresent(wd);
        return (T) this;
    }

}
