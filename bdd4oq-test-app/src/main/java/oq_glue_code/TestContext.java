package oq_glue_code;

import io.restassured.RestAssured;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TestContext {
    private static TestContext instance;
    private Participant participant;

    public static TestContext getInstance() {
        if (instance == null) {
            instance = new TestContext();
        }
        return instance;
    }

    public static void cleanup() {
        if (instance != null) {
            instance.webDriver.close();
            instance.webDriver.quit();
            instance = null;
        }
    }

    public static WebDriver webDriver(){
        return getInstance().getWebDriver();
    }

    public static Participant participant(){
        return getInstance().getParticipant();
    }

    private WebDriver webDriver;

    private TestContext() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().setSize(new Dimension(1024, 768));
        webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8098;

    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public Participant getParticipant(){
        return participant;
    }

    public void setParticipant(Participant participant){
        this.participant = participant;
    }
}
