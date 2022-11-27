package tests;

import org.junit.*;
import static org.junit.Assert.*;

import java.awt.Point;

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
	
	@Test
	public void testStep() {
		
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
				originalGrid[i][j] = fireController.getCellState(new Point(i, j));
				System.out.println(fireController.getCellState(new Point(i, j)));
			}
		}
		
		fireController.doOneStep(1);
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				newGrid[i][j] = fireController.getCellState(new Point(i, j));
			}
		}
//		ReadDataTestCase test = new ReadDataTestCase();
//		ArrayList<String> dataFiles = new ArrayList<String>();
//		dataFiles.add("yob1890.txt");
//		dataFiles.add("yob1891.txt");
//		dataFiles.add("yob1892.txt");
//		ArrayList<String> frequency = new ArrayList<String>();
//		ArrayList<String> rank = new ArrayList<String>();
//		ArrayList<String> name = new ArrayList<String>();
//		ArrayList<Integer> year = new ArrayList<Integer>();
//		ArrayList<String> gender = new ArrayList<String>();
//		test.loopThroughtDataFiles(dataFiles, gender, name, rank, frequency, year);
//		ArrayList<String> userInputs = new ArrayList<String>();
//		userInputs.add("Y");
//		userInputs.add("hello");
		assertEquals(originalGrid, newGrid);
	}

}
