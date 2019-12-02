import base.core.ExampleSeleniumBaseTest;
import base.poms.ContenctPom;
import base.poms.ExamplePom;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import com.shield34.optimizer.sdk.listeners.Shield34TestNgListener;

@Listeners({ Shield34TestNgListener.class })
public class AdvancedExampleTest extends ExampleSeleniumBaseTest {

    @Test
    public void exampleTest() {
        // your test here , for example here we search for Wikipedia in Wikipedia website.
        ExamplePom examplePom = new ExamplePom();
        Assert.assertTrue(examplePom.isDisplayed(), "Expected search component to be displayed");
        examplePom.setSearch("Wikipedia");
        ContenctPom contenctPom = examplePom.clickSearch();
        Assert.assertTrue(contenctPom.isDisplayed(), "Expected content component to be displayed");
    }
}
