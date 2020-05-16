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
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
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
    private ParticipantDto peter;

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



    @Given("A participant Peter")
    public void aParticipantPeter(){
        peter = new ParticipantDto();
}

    @And("Peter has first name {string}, last name {string}, birthday {string} and is {string}")
    public void peterHasFirstNameLastNameBirthdayAndIs(String firstName, String lastName, String birthday, String gender){
        peter.firstName = firstName;
        peter.lastName = lastName;
        peter.birthday = birthday;
        peter.gender = gender;
    }


    @And("Peter is not registered yet")
    public void peterIsNotRegisteredYet(){
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
        webDriver.navigate().to("http://localhost:8098/");
    }

    @When("Patricia enters Peter's data")
    public void patriciaEntersPetersData() {
        webDriver.findElement(By.id("firstName")).sendKeys(peter.firstName);
        webDriver.findElement(By.id("lastName")).sendKeys(peter.lastName);
        webDriver.findElement(By.id("birthday")).sendKeys(peter.birthday);
        webDriver.findElement(By.id("gender")).sendKeys(peter.gender);
    }

    @And ("wants to register them")
    public void wantsToRegisterThem(){
        webDriver.findElement(By.id("registerParticipant")).click();
    }

    @Then("Peter should be found in the system")
    public void peterCanBeFoundInTheSystem(){
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
    }



}
