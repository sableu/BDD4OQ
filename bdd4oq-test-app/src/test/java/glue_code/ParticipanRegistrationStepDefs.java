package glue_code;

import com.github.andreashosbach.cucumber_scenarioo_plugin.model.Screenshot;
import glue_code.backend_api.ParticipantDto;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSender;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
public class ParticipanRegistrationStepDefs {

    private WebDriver webDriver;

    private ParticipantDto participant;

    @Before
    public void setupDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        webDriver = new ChromeDriver();

        webDriver.manage().window().setSize(new Dimension(1024, 768));

        webDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8098;
    }

    @After
    public void cleanupDriver() {
        webDriver.close();
        webDriver.quit();
    }

    @AfterStep
    public void afterStep() {
        TakesScreenshot scrShot = ((TakesScreenshot) webDriver);
        Screenshot.save(webDriver.getTitle(), scrShot.getScreenshotAs(OutputType.BYTES));
    }


/*
    Given A participant Peter
    And Peter has first name "Peter", last name "Parker", birthday "08.05.2020" and is "male"
    And Peter is not registered yet
    And Patricia wants to register Peter
    When Patricia enters Peters data
    Then Peter can be found in the system


    */


    @Given("Peter is not registered yet")
    public void peterIsNotRegisteredYet() {
        /*
        RequestSender sender = when();
        Response response = sender.get("/api/participant/" + peter.getId());
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(404);
         */
    }

    @And("Patricia wants to register Peter")
    public void patriciaWantsToRegisterPeter() {
        webDriver.navigate().to("http://localhost:8098/");
    }

    @When("Patricia enters {string}, {string}, {string}, and {string} in the respective fields")
    public void patriciaEntersAndInTheRespectiveFields(String firstName, String lastName, String birthday, String gender){

        participant = new ParticipantDto();
        participant.firstName = firstName;
        participant.lastName = lastName;
        participant.birthday = birthday;
        participant.gender = gender;

        webDriver.findElement(By.id("firstName")).sendKeys(participant.firstName);
        webDriver.findElement(By.id("lastName")).sendKeys(participant.lastName);
        webDriver.findElement(By.id("birthday")).sendKeys(participant.birthday);
        webDriver.findElement(By.id("gender")).sendKeys(participant.gender);
        webDriver.findElement(By.id("registerParticipant")).click();
    }

    @Then("Peter can be found in the system")
    public void peterCanBeFoundInTheSystem(){
        long id = Long.parseLong(webDriver.findElement(By.id("registrationId")).getText());
        RequestSender sender = when();
        Response response = sender.get("/api/participant/" + id);
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(200);
        ParticipantDto maybePeter = vResponse.extract().as(ParticipantDto.class);
        assertThat(maybePeter.firstName, is(participant.firstName));
        assertThat(maybePeter.lastName, is(participant.lastName));
        assertThat(maybePeter.birthday, is(participant.birthday));
        assertThat(maybePeter.gender, is(participant.gender));
    }


}
