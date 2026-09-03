package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import listeners.ExtentTestListener;
import org.pages.LoginPage;
import org.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.utils.ConfigReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.utils.ReportUtil.reportStep;

@Listeners(ExtentTestListener.class)
public class ProductTest extends BaseTest {

    private ProductPage loginAndOpenProductPage() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        ProductPage productPage =
                new ProductPage(driver);

        boolean result =
                productPage.isProductPageDisplayed();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Products Page",
                "Products page should be displayed"
        );

        Assert.assertTrue(
                result,
                "Products page was not displayed"
        );

        return productPage;
    }


    @Test(description = "Verify Products page title")
    public void verifyProductPageTest() {

        ProductPage productPage =
                loginAndOpenProductPage();

        String title =
                productPage.getPageTitle();

        boolean result =
                title.equals("Products");

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Products Page Title",
                "Page title should be Products"
        );

        Assert.assertEquals(
                title,
                "Products"
        );
    }


    @Test(description = "Verify products are displayed")
    public void verifyProductCountTest() {

        ProductPage productPage =
                loginAndOpenProductPage();

        int count =
                productPage.getProductCount();

        boolean result =
                count > 0;

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Count",
                "Products should be displayed"
        );

        Assert.assertTrue(
                result,
                "No products displayed"
        );
    }


    @Test(description = "Verify product names")
    public void verifyProductNamesTest() {

        ProductPage productPage =
                loginAndOpenProductPage();

        List<String> names =
                productPage.getProductNames();

        boolean result =
                !names.isEmpty();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Names",
                "Product names should be displayed"
        );

        Assert.assertFalse(
                names.isEmpty(),
                "Product names are not displayed"
        );
    }


    @Test(description = "Verify product prices")
    public void verifyProductPricesTest() {

        ProductPage productPage =
                loginAndOpenProductPage();

        List<String> prices =
                productPage.getProductPrices();

        boolean result =
                !prices.isEmpty();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Prices",
                "Product prices should be displayed"
        );

        Assert.assertFalse(
                prices.isEmpty(),
                "Product prices are not displayed"
        );
    }


    @Test(description = "Verify product can be added to cart")
    public void addProductToCartTest() {

        ProductPage productPage =
                loginAndOpenProductPage();

        productPage.addFirstProductToCart();

        int count =
                productPage.getCartItemCount();

        boolean result =
                count == 1;

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Added",
                "Cart count should be 1"
        );

        Assert.assertEquals(
                count,
                1
        );
    }

    @Test(description = "Verify product can be removed")
    public void removeProductFromCartTest() {

        ProductPage productPage =
                loginAndOpenProductPage();

        String product =
                "Sauce Labs Backpack";

        productPage.addProductToCart(product);

        productPage.removeProductFromCart(product);

        boolean result =
                !productPage.isProductAdded(product);

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Removed",
                product + " should be removed from cart"
        );

        Assert.assertTrue(
                result,
                product + " was not removed"
        );
    }


    @Test(description = "Verify cart badge")
    public void verifyCartBadgeTest() {

        ProductPage productPage =
                loginAndOpenProductPage();

        productPage.addFirstProductToCart();

        boolean result =
                productPage.isCartBadgeDisplayed();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Cart Badge",
                "Cart badge should be displayed"
        );

        Assert.assertTrue(
                result,
                "Cart badge was not displayed"
        );
    }


    @Test(description = "Verify cart page navigation")
    public void openCartTest() {

        ProductPage productPage =
                loginAndOpenProductPage();

        productPage.clickCart();

        boolean result =
                driver.getCurrentUrl()
                        .contains("cart.html");

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Cart Page",
                "Cart page should be displayed"
        );

        Assert.assertTrue(
                result,
                "Cart page was not displayed"
        );
    }


    @Test(description = "Verify sorting A to Z")
    public void sortProductsAToZTest() {

        ProductPage productPage =
                loginAndOpenProductPage();

        productPage.selectSortOption("az");

        List<String> actual =
                productPage.getProductNames();

        List<String> expected =
                new ArrayList<>(actual);

        expected.sort(
                String.CASE_INSENSITIVE_ORDER
        );

        boolean result =
                actual.equals(expected);

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Sorting A-Z",
                "Products should be sorted alphabetically A-Z"
        );

        Assert.assertEquals(
                actual,
                expected
        );
    }


    @Test(description = "Verify sorting Z to A")
    public void sortProductsZToATest() {

        ProductPage productPage =
                loginAndOpenProductPage();

        productPage.selectSortOption("za");

        List<String> actual =
                productPage.getProductNames();

        List<String> expected =
                new ArrayList<>(actual);

        expected.sort(
                String.CASE_INSENSITIVE_ORDER.reversed()
        );

        boolean result =
                actual.equals(expected);

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Sorting Z-A",
                "Products should be sorted alphabetically Z-A"
        );

        Assert.assertEquals(
                actual,
                expected
        );
    }


    @Test(description = "Verify sorting price low to high")
    public void sortProductsLowToHighTest() {

        ProductPage productPage =
                loginAndOpenProductPage();

        productPage.selectSortOption("lohi");

        List<Double> actual =
                productPage.getProductPricesAsNumbers();

        List<Double> expected =
                new ArrayList<>(actual);

        Collections.sort(expected);

        boolean result =
                actual.equals(expected);

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Price Sorting Low To High",
                "Products should be sorted from low price to high price"
        );

        Assert.assertEquals(
                actual,
                expected
        );
    }


    @Test(description = "Verify sorting price high to low")
    public void sortProductsHighToLowTest() {

        ProductPage productPage =
                loginAndOpenProductPage();

        productPage.selectSortOption("hilo");

        List<Double> actual =
                productPage.getProductPricesAsNumbers();

        List<Double> expected =
                new ArrayList<>(actual);

        Collections.sort(expected);
        Collections.reverse(expected);

        boolean result =
                actual.equals(expected);

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Price Sorting High To Low",
                "Products should be sorted from high price to low price"
        );

        Assert.assertEquals(
                actual,
                expected
        );
    }
}