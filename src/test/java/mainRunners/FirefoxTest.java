package mainRunners;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import testRunner.Firefox_TestRunner;

public class FirefoxTest {

	public static void main(String[] args) {
		JUnitCore junit = new JUnitCore();
		Result result = junit.run(Firefox_TestRunner.class);
		if (result.getFailureCount() > 0) {
			System.out.println("Tests are failed");
		}
	}
}
