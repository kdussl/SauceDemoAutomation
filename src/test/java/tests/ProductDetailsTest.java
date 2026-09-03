package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import listeners.ExtentTestListener;
import org.pages.LoginPage;
import org.pages.ProductDetailsPage;
import org.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.utils.ConfigReader;

import static org.utils.ReportUtil.reportStep;

@Listeners(ExtentTestListener.class)
public class ProductDetailsTest extends BaseTest {

    private ProductDetailsPage loginAndOpenProductDetails() {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        ProductPage productPage =
                new ProductPage(driver);

        Assert.assertTrue(
                productPage.isProductPageDisplayed(),
                "Products page was not displayed"
        );

        productPage.clickProduct(
                "Sauce Labs Backpack"
        );

        ProductDetailsPage detailsPage =
                new ProductDetailsPage(driver);

        Assert.assertTrue(
                detailsPage.isProductDetailsPageDisplayed(),
                "Product details page was not displayed"
        );

        return detailsPage;
    }


    @Test(description = "Verify product details page")
    public void verifyProductDetailsPageTest() {

        ProductDetailsPage page =
                loginAndOpenProductDetails();

        boolean result =
                page.isProductDetailsPageDisplayed();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Details Page",
                "Product details page should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify product name")
    public void verifyProductNameTest() {

        ProductDetailsPage page =
                loginAndOpenProductDetails();

        String actual =
                page.getProductName();

        boolean result =
                actual.equals("Sauce Labs Backpack");

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Name",
                "Product name should be Sauce Labs Backpack"
        );

        Assert.assertEquals(
                actual,
                "Sauce Labs Backpack"
        );
    }


    @Test(description = "Verify product description")
    public void verifyProductDescriptionTest() {

        ProductDetailsPage page =
                loginAndOpenProductDetails();

        String description =
                page.getProductDescription();

        boolean result =
                description != null &&
                        !description.trim().isEmpty();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Description",
                "Product description should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify product price")
    public void verifyProductPriceTest() {

        ProductDetailsPage page =
                loginAndOpenProductDetails();

        String price =
                page.getProductPrice();

        boolean result =
                price.startsWith("$");

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Price",
                "Product price should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify product image")
    public void verifyProductImageTest() {

        ProductDetailsPage page =
                loginAndOpenProductDetails();

        boolean result =
                page.isProductImageDisplayed();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Image",
                "Product image should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify Add To Cart button")
    public void verifyAddToCartButtonTest() {

        ProductDetailsPage page =
                loginAndOpenProductDetails();

        boolean result =
                page.isAddToCartButtonDisplayed();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Add To Cart Button",
                "Add To Cart button should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify product can be added to cart")
    public void addProductToCartTest() {

        ProductDetailsPage page =
                loginAndOpenProductDetails();

        page.addProductToCart();

        boolean result =
                page.isRemoveButtonDisplayed();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Added",
                "Remove button should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify cart count")
    public void verifyCartCountTest() {

        ProductDetailsPage page =
                loginAndOpenProductDetails();

        page.addProductToCart();

        int count =
                page.getCartItemCount();

        boolean result =
                count == 1;

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Cart Count",
                "Cart count should be 1"
        );

        Assert.assertEquals(count, 1);
    }


    @Test(description = "Verify product can be removed")
    public void removeProductFromCartTest() {

        ProductDetailsPage page =
                loginAndOpenProductDetails();

        page.addProductToCart();

        page.removeProductFromCart();

        boolean result =
                page.isAddToCartButtonDisplayed();

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Product Removed",
                "Add To Cart button should be displayed"
        );

        Assert.assertTrue(result);
    }


    @Test(description = "Verify Back To Products")
    public void backToProductsTest() {

        ProductDetailsPage page =
                loginAndOpenProductDetails();

        page.clickBackToProducts();

        boolean result =
                driver.getCurrentUrl()
                        .contains("inventory.html");

        reportStep(
                result ? Status.PASS : Status.FAIL,
                "Verify Back To Products",
                "Products page should be displayed"
        );

        Assert.assertTrue(result);
    }
}