package oq_glue_code;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.MultipartBody;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static oq_glue_code.TestContext.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ParticipantOverviewStepDefs {

    @Given("a participant with first name {string}, last name {string}, birthday {string} and is {string}")
    public void aParticipantWithFirstNameLastNameBirthdayAndIs(String firstName, String lastName, String birthday, String gender) {
        addParticipant(firstName);
        participant(firstName).lastName = lastName;
        participant(firstName).birthday = birthday;
        participant(firstName).gender = gender;
    }

    @Given("a participant with first name {string}, last name {string}, birthday {string}, gender {string} is registered")
    public void aParticipantWithFirstNameLastNameBirthdayGenderIsRegistered(String firstName, String lastName, String birthday, String gender) {
        aParticipantWithFirstNameLastNameBirthdayAndIs(firstName, lastName, birthday, gender);
        ParticipantRegistrationStepDefs.patriciaWantsToRegister(firstName);
        ParticipantRegistrationStepDefs.patriciaEntersData(firstName);
        ParticipantRegistrationStepDefs.registersThem(firstName);
        shouldBeFoundInTheOverview(firstName);
    }

    @Given("participants with first name, last name, birthday, gender are registered")
    public void participants_with_first_name_last_name_birthday_gender_are_registered(DataTable dataTable) {
        for (List<String> list : dataTable.asLists()) {
            aParticipantWithFirstNameLastNameBirthdayGenderIsRegistered(list.get(0), list.get(1), list.get(2), list.get(3));
        }
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
            if (participant(firstName).firstName.equals(tableCellFirstName.getText()) && participant(firstName).lastName.equals(tableCellLastName.getText()) && participant(firstName).birthday.equals(tableCellBirthday.getText())) {
                return true;
            }
        }
        return false;
    }

    @Then("the participants should be found in the overview")
    public void the_participants_should_be_found_in_the_overview() {
        for (Participant participant : participants()) {
            participantOverviewContainsParticipant(participant.firstName);
        }
    }
}
