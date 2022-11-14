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

public class FireModel {

	List<Point> treePositions;
	private FireController fireController;

	Random rand = new Random();
	private CellState[][] forest;	// The squares making up the maze

	public FireModel(int rows, int columns, double userForestDensity, int userNumBurningTrees, FireController fireController){
		assert(rows > 0 && columns > 0);
		treePositions = new ArrayList<>();
		this.fireController = fireController;
		createForest(rows, columns, userNumBurningTrees, userForestDensity);
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

	public void createForest(int rows, int cols, int numBurningTrees, double forestDensity) {
		assert(rows > 0 && cols > 0);
		treePositions.clear();
		forest = new CellState[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				double randomForestDensity = rand.nextDouble();
				if (i % (rows-1) == 0 || j % (cols-1) == 0) {
//					System.out.println("i = " + i);
//					System.out.println("j = " + j);
					forest[i][j] = new EdgeCellState(this, fireController);
				}
				else {
					if (randomForestDensity <= forestDensity) {
						forest[i][j] = new LiveTreeState(this, fireController);
						treePositions.add(new Point(i,j));
						
					}
					else {
						forest[i][j] = new GroundCellState(this, fireController);
					}
				}
			}
		}
		assignBurningTrees(numBurningTrees);
	}
	
	public void	assignBurningTrees(int numBurningTrees) {
		int numberOfTrees = treePositions.size();
		int numberOfTreesChanged = 0;
		while (numberOfTreesChanged < numBurningTrees) {
			int burningTree = rand.nextInt(numberOfTrees);
			Point newBurningTree = treePositions.get(burningTree);
			if (!(forest[newBurningTree.x][newBurningTree.y] instanceof BurningState)) {
				forest[newBurningTree.x][newBurningTree.y] = new BurningState(this, fireController, 0);
				numberOfTreesChanged++;
			}
		}
	}
	

	
	public void treeChangeState(Point treePosition, CellState newState) {
		forest[treePosition.x][treePosition.y] = newState;
	}
}
