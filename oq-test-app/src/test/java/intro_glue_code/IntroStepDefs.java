package intro_glue_code;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.andreashosbach.cucumber_scenarioo_plugin.model.Screenshot;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntroStepDefs {
    private WebDriver webDriver;

    @Before
    public void setupDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        webDriver = new ChromeDriver();

        webDriver.manage().window().setSize(new Dimension(1024, 768));

        webDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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

    @Given("a webbrowser")
    public void aWebbrowser() {
        webDriver.navigate().to("about:version");
    }

    @Given("the website {string} is displayed")
    public void theWebsite(String url) {
        webDriver.navigate().to(url);
    }

    @When("the user clicks on the link {string}")
    public void theLinkIsClicked(String linkText) {
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(linkText))).click();
    }

    @Then("the website with title {string} should be displayed")
    public void theWebsiteWithTitleIsDisplayed(String title) {
        assertEquals(title, webDriver.getTitle());
    }


    @Given("the user enters {string} into the field {string}")
    public void theTextIsEnteredInTheField(String text, String fieldId) {
        webDriver.findElement(By.id(fieldId)).sendKeys(text);
    }

    @When("the user clicks on the submit button")
    public void theButtonIsClicked() {
        webDriver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
    }

    @Then("a page containing the following text should be displayed")
    public void aPageContainingTheFollowingTextShouldBeDisplayed(String text) {
        assertTrue(webDriver.findElement(By.tagName("body")).getText().contains(text.replaceAll("\n", " ")));
    }
}
