
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/intro_feature_files",
        glue = "intro_glue_files",
        strict = true)


public class IntroTestRunner {
}
