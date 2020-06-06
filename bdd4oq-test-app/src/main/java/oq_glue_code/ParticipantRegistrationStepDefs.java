package oq_glue_code;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;

import static oq_glue_code.TestContext.*;


public class ParticipantRegistrationStepDefs {

    @And("Patricia wants to register {string}")
    public static void  patriciaWantsToRegister(String firstName) {
        webDriver().findElement(By.linkText("Participant Registration")).click();
        addParticipant(firstName);
    }

    @When("Patricia enters {string}'s data")
    public static void patriciaEntersData(String firstName) {
        webDriver().findElement(By.id("firstName")).sendKeys(participant(firstName).firstName);
        webDriver().findElement(By.id("lastName")).sendKeys(participant(firstName).lastName);
        webDriver().findElement(By.id("birthday")).sendKeys(participant(firstName).birthday);
        webDriver().findElement(By.id("gender")).sendKeys(participant(firstName).gender);
    }

    @And("registers {string}'s data")
    public static void registersThem(String firstName) {
        webDriver().findElement(By.id("registerParticipant")).click();
        participant(firstName).id = Long.parseLong(webDriver().findElement(By.id("participantId")).getText());
    }

    @When("Patricia registers {string}")
    public void patriciaRegisters(String firstName){
        patriciaWantsToRegister(firstName);
        patriciaEntersData(firstName);
        registersThem(firstName);
    }
}
