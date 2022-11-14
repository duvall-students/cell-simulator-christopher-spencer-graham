package application;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import cell_states.CellState;

public abstract class Model {
	
	
	protected Controller controller;

	protected Random rand = new Random();
	protected CellState[][] grid;	// The squares making up the maze
	

	public int getNumRows(){
		assert(grid!=null);
		return grid.length;
	}

	public int getNumCols(){
		assert(grid!=null);
		return grid[0].length;
	}
	
	public CellState getState(Point position){
		assert(grid!=null);
		return grid[position.x][position.y];
	}

	protected void createGrid(int rows, int cols) {
		assert(rows > 0 && cols > 0);
		grid = new CellState[rows][cols];
	}
	
	/*
	 * Check to see if the square is inside the outer walls of the maze
	 */
	public boolean inBounds(Point p){
		assert(grid!=null);
		return (p!= null && p.x < grid.length-1 && p.x > 0 && p.y < grid[0].length-1 && p.y >0);
	}

	/*
	 * Check to see if the point is in bounds (won't cause out-of-bounds or null errors)
	 */
	public boolean validPoint(Point p){
		assert(grid!=null);
		return (p!=null && p.x < grid.length && p.x >= 0 && p.y < grid[0].length && p.y >= 0);
	}

	
	public void changeState(Point cellPosition, CellState newState) {
		grid[cellPosition.x][cellPosition.y] = newState;
	}
}
