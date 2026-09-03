package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import listeners.ExtentTestListener;
import org.openqa.selenium.By;
import org.pages.CartPage;
import org.pages.CheckoutPage;
import org.pages.ConfirmationPage;
import org.pages.LoginPage;
import org.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.utils.ConfigReader;

import static org.utils.ReportUtil.reportStep;

@Listeners(ExtentTestListener.class)
public class ConfirmationTest extends BaseTest {

    private ConfirmationPage completeOrder() {

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

        checkoutPage.enterCheckoutInformation(
                "Sarav",
                "Tester",
                "600001"
        );

        checkoutPage.clickContinue();

        waitForOverviewPage();

        driver.findElement(
                By.id("finish")
        ).click();

        reportStep(
                Status.PASS,
                "Click Finish",
                "Order should be completed successfully"
        );

        ConfirmationPage confirmationPage =
                new ConfirmationPage(driver);

        Assert.assertTrue(
                confirmationPage.isConfirmationPageDisplayed()
        );

        return confirmationPage;
    }

    private void waitForOverviewPage() {

        new org.openqa.selenium.support.ui.WebDriverWait(
                driver,
                java.time.Duration.ofSeconds(10)
        ).until(
                org.openqa.selenium.support.ui.ExpectedConditions
                        .urlContains("checkout-step-two.html")
        );
    }


    @Test(description = "Verify confirmation page")
    public void verifyConfirmationPageTest() {

        ConfirmationPage page =
                completeOrder();

        boolean result =
                page.isConfirmationPageDisplayed();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Confirmation Page",
                "Confirmation page should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify confirmation header")
    public void verifyConfirmationHeaderTest() {

        ConfirmationPage page =
                completeOrder();

        String header =
                page.getConfirmationHeader();

        boolean result =
                header.equals(
                        "Thank you for your order!"
                );

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Confirmation Header",
                "Header should be Thank you for your order!"
        );

        Assert.assertEquals(
                header,
                "Thank you for your order!"
        );
    }


    @Test(description = "Verify confirmation message")
    public void verifyConfirmationMessageTest() {

        ConfirmationPage page =
                completeOrder();

        String message =
                page.getConfirmationMessage();

        boolean result =
                message != null &&
                        !message.trim().isEmpty();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Confirmation Message",
                "Confirmation message should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify Back Home")
    public void backHomeTest() {

        ConfirmationPage page =
                completeOrder();

        page.clickBackHome();

        boolean result =
                driver.getCurrentUrl()
                        .contains("inventory.html");

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Back Home",
                "Products page should be displayed"
        );

        Assert.assertTrue(result);
    }
}