package tests.pages;

import base.BasePage;
import driver.WebUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ContactUsPage extends BasePage<ContactUsPage> {

    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getURL() {
        return "/contact/";
    }

    @FindBy(id="first_name")
    private WebElement txtbxFirstName;

    @FindBy(id="last_name")
    private WebElement txtbxLastName;

    @FindBy(id="email")
    private WebElement txtbxEmailId;

    @FindBy(id="company")
    private WebElement txtbxCompany;

    @FindBy(id="URL")
    private WebElement txtbxCompanyURL;

    @FindBy(css="label.form__checkbox__icon")
    private WebElement chbkNewsLetters;

    @FindBy(xpath="//div[@class='flounder']")
    private WebElement selRole;

    @FindBy(css="div.flounder.flounder-country_code")
    private WebElement selCountry;

    @FindBy(id="phone__country-code")
    private WebElement txtbxCountryCode;

    @FindBy(id="phone__subscriber-number")
    private WebElement txtbxPhoneNumber;

    @FindBy(id="description")
    private WebElement txtbxDescription;

    @FindBy(id="submit-button")
    private WebElement btnSubmit;

    @FindBy(id="message--success")
    private WebElement lblMessageSuccess;

    @FindBy(css="div.form__consumer__message.js-form__consumer__message")
    private WebElement lblEmailContact;

    @FindBy(css = "span.contact__form__legal > a")
    private WebElement lnkPrivacyPolicy;

    @FindBy(css = "div.cookie-bar__cookie-warning__close-x.js-accept-cookies")
    private WebElement cookieClose;



    public boolean isContactUsPageoaded(){

        List<WebElement> allElements = new ArrayList<WebElement>();

        allElements.add(txtbxFirstName);
        allElements.add(txtbxLastName);
        allElements.add(txtbxEmailId);
        allElements.add(txtbxCompany);
        allElements.add(txtbxCompanyURL);
        allElements.add(chbkNewsLetters);
        allElements.add(selRole);
        allElements.add(selCountry);
        allElements.add(txtbxCountryCode);
        allElements.add(txtbxPhoneNumber);
        allElements.add(txtbxDescription);
        allElements.add(btnSubmit);
        allElements.add(lblEmailContact);

        try {
            WebUtils.waitForElementsToBeDisplayed(wd, allElements, 30);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    @Step("Create the contact request")
    public void contactUSTest(String firstName, String lastName, String emilAddress,
                              String companyName, String companyURL, String newLetters,
                              String role, String countryName, String phoneNumber,
                              String descriptionText){

        WebUtils.waitForPageLoad(wd);

        WebUtils.fill(txtbxFirstName,firstName);

        WebUtils.fill(txtbxLastName,lastName);

        WebUtils.fill(txtbxEmailId,emilAddress);

        WebUtils.fill(txtbxCompany,companyName);

        WebUtils.fill(txtbxCompanyURL,companyURL);

        if (newLetters.equals("true")) {
            WebUtils.moveToElementAndClick(wd, chbkNewsLetters);
        }

        if (!role.equals("")) {
            WebUtils.clickWithWaitForElement(wd, selRole);

            String roleXpath = "//div[@class='flounder__option' and normalize-space()='" + role + "']";
            WebUtils.clickWithWaitForElement(wd, wd.findElement(By.xpath(roleXpath)));
        }

        WebUtils.clickWithWaitForElement(wd,selCountry);

        String countryXpath = "//div[@class='flounder__option' and normalize-space()='"+countryName+"']";

        WebUtils.moveToElementAndClick(wd, wd.findElement(By.xpath(countryXpath)));

        WebUtils.fill(txtbxPhoneNumber,phoneNumber);

        WebUtils.fill(txtbxDescription,descriptionText);

        submitData();
    }

    @Step("Submit data")
    public void submitData(){
        WebUtils.clickWithWaitForElement(wd, btnSubmit);
    }

    public String getSuccessMessageText() {

        WebUtils.waitForElementToBeDisplayed(wd, lblMessageSuccess, 60);

        return WebUtils.getTextValue(wd, lblMessageSuccess);
    }

    public String getContactUsText() {

        WebUtils.waitForElementToBeDisplayed(wd, lblEmailContact, 60);

        return WebUtils.getTextValue(wd, lblEmailContact);
    }

    public void gotoPrivacyPolicy(){

        if(WebUtils.isElementDisplayed(cookieClose)){
            WebUtils.clickWithWaitForElement(wd, cookieClose, 30);
        }
        WebUtils.clickWithWaitForElement(wd, lnkPrivacyPolicy);
    }

    private WebElement getWarningSymbol(String labelName){

        return wd.findElement(By.xpath("//label[normalize-space()='"+labelName+"']" +
                "/following-sibling::div[contains(@class,'error-container--icon')]"));
    }

    public WebElement getWarningSymbolForPhoneCountry(String labelName){

        return wd.findElement(By.xpath("//label[normalize-space()='"+labelName+"']" +
                "/following-sibling::*/div[contains(@class,'error-container--icon')]"));
    }

    public boolean isWarningSymbolDisplayed(String labelName){
        try {
            return WebUtils.isElementDisplayed(getWarningSymbol(labelName));
        }catch(Exception e){ return false;}

    }

    public String getWarningSymbolTooltipText(String labelName){

        return WebUtils.getTextValue(wd, getWarningSymbol(labelName));
    }

    public void hoverOnWarningSymbol(String labelName){

        WebUtils.moveToElement(wd, getWarningSymbol(labelName));
    }

    public void fillDescriptionText(String descriptionText){

        txtbxDescription.clear();
        WebUtils.fill(txtbxDescription, descriptionText);
    }

    public void setFocus(){
        JavascriptExecutor jsx = (JavascriptExecutor)wd;
        jsx.executeScript("window.scrollBy(0,450)", "");

    }
}
