package tests;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestCellState.class})


public class TestSuite {
	@BeforeClass
	public static void printMe() {
		System.out.print("Running test suite including the tests of ");
		System.out.print("TestCellState ");
		System.out.println();
	}

}
