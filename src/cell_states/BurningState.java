package cell_states;

import java.awt.Point;

import application.FireController;
import application.FireModel;
import javafx.scene.paint.Color;

public class BurningState extends CellState {


	
	private static final Color RED = Color.RED;
	private double burnStart;

	public BurningState(FireModel fireModel, FireController fireController, double burnStart) {
		super(RED, fireModel, fireController);
		this.burnStart = burnStart;
	}

	
	@Override
	public CellState act(Point myPosition, double elapsedTime) {
		this.color = RED;
		if(fireController.getBurnTime() <= elapsedTime - burnStart) {
			return new DeadTreeState(fireModel, fireController);
		}
		
		return this;
	}




}
