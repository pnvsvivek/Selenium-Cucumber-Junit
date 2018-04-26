package mainRunners;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import testRunner.Chrome_TestRunner;

public class ChromeTest {

	public static void main(String[] args) {
		JUnitCore junit = new JUnitCore();
		Result result = junit.run(Chrome_TestRunner.class);
		if (result.getFailureCount() > 0) {
			System.out.println("Tests are failed");
		}
	}
}
