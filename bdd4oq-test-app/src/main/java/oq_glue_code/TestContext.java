package oq_glue_code;

import io.restassured.RestAssured;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestContext {
    private static TestContext instance;

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
            instance.participants = new ArrayList<>();
        }
    }

    public static WebDriver webDriver(){
        return getInstance().getWebDriver();
    }
    private List<Participant> participants =  new ArrayList<>();

    public static Participant participant(String firstName){
        return getInstance().getParticipant(firstName);
    }

    public static List<Participant> participants(){
        return getInstance().getParticipants();
    }

    public static void addParticipant(String firsName){
        Participant participant = new Participant();
        participant.firstName = firsName;
        getInstance().addParticipant(participant);
    }

    public static  void clearParticipants(){
        getInstance().getParticipants().clear();
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

    private WebDriver getWebDriver() {
        return webDriver;
    }

    private Participant getParticipant(String firstName){
        return participants.stream().filter(p -> firstName.equals(p.firstName)).findFirst().get();
    }

    private List<Participant> getParticipants(){
        return participants;
    }

    private void addParticipant(Participant participant){
        this.participants.add(participant);
    }
}
