package cell_states;

import application.FireController;
import application.FireModel;
import javafx.scene.paint.Color;

public class GroundCellState extends NonChangingCellState {

	private static final Color BROWN = Color.BROWN;

	public GroundCellState(FireModel fireModel, FireController fireController) {
		super(BROWN, fireModel, fireController);
		// TODO Auto-generated constructor stub
	}

}
