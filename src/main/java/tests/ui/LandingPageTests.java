package tests.ui;

import base.BaseUITest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import listners.Retry;
import org.testng.Assert;
import org.testng.annotations.*;
import tests.pages.ContactUsPage;
import tests.pages.LandingPage;

public class LandingPageTests extends BaseUITest
{
    private LandingPage landingPage;


    @BeforeClass(alwaysRun=true)
    public void beforeClass()
    {
        landingPage = new LandingPage(wd).navigateTo();
    }

    @BeforeMethod(alwaysRun=true)
    public void beforeMethod()
    {
        landingPage.navigateTo();
    }

    @AfterClass()
    public void afterTests()
    {
        wd.quit();
    }


    @Test(retryAnalyzer = Retry.class)
    @Description("Navigate to contact us page from contact menu")
    @Severity(SeverityLevel.CRITICAL)
    public void TestNavigateFromContactMenuOption(){
        landingPage.gotoContactPageByContactMenu();

        Assert.assertEquals(wd.getCurrentUrl(), "https://www.sociomantic.com/contact/");

        ContactUsPage contactUsPage = new ContactUsPage(wd);
        contactUsPage.setFocus();
        Assert.assertTrue(contactUsPage.isContactUsPageoaded(),
                "Some elements on the page are not loaded properly");
    }

    @Test(retryAnalyzer = Retry.class)
    @Description("Navigate to contact us page from contact us link")
    @Severity(SeverityLevel.CRITICAL)
    public void TestNavigateFromContactUsLink(){
        landingPage.gotoContactPageByContactUsLink();

        Assert.assertEquals(wd.getCurrentUrl(), "https://www.sociomantic.com/contact/");

        ContactUsPage contactUsPage = new ContactUsPage(wd);
        contactUsPage.setFocus();
        Assert.assertTrue(contactUsPage.isContactUsPageoaded(),
                "Some elements on the page are not loaded properly");
    }
}