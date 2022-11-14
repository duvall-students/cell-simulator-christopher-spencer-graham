package application;
import java.awt.Point;
import javafx.scene.paint.Color;
import java.util.Random;

import cell_states.BurningState;
import cell_states.CellState;
import cell_states.EdgeCellState;
import cell_states.GroundCellState;
import cell_states.LiveTreeState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * @author Christopher Boyette
 */

public class FireModel extends Model{

	protected List<Point> treePositions;

	public FireModel(int rows, int columns, double userForestDensity, int userNumBurningTrees, FireController fireController){
		assert(rows > 0 && columns > 0);
		treePositions = new ArrayList<>();
		this.controller = fireController;
		createForest(rows, columns, userNumBurningTrees, userForestDensity);
	}

	public void createForest(int rows, int cols, int numBurningTrees, double forestDensity) {
		createGrid(rows, cols);
		treePositions.clear();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				double randomForestDensity = rand.nextDouble();
				if (i % (rows-1) == 0 || j % (cols-1) == 0) {
//					System.out.println("i = " + i);
//					System.out.println("j = " + j);
					grid[i][j] = new EdgeCellState(this, (FireController) controller);
				}
				else {
					if (randomForestDensity <= forestDensity) {
						grid[i][j] = new LiveTreeState(this, (FireController) controller);
						treePositions.add(new Point(i,j));
						
					}
					else {
						grid[i][j] = new GroundCellState(this, (FireController) controller);
					}
				}
			}
		}
		assignBurningTrees(numBurningTrees);
	}
	
	private void assignBurningTrees(int numBurningTrees) {
		int numberOfTrees = treePositions.size();
		int numberOfTreesChanged = 0;
		while (numberOfTreesChanged < numBurningTrees) {
			int burningTree = rand.nextInt(numberOfTrees);
			Point newBurningTree = treePositions.get(burningTree);
			if (!(grid[newBurningTree.x][newBurningTree.y] instanceof BurningState)) {
				grid[newBurningTree.x][newBurningTree.y] = new BurningState(this, (FireController) controller, 0);
				numberOfTreesChanged++;
			}
		}
	}
	


}
