package application;

import java.awt.Point;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class Display extends Application{
	protected final int MILLISECOND_DELAY = 1000;	// speed of animation

	protected final int EXTRA_VERTICAL = 100; 	// GUI area allowance when making the scene width
	protected final int EXTRA_HORIZONTAL = 150; 	// GUI area allowance when making the scene width
	protected final int BLOCK_SIZE = 12;     		// size of each cell in pixels

	protected Scene myScene;						// the container for the GUI
	protected boolean paused = false;		
	protected Button pauseButton;
	protected Stage myStage;
	protected Timeline animation;
	
	protected int numRows; 
	protected int numCols;
	protected String simulationTitle = "";
	
	protected Controller controller;
	protected Rectangle[][] mirrorObjects;
	// Create the scene - Controls and Maze areas
	
	public void start(Stage stage) {
		numRows = 50;
		numCols = 50;
		myStage = stage;
		initializeVariables();
		createNewSimulation();
	}
	
	abstract void initializeVariables();
	
	protected Group setupSimulation(){
		Group drawing = new Group();
		mirrorObjects = new Rectangle[numRows][numCols];
		for(int i = 0; i< numRows; i++){
			for(int j =0; j < numCols; j++){
				Rectangle rect = new Rectangle(j*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				rect.setFill(controller.getCellState(new Point(i,j)));
				mirrorObjects[i][j] = rect;
				drawing.getChildren().add(rect);
			}	
		}
		return drawing;
	}
	
	protected Scene setupScene () {
		// Make three container 
		Group simulationDrawing = setupSimulation();
		HBox searches = setupParameterButtons();
		HBox controls = setupControlButtons();

		VBox root = new VBox();
		root.setAlignment(Pos.TOP_CENTER);
		root.setSpacing(10);
		root.setPadding(new Insets(10, 10, 10, 10));
		root.getChildren().addAll(searches,simulationDrawing,controls);

		Scene scene = new Scene(root, numCols*BLOCK_SIZE + EXTRA_HORIZONTAL, 
				numRows*BLOCK_SIZE + EXTRA_VERTICAL, Color.ANTIQUEWHITE);

		return scene;
	}
	
	public void redraw(){
		for(int i = 0; i< mirrorObjects.length; i++){
			for(int j =0; j < mirrorObjects[i].length; j++){
				//System.out.println("i = " + i + " j = " + j + " color = " + controller.getCellState(new Point(i,j)));
				mirrorObjects[i][j].setFill(controller.getCellState(new Point(i,j)));
			}
		}
	}
	
	protected void createNewSimulation() {
		// Initializing the gui
		myScene = setupScene();
		myStage.setScene(myScene);
		myStage.setTitle(simulationTitle);
		myStage.show();

		// Makes the animation happen.  Will call "step" method repeatedly.
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(MILLISECOND_DELAY));
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	protected void doOneStep(double elapsedTime) {
		controller.doOneStep(elapsedTime);
		redraw();
	}
	public void step(double elapsedTime){
		if(!paused) {
			doOneStep(elapsedTime);
			
		}
	}
	
	protected HBox setupControlButtons(){
		// Make the controls part
		HBox controls = new HBox();
		controls.setAlignment(Pos.BASELINE_CENTER);
		controls.setSpacing(10);

		// New Simulation Button
		Button newFireButton = new Button("New Simulation");
		newFireButton.setOnAction(value ->  {
			animation.stop();
			createNewSimulation();
			redraw();
			paused = false;
			});
		controls.getChildren().add(newFireButton);
		
		// Pause Button
		pauseButton = new Button("Pause");
		pauseButton.setOnAction(value ->  {
			pressPause();
		});
		controls.getChildren().add(pauseButton);

		
		// Step Button
		Button stepButton = new Button("Step");
		stepButton.setOnAction(value ->  {
			doOneStep(MILLISECOND_DELAY);
		});
		controls.getChildren().add(stepButton);
		return controls;
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
	


	protected HBox setupParameterButtons(){
		HBox parameters = new HBox();
		parameters.setAlignment(Pos.BASELINE_CENTER);
		parameters.setSpacing(2);

		// Grid Height TextBox
		TextField gridHeight = createTextField(parameters, "Grid Height:",Integer.toString(numRows));
		gridHeight.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
				      String newValue) {
				numRows = Integer.parseInt(gridHeight.getText());
		    	System.out.println(numRows);
			}
			});
		
		
		// Grid Width TextBox
    	TextField gridWidth = createTextField(parameters, "Grid Width:",Integer.toString(numCols));
    	gridWidth.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
				      String newValue) {
				numCols = Integer.parseInt(gridWidth.getText());
		    	System.out.println(numCols);
			}
			});
    	
		return parameters;
	}
	
	protected TextField createTextField(HBox parameters , String label, String value) {
		Label title = new Label(label);
		TextField textBox = new TextField(value);
		textBox.setPrefColumnCount(4);
		parameters.getChildren().addAll(title, textBox);
		
		return textBox;

	}
	
	public Point getModelDimensions() {
		return new Point(numRows, numCols);
	}
}
