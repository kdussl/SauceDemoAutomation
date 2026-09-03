package org.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportUtil {

    private static WebDriver driver;

    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

    public static void reportStep(
            Status status,
            String stepName,
            String expectedResult) {

        try {

            if (driver == null) {
                throw new IllegalStateException(
                        "WebDriver is not initialized"
                );
            }

            if (ExtentReportManager.getTest() == null) {
                throw new IllegalStateException(
                        "ExtentTest is not initialized"
                );
            }

            String screenshotDirectory =
                    System.getProperty("user.dir")
                            + File.separator
                            + "reports"
                            + File.separator
                            + "screenshots";

            Path directory =
                    Paths.get(screenshotDirectory);

            Files.createDirectories(directory);

            String timestamp =
                    LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern(
                                    "yyyyMMdd_HHmmss_SSS"
                            )
                    );

            Path destination =
                    directory.resolve(
                            "screenshot_" +
                                    timestamp +
                                    ".png"
                    );

            File source =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(
                                    OutputType.FILE
                            );

            Files.copy(
                    source.toPath(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            String details =
                    "<b>Step:</b> " +
                            stepName +
                            "<br><br>" +
                            "<b>Expected Result:</b> " +
                            expectedResult;

            ExtentReportManager.getTest()
                    .log(
                            status,
                            details,
                            MediaEntityBuilder
                                    .createScreenCaptureFromPath(
                                            destination.toString()
                                    )
                                    .build()
                    );

        } catch (Exception e) {

            if (ExtentReportManager.getTest() != null) {

                ExtentReportManager.getTest()
                        .fail(
                                "Unable to create report step: "
                                        + e.getMessage()
                        );
            }
        }
    }
}