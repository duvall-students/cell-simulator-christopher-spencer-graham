import java.awt.Point;

public class FireController {

	private FireModel fireModel;
	private ForestDisplay forestDisplay;
	
	private double burnTime = 1;
	private double spreadProb = .4;
	
	public FireController(int numRows, int numCols, double forestDensity, int numBurningTrees, ForestDisplay forestDisplay) {
		fireModel = new FireModel(numRows, numCols, forestDensity, numBurningTrees);
		this.forestDisplay = forestDisplay;
		
	}
	
	public void newForest() {
		fireModel.createForest(fireModel.getNumRows(), fireModel.getNumCols());
	}
	
	
	public void startSimulation() {
		
	}
	
	public void doOneStep() {
		for(int x = 0; x <= fireModel.getNumRows(); x++) {
			for(int y = 0; y <= fireModel.getNumCols(); y++) {
				fireModel.getState(new Point(x,y)).act();
			}
		}
	}
	
}
