import java.awt.Point;
import javafx.scene.paint.Color;
import java.util.Random;
import java.util.Collection;
import java.util.List;

/*
 * @author Christopher Boyette
 */

public class FireModel {
	private static final Color EDGE = Color.BLACK;	
	private static final Color EMPTY = Color.BROWN;
	private static final Color NON_BURNING_TREE = Color.GREEN;
	private static final Color BURNING_TREE = Color.RED;
	private static final Color BURNT_DOWN_TREE = Color.YELLOW;
	private static Double FOREST_DENSITY = 1.0;
	private static Double SPREAD_PROBABILITY = 0.4;
	private static Double BURN_TIME = 1.0;
	private static int NUMBER_BURNING_TREES = 1;
	List<Point> treePositions;
	Collection<Point> treeNeighbors;

	Random rand = new Random();
	private Color[][] forest;	// The squares making up the maze

	public FireModel(int rows, int columns){
		assert(rows > 0 && columns > 0);
		createForest(rows, columns);
	}

	public int getNumRows(){
		assert(forest!=null);
		return forest.length;
	}

	public int getNumCols(){
		assert(forest!=null);
		return forest[0].length;
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

	/* 
	 * 
	 * 
	 * 
	 * Remaining code is from "Introduction to Programming Using Java" by Eck.
	 *
	 *
	 *
	 */
	/*
	 * Create a new random maze of the given dimensions and store the result.
	 * Maze has no cycles.
	 */

	public void createForest(int rows, int cols) {
		assert(rows > 0 && cols > 0);
		forest = new Color[rows][cols];
		double randomForestDensity = rand.nextDouble();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i == 0 || j % (cols-1) == 0) {
					forest[i][j] = EDGE;
				}
				else {
					if (randomForestDensity <= FOREST_DENSITY) {
						forest[i][j] = NON_BURNING_TREE;
						treePositions.add(new Point(i,j));
						
					}
					else {
						forest[i][j] = EMPTY;
					}
				}
			}
		}
	}
	
	public void	assignBurningTrees() {
		int numberOfTrees = treePositions.size();
		int numberOfTreesChanged = 0;
		while (numberOfTreesChanged < numberOfTrees) {
			int burningTree = rand.nextInt(numberOfTrees);
			Point newBurningTree = treePositions.get(burningTree);
			if (forest[newBurningTree.x][newBurningTree.y] !=  BURNING_TREE) {
				forest[newBurningTree.x][newBurningTree.y] = BURNING_TREE;
				numberOfTreesChanged++;
			}
		}
	}
	
	public Collection<Point> getNeighbors(Point treePosition) {
		treeNeighbors.add(new Point(treePosition.x+1,treePosition.y));
		treeNeighbors.add(new Point(treePosition.x-1,treePosition.y));
		treeNeighbors.add(new Point(treePosition.x,treePosition.y+1));
		treeNeighbors.add(new Point(treePosition.x,treePosition.y-1));
		return treeNeighbors;
	}
}
