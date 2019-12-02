package base.core;

import base.utils.LocalDriverManager;
import base.utils.TestProperties;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import java.io.IOException;
import java.util.logging.Level;
import com.shield34.optimizer.sdk.wrappers.Shield34WebDriverWrapper;

public class SeleniumBaseTest {

    public static TestProperties testProperties = TestProperties.instance();

    public WebDriver driver = null;

    /**
     * Creating web driver
     */
    public void exampleBeforeClass() {
        if (testProperties.isChrome()) {
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
            // Enable performance logging
            LoggingPreferences loggingPreferences = new LoggingPreferences();
            loggingPreferences.enable(LogType.BROWSER, Level.ALL);
            loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);
            desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
            driver = SeleniumDriver.createDriver(BrowserType.CHROME, desiredCapabilities);
        } else if (testProperties.isIE()) {
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
            driver = SeleniumDriver.createDriver(BrowserType.IE, desiredCapabilities);
        } else {
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
            driver = SeleniumDriver.createDriver(BrowserType.FIREFOX, desiredCapabilities);
        }
        LocalDriverManager.setWebDriver(driver);
    }

    /**
     * Closing web driver after class
     */
    @AfterClass(alwaysRun = true)
    protected void afterClassActions() {
        closeBrowser();
    }

    /**
     * Cleaning web driver after suite
     */
    @AfterSuite(alwaysRun = true)
    protected void afterSuiteActions() {
        if (testProperties.isChrome())
            cleanChromeDrivers();
    }

    /**
     * This method will close all the browsers
     *
     * @author Vignesh.mohan
     */
    public void cleanChromeDrivers() {
        Runtime rt = Runtime.getRuntime();
        try {
            System.out.println("Killing all chrome and chromedriver instances ...");
            try {
                if (driver != null)
                    Shield34WebDriverWrapper.quit(driver);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (SystemUtils.IS_OS_WINDOWS)
                rt.exec("taskkill /IM chromedriver.exe /f");
            else if (SystemUtils.IS_OS_LINUX)
                rt.exec("pkill chromedriver");
        } catch (NullPointerException | IOException | WebDriverException e) {
            System.err.println("Warning - Could not kill chrome and chromeDriver instances");
        }
    }

    /**
     * Closing web driver
     */
    public void closeBrowser() {
        try {
            if (driver != null) {
                Shield34WebDriverWrapper.close(driver);
                driver = null;
            }
        } catch (Exception ee) {
            System.out.println("Could not close driver");
        }
        try {
            Shield34WebDriverWrapper.quit(driver);
        } catch (Exception e) {
        }
    }
}
