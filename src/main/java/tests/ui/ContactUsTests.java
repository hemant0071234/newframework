package tests.ui;

import base.BaseUITest;
import constants.Groups;
import io.qameta.allure.Description;
import listners.Retry;
import org.testng.Assert;
import org.testng.annotations.*;
import tests.pages.ContactUsPage;

import java.io.IOException;

public class ContactUsTests extends BaseUITest
{
    private ContactUsPage contactPage;

    @BeforeClass(alwaysRun=true)
    public void beforeClass()
    {
        contactPage = new ContactUsPage(wd).navigateTo();
    }

    @BeforeMethod(alwaysRun=true)
    public void beforeMethod()
    {
        contactPage.navigateTo();
        contactPage.setFocus();
    }

    @AfterClass()
    public void afterTests()
    {
        wd.quit();
    }

    @Test(retryAnalyzer = Retry.class, dataProvider="getData")
    @Description("Send data with different data sets mentioned in data provider")
    public void TestContactUsTestWithMultipleData(String firstName, String lastName, String emilAddress,
                                                  String companyName, String companyURL, String newLetters,
                                                  String role, String countryName, String phoneNumber,
                                                  String descriptionText) {

        contactPage.contactUSTest(firstName, lastName, emilAddress, companyName, companyURL,
                newLetters, role, countryName, phoneNumber, descriptionText);

        Assert.assertTrue(contactPage.getSuccessMessageText().equalsIgnoreCase(
                "Thanks! We'll be in touch soon.\n" +
                        "In the meantime check out our latest news."));
    }

    @Test(retryAnalyzer = Retry.class, groups = {Groups.CATEGORY_SANITY, Groups.CATEGORY_UI})
    @Description("Send data with only required fields information")
    public void TestCreateQueryWithRequiredFields(){
        contactPage.contactUSTest(
                "Hemant",
                "Janrao",
                "hemant.janrao@gmail.com",
                "Test company",
                "http://www.testcompany.com",
                "false",
                "",
                "Algeria",
                "9130021557",
                "Test description"
        );

        Assert.assertTrue(contactPage.getSuccessMessageText().equalsIgnoreCase(
                "Thanks! We'll be in touch soon.\n" +
                        "In the meantime check out our latest news."));
    }

    @Test(retryAnalyzer = Retry.class)
    @Description("Send data with all the fields information")
    public void TestCreateQueryWithAllFields(){
        contactPage.contactUSTest(
                "Hemant",
                "Janrao",
                "hemant.janrao@gmail.com",
                "Test company",
                "http://www.testcompany.com",
                "true",
                "Agency",
                "Algeria",
                "9130021557",
                "Test description"
        );

        Assert.assertTrue(contactPage.getSuccessMessageText().equalsIgnoreCase(
                "Thanks! We'll be in touch soon.\n" +
                        "In the meantime check out our latest news."));
    }

    @Test(retryAnalyzer = Retry.class)
    @Description("Send data with all the fields information")
    public void TestMoreThan500CharactersNotAllowed(){

        String moreThan500Chars = "A paragraph (from the Ancient Greek παράγραφος paragraphos, \"to write beside\" or \"written beside\") is a self-contained unit of a discourse in writing dealing with a particular point or idea. A paragraph consists of one or more sentences.\n" +
                "A paragraph (from the Ancient Greek παράγραφος paragraphos, \"to write beside\" or \"written beside\") is a self-contained unit of a discourse in writing dealing with a particular point or idea. A paragraph consists of one or more sentensdsfsdf fsddsdsdsdsdfdfdfdfddd";

        String lessThan500Chars = "A paragraph (from the Ancient Greek παράγραφος paragraphos";

        contactPage.contactUSTest(
                "Hemant",
                "Janrao",
                "hemant.janrao@gmail.com",
                "Test company",
                "http://www.testcompany.com",
                "true",
                "Agency",
                "Algeria",
                "9130021557",
                moreThan500Chars
        );

        Assert.assertTrue(contactPage.isWarningSymbolDisplayed("Message"));

        contactPage.hoverOnWarningSymbol("Message");

        Assert.assertEquals(contactPage.getWarningSymbolTooltipText("Message"),
                "This value is too long. It should have 500 characters or less.");


        contactPage.fillDescriptionText(lessThan500Chars);

        Assert.assertFalse(contactPage.isWarningSymbolDisplayed("Message"));

        contactPage.submitData();

        Assert.assertTrue(contactPage.getSuccessMessageText().equalsIgnoreCase(
                "Thanks! We'll be in touch soon.\n" +
                        "In the meantime check out our latest news."));
    }

    @Test(retryAnalyzer = Retry.class)
    @Description("Verify the contact us information on contact page")
    public void TestContactUsEmailLabelText(){
        Assert.assertTrue(contactPage.getContactUsText().equalsIgnoreCase(
                "if you're a Consumer, please reach us out directly at individualrights@dunnhumby.com"));
    }

    @Test(retryAnalyzer = Retry.class)
    @Description("Go to Privacy policy page")
    public void TestGotoPrivacyPolicyPage(){

        contactPage.gotoPrivacyPolicy();
        Assert.assertTrue(wd.getCurrentUrl().
                equalsIgnoreCase("https://www.sociomantic.com/website-privacy/"));
    }

    @Test(retryAnalyzer = Retry.class)
    @Description("Go to Privacy policy page")
    public void TestMandatoryFields(){

        contactPage.submitData();

        Assert.assertTrue(contactPage.isWarningSymbolDisplayed("First Name"));
        Assert.assertTrue(contactPage.isWarningSymbolDisplayed("Last Name"));
        Assert.assertTrue(contactPage.isWarningSymbolDisplayed("Work Email"));
        Assert.assertTrue(contactPage.isWarningSymbolDisplayed("Company"));
        Assert.assertTrue(contactPage.isWarningSymbolDisplayed("Company URL"));
        Assert.assertFalse(contactPage.isWarningSymbolDisplayed("I am a(n)"));
        Assert.assertTrue(contactPage.getWarningSymbolForPhoneCountry("Country").isDisplayed());
        Assert.assertTrue(contactPage.getWarningSymbolForPhoneCountry("Phone").isDisplayed());
        Assert.assertTrue(contactPage.isWarningSymbolDisplayed("Message"));

        contactPage.contactUSTest(
                "Hemant",
                "Janrao",
                "hemant.janrao@gmail.com",
                "Test company",
                "http://www.testcompany.com",
                "true",
                "Agency",
                "Algeria",
                "9130021557",
                "Test description"
        );

        Assert.assertTrue(contactPage.getSuccessMessageText().equalsIgnoreCase(
                "Thanks! We'll be in touch soon.\n" +
                        "In the meantime check out our latest news."));
    }

    @DataProvider(name="getData")
    public Object[][] getTestData() throws IOException {
        return parseExcelDataToDataProvider("src/main/resources/testdata.xls", "Sheet1");
    }
}