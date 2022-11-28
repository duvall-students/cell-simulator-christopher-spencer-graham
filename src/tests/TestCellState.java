package tests;

import org.junit.*;

import application.FireController;
import application.FireModel;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import cell_states.*;

public class TestCellState {

	private int numRows = 25;
	private int numCols = 25;
	private double forestDensity = 1;
	private int numBurningTrees = 1;
	private double burnTime = 1.0;
	private double spreadProb = 1;
	
	
	@Test
	public void testBurningState() {
		//controller and model dont matter for the test so just make a random one of each
		FireController controller = new FireController(numRows , numCols, forestDensity, numBurningTrees, burnTime, spreadProb);
		FireModel model = new FireModel(numRows, numCols, forestDensity, numBurningTrees, controller);
		double endTime = 100;
		double stepSize = 0.1;
		//for each startTime between 0 and endTime make a burnState at that startTime and then 
		//loop through every elapsedTime between 0 and burnTime + endTime
		//if the elapsedTime is >= startTime + burnTime then the state should become a deadState
		//otherwise it should remain as a burningState
		for(int time = 0; time <= endTime; time++) {
			double startTime = time * stepSize;
			for(int eTime = 0; eTime <= endTime + burnTime; eTime++) {
				double elapsedTime = eTime * stepSize;
				if(elapsedTime >= startTime + burnTime) {
					assert(testBurningStateHelper(model, controller, startTime, elapsedTime) instanceof DeadTreeState);
				}
				else {
					assert(testBurningStateHelper(model, controller, startTime, elapsedTime) instanceof BurningState);
				}
			}
		}
	}
	
	public CellState testBurningStateHelper(FireModel model, FireController controller, double burnStart, double elapsedTime) {
		return new BurningState(model, controller, burnStart).act(new Point(0,0), elapsedTime);
	}
	
	
	
	@Test
	public void testNonChangingStates() {
		//controller and model dont matter for the test so just make a random one of each
		FireController controller = new FireController(numRows , numCols, forestDensity, numBurningTrees, burnTime, spreadProb);
		FireModel model = new FireModel(numRows, numCols, forestDensity, numBurningTrees, controller);
		EdgeCellState edge = new EdgeCellState(model, controller);
		DeadTreeState dead = new DeadTreeState(model, controller);
		GroundCellState ground = new GroundCellState(model, controller);
		for(double i = 0; i <= 10; i++) {
			assert(edge.act(new Point(0,0), i) instanceof EdgeCellState);
			assert(dead.act(new Point(0,0), i) instanceof DeadTreeState);
			assert(ground.act(new Point(0,0), i) instanceof GroundCellState);
		}
		
	
	}
	
	
	@Test
	public void testLiveTreeStatesOneBurningNeighbor() {
		//controller and model dont matter for the test so just make a random one of each
		FireController controller = new FireController(numRows , numCols, forestDensity, numBurningTrees, burnTime, spreadProb);
		FireModel model = new FireModel(numRows, numCols, forestDensity, numBurningTrees, controller);
		//make a new liveTree and a new burning tree
		LiveTreeState live = new LiveTreeState(model, controller);
		Collection<CellState> burningTrees = new ArrayList<>();
		burningTrees.add(new BurningState(model, controller, 0));
		double avg = testLiveTrees(live, burningTrees);
		//check if the average of the tests is within a certain tolerance of the spreadProb
		assert(equals(avg, spreadProb));
	}
	
//	@Test
//	public void testLiveTreeStatesTwoBurningNeighbor() {
//		//controller and model dont matter for the test so just make a random one of each
//		FireController controller = new FireController(numRows , numCols, forestDensity, numBurningTrees, burnTime, spreadProb);
//		FireModel model = new FireModel(numRows, numCols, forestDensity, numBurningTrees, controller);
//		//make a new liveTree and a new burning tree
//		LiveTreeState live = new LiveTreeState(model, controller);
//		Collection<CellState> burningTrees = new ArrayList<>();
//		burningTrees.add(new BurningState(model, controller, 0));
//		burningTrees.add(new BurningState(model, controller, 0));
//		double avg = testLiveTrees(live, burningTrees);
//		//check if the average of the tests is within a certain tolerance of the spreadProb
//		System.out.println(avg);
//		assert(equals(avg, spreadProb*2));
//	}
	
	public double testLiveTrees(LiveTreeState live, Collection<CellState> burningTrees) {
		int numTests = 1000;
		double[] testResults = new double[numTests];
		//each test will run testSize number of times
		int testSize = 1000;
		
		//get the probability of spreading from each test then average all of them
		for(int i = 0; i < testResults.length; i++) {
			testResults[i] = liveTreeTestHelper(live, burningTrees, testSize)/(double)testSize;
		}
		double sum = 0;
		for(int i = 0; i < testResults.length; i++) {
			sum += testResults[i];
		}
		double avg = sum/testResults.length;
		//System.out.println(avg);
		return avg;
	}
	
	private int liveTreeTestHelper(LiveTreeState live, Collection<CellState> burningTrees, int testSize) {
		int numTrues = 0;
		
		for(int i = 0; i <= testSize; i++) {
			CellState newCell = live.act(new Point(0,0), 0, burningTrees);
			if(newCell instanceof BurningState) {
				numTrues++;
			}
		}
		return numTrues;
	}
	
	/**
	 * equals method for checking small differences taken from
	 * http://www.java2s.com/Tutorials/Java/Data_Type/Double/Check_if_two_double_values_are_almost_equal_in_Java.htm
	 */
	private final double EPSILON = 0.01;
	
	/** Compare two doubles, using default epsilon */
	  public boolean equals(double a, double b) {
	    if (a==b) return true;
	    // If the difference is less than epsilon, treat as equal.
	    return Math.abs(a - b) < EPSILON * Math.max(Math.abs(a), Math.abs(b));
	  }
	
	
	
}
