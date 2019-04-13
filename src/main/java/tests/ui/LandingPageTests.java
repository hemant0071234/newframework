package tests.ui;

import base.BaseUITest;
import constants.Groups;
import io.qameta.allure.Description;
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
        Assert.assertTrue(landingPage.isLandingPageLoaded(),
                "Some elements on the page are not loaded properly");
    }

    @AfterClass()
    public void afterTests()
    {
        wd.quit();
    }


    @Test(retryAnalyzer = Retry.class, groups = {Groups.CATEGORY_SANITY, Groups.CATEGORY_UI})
    @Description("Navigate to contact us page from contact menu")
    public void TestNavigateFromContactMenuOption(){

        landingPage.gotoContactPageByContactMenu();

        Assert.assertEquals(wd.getCurrentUrl(), "https://www.sociomantic.com/contact/");

        ContactUsPage contactUsPage = new ContactUsPage(wd);
        contactUsPage.setFocus();
        Assert.assertTrue(contactUsPage.isContactUsPageoaded(),
                "Some elements on the page are not loaded properly");
    }

    @Test(retryAnalyzer = Retry.class, groups = {Groups.CATEGORY_SANITY, Groups.CATEGORY_UI})
    @Description("Navigate to contact us page from contact us link")
    public void TestNavigateFromContactUsLink(){

        landingPage.gotoContactPageByContactUsLink();

        Assert.assertEquals(wd.getCurrentUrl(), "https://www.sociomantic.com/contact/");

        ContactUsPage contactUsPage = new ContactUsPage(wd);
        contactUsPage.setFocus();
        Assert.assertTrue(contactUsPage.isContactUsPageoaded(),
                "Some elements on the page are not loaded properly");
    }
}