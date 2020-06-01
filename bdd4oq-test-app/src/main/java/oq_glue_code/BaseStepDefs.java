package oq_glue_code;

import com.github.andreashosbach.cucumber_scenarioo_plugin.model.Screenshot;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class BaseStepDefs {

    @Before
    public void setupDriver() {
        TestContext.getInstance().getWebDriver().navigate().to("about:blank");
    }

    @AfterStep
    public void afterStep() {
        TakesScreenshot scrShot = ((TakesScreenshot) (TestContext.getInstance().getWebDriver()));
        String title = TestContext.getInstance().getWebDriver().getTitle();
        Screenshot.save(title.isEmpty() ? "Unavailable" : title, scrShot.getScreenshotAs(OutputType.BYTES));
    }
}
