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
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSender;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


public class WeightMeasurementsStepDefs {

    private WebDriver webDriver;
    private Long idAva;

    @Before
    public void before(){
        webDriver = TestContext.getInstance().getWebDriver();
    }

    @Given("Ava with first name {string}, last name {string}, birthday {string}, gender {string} is registered")
    public void avaWithFirstNameLastNameBirthdayGenderIsRegistered(String firstName, String lastName, String birthday, String gender) {

        ParticipantDto participantDto = new ParticipantDto();

        participantDto.firstName = firstName;
        participantDto.lastName = lastName;
        participantDto.birthday = birthday;
        participantDto.gender = gender;

        RequestSpecification request = given();
        request.contentType(ContentType.JSON);
        request.body(participantDto);
        RequestSender sender = request.when();
        Response response = sender.post("/api/participant");

        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_CREATED);

        idAva = vResponse.extract().as(Long.class);
    }

    //@And ("Ava has no weight measurement entry yet")

    @And("Patricia wants to register Ava's baseline weight measurement")
    public void patriciaWantsToRegisterAvasBaselineWeightMeasurement(){
        webDriver.navigate().to("http://localhost:8098/#/participant/"+idAva);
    }

    @When("Patricia enters {double} kg in the weight field, {string} in the time field and {string} in the comment field")
    public void patriciaEntersKgInTheWeightFieldInTheTimeFieldAndInTheCommentField(Double weight, String dateTime, String comment){
        webDriver.findElement(By.id("weight")).sendKeys(weight.toString());
        webDriver.findElement(By.id("dateTime")).sendKeys(dateTime);
        webDriver.findElement(By.id("comment")).sendKeys(comment);
    }

    @And("she saves these entries")
    public void sheSavesTheseEntries(){
        webDriver.findElement(By.id("addWeight")).click();
    }

    @Then("Ava's baseline weight entry should be found in the system")
    public void avasBaselineWeightEntryShouldBeFoundInTheSystem(){
        long id = Long.parseLong(webDriver.findElement(By.id("baselineId")).getText());
        assertThat(id, not(-1));
    }
}
