package oq_glue_code;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;

import static oq_glue_code.TestContext.participant;
import static oq_glue_code.TestContext.webDriver;


public class ParticipantRegistrationStepDefs {

    @And("Patricia wants to register {string}")
    public static void  patriciaWantsToRegister(String firstName) {
        webDriver().findElement(By.linkText("Participant Registration")).click();
    }

    @When("Patricia enters {string}'s data")
    public static void patriciaEntersData(String firstName) {
        webDriver().findElement(By.id("firstName")).sendKeys(participant().firstName);
        webDriver().findElement(By.id("lastName")).sendKeys(participant().lastName);
        webDriver().findElement(By.id("birthday")).sendKeys(participant().birthday);
        webDriver().findElement(By.id("gender")).sendKeys(participant().gender);
    }

    @And("registers them")
    public static void registersThem() {
        webDriver().findElement(By.id("registerParticipant")).click();
        participant().id = Long.parseLong(webDriver().findElement(By.id("participantId")).getText());
    }

    @When("Patricia registers {string}")
    public void patriciaRegisters(String firstName){
        patriciaWantsToRegister(firstName);
        patriciaEntersData(firstName);
        registersThem();
    }
}
