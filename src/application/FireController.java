package application;
import java.awt.Point;

import javafx.scene.paint.Color;

public class FireController {

	private FireModel fireModel;
	private ForestDisplay forestDisplay;
	private double burnTime;
	private double spreadProb;
	
	public FireController(int numRows, int numCols, double forestDensity, int numBurningTrees, double userBurnTime, double userSpreadProb, ForestDisplay forestDisplay) {
		fireModel = new FireModel(numRows, numCols, forestDensity, numBurningTrees, this);
		this.forestDisplay = forestDisplay;
		burnTime = userBurnTime;
		spreadProb = userSpreadProb;
	}
	
	public void newForest(int numRows, int numCols, int numBurningTrees, double forestDensity) {
		fireModel.createForest(numRows, numCols, numBurningTrees, forestDensity);
		//forestDisplay.redraw();
	}
	
	public double getBurnTime() {
		return burnTime;
	}
	
	public Color getCellState(Point point) {
		return fireModel.getState(point).getColor();
	}
	
	public double getBurnProbability() {
		return spreadProb;
	}
	
	public void doOneStep(double elapsedTime) {
		for(int x = 0; x < fireModel.getNumRows(); x++) {
			for(int y = 0; y < fireModel.getNumCols(); y++) {
				Point cellPosition = new Point(x,y);
				fireModel.treeChangeState(cellPosition, fireModel.getState(cellPosition).act(cellPosition, elapsedTime));
			}
		}
	}
	
}
