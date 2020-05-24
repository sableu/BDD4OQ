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
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;


public class ParticipantOverviewStepDefs {

    private WebDriver webDriver;
    private ParticipantDto natasha;
    private ParticipantDto maybeNatasha = new ParticipantDto();
    private Long idNatasha;

    @Before
    public void before() {
        webDriver = TestContext.getInstance().getWebDriver();
    }

    @Given("{string} {string} born the {string} cannot be found in the overview")
    public void firstNameLastNameBornTheBirthdayCannotBeFoundInTheOverview(String firstName, String lastName, String birthday) {
        natasha = new ParticipantDto();
        natasha.firstName = firstName;
        natasha.lastName = lastName;
        natasha.birthday = birthday;
        webDriver.navigate().to("http://localhost:8098/#/participant");

        List<WebElement> row = webDriver.findElements(By.xpath("//*[@id='participantTable']/tbody/tr/td[1]"));
        int numParticipants = row.size();
        WebElement participantTable = webDriver.findElement(By.id("participantTable"));
        for (int i = 1; i <= numParticipants; i++) {
            WebElement tableCellFirstName = participantTable.findElement(By.xpath("//*[@id='participantTable']/tbody/tr[" + i + "]/td[2]"));
            WebElement tableCellLastName = participantTable.findElement(By.xpath("//*[@id='participantTable']/tbody/tr[" + i + "]/td[3]"));
            WebElement tableCellBirthday = participantTable.findElement(By.xpath("//*[@id='participantTable']/tbody/tr[" + i + "]/td[4]"));
            if (natasha.firstName.equals(tableCellFirstName.getText()) && natasha.lastName.equals(tableCellLastName.getText()) && natasha.birthday.equals(tableCellBirthday.getText())) {
                fail("Natasha unexpectedly found in the overview");
            }
        }
    }

    @And("she is not registered yet")
    public void she_is_not_registered_yet() {
        webDriver.navigate().to("http://localhost:8098/");
        RequestSpecification request = given();
        request.param("firstName", natasha.getFirstName());
        request.param("lastName", natasha.getLastName());
        request.param("birthday", natasha.getBirthday());
        RequestSender sender = request.when();
        Response response = sender.get("/api/participant/search");
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(404);
    }

    @When("Patricia registers her")
    public void patricia_registers_her() {
        webDriver.navigate().to("http://localhost:8098/#/participantRegistration");
        natasha.gender = "female";
        webDriver.findElement(By.id("firstName")).sendKeys(natasha.firstName);
        webDriver.findElement(By.id("lastName")).sendKeys(natasha.lastName);
        webDriver.findElement(By.id("birthday")).sendKeys(natasha.birthday);
        webDriver.findElement(By.id("gender")).sendKeys(natasha.gender);
        webDriver.findElement(By.id("registerParticipant")).click();
    }

    @Then("Natasha should be found in the overview.")
    public void natasha_should_be_found_in_the_overview() {
        idNatasha = Long.parseLong(webDriver.findElement(By.id("participantId")).getText());
        RequestSender sender = when();
        Response response = sender.get("/api/participant/" + idNatasha);
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(200);
        ParticipantDto maybeNatasha = vResponse.extract().as(ParticipantDto.class);
        assertThat(maybeNatasha.firstName, is(natasha.firstName));
        assertThat(maybeNatasha.lastName, is(natasha.lastName));
        assertThat(maybeNatasha.birthday, is(natasha.birthday));
        assertThat(maybeNatasha.gender, is(natasha.gender));
    }
}