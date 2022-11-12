
import java.awt.Point;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ForestDisplay {
	/*
	 * GUI settings
	 */
	private final int MILLISECOND_DELAY = 15;	// speed of animation
	private final int EXTRA_VERTICAL = 100; 	// GUI area allowance when making the scene width
	private final int EXTRA_HORIZONTAL = 150; 	// GUI area allowance when making the scene width
	private final int BLOCK_SIZE = 12;     		// size of each cell in pixels
	private final int NUM_ROWS = 21; 
	private final int NUM_COLUMNS = 51;

	private Scene myScene;						// the container for the GUI
	private boolean paused = false;		
	private Button pauseButton;

	private Rectangle[][] mirrorFire;	// the Rectangle objects that will get updated and drawn.  It is 
	// called "mirror" maze because there is one entry per square in 
	// the maze.
	
	
	

	private FireController controller;
	
	/*
	 * Maze color settings
	 */
	private Color[] color  = new Color[] {
			Color.rgb(200,0,0),		// wall color
			Color.rgb(128,128,255),	// path color
			Color.WHITE,			// empty cell color
			Color.rgb(200,200,200)	// visited cell color
	};  		// the color of each of the states  

	
	


	// Start of JavaFX Application
	public void start(Stage stage) {

		
		//Make MazeController
		controller = new FireController(NUM_ROWS,NUM_COLUMNS, 0.4, 1,1,this);
		
		
		// Initializing the gui
		myScene = setupScene();
		stage.setScene(myScene);
		stage.setTitle("aMAZEing");
		stage.show();

		// Makes the animation happen.  Will call "step" method repeatedly.
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(MILLISECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	// Create the scene - Controls and Maze areas
	private Scene setupScene () {
		// Make three container 
		Group mazeDrawing = setupMaze();
		HBox searches = setupSearchButtons();
		HBox controls = setupControlButtons();

		VBox root = new VBox();
		root.setAlignment(Pos.TOP_CENTER);
		root.setSpacing(10);
		root.setPadding(new Insets(10, 10, 10, 10));
		root.getChildren().addAll(searches,mazeDrawing,controls);

		Scene scene = new Scene(root, NUM_COLUMNS*BLOCK_SIZE+ EXTRA_HORIZONTAL, 
				NUM_ROWS*BLOCK_SIZE + EXTRA_VERTICAL, Color.ANTIQUEWHITE);

		return scene;
	}

	private HBox setupControlButtons(){
		// Make the controls part
		HBox controls = new HBox();
		controls.setAlignment(Pos.BASELINE_CENTER);
		controls.setSpacing(10);

		Button newMazeButton = new Button("New Simulation");
		newMazeButton.setOnAction(value ->  {
			controller.newSimulation();
			});
		controls.getChildren().add(newMazeButton);

		pauseButton = new Button("Pause");
		pauseButton.setOnAction(value ->  {
			pressPause();
		});
		controls.getChildren().add(pauseButton);

		Button stepButton = new Button("Step");
		stepButton.setOnAction(value ->  {
			controller.doOneStep(MILLISECOND_DELAY);
		});
		controls.getChildren().add(stepButton);
		return controls;
	}

	private HBox setupSearchButtons(){
		HBox searches = new HBox();
		searches.setAlignment(Pos.BASELINE_CENTER);
		searches.setSpacing(5);

		TextField gridHeight = new TextField("Grid Height");
		//Button dfsButton = new Button("");
		gridHeight.setOnAction(value ->  {
			//controller.startSearch("DFS");
		});
		searches.getChildren().add(gridHeight);

		TextField gridWidth = new TextField("Grid Width");
		//Button bfsButton = new Button("Breadth-First Search");
		gridWidth.setOnAction(value ->  {
			//controller.startSearch("BFS");
		});
		searches.getChildren().add(gridWidth);

		TextField burnTime = new TextField("Burn Time");
		//Button greedyButton = new Button("Greedy");
		burnTime.setOnAction(value ->  {
			//controller.startSearch("Greedy");
		});
		searches.getChildren().add(burnTime);

		TextField spreadProb = new TextField("Spread Probability");
		//Button randButton = new Button("Random Walk");
		spreadProb.setOnAction(value ->  {
			//controller.startSearch("RandomWalk");
		});
		searches.getChildren().add(spreadProb);

		TextField forestDensity = new TextField("Forest Density");
		//Button magicButton = new Button("Magic!");
		forestDensity.setOnAction(value ->  {
			//controller.startSearch("Magic");
		});
		searches.getChildren().add(forestDensity);
		
		TextField numBurningTrees = new TextField("# of Burning Trees");
		//Button magicButton = new Button("Magic!");
		numBurningTrees.setOnAction(value ->  {
			//controller.startSearch("Magic");
		});
		searches.getChildren().add(numBurningTrees);
		return searches;
	}

	public Point getMazeDimensions() {
		return new Point(NUM_ROWS, NUM_COLUMNS);
	}

	/*
	 * Setup the maze part for drawing. In particular,
	 * make the mirrorMaze.
	 */
	private Group setupMaze(){
		Group drawing = new Group();
		mirrorFire = new Rectangle[NUM_ROWS][NUM_COLUMNS];
		for(int i = 0; i< NUM_ROWS; i++){
			for(int j =0; j < NUM_COLUMNS; j++){
				Rectangle rect = new Rectangle(j*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				rect.setFill(color[controller.getCellState(new Point(i,j))]);
				mirrorFire[i][j] = rect;
				drawing.getChildren().add(rect);
			}	
		}
		return drawing;
	}
	


	/*
	 * Toggle the pause button
	 */
	public void pressPause(){
		this.paused = !this.paused;
		if(this.paused){
			pauseButton.setText("Resume");
		}
		else{
			pauseButton.setText("Pause");
		}
	}

	/*
	 * Pause the animation (regardless of current state of pause button)
	 */
	public void pauseIt(){
		this.paused = true;
		pauseButton.setText("Resume");
	}

	/*
	 * resets all the rectangle colors according to the 
	 * current state of that rectangle in the maze.  This 
	 * method assumes the display maze matches the model maze
	 */
	public void redraw(){
		for(int i = 0; i< mirrorFire.length; i++){
			for(int j =0; j < mirrorFire[i].length; j++){
				mirrorFire[i][j].setFill(color[controller.getCellState(new Point(i,j))]);
			}
		}
	}

	/*
	 * Does a step in the search only if not paused.
	 */
	public void step(double elapsedTime){
		if(!paused) {
			controller.doOneStep(elapsedTime);
		}
	}


	
	




	public static void main(String[] args) {
		launch(args);
	}

}
