package oq_glue_code;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static oq_glue_code.TestContext.participant;
import static oq_glue_code.TestContext.webDriver;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ParticipantOverviewStepDefs {
    
    @Given("a participant with first name {string}, last name {string}, birthday {string} and is {string}")
    public void aParticipantWithFirstNameLastNameBirthdayAndIs(String firstName, String lastName, String birthday, String gender) {
        participant().firstName = firstName;
        participant().lastName = lastName;
        participant().birthday = birthday;
        participant().gender = gender;
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
        webDriver().findElement(By.linkText("Participants")).click();
        List<WebElement> row = webDriver().findElements(By.xpath("//*[@id='participantTable']/tbody/tr/td[1]"));
        int numParticipants = row.size();
        WebElement participantTable = webDriver().findElement(By.id("participantTable"));
        for (int i = 1; i <= numParticipants; i++) {
            WebElement tableCellFirstName = participantTable.findElement(By.xpath("//*[@id='participantTable']/tbody/tr[" + i + "]/td[2]"));
            WebElement tableCellLastName = participantTable.findElement(By.xpath("//*[@id='participantTable']/tbody/tr[" + i + "]/td[3]"));
            WebElement tableCellBirthday = participantTable.findElement(By.xpath("//*[@id='participantTable']/tbody/tr[" + i + "]/td[4]"));
            if (participant().firstName.equals(tableCellFirstName.getText()) && participant().lastName.equals(tableCellLastName.getText()) && participant().birthday.equals(tableCellBirthday.getText())) {
                return true;
            }
        }
        return false;
    }

    @Given("a participant with first name {string}, last name {string}, birthday {string}, gender {string} is registered")
    public void aParticipantWithFirstNameLastNameBirthdayGenderIsRegistered(String firstName, String lastName, String birthday, String gender) {
        aParticipantWithFirstNameLastNameBirthdayAndIs(firstName, lastName, birthday, gender);
        ParticipantRegistrationStepDefs.patriciaWantsToRegister(firstName);
        ParticipantRegistrationStepDefs.patriciaEntersData(firstName);
        ParticipantRegistrationStepDefs.registersThem();
        shouldBeFoundInTheOverview(firstName);
    }
}
