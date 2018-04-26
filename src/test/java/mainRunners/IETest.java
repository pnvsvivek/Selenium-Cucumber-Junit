package mainRunners;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import testRunner.IE_TestRunner;

public class IETest {

	public static void main(String[] args) {
		JUnitCore junit = new JUnitCore();
		Result result = junit.run(IE_TestRunner.class);
		if (result.getFailureCount() > 0) {
			System.out.println("Tests are failed");
		}
	}
}
