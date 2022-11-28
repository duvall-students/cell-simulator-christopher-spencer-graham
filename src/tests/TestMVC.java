package tests;

import org.junit.*;
import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Arrays;

import application.FireController;
import application.FireModel;
import cell_states.CellState;
import javafx.scene.paint.Color;

public class TestMVC {

	FireController fireController;
	int rows;
	int columns;
	double userForestDensity;
	int userNumBurningTrees;
	double userBurnTime;
	double userSpread;
	protected Color[][] originalGrid;
	protected Color[][] newGrid;
	
	public boolean testStep() {
		
		rows = 4;
		columns = 4;
		userForestDensity = 1;
		userNumBurningTrees = 1;
		userBurnTime = 1.0;
		userSpread = 0.4;
		originalGrid = new Color[rows][columns];
		newGrid = new Color[rows][columns];
		fireController = new FireController(rows, columns, userForestDensity, userNumBurningTrees, userBurnTime, userSpread);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				originalGrid[i][j] = fireController.getCellStateColor(new Point(i, j));
//				System.out.println(fireController.getCellState(new Point(i, j)));
			}
		}
		
		fireController.doOneStep(1);
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				newGrid[i][j] = fireController.getCellStateColor(new Point(i, j));
			}
		}
		
//		for (int i = 0; i < rows; i++) {
//			for (int j = 0; j < columns; j++) {
//				if ((originalGrid[i][j]).equals(newGrid[i][j])) {
//					System.out.println(i + j);
//				}
//				
//			}
//		}
		
		if (Arrays.deepEquals(originalGrid, newGrid)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Test
	public void TestMVCRepeatably() {
		int timesSuccess = 0;
	    for (int i = 0; i < 10000; i++) {
	        boolean result = testStep();
	    	if (result) {
	        	timesSuccess += 1;
	        }
	    }
	    System.out.println(timesSuccess);
	    assertEquals(timesSuccess,1600,1);
	}

}
