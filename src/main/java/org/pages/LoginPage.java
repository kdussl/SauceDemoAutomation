package org.pages;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.utils.ReportUtil.reportStep;

public class LoginPage {

	private final WebDriver driver;
	private final WebDriverWait wait;

	private final By usernameField =
			By.id("user-name");

	private final By passwordField =
			By.id("password");

	private final By loginButton =
			By.id("login-button");

	private final By errorMessage =
			By.cssSelector("[data-test='error']");

	private final By productsTitle =
			By.cssSelector(".title");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(
				driver,
				Duration.ofSeconds(10)
		);
	}

	public void enterUsername(String username) {

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(
						usernameField
				)
		).sendKeys(username);

		reportStep(
				Status.PASS,
				"Enter Username",
				"Username should be entered successfully"
		);
	}

	public void enterPassword(String password) {

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(
						passwordField
				)
		).sendKeys(password);

		reportStep(
				Status.PASS,
				"Enter Password",
				"Password should be entered successfully"
		);
	}

	public void clickLogin() {

		wait.until(
				ExpectedConditions.elementToBeClickable(
						loginButton
				)
		).click();

		reportStep(
				Status.PASS,
				"Click Login",
				"Login button should be clicked successfully"
		);
	}

	public void login(String username, String password) {

		enterUsername(username);
		enterPassword(password);
		clickLogin();
	}

	public boolean isProductsPageDisplayed() {

		try {
			return wait.until(
					ExpectedConditions.visibilityOfElementLocated(
							productsTitle
					)
			).isDisplayed();

		} catch (Exception e) {
			return false;
		}
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