
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/jba_feature_files",
        glue = "oq_glue_code",
        strict = true,
        plugin = "com.github.andreashosbach.cucumber_scenarioo_plugin.CucumberScenariooPlugin:target/scenarioo",
        tags = "not @Ignore"
)
public class OQTestRunner {
}