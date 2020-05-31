
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/test_feature_files",
        glue = "test_glue_code",
        strict = true,
        tags = "not @Ignore"
)
public class TestRunner {
}
