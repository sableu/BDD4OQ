import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        junit.main("OQTestRunner");
    }
}
