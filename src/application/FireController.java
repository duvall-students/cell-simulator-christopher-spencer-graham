package application;
import java.awt.Point;

import javafx.scene.paint.Color;

public class FireController {

	private FireModel fireModel;
	private ForestDisplay forestDisplay;
	
	private double burnTime = 1;
	private double spreadProb = .4;
	
	public FireController(int numRows, int numCols, double forestDensity, int numBurningTrees, double burnTime, ForestDisplay forestDisplay) {
		fireModel = new FireModel(numRows, numCols, forestDensity, numBurningTrees, this);
		this.forestDisplay = forestDisplay;
		
	}
	
	public void newForest() {
		fireModel.createForest(fireModel.getNumRows(), fireModel.getNumCols());
	}
	
	public double getBurnTime() {
		return burnTime;
	}
	public void newSimulation() {
		
	}
	public Color getCellState(Point point) {
		return fireModel.getState(point).getColor();
	}
	
	public double getBurnProbability() {
		return spreadProb;
	}
	
	public void doOneStep(double elapsedTime) {
		for(int x = 0; x <= fireModel.getNumRows(); x++) {
			for(int y = 0; y <= fireModel.getNumCols(); y++) {
				Point cellPosition = new Point(x,y);
				fireModel.treeChangeState(cellPosition, fireModel.getState(cellPosition).act(cellPosition, elapsedTime));
			}
		}
	}
	
}
