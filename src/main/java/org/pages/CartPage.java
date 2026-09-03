package org.pages;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.utils.ReportUtil.reportStep;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle =
            By.cssSelector(".title");

    private final By cartItems =
            By.cssSelector(".cart_item");

    private final By cartItemNames =
            By.cssSelector(".inventory_item_name");

    private final By cartItemPrices =
            By.cssSelector(".inventory_item_price");

    private final By continueShoppingButton =
            By.id("continue-shopping");

    private final By checkoutButton =
            By.id("checkout");

    public CartPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public boolean isCartPageDisplayed() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            pageTitle
                    )
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        pageTitle
                )
        ).getText();
    }

    public int getCartItemCount() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            cartItems
                    )
            ).size();

        } catch (Exception e) {
            return 0;
        }
    }

    public List<String> getCartProductNames() {

        List<WebElement> elements =
                wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                cartItemNames
                        )
                );

        List<String> names =
                new ArrayList<>();

        for (WebElement element : elements) {
            names.add(element.getText());
        }

        return names;
    }

    public List<String> getCartProductPrices() {

        List<WebElement> elements =
                wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                cartItemPrices
                        )
                );

        List<String> prices =
                new ArrayList<>();

        for (WebElement element : elements) {
            prices.add(element.getText());
        }

        return prices;
    }

    public boolean isProductDisplayed(String productName) {

        By product =
                By.xpath(
                        "//div[@class='cart_item']" +
                                "[.//div[@class='inventory_item_name' " +
                                "and normalize-space()='" +
                                productName +
                                "']]"
                );

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(product)
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public void removeProduct(String productName) {

        By removeButton =
                By.xpath(
                        "//div[@class='cart_item']" +
                                "[.//div[@class='inventory_item_name' " +
                                "and normalize-space()='" +
                                productName +
                                "']]" +
                                "//button[contains(@id,'remove')]"
                );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        removeButton
                )
        ).click();

        reportStep(
                Status.PASS,
                "Remove Product: " + productName,
                "Product should be removed from cart"
        );
    }

    public void clickContinueShopping() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        continueShoppingButton
                )
        ).click();

        reportStep(
                Status.PASS,
                "Continue Shopping",
                "Products page should be displayed"
        );
    }

    public void clickCheckout() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        checkoutButton
                )
        ).click();

        reportStep(
                Status.PASS,
                "Click Checkout",
                "Checkout information page should be displayed"
        );
    }
}