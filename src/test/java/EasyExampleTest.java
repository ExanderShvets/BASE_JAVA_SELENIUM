import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.core.ExampleSeleniumBaseTest;
import com.shield34.optimizer.sdk.wrappers.Shield34WebElementWrapper;
import com.shield34.optimizer.sdk.wrappers.Shield34WebDriverWrapper;
import org.testng.annotations.Listeners;
import com.shield34.optimizer.sdk.listeners.Shield34TestNgListener;

/*
Test scenario:
1. Go to https://google.com
2. Insert "Shield34" string in the search bar
3. Click "Search"
4. Verify that we have moved to the page that holds the search results
 */
@Listeners({ Shield34TestNgListener.class })
public class EasyExampleTest extends ExampleSeleniumBaseTest {

    @BeforeClass
    void openBrowser() {
        // Navigating to google.com
        Shield34WebDriverWrapper.get(driver, "https://google.com");
    }

    @Test
    public void demoTest1() {
        // Locating the search bar element
        WebElement searchBar = Shield34WebDriverWrapper.findElement(driver, By.cssSelector("input[name=q]"));
        // Inserting "Shield34"
        Shield34WebElementWrapper.sendKeys(searchBar, "Shield34");
        // Locating the "Search" button element
        WebElement searchClick = Shield34WebDriverWrapper.findElement(driver, By.cssSelector("input[name=btnK]"));
        // Clicking the "Search" button
        Shield34WebElementWrapper.click(searchClick);
        // Verifying that we have moved to the results page
        Assert.assertTrue(driver.getTitle().contains("Shield34"));
    }
}
