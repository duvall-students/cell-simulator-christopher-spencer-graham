package cell_states;

import application.FireController;
import application.FireModel;
import javafx.scene.paint.Color;

public class EdgeCellState extends NonChangingCellState {

	private static final Color BLACK = Color.BLACK;

	public EdgeCellState(FireModel fireModel, FireController fireController) {
		super(BLACK, fireModel, fireController);
		
	}

}
