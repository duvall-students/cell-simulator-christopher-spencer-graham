package cell_states;

import java.awt.Point;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import application.FireController;
import application.FireModel;
import javafx.scene.paint.Color;

public class LiveTreeState extends CellState {

	private static final Color GREEN = Color.GREEN;

	public LiveTreeState(FireModel fireModel, FireController fireController) {
		super(GREEN, fireModel, fireController);
		
	}

	@Override
	public CellState act(Point myPosition, double elapsedTime) {
		Random random = new Random();
		this.color = GREEN;
		Collection<Point> neighbors = getNeighbors(myPosition);
		for(Point c : neighbors) {
			BurningState possibleBurningState = null;
			try {
				possibleBurningState = (BurningState) fireModel.getState(c);
			}
			catch(ClassCastException e) {
				continue;
			}
			if(possibleBurningState != null) {
				if(fireController.getBurnProbability() < random.nextDouble()) {
					return new BurningState(fireModel, fireController, elapsedTime);
				}
			}
		}
		return this;
	}

}
