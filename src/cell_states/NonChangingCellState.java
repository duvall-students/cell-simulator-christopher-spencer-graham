package cell_states;

import application.FireController;
import application.FireModel;
import javafx.scene.paint.Color;

public abstract class NonChangingCellState extends CellState{

	public NonChangingCellState(Color color, FireModel fireModel, FireController fireController) {
		super(color, fireModel, fireController);
	}

}
