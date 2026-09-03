package org.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

	private static ExtentReports extent;

	private static final ThreadLocal<ExtentTest> extentTest =
			new ThreadLocal<>();

	public static ExtentReports getExtentReports() {

		if (extent == null) {

			String reportPath =
					System.getProperty("user.dir")
							+ "/reports/ExtentReport.html";

			ExtentSparkReporter sparkReporter =
					new ExtentSparkReporter(
							reportPath
					);

			sparkReporter.config()
					.setReportName(
							"Selenium Automation Report"
					);

			sparkReporter.config()
					.setDocumentTitle(
							"Test Execution Report"
					);

			extent =
					new ExtentReports();

			extent.attachReporter(
					sparkReporter
			);

			extent.setSystemInfo(
					"Project",
					"SauceDemo Automation"
			);

			extent.setSystemInfo(
					"Browser",
					"Microsoft Edge"
			);

			extent.setSystemInfo(
					"Environment",
					"QA"
			);
		}

		return extent;
	}

	public static void createTest(
			String testName) {

		ExtentTest test =
				getExtentReports()
						.createTest(testName);

		extentTest.set(test);
	}

	public static ExtentTest getTest() {

		return extentTest.get();
	}

	public static void flushReport() {

		if (extent != null) {
			extent.flush();
		}
	}
}