package base.core;

import base.utils.TestProperties;
import com.shield34.optimizer.sdk.extensions.InitializeProxyExtension;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class SeleniumDriver {

    /**
     * Creating web driver
     *
     * @param browser      browser name
     * @param capabilities web driver capabilities
     */
    public static WebDriver createDriver(String browser, DesiredCapabilities capabilities) {
        // setDriverProperty(browser);
        WebDriver driver = null;
        switch (browser.toLowerCase()) {
            case BrowserType.CHROME:
                WebDriverManager.chromedriver().setup();
                if (capabilities == null) {
                    capabilities = DesiredCapabilities.chrome();
                }
                ChromeOptions options = new ChromeOptions();
                // options.addArguments("--headless");
                options.addArguments("--start-maximized", "--disable-translate", "--disable-gpu", "--verbose", "--privileged");
                options.addArguments("--test-type");
                // overcome limited resource problems
                options.addArguments("--disable-dev-shm-usage");
                // applicable to windows os only
                options.addArguments("--disable-gpu");
                // Bypass OS security model
                options.addArguments("--no-sandbox");
                // disabling extensions
                options.addArguments("--disable-extensions");
                InitializeProxyExtension.addProxyToCapabilities(capabilities);
                options.setCapability("goog:chromeOptions", capabilities);
                ChromeDriverService service = new ChromeDriverService.Builder().usingAnyFreePort().build();
                try {
                    driver = new ChromeDriver(service, options);
                } catch (WebDriverException e) {
                    e.printStackTrace();
                }
                break;
            case BrowserType.FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                if (capabilities == null) {
                    capabilities = DesiredCapabilities.firefox();
                }
                driver = new FirefoxDriver(capabilities);
                break;
            case BrowserType.IE:
                WebDriverManager.iedriver().setup();
                if (capabilities == null) {
                    capabilities = DesiredCapabilities.internetExplorer();
                }
                capabilities.setCapability("requireWindowFocus", true);
                capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, false);
                capabilities.setCapability("ie.ensureCleanSession", true);
                driver = new InternetExplorerDriver(capabilities);
                break;
        }
        driver.manage().window().maximize();
        return driver;
    }
}
