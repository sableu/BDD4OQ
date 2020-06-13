package oq_glue_code;

import com.github.andreashosbach.cucumber_scenarioo_plugin.model.Screenshot;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSender;
import org.apache.http.HttpStatus;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static io.restassured.RestAssured.when;
import static oq_glue_code.TestContext.*;


public class GeneralStepDefs {
    @Before
    public void setup() {
        webDriver().navigate().to("about:blank");
    }

    @After
    public void cleanupData() {
        for(Participant participant : participants()){
            if (participant.id != null) {
                RequestSender sender = when();
                Response response = sender.delete("/api/participant/" + participant.id);
                ValidatableResponse vResponse = response.then();
                vResponse.statusCode(HttpStatus.SC_NO_CONTENT);
            }
        }
        clearParticipants();
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
