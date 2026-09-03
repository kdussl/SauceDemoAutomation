package org.pages;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.utils.ReportUtil.reportStep;

public class ConfirmationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By completeHeader =
            By.cssSelector(".complete-header");

    private final By completeText =
            By.cssSelector(".complete-text");

    private final By backHomeButton =
            By.id("back-to-products");

    public ConfirmationPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public boolean isConfirmationPageDisplayed() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            completeHeader
                    )
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public String getConfirmationHeader() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        completeHeader
                )
        ).getText();
    }

    public String getConfirmationMessage() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        completeText
                )
        ).getText();
    }

    public void clickBackHome() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        backHomeButton
                )
        ).click();

        reportStep(
                Status.PASS,
                "Click Back Home",
                "Products page should be displayed"
        );
    }
}