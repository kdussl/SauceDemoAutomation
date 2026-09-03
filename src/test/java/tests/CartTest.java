package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import listeners.ExtentTestListener;
import org.pages.CartPage;
import org.pages.LoginPage;
import org.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.utils.ConfigReader;

import static org.utils.ReportUtil.reportStep;

@Listeners(ExtentTestListener.class)
public class CartTest extends BaseTest {

    private CartPage loginAndOpenCart() {

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

        return cartPage;
    }


    @Test(description = "Verify Cart page")
    public void verifyCartPageTest() {

        CartPage page =
                loginAndOpenCart();

        String title =
                page.getPageTitle();

        boolean result =
                title.equals("Your Cart");

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Cart Page",
                "Page title should be Your Cart"
        );

        Assert.assertEquals(
                title,
                "Your Cart"
        );
    }


    @Test(description = "Verify cart item count")
    public void verifyCartItemCountTest() {

        CartPage page =
                loginAndOpenCart();

        int count =
                page.getCartItemCount();

        boolean result =
                count == 1;

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Cart Item Count",
                "Cart should contain one product"
        );

        Assert.assertEquals(
                count,
                1
        );
    }


    @Test(description = "Verify product in cart")
    public void verifyProductInCartTest() {

        CartPage page =
                loginAndOpenCart();

        boolean result =
                page.isProductDisplayed(
                        "Sauce Labs Backpack"
                );

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product In Cart",
                "Sauce Labs Backpack should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify product price")
    public void verifyProductPriceTest() {

        CartPage page =
                loginAndOpenCart();

        String price =
                page.getCartProductPrices().get(0);

        boolean result =
                price.startsWith("$");

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Price",
                "Product price should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify product removal")
    public void removeProductTest() {

        CartPage page =
                loginAndOpenCart();

        page.removeProduct(
                "Sauce Labs Backpack"
        );

        int count =
                page.getCartItemCount();

        boolean result =
                count == 0;

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Removed",
                "Cart should be empty"
        );

        Assert.assertEquals(
                count,
                0
        );
    }


    @Test(description = "Verify continue shopping")
    public void continueShoppingTest() {

        CartPage page =
                loginAndOpenCart();

        page.clickContinueShopping();

        boolean result =
                driver.getCurrentUrl()
                        .contains("inventory.html");

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Continue Shopping",
                "Products page should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify checkout navigation")
    public void checkoutTest() {

        CartPage page =
                loginAndOpenCart();

        page.clickCheckout();

        boolean result =
                driver.getCurrentUrl()
                        .contains("checkout-step-one.html");

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Checkout Page",
                "Checkout information page should be displayed"
        );

        Assert.assertTrue(result);
    }
}