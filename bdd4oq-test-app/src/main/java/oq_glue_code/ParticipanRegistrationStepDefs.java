package oq_glue_code;

import io.restassured.RestAssured;
import oq_glue_code.backend_api.ParticipantDto;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.*;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSender;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ParticipanRegistrationStepDefs {


    private WebDriver webDriver;
    private ParticipantDto peter;
    private Long idPeter;
    private ParticipantDto participant;
    private Long idParticipant;


    @Before
    public void before() {
        webDriver = TestContext.getInstance().getWebDriver();
    }

    @Given("A participant Peter")
    public void aParticipantPeter() {
        peter = new ParticipantDto();
    }

    @And("Peter has first name {string}, last name {string}, birthday {string} and is {string}")
    public void peterHasFirstNameLastNameBirthdayAndIs(String firstName, String lastName, String birthday, String gender) {
        peter.firstName = firstName;
        peter.lastName = lastName;
        peter.birthday = birthday;
        peter.gender = gender;
    }

    @And("Peter is not registered yet")
    public void peterIsNotRegisteredYet() {
        webDriver.navigate().to("http://localhost:8098/#/participant");
        RequestSpecification request = RestAssured.given();
        request.param("firstName", peter.getFirstName());
        request.param("lastName", peter.getLastName());
        request.param("birthday", peter.getBirthday());
        RequestSender sender = request.when();
        Response response = sender.get("/api/participant/search");
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(404);
//        assertThat(vResponse.extract().statusCode(), is(404));
 /*  //alternative instead of giving back '404', delete Peter if he exists --> Todo
        if(vResponse.extract().statusCode() == HttpStatus.SC_OK){
            ParticipantDto peter ;
            RequestSender deleteSender = when();
            Response deleteResponse = sender.get("/api/participant/" + peter.get);
            ValidatableResponse vDeleteResponse = deleteResponse.then();
            vDeleteResponse.statusCode(HttpStatus.SC_OK);
        }
 */
    }

    @And("Patricia wants to register Peter")
    public void patriciaWantsToRegisterPeter() {
        webDriver.findElement(By.linkText("Participant Registration")).click();
    }

    @When("Patricia enters Peter's data")
    public void patriciaEntersPetersData() {
        webDriver.findElement(By.id("firstName")).sendKeys(peter.firstName);
        webDriver.findElement(By.id("lastName")).sendKeys(peter.lastName);
        webDriver.findElement(By.id("birthday")).sendKeys(peter.birthday);
        webDriver.findElement(By.id("gender")).sendKeys(peter.gender);
    }

    @And("registers them")
    public void registersThem() {
        webDriver.findElement(By.id("registerParticipant")).click();
    }

    @Then("Peter should be found in the system")
    public void peterShouldBeFoundInTheSystem() {
        idPeter = Long.parseLong(webDriver.findElement(By.id("participantId")).getText());
        RequestSender sender = RestAssured.when();
        Response response = sender.get("/api/participant/" + idPeter);
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(200);
        ParticipantDto maybePeter = vResponse.extract().as(ParticipantDto.class);
        MatcherAssert.assertThat(maybePeter.firstName, CoreMatchers.is(peter.firstName));
        MatcherAssert.assertThat(maybePeter.lastName, CoreMatchers.is(peter.lastName));
        MatcherAssert.assertThat(maybePeter.birthday, CoreMatchers.is(peter.birthday));
        MatcherAssert.assertThat(maybePeter.gender, CoreMatchers.is(peter.gender));
    }

    @And("Peter's details should be displayed")
    public void petersDetailsShouldBeDisplayed() {
        String redirectedURL = webDriver.getCurrentUrl();
        assertThat(redirectedURL, is("http://localhost:8098/#/participant/" + idPeter));
    }

    @Given("A participant")
    public void aParticipant() {
        participant = new ParticipantDto();
    }

    @And("the participant has {string}, {string}, {string} and is {string}")
    public void participantHasFirstNameLastNameBirthdayAndIs(String firstName, String lastName, String birthday, String gender) {
        participant.firstName = firstName;
        participant.lastName = lastName;
        participant.birthday = birthday;
        participant.gender = gender;
    }

    @And("the participant is not registered yet")
    public void theParticipantIsNotRegisteredYet() {
        webDriver.navigate().to("http://localhost:8098/#/participant");
        RequestSpecification request = RestAssured.given();
        request.param("firstName", participant.getFirstName());
        request.param("lastName", participant.getLastName());
        request.param("birthday", participant.getBirthday());
        RequestSender sender = request.when();
        Response response = sender.get("/api/participant/search");
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(404);
    }

    @And("Patricia wants to register the participant")
    public void patriciaWantsToRegisterTheParticipant() {
        webDriver.findElement(By.linkText("Participant Registration")).click();
    }

    @When("Patricia enters the participants data")
    public void patriciaEntersTheParticipantsData() {
        webDriver.findElement(By.id("firstName")).sendKeys(participant.firstName);
        webDriver.findElement(By.id("lastName")).sendKeys(participant.lastName);
        webDriver.findElement(By.id("birthday")).sendKeys(participant.birthday);
        webDriver.findElement(By.id("gender")).sendKeys(participant.gender);
    }

    @Then("the participant should be found in the system")
    public void theParticipantShouldBeFoundInTheSystem() {
        idParticipant = Long.parseLong(webDriver.findElement(By.id("participantId")).getText());
        RequestSender sender = RestAssured.when();
        Response response = sender.get("/api/participant/" + idParticipant);
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(200);
        ParticipantDto maybePeter = vResponse.extract().as(ParticipantDto.class);
        MatcherAssert.assertThat(maybePeter.firstName, CoreMatchers.is(participant.firstName));
        MatcherAssert.assertThat(maybePeter.lastName, CoreMatchers.is(participant.lastName));
        MatcherAssert.assertThat(maybePeter.birthday, CoreMatchers.is(participant.birthday));
        MatcherAssert.assertThat(maybePeter.gender, CoreMatchers.is(participant.gender));
    }

    @And("the participant's details should be displayed")
    public void theParticipantsDetailsShouldBeDisplayed() {
        String redirectedURL = webDriver.getCurrentUrl();
        assertThat(redirectedURL, is("http://localhost:8098/#/participant/" + idParticipant));
    }
}