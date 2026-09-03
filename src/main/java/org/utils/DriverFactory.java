package org.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {

    public static WebDriver initializeDriver() {

        String browser =
                ConfigReader.getProperty("browser");

        WebDriver driver;

        if (browser.equalsIgnoreCase("edge")) {

            WebDriverManager
                    .edgedriver()
                    .setup();

            driver =
                    new EdgeDriver();

        } else {

            throw new RuntimeException(
                    "Browser not supported: " +
                            browser
            );
        }

        driver.manage()
                .window()
                .maximize();

        return driver;
    }
}