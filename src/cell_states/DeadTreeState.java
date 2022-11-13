package cell_states;

import application.FireController;
import application.FireModel;
import javafx.scene.paint.Color;

public class DeadTreeState extends NonChangingCellState {

	private static final Color YELLOW = Color.YELLOW;

	public DeadTreeState(FireModel fireModel, FireController fireController) {
		super(YELLOW, fireModel, fireController);
	}

}
