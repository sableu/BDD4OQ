package oq_glue_code;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


public class BaselineWeightMeasurementStepDefs {

    private WebDriver webDriver;

    @Before
    public void before() {
        webDriver = TestContext.getInstance().getWebDriver();
    }

    @And("{string} has no baseline weight measurement entry yet")
    public void hasNoWeightEntryYet(String firstName) {
    }

    @And("Patricia wants to set {string}'s baseline weight measurement")
    public void patriciaWantsToRegisterBaselineWeightMeasurement(String firstName) {
        webDriver.navigate().to("http://localhost:8098/#/participant/" + TestContext.getInstance().getParticipant().id);
    }

    @When("Patricia enters {double} kg in the weight field, {string} in the time field and {string} in the comment field")
    public void patriciaEntersKgInTheWeightFieldInTheTimeFieldAndInTheCommentField(Double weight, String dateTime, String comment) {
        webDriver.findElement(By.id("weight")).sendKeys(weight.toString());
        webDriver.findElement(By.id("dateTime")).sendKeys(dateTime);
        webDriver.findElement(By.id("comment")).sendKeys(comment);
    }

    @And("she saves these entries")
    public void sheSavesTheseEntries() {
        webDriver.findElement(By.id("addWeight")).click();
    }

    @Then("{string}'s baseline weight entry should be found in the system")
    public void baselineWeightEntryShouldBeFoundInTheSystem(String firstName) {
        long id = Long.parseLong(webDriver.findElement(By.id("baselineId")).getText());
        assertThat(id, not(-1));
    }
}
