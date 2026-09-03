package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.utils.ExtentReportManager;

public class ExtentTestListener
		implements ITestListener {

	@Override
	public void onStart(
			ITestContext context) {

		ExtentReportManager
				.getExtentReports();
	}

	@Override
	public void onTestStart(
			ITestResult result) {

		ExtentReportManager.createTest(
				result.getMethod()
						.getMethodName()
		);
	}

	@Override
	public void onTestSuccess(
			ITestResult result) {

		ExtentReportManager.getTest()
				.pass(
						"Test Passed Successfully"
				);
	}

	@Override
	public void onTestFailure(
			ITestResult result) {

		ExtentReportManager.getTest()
				.fail(
						"Test Failed"
				);

		if (result.getThrowable() != null) {

			ExtentReportManager.getTest()
					.fail(
							result.getThrowable()
					);
		}
	}

	@Override
	public void onTestSkipped(
			ITestResult result) {

		ExtentReportManager.getTest()
				.skip(
						"Test Skipped"
				);
	}

	@Override
	public void onFinish(
			ITestContext context) {

		ExtentReportManager
				.flushReport();
	}
}