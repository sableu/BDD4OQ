import oq_glue_code.TestContext;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class Main {
    public static void main(String[] args) {
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        runTests(listener);
        TestContext.cleanup();
        listener.getSummary().printTo(new PrintWriter(System.out));
        listener.getSummary().getFailures().stream().forEach(f -> System.out.println("[FAILED] "+f.getTestIdentifier().getDisplayName()));
    }

    private static void runTests(TestExecutionListener listener) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(OQRunner.class))
                .build();
        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(request);
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }
}
