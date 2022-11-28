package tests;

import org.junit.*;

import application.FireController;

import static org.junit.Assert.*;
import cell_states.*;

public class TestCellState {

	private int numRows = 25;
	private int numCols = 25;
	private double forestDensity = 1;
	private int numBurningTrees = 1;
	private double burnTime = 1.0;
	private double spreadProb = 0.4;
	
	
	@Test
	public void testBurningState() {
		FireController controller = new FireController(numRows , numCols, forestDensity, numBurningTrees, burnTime, spreadProb);
		
		BurningState burningState = new BurningState();
	}
}
