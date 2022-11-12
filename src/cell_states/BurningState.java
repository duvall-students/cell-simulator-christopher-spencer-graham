package cell_states;

import java.awt.Point;

import application.FireController;
import application.FireModel;
import javafx.scene.paint.Color;

public class BurningState extends CellState {


	
	public BurningState(FireModel fireModel, FireController fireController) {
		super(Color.RED, fireModel, fireController);

		
	}

	
	@Override
	public CellState act(Point myPosition) {
		// TODO Auto-generated method stub
		return null;
	}




}
