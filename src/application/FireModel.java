package application;
import java.awt.Point;
import javafx.scene.paint.Color;
import java.util.Random;

import cell_states.BurningState;
import cell_states.CellState;
import cell_states.EdgeCellState;
import cell_states.GroundCellState;
import cell_states.LiveTreeState;

import java.util.Collection;
import java.util.List;

/*
 * @author Christopher Boyette
 */

public class FireModel {

	
	private Double forestDensiity = 1.0;

	private int numberInitialBurningTrees = 1;
	List<Point> treePositions;
	private FireController fireController;


	Random rand = new Random();
	private CellState[][] forest;	// The squares making up the maze

	public FireModel(int rows, int columns, double forestDensity, int numBurningTrees, FireController fireController){
		assert(rows > 0 && columns > 0);
		createForest(rows, columns);
		this.fireController = fireController;
	}

	public int getNumRows(){
		assert(forest!=null);
		return forest.length;
	}

	public int getNumCols(){
		assert(forest!=null);
		return forest[0].length;
	}
	
	public CellState getState(Point position){
		assert(forest!=null);
		return forest[position.x][position.y];
	}

	/*
	 * Check to see if the square is inside the outer walls of the maze
	 */
	public boolean inBounds(Point p){
		assert(forest!=null);
		return (p!= null && p.x < forest.length-1 && p.x > 0 && p.y < forest[0].length-1 && p.y >0);
	}

	/*
	 * Check to see if the point is in bounds (won't cause out-of-bounds or null errors)
	 */
	public boolean validPoint(Point p){
		assert(forest!=null);
		return (p!=null && p.x < forest.length && p.x >= 0 && p.y < forest[0].length && p.y >= 0);
	}

	public void createForest(int rows, int cols) {
		assert(rows > 0 && cols > 0);
		forest = new CellState[rows][cols];
		double randomForestDensity = rand.nextDouble();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i == 0 || j % (cols-1) == 0) {
					forest[i][j] = new EdgeCellState(this, fireController);
				}
				else {
					if (randomForestDensity <= forestDensiity) {
						forest[i][j] = new LiveTreeState(this, fireController);
						treePositions.add(new Point(i,j));
						
					}
					else {
						forest[i][j] = new GroundCellState(this, fireController);
					}
				}
			}
		}
	}
	
	public void	assignBurningTrees() {
		int numberOfTrees = treePositions.size();
		int numberOfTreesChanged = 0;
		while (numberOfTreesChanged < numberInitialBurningTrees) {
			int burningTree = rand.nextInt(numberOfTrees);
			Point newBurningTree = treePositions.get(burningTree);
			if (!(forest[newBurningTree.x][newBurningTree.y] instanceof BurningState)) {
				forest[newBurningTree.x][newBurningTree.y] = new BurningState(this, fireController);
				numberOfTreesChanged++;
			}
		}
	}
	

	
	public void treeChangeState(Point treePosition, CellState newState) {
		forest[treePosition.x][treePosition.y] = newState;
	}
}
