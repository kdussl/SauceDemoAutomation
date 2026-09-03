package org.pages;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.utils.ReportUtil.reportStep;

public class ProductDetailsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productName =
            By.cssSelector(".inventory_details_name");

    private final By productDescription =
            By.cssSelector(".inventory_details_desc");

    private final By productPrice =
            By.cssSelector(".inventory_details_price");

    private final By productImage =
            By.cssSelector(".inventory_details_img");

    private final By addToCartButton =
            By.cssSelector("button[id^='add-to-cart']");

    private final By removeButton =
            By.cssSelector("button[id^='remove']");

    private final By backToProductsButton =
            By.id("back-to-products");

    private final By cartBadge =
            By.cssSelector(".shopping_cart_badge");

    public ProductDetailsPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public boolean isProductDetailsPageDisplayed() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            productName
                    )
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public String getProductName() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        productName
                )
        ).getText();
    }

    public String getProductDescription() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        productDescription
                )
        ).getText();
    }

    public String getProductPrice() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        productPrice
                )
        ).getText();
    }

    public boolean isProductImageDisplayed() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            productImage
                    )
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAddToCartButtonDisplayed() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            addToCartButton
                    )
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRemoveButtonDisplayed() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            removeButton
                    )
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public void addProductToCart() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        addToCartButton
                )
        ).click();

        reportStep(
                Status.PASS,
                "Add Product To Cart",
                "Product should be added to cart"
        );
    }

    public void removeProductFromCart() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        removeButton
                )
        ).click();

        reportStep(
                Status.PASS,
                "Remove Product From Cart",
                "Product should be removed from cart"
        );
    }

    public int getCartItemCount() {

        try {
            return Integer.parseInt(
                    wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    cartBadge
                            )
                    ).getText()
            );

        } catch (Exception e) {
            return 0;
        }
    }

    public void clickBackToProducts() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        backToProductsButton
                )
        ).click();

        reportStep(
                Status.PASS,
                "Back To Products",
                "Products page should be displayed"
        );
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}