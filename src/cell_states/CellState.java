package cell_states;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import application.FireController;
import application.FireModel;
import javafx.scene.paint.Color;

public abstract class CellState {
	
	protected Color color;
	protected FireModel fireModel;
	protected FireController fireController;

	public CellState(Color color, FireModel fireModel, FireController fireController) {
		this.color = color;
		this.fireModel = fireModel;
		this.fireController = fireController;
	}
	/**
	 * by default a cell doesn't do anything. subclasses must override in order for act to do anything 
	 * 
	 * @param myPosition
	 * @return CellState by default it returns itself
	 */
	public CellState act(Point myPosition, double elapsedTime) {
		return this;
	}
	
	public Color getColor() {
		return color;
	}
	
	/**
	 * getNeighbors finds the cells that are neighboring this cell.
	 * 
	 * a cell's neighbors are defined as those cells that are orthogonally adjacent to it
	 * 
	 * i.e. 		
	 * 					x#x
	 * 					#@#
	 * 					x#x
	 * a cell at the location of the @ symbol would have neighbors at the # symbols,
	 * but the cells at the x's would not be neighbors
	 * 
	 * @param cellPosition a point that says where the cell is in a 2D grid of cells
	 * @return cellNeighbors a list of cellPosistions that are neighbors to this
	 */
	public Collection<CellState> getNeighbors(Point cellPosition) {
		List<CellState> cellNeighbors = new ArrayList<>();
		Point north = new Point(cellPosition.x,cellPosition.y+1);
		Point south = new Point(cellPosition.x,cellPosition.y-1);
		Point east = new Point(cellPosition.x+1,cellPosition.y);
		Point west = new Point(cellPosition.x-1,cellPosition.y);
		
		addToNeighborsIfValid(north, cellNeighbors);
		addToNeighborsIfValid(south, cellNeighbors);
		addToNeighborsIfValid(east, cellNeighbors);
		addToNeighborsIfValid(west, cellNeighbors);
		return cellNeighbors;
	}
	
	private Collection<CellState> addToNeighborsIfValid(Point direction, Collection<CellState> neighbors) {
		CellState state = fireModel.getState(direction);
		if(state != null) {
			neighbors.add(state);
		}
		return neighbors;
	}

}
