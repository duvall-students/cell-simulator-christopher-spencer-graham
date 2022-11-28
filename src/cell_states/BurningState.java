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
//		burnStart = Math.round(burnStart * 10.0) / 10.0;
		this.burnStart = burnStart;
	}

	
	@Override
	public CellState act(Point myPosition, double elapsedTime) {
//		elapsedTime = Math.round(elapsedTime * 10.0) / 10.0;
		this.color = RED;
		if(fireController.getBurnTime() <= elapsedTime - burnStart) {
			return new DeadTreeState(fireModel, fireController);
		}
		
		return this;
	}




}
