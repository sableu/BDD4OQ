package oq_glue_code;

import io.cucumber.java.de.Wenn;
import oq_glue_code.backend_api.ParticipantDto;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ParticipanRegistrationStepDefs {

    private WebDriver webDriver;

    @Before
    public void before() {
        webDriver = TestContext.getInstance().getWebDriver();
    }

    ParticipantDto getParticipant() {
        return TestContext.getInstance().getParticipant();
    }

    @Given("Patricia has the application open")
    public void hasTheApplicationOpen() {
        webDriver.navigate().to("http://localhost:8098/");
    }

    @Given("a participant with first name {string}, last name {string}, birthday {string} and is {string}")
    public void aParticipantWithFirstNameLastNameBirthdayAndIs(String firstName, String lastName, String birthday, String gender) {
        TestContext.getInstance().setParticipant(new ParticipantDto());
        getParticipant().firstName = firstName;
        getParticipant().lastName = lastName;
        getParticipant().birthday = birthday;
        getParticipant().gender = gender;
    }

    @And("{string} is not registered yet")
    public void isNotRegisteredYet(String firstName) {
        assertThat(participantOverviewContainsParticipant(firstName), is(false));
    }

    @Then("{string} should be found in the overview")
    public void shouldBeFoundInTheOverview(String firstName) {
        assertThat(participantOverviewContainsParticipant(firstName), is(true));
    }

    private boolean participantOverviewContainsParticipant(String firstName) {
        webDriver.findElement(By.linkText("Participants")).click();
        List<WebElement> row = webDriver.findElements(By.xpath("//*[@id='participantTable']/tbody/tr/td[1]"));
        int numParticipants = row.size();
        WebElement participantTable = webDriver.findElement(By.id("participantTable"));
        for (int i = 1; i <= numParticipants; i++) {
            WebElement tableCellFirstName = participantTable.findElement(By.xpath("//*[@id='participantTable']/tbody/tr[" + i + "]/td[2]"));
            WebElement tableCellLastName = participantTable.findElement(By.xpath("//*[@id='participantTable']/tbody/tr[" + i + "]/td[3]"));
            WebElement tableCellBirthday = participantTable.findElement(By.xpath("//*[@id='participantTable']/tbody/tr[" + i + "]/td[4]"));
            if (getParticipant().firstName.equals(tableCellFirstName.getText()) && getParticipant().lastName.equals(tableCellLastName.getText()) && getParticipant().birthday.equals(tableCellBirthday.getText())) {
                return true;
            }
        }
        return false;
    }

    @And("Patricia wants to register {string}")
    public void patriciaWantsToRegister(String firstName) {
        webDriver.findElement(By.linkText("Participant Registration")).click();
    }

    @When("Patricia enters {string}'s data")
    public void patriciaEntersData(String firstName) {
        webDriver.findElement(By.id("firstName")).sendKeys(getParticipant().firstName);
        webDriver.findElement(By.id("lastName")).sendKeys(getParticipant().lastName);
        webDriver.findElement(By.id("birthday")).sendKeys(getParticipant().birthday);
        webDriver.findElement(By.id("gender")).sendKeys(getParticipant().gender);
    }

    @And("registers them")
    public void registersThem() {
        webDriver.findElement(By.id("registerParticipant")).click();
        getParticipant().id = Long.parseLong(webDriver.findElement(By.id("participantId")).getText());
    }

    @When("Patricia registers {string}")
    public void patriciaRegisters(String firstName){
        patriciaWantsToRegister(firstName);
        patriciaEntersData(firstName);
        registersThem();
    }

    @Then("{string}'s details should be displayed")
    public void detailsShouldBeDisplayed(String firstName) {
        assertThat(webDriver.findElement(By.id("participantFirstName")).getText(), is(getParticipant().firstName));
        assertThat(webDriver.findElement(By.id("participantLastName")).getText(), is(getParticipant().lastName));
        assertThat(webDriver.findElement(By.id("participantBirthday")).getText(), is(getParticipant().birthday));
        assertThat(webDriver.findElement(By.id("participantGender")).getText(), is(getParticipant().gender));
    }

    @Given("a participant with first name {string}, last name {string}, birthday {string}, gender {string} is registered")
    public void aParticipantWithFirstNameLastNameBirthdayGenderIsRegistered(String firstName, String lastName, String birthday, String gender) {
        aParticipantWithFirstNameLastNameBirthdayAndIs(firstName, lastName, birthday, gender);
        patriciaWantsToRegister(firstName);
        patriciaEntersData(firstName);
        registersThem();
        shouldBeFoundInTheOverview(firstName);
    }
}
