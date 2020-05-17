package glue_code;

import glue_code.backend_api.ParticipantDto;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.*;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSender;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ParticipanRegistrationStepDefs {

   private ParticipantDto peter;
   private WebDriver webDriver;

   @Before
   public void before(){
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
        webDriver.navigate().to("http://localhost:8098/");
        RequestSpecification request = given();
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
        //webDriver.navigate().to("http://localhost:8098/");
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
        long id = Long.parseLong(webDriver.findElement(By.id("registrationId")).getText());
        RequestSender sender = when();
        Response response = sender.get("/api/participant/" + id);
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(200);
        ParticipantDto maybePeter = vResponse.extract().as(ParticipantDto.class);
        assertThat(maybePeter.firstName, is(peter.firstName));
        assertThat(maybePeter.lastName, is(peter.lastName));
        assertThat(maybePeter.birthday, is(peter.birthday));
        assertThat(maybePeter.gender, is(peter.gender));

        webDriver.navigate().to("http://localhost:8098/#/participant/" + id);
    }


}
