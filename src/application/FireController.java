package application;
import java.awt.Point;

import javafx.scene.paint.Color;

public class FireController extends Controller{


	private double burnTime;
	private double spreadProb;

	
	public FireController(int numRows, int numCols, double forestDensity, int numBurningTrees, double userBurnTime, double userSpreadProb) {
		super();
		model = new FireModel(numRows, numCols, forestDensity, numBurningTrees, this);
		burnTime = userBurnTime;
		spreadProb = userSpreadProb;
	}
	
	public void newForest(int numRows, int numCols, int numBurningTrees, double forestDensity) {
		
		((FireModel) model).createForest(numRows, numCols, numBurningTrees, forestDensity);
		//forestDisplay.redraw();
	}
	
	public double getBurnTime() {
		return burnTime;
	}
	
	public Color getCellState(Point point) {
		return model.getState(point).getColor();
	}
	
	public double getBurnProbability() {
		return spreadProb;
	}

	
}
