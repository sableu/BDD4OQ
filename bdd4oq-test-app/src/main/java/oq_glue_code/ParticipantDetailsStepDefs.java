package oq_glue_code;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;

import static oq_glue_code.TestContext.participant;
import static oq_glue_code.TestContext.webDriver;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


public class ParticipantDetailsStepDefs {

    @Then("{string}'s details should be displayed")
    public void detailsShouldBeDisplayed(String firstName) {
        assertThat(webDriver().findElement(By.id("participantFirstName")).getText(), is(participant().firstName));
        assertThat(webDriver().findElement(By.id("participantLastName")).getText(), is(participant().lastName));
        assertThat(webDriver().findElement(By.id("participantBirthday")).getText(), is(participant().birthday));
        assertThat(webDriver().findElement(By.id("participantGender")).getText(), is(participant().gender));
    }

    @And("{string} has no baseline weight measurement entry yet")
    public void hasNoWeightEntryYet(String firstName) {
    }

    @And("Patricia wants to set {string}'s baseline weight measurement")
    public void patriciaWantsToRegisterBaselineWeightMeasurement(String firstName) {
        webDriver().navigate().to("http://localhost:8098/#/participant/" + participant().id);
    }

    @When("Patricia enters {double} kg in the weight field, {string} in the time field and {string} in the comment field")
    public void patriciaEntersKgInTheWeightFieldInTheTimeFieldAndInTheCommentField(Double weight, String dateTime, String comment) {
        webDriver().findElement(By.id("weight")).sendKeys(weight.toString());
        webDriver().findElement(By.id("dateTime")).sendKeys(dateTime);
        webDriver().findElement(By.id("comment")).sendKeys(comment);
    }

    @And("she saves these entries")
    public void sheSavesTheseEntries() {
        webDriver().findElement(By.id("addWeight")).click();
    }

    @Then("{string}'s baseline weight entry should be found in the system")
    public void baselineWeightEntryShouldBeFoundInTheSystem(String firstName) {
        long id = Long.parseLong(webDriver().findElement(By.id("baselineId")).getText());
        assertThat(id, not(-1));
    }
}
