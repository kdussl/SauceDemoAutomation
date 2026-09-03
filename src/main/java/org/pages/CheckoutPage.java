package org.pages;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.utils.ReportUtil.reportStep;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle =
            By.cssSelector(".title");

    private final By firstNameField =
            By.id("first-name");

    private final By lastNameField =
            By.id("last-name");

    private final By postalCodeField =
            By.id("postal-code");

    private final By continueButton =
            By.id("continue");

    private final By cancelButton =
            By.id("cancel");

    private final By errorMessage =
            By.cssSelector("[data-test='error']");

    public CheckoutPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public boolean isCheckoutPageDisplayed() {

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

    public void enterFirstName(String firstName) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        firstNameField
                )
        ).sendKeys(firstName);

        reportStep(
                Status.PASS,
                "Enter First Name",
                "First name should be entered"
        );
    }

    public void enterLastName(String lastName) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        lastNameField
                )
        ).sendKeys(lastName);

        reportStep(
                Status.PASS,
                "Enter Last Name",
                "Last name should be entered"
        );
    }

    public void enterPostalCode(String postalCode) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        postalCodeField
                )
        ).sendKeys(postalCode);

        reportStep(
                Status.PASS,
                "Enter Postal Code",
                "Postal code should be entered"
        );
    }

    public void enterCheckoutInformation(
            String firstName,
            String lastName,
            String postalCode) {

        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public void clickContinue() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        continueButton
                )
        ).click();

        reportStep(
                Status.PASS,
                "Click Continue",
                "Checkout overview page should be displayed"
        );
    }

    public void clickCancel() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        cancelButton
                )
        ).click();

        reportStep(
                Status.PASS,
                "Click Cancel",
                "Cart page should be displayed"
        );
    }

    public boolean isErrorMessageDisplayed() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            errorMessage
                    )
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        errorMessage
                )
        ).getText();
    }
}