package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import listeners.ExtentTestListener;
import org.pages.CartPage;
import org.pages.CheckoutPage;
import org.pages.LoginPage;
import org.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.utils.ConfigReader;

import static org.utils.ReportUtil.reportStep;

@Listeners(ExtentTestListener.class)
public class CheckoutTest extends BaseTest {

    private CheckoutPage loginAndOpenCheckout() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        ProductPage productPage =
                new ProductPage(driver);

        Assert.assertTrue(
                productPage.isProductPageDisplayed()
        );

        productPage.addProductToCart(
                "Sauce Labs Backpack"
        );

        productPage.clickCart();

        CartPage cartPage =
                new CartPage(driver);

        Assert.assertTrue(
                cartPage.isCartPageDisplayed()
        );

        cartPage.clickCheckout();

        CheckoutPage checkoutPage =
                new CheckoutPage(driver);

        Assert.assertTrue(
                checkoutPage.isCheckoutPageDisplayed()
        );

        return checkoutPage;
    }


    @Test(description = "Verify checkout page")
    public void verifyCheckoutPageTest() {

        CheckoutPage page =
                loginAndOpenCheckout();

        String title =
                page.getPageTitle();

        boolean result =
                title.equals("Checkout: Your Information");

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Checkout Page",
                "Page title should be Checkout: Your Information"
        );

        Assert.assertEquals(
                title,
                "Checkout: Your Information"
        );
    }


    @Test(description = "Verify valid checkout information")
    public void validCheckoutInformationTest() {

        CheckoutPage page =
                loginAndOpenCheckout();

        page.enterCheckoutInformation(
                "Sarav",
                "Tester",
                "600001"
        );

        page.clickContinue();

        boolean result =
                driver.getCurrentUrl()
                        .contains("checkout-step-two.html");

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Checkout Overview",
                "Checkout overview page should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify empty first name")
    public void emptyFirstNameTest() {

        CheckoutPage page =
                loginAndOpenCheckout();

        page.enterLastName("Tester");
        page.enterPostalCode("600001");

        page.clickContinue();

        boolean result =
                page.isErrorMessageDisplayed();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Empty First Name",
                "Error message should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify empty last name")
    public void emptyLastNameTest() {

        CheckoutPage page =
                loginAndOpenCheckout();

        page.enterFirstName("Sarav");
        page.enterPostalCode("600001");

        page.clickContinue();

        boolean result =
                page.isErrorMessageDisplayed();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Empty Last Name",
                "Error message should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify empty postal code")
    public void emptyPostalCodeTest() {

        CheckoutPage page =
                loginAndOpenCheckout();

        page.enterFirstName("Sarav");
        page.enterLastName("Tester");

        page.clickContinue();

        boolean result =
                page.isErrorMessageDisplayed();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Empty Postal Code",
                "Error message should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify cancel checkout")
    public void cancelCheckoutTest() {

        CheckoutPage page =
                loginAndOpenCheckout();

        page.clickCancel();

        boolean result =
                driver.getCurrentUrl()
                        .contains("cart.html");

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Cancel Checkout",
                "Cart page should be displayed"
        );

        Assert.assertTrue(result);
    }
}