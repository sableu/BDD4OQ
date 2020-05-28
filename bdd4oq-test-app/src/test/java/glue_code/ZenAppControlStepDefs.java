package glue_code;

import glue_code.backend_api.ParticipantDto;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSender;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ZenAppControlStepDefs {


    private WebDriver webDriver;
    private ParticipantDto appTester;
    private Long idAppTester;


    @Before
    public void before() {
        webDriver = TestContext.getInstance().getWebDriver();
    }

    @Given("A participant AppTester")
    public void aParticipantAppTester() {
        appTester = new ParticipantDto();
    }

    @And("AppTester has first name {string}, last name {string}, birthday {string} and is {string}")
    public void appTesterHasFirstNameLastNameBirthdayAndIs(String firstName, String lastName, String birthday, String gender) {
        appTester.firstName = firstName;
        appTester.lastName = lastName;
        appTester.birthday = birthday;
        appTester.gender = gender;
    }

    @And("AppTester is not registered yet")
    public void appTesterIsNotRegisteredYet() {
        webDriver.navigate().to("http://localhost:8098/#/participant");
        RequestSpecification request = given();
        request.param("firstName", appTester.getFirstName());
        request.param("lastName", appTester.getLastName());
        request.param("birthday", appTester.getBirthday());
        RequestSender sender = request.when();
        Response response = sender.get("/api/participant/search");
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(404);
    }

    @And("Checky wants to register AppTester")
    public void checkyWantsToRegisterAppTester() {
        webDriver.findElement(By.linkText("Participant Registration")).click();
    }

    @When("Checky enters AppTester's data")
    public void checkyEntersAppTestersData() {
        webDriver.findElement(By.id("firstName")).sendKeys(appTester.firstName);
        webDriver.findElement(By.id("lastName")).sendKeys(appTester.lastName);
        webDriver.findElement(By.id("birthday")).sendKeys(appTester.birthday);
        webDriver.findElement(By.id("gender")).sendKeys(appTester.gender);
    }

    @And("registers AppTester")
    public void registersAppTester() {
    }

    @And("Checky registers AppTester")
    public void checkyRegistersAppTester() {
        webDriver.findElement(By.id("registerParticipant")).click();
    }

    @Then("AppTester should be found in the system")
    public void appTesterShouldBeFoundInTheSystem() {
        idAppTester = Long.parseLong(webDriver.findElement(By.id("participantId")).getText());
        RequestSender sender = when();
        Response response = sender.get("/api/participant/" + idAppTester);
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(200);
        ParticipantDto maybePeter = vResponse.extract().as(ParticipantDto.class);
        assertThat(maybePeter.firstName, is(appTester.firstName));
        assertThat(maybePeter.lastName, is(appTester.lastName));
        assertThat(maybePeter.birthday, is(appTester.birthday));
        assertThat(maybePeter.gender, is(appTester.gender));
    }

    @And("AppTester's details should be displayed")
    public void appTestersDetailsShouldBeDisplayed() {
        String redirectedURL = webDriver.getCurrentUrl();
        assertThat(redirectedURL, is("http://localhost:8098/#/participant/" + idAppTester));
    }
}
