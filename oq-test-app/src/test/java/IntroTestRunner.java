
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/intro_feature_files",
        glue = "intro_glue_code",
        strict = true,
        plugin = "com.github.andreashosbach.cucumber_scenarioo_plugin.CucumberScenariooPlugin:target/scenarioo"
)
public class IntroTestRunner {
}
