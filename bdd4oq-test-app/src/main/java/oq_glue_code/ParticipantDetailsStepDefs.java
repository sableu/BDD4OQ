package oq_glue_code;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static oq_glue_code.TestContext.participant;
import static oq_glue_code.TestContext.webDriver;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


public class ParticipantDetailsStepDefs {

    WeightEntry participantBaselineWeightMeasurement = new WeightEntry();

    @Then("{string}'s details should be displayed")
    public void detailsShouldBeDisplayed(String firstName) {
        assertThat(webDriver().findElement(By.id("participantFirstName")).getText(), is(participant(firstName).firstName));
        assertThat(webDriver().findElement(By.id("participantLastName")).getText(), is(participant(firstName).lastName));
        assertThat(webDriver().findElement(By.id("participantBirthday")).getText(), is(participant(firstName).birthday));
        assertThat(webDriver().findElement(By.id("participantGender")).getText(), is(participant(firstName).gender));
    }

    @And("{string} has no baseline weight measurement entry yet")
    public void hasNoWeightEntryYet(String firstName) {
        webDriver().navigate().to("http://localhost:8098/#/participant/" + participant(firstName).id);
        assertThat(webDriver().findElement(By.id("weight")).getText(), is(""));
    }

    @And("Patricia wants to set {string}'s baseline weight measurement")
    public void patriciaWantsToRegisterBaselineWeightMeasurement(String firstName) {
        webDriver().navigate().to("http://localhost:8098/#/participant/" + participant(firstName).id);
    }

    @When("Patricia enters {double} kg in the weight field, {string} in the time field and {string} in the comment field")
    public void patriciaEntersKgInTheWeightFieldInTheTimeFieldAndInTheCommentField(Double weight, String dateTime, String comment) {
        participantBaselineWeightMeasurement.weight = weight;
        participantBaselineWeightMeasurement.dateTime = dateTime;
        participantBaselineWeightMeasurement.comment = comment;
        webDriver().findElement(By.id("weight")).sendKeys(weight.toString());
        webDriver().findElement(By.id("dateTime")).sendKeys(dateTime);
        webDriver().findElement(By.id("comment")).sendKeys(comment);
    }

    @And("she saves these entries")
    public void sheSavesTheseEntries() {
        webDriver().findElement(By.id("setBaselineWeight")).click();
    }

    @Then("{string}'s baseline weight entry should be found in the system")
    public void baselineWeightEntryShouldBeFoundInTheSystem(String firstName) {
        long id = Long.parseLong(webDriver().findElement(By.id("baselineId")).getText());
        assertThat(id, not(-1));
        //assertThat(webDriver().findElement(By.id("weight")).getText(), is(participantBaselineWeightMeasurement.weight));
    }

    @When("Patricia enters {double} kg and any valid date time")
    public void patriciaEntersKg(Double weight) {
        webDriver().findElement(By.id("weight")).sendKeys(weight.toString());
        webDriver().findElement(By.id("dateTime")).sendKeys("06-Jun-2020, 4:15pm");
    }

    @Then("she can set the baseline weight measurement")
    public void sheCanSetTheBaselineWeightMeasurement() {
        assertThat(webDriver().findElement(By.id("setBaselineWeight")).isEnabled(), is(true));
        webDriver().findElement(By.id("setBaselineWeight")).click();
    }

    @Then("she cannot set the baseline weight measurement")
    public void sheCannotSetTheBaselineWeightMeasurement() {
        assertThat(webDriver().findElement(By.id("setBaselineWeight")).isEnabled(), is(false));
    }

    @And("{string}'s baseline weight measurement is set")
    public void baselineWeightMeasurementIsSet(String firstName) {
        webDriver().navigate().to("http://localhost:8098/#/participant/" + participant(firstName).id);
        patriciaEntersKgInTheWeightFieldInTheTimeFieldAndInTheCommentField(68.5, "15.5.20, 8:15am", "any comment");
        sheSavesTheseEntries();
        webDriver().navigate().refresh();
    }

    @Then("{string}'s baseline weight entry should be displayed on that page")
    public void baselineWeightEntryShouldBeDisplayedOnThatPage(String firstName) {
        assertThat(webDriver().findElement(By.id("weight")).getAttribute("value"), is(participantBaselineWeightMeasurement.weight.toString()));
    }

    @Given("{string} did not give her consent so far")
    public void did_not_give_her_consent_so_far(String firstName) {
        webDriver().findElement(By.xpath("//*[@id=\"participantTable\"]/tbody/tr")).click();
        assertThat(webDriver().findElement(By.id("participantConsent")).isSelected(), is(false));
    }

    @When("Patricia registers that {string} gave her consent")
    public void patricia_registers_that_gave_her_consent(String string) {
        webDriver().findElement(By.xpath("//*[@for=\"participantConsent\"]")).click();
        webDriver().findElement(By.id("updateConsent")).click();
    }
    @When("she displays {string}'s details")
    public void she_displays_s_details(String string) {
        webDriver().navigate().refresh();
    }
    @Then("Patricia should see on the participant detail page that the consent was given.")
    public void patricia_should_see_on_the_participant_detail_page_that_the_consent_was_given() {
        assertThat(webDriver().findElement(By.id("participantConsent")).isSelected(), is(true));
    }
}
