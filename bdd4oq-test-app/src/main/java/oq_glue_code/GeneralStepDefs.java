package oq_glue_code;

import com.github.andreashosbach.cucumber_scenarioo_plugin.model.Screenshot;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static oq_glue_code.TestContext.webDriver;


public class GeneralStepDefs {

    @Before
    public void setupDriver() {
        webDriver().navigate().to("about:blank");
        TestContext.getInstance().setParticipant(new Participant());
    }

    @Given("Patricia has the application open")
    public void hasTheApplicationOpen() {
        webDriver().navigate().to("http://localhost:8098/");
    }

    @AfterStep
    public void afterStep() {
        TakesScreenshot scrShot = ((TakesScreenshot) (webDriver()));
        String title = webDriver().getTitle();
        Screenshot.save(title.isEmpty() ? "Unavailable" : title, scrShot.getScreenshotAs(OutputType.BYTES));
    }
}
