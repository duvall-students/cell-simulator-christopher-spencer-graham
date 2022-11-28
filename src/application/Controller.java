package application;

import java.awt.Point;

import cell_states.CellState;
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
				updateCellState(cellPosition, model.getState(cellPosition).act(cellPosition, totalElapsedTime));
			}
		}
	}
	
	public void updateCellState(Point cellPosition, CellState state) {
		model.changeState(cellPosition, state);
	}
  
	public Color getCellStateColor(Point point) {
		return getCellState(point).getColor();
	}
	
	public CellState getCellState(Point point) {
		return model.getState(point);
	}
}
