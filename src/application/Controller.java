package application;

import java.awt.Point;

import javafx.scene.paint.Color;

public abstract class Controller {
	
	protected Model model;
	protected double totalElapsedTime;
	
	public Controller() {
		totalElapsedTime = 0;
	}
	
	public void doOneStep(double elapsedTime) {
		totalElapsedTime += elapsedTime;
		for(int x = 0; x < model.getNumRows(); x++) {
			for(int y = 0; y < model.getNumCols(); y++) {
				Point cellPosition = new Point(x,y);
				model.changeState(cellPosition, model.getState(cellPosition).act(cellPosition, totalElapsedTime));
			}
		}
	}
  
	public Color getCellState(Point point) {
		return model.getState(point).getColor();
	}
}
