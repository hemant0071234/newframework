package driver;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PropertyUtils;

import java.util.List;

public class WebUtils {

    public static void moveToElementAndClick(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);

        actions.moveToElement(element).click().perform();
    }

    public static void moveToElement(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);

        actions.
                moveToElement(element)
                .perform();
    }

    public static void fill(WebElement element, String value) {
        if (!isElementDisplayed(element)) {
            throw new ElementNotVisibleException(element + " is not visible");
        }
        element.clear();

        element.sendKeys(value);
    }

    public static boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public static void waitForElementToBeDisplayed(WebDriver driver, WebElement element, long timeout) {
        try {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));

        } catch (TimeoutException e) {
            throw new ElementNotVisibleException("Timeout" + element + " is not visible/present.");
        }
    }

    public static void waitForElementsToBeDisplayed(WebDriver driver, List<WebElement> elements, long timeout) {
        try {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfAllElements(elements));

        } catch (TimeoutException e) {
            throw new ElementNotVisibleException("Timeout" + elements + " are not visible/present.");
        }
    }

    private static void waitForElementToBeClickable(WebDriver driver, WebElement element, long timeout) {
        try {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(element));

        } catch (TimeoutException e) {
            throw new ElementNotVisibleException("Timeout" + element + " is not visible/present.");
        }
    }


    public static void waitForPageLoad(WebDriver driver) {
        new WebDriverWait(driver, 120).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public static void clickWithWaitForElement(WebDriver wd, WebElement elem, long timeout) {
        WebUtils.waitForElementToBeDisplayed(wd, elem, timeout);
        elem.click();
    }


    public static void clickWithWaitForElement(WebDriver wd, WebElement elem) {
        WebUtils.waitForElementToBeDisplayed(wd, elem, PropertyUtils.getDefaultTimeOutForElement());
        WebUtils.waitForElementToBeClickable(wd, elem, PropertyUtils.getDefaultTimeOutForElement());
        elem.click();
    }


    public static String getTextValue(WebDriver wd, WebElement elem) {
        WebUtils.waitForElementToBeDisplayed(wd, elem, PropertyUtils.getDefaultTimeOutForElement());
        return elem.getText();
    }

    public static void scrollToElement(WebDriver wd, WebElement elem) {
        ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView();", elem);
    }



}
