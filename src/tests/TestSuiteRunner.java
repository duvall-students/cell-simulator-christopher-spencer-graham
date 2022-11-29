package tests;

import org.junit.runner.notification.Failure;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class TestSuiteRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestSuite.class);
		for(Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println("Number of tests to run: " + result.getRunCount());
		System.out.println("Number of tests failed: " + result.getFailureCount());
		
		if(result.wasSuccessful()) {
			System.out.println("All Tests Passed");
		}
		System.out.println("Time taken: " + (double)result.getRunTime()/1000.0 + " seconds");
		
	}
}
