package org.pages;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.utils.ReportUtil.reportStep;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle =
            By.cssSelector(".title");

    private final By productItems =
            By.cssSelector(".inventory_item");

    private final By productNames =
            By.cssSelector(".inventory_item_name");

    private final By productPrices =
            By.cssSelector(".inventory_item_price");

    private final By addToCartButtons =
            By.cssSelector("button[id^='add-to-cart']");

    private final By cartIcon =
            By.cssSelector(".shopping_cart_link");

    private final By cartBadge =
            By.cssSelector(".shopping_cart_badge");

    private final By sortDropdown =
            By.cssSelector(".product_sort_container");

    public ProductPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public boolean isProductPageDisplayed() {

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

    public int getProductCount() {

        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        productItems
                )
        ).size();
    }

    public List<String> getProductNames() {

        List<WebElement> elements =
                wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                productNames
                        )
                );

        List<String> names = new ArrayList<>();

        for (WebElement element : elements) {
            names.add(element.getText());
        }

        return names;
    }

    public List<String> getProductPrices() {

        List<WebElement> elements =
                wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                productPrices
                        )
                );

        List<String> prices = new ArrayList<>();

        for (WebElement element : elements) {
            prices.add(element.getText());
        }

        return prices;
    }

    public List<Double> getProductPricesAsNumbers() {

        List<WebElement> elements =
                wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                productPrices
                        )
                );

        List<Double> prices = new ArrayList<>();

        for (WebElement element : elements) {

            String price =
                    element.getText()
                            .replace("$", "")
                            .trim();

            prices.add(
                    Double.parseDouble(price)
            );
        }

        return prices;
    }

    public void clickProduct(String productName) {

        By product = By.xpath(
                "//*[contains(@class,'inventory_item_name')"
                        + " and normalize-space()='" + productName + "']"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(product)
        ).click();

        reportStep(
                Status.PASS,
                "Open Product: " + productName,
                "Product details page should be displayed"
        );
    }

    public void addFirstProductToCart() {

        wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        addToCartButtons
                )
        ).get(0).click();

        reportStep(
                Status.PASS,
                "Add First Product To Cart",
                "First product should be added to cart"
        );
    }

    public void addProductToCart(String productName) {

        String productId = "add-to-cart-" +
                productName.toLowerCase()
                        .replace(" ", "-");

        By addButton = By.id(productId);

        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();

        reportStep(
                Status.PASS,
                "Add Product: " + productName,
                "Product should be added to cart"
        );
    }

    public boolean isProductAdded(String productName) {

        By product = By.xpath(
                "//div[contains(@class,'cart_item')]"
                        + "//*[normalize-space()='" + productName + "']"
        );

        try {
            Thread.sleep(10000);
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(product)
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public void removeProductFromCart(String productName) {

        String productId = "remove-" +
                productName.toLowerCase()
                        .replace(" ", "-");

        By removeButton = By.id(productId);

        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();

        reportStep(
                Status.PASS,
                "Remove Product: " + productName,
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

    public boolean isCartBadgeDisplayed() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            cartBadge
                    )
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public void clickCart() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        cartIcon
                )
        ).click();

        reportStep(
                Status.PASS,
                "Open Shopping Cart",
                "Shopping cart page should be displayed"
        );
    }

    public void selectSortOption(String option) {

        WebElement dropdown =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                sortDropdown
                        )
                );

        Select select =
                new Select(dropdown);

        select.selectByValue(option);

        reportStep(
                Status.PASS,
                "Sort Products",
                "Products should be sorted using " + option
        );
    }
}