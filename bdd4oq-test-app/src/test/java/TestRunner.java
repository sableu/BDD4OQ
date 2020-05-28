
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/jba_feature_files",
        glue = "glue_code",
        strict = true,
        plugin = "com.github.andreashosbach.cucumber_scenarioo_plugin.CucumberScenariooPlugin:target/scenarioo",
        tags = "not @Ignore and not @TestOfQOTestApp"
)
public class TestRunner {
}
