package cell_states;

import java.awt.Point;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import application.FireController;
import application.FireModel;
import javafx.scene.paint.Color;
import java.lang.ArrayIndexOutOfBoundsException;

public class LiveTreeState extends CellState {

	private static final Color GREEN = Color.GREEN;

	public LiveTreeState(FireModel fireModel, FireController fireController) {
		super(GREEN, fireModel, fireController);
		
	}

	@Override
	public CellState act(Point myPosition, double elapsedTime) {
		Collection<CellState> neighbors = getNeighbors(myPosition);
		return act(myPosition, elapsedTime, neighbors);
	}
	
	public CellState act(Point myPosition, double elapsedTime, Collection<CellState> neighbors) {
		this.color = GREEN;
		Random random = new Random();
		for(CellState c : neighbors) {
			if(c instanceof BurningState) {
				if(fireController.getBurnProbability() > random.nextDouble()) {
					return new BurningState(fireModel, fireController, elapsedTime);
				}
			}
		}
		return this;
	}

}
