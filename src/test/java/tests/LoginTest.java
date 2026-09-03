package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import listeners.ExtentTestListener;
import org.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.utils.ConfigReader;

import static org.utils.ReportUtil.reportStep;

@Listeners(ExtentTestListener.class)
public class LoginTest extends BaseTest {

	@Test(description = "Verify user can login with valid credentials")
	public void validLoginTest() {

		LoginPage loginPage =
				new LoginPage(driver);

		loginPage.login(
				ConfigReader.getProperty("username"),
				ConfigReader.getProperty("password")
		);

		boolean result =
				loginPage.isProductsPageDisplayed();

		reportStep(
				result ? Status.PASS : Status.FAIL,
				"Verify Products Page",
				"Products page should be displayed after successful login"
		);

		Assert.assertTrue(
				result,
				"Products page was not displayed after successful login"
		);
	}


	@Test(description = "Verify login fails with invalid username")
	public void invalidUsernameTest() {

		LoginPage loginPage =
				new LoginPage(driver);

		loginPage.enterUsername("invalid_user");

		loginPage.enterPassword(
				ConfigReader.getProperty("password")
		);

		loginPage.clickLogin();

		boolean result =
				loginPage.isErrorMessageDisplayed();

		reportStep(
				result ? Status.PASS : Status.FAIL,
				"Verify Invalid Username",
				"Error message should be displayed for invalid username"
		);

		Assert.assertTrue(
				result,
				"Error message was not displayed"
		);
	}


	@Test(description = "Verify login fails with invalid password")
	public void invalidPasswordTest() {

		LoginPage loginPage =
				new LoginPage(driver);

		loginPage.enterUsername(
				ConfigReader.getProperty("username")
		);

		loginPage.enterPassword("invalid_password");

		loginPage.clickLogin();

		boolean result =
				loginPage.isErrorMessageDisplayed();

		reportStep(
				result ? Status.PASS : Status.FAIL,
				"Verify Invalid Password",
				"Error message should be displayed for invalid password"
		);

		Assert.assertTrue(
				result,
				"Error message was not displayed"
		);
	}


	@Test(description = "Verify login fails with empty username")
	public void emptyUsernameTest() {

		LoginPage loginPage =
				new LoginPage(driver);

		loginPage.enterPassword(
				ConfigReader.getProperty("password")
		);

		loginPage.clickLogin();

		boolean result =
				loginPage.isErrorMessageDisplayed();

		reportStep(
				result ? Status.PASS : Status.FAIL,
				"Verify Empty Username",
				"Error message should be displayed when username is empty"
		);

		Assert.assertTrue(
				result,
				"Error message was not displayed"
		);
	}


	@Test(description = "Verify login fails with empty password")
	public void emptyPasswordTest() {

		LoginPage loginPage =
				new LoginPage(driver);

		loginPage.enterUsername(
				ConfigReader.getProperty("username")
		);

		loginPage.clickLogin();

		boolean result =
				loginPage.isErrorMessageDisplayed();

		reportStep(
				result ? Status.PASS : Status.FAIL,
				"Verify Empty Password",
				"Error message should be displayed when password is empty"
		);

		Assert.assertTrue(
				result,
				"Error message was not displayed"
		);
	}


	@Test(description = "Verify login fails with empty credentials")
	public void emptyCredentialsTest() {

		LoginPage loginPage =
				new LoginPage(driver);

		loginPage.clickLogin();

		boolean result =
				loginPage.isErrorMessageDisplayed();

		reportStep(
				result ? Status.PASS : Status.FAIL,
				"Verify Empty Credentials",
				"Error message should be displayed when credentials are empty"
		);

		Assert.assertTrue(
				result,
				"Error message was not displayed"
		);
	}


	@Test(description = "Verify locked user cannot login")
	public void lockedUserTest() {

		LoginPage loginPage =
				new LoginPage(driver);

		loginPage.enterUsername("locked_out_user");

		loginPage.enterPassword(
				ConfigReader.getProperty("password")
		);

		loginPage.clickLogin();

		boolean result =
				loginPage.isErrorMessageDisplayed();

		reportStep(
				result ? Status.PASS : Status.FAIL,
				"Verify Locked User",
				"Error message should be displayed for locked user"
		);

		Assert.assertTrue(
				result,
				"Locked user error message was not displayed"
		);
	}
}