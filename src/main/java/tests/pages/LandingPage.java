package tests.pages;

        import base.BasePage;
        import driver.WebUtils;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.FindBy;

        import java.util.ArrayList;
        import java.util.List;

public class LandingPage extends BasePage<LandingPage> {

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getURL() {
        return "/";
    }

    @FindBy(xpath = "//div[@class='footer__block--contact']//a[normalize-space()='Contact us']")
    private WebElement linkContactUs;

    @FindBy(xpath = "//ul[@id='menu-main-menu']//li/a[text()='Contact']")
    private WebElement mnuContact;

    public boolean isLandingPageLoaded(){

        List<WebElement> allElements = new ArrayList<WebElement>();

        allElements.add(linkContactUs);
        allElements.add(mnuContact);

        try {
            WebUtils.waitForElementsToBeDisplayed(wd, allElements, 30);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    public void gotoContactPageByContactUsLink(){

        WebUtils.moveToElementAndClick(wd, linkContactUs);
    }

    public void gotoContactPageByContactMenu(){

        WebUtils.clickWithWaitForElement(wd, mnuContact);
    }
}
