package tests;

import org.junit.*;

import application.FireController;
import application.FireModel;

import static org.junit.Assert.*;

import java.awt.Point;

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
	public void testLiveTreeStates() {
		
	}
	
	
	
}
