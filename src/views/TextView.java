package views;
import java.util.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import model.Map;

/**
 * This class is one of the views for the GUI version of the Wumpus game. 
 * 
 * CSC 335 Project #4, TA: Junting Lye
 * @author Rajeev Ram
 */
public class TextView extends BorderPane implements Observer {
	
	/* These are the instance variables for the driver */
	
	// These are the maps for the game.
	private Map theGameMap;
	private char[][] theGameBoard;
	private Map visibleMap;
	
	// The main area on which the game is played in text form
	private TextArea stringDisplay;
	private Text warning;
	private HBox message;
	
	// Is the game over?
	static boolean gameStillRunning = true;
	
	// The buttons for shooting the arrow
	final private Button north = new Button("N");
	final private Button south = new Button("S");
	final private Button east = new Button("E");
	final private Button west = new Button("W");

//––––––––––––––––––––––––––––––––––––––––––––––––––
// Construction Methods
	
	public TextView(Map theRealMap, Map theViewMap) {
		theGameMap = theRealMap;
		theGameBoard = theGameMap.getGameBoard();
		visibleMap = theViewMap;
		createViewPane();
	}
	
	/**
	 * This method creates the basic layout for the text view
	 */
	private void createViewPane() {
			
		stringDisplay = new TextArea(visibleMap.toString().trim());
		styleTextArea();
		
		this.setOnKeyReleased(new ArrowKey());
		
		GridPane arrowPanel = new GridPane();
		
		BorderPane.setAlignment(arrowPanel, Pos.CENTER_RIGHT);
		BorderPane.setMargin(arrowPanel, new Insets(175, 15, 0, 0));
		setRight(arrowPanel);
		
		arrowPanel.add(north,1,0);
		arrowPanel.add(south,1,2);
		arrowPanel.add(east,3,1);
		arrowPanel.add(west,0,1);
		styleArrowButtons();
		
		warning = new Text(visibleMap.getWarning());
		styleWarningText();
		
		message = new HBox();
		message.getChildren().add(warning);
		HBox.setMargin(warning, new Insets(0,0,20,15));
		
		setBottom(message);
		
	}
	
//––––––––––––––––––––––––––––––––––––––––––––––––––
// Styling Methods
	
	/**
	 * This method styles the text area with the map
	 */
	private void styleTextArea() {
		
		Font oneFont = new Font("Courier New", 34);
		stringDisplay.setEditable(false);
		stringDisplay.setFont(oneFont);
		stringDisplay.setMaxHeight(500);
		stringDisplay.setMaxWidth(515);
		setLeft(stringDisplay);
		BorderPane.setMargin(stringDisplay,new Insets(10,10,0,10));
		
	}
	
	/**
	 * This method styles the warning message at the bottom
	 */
	private void styleWarningText() {
		
		String rightNow = visibleMap.getWarning();
		Font someFont = new Font("Verdana", 24);
		warning.setFont(someFont);
		
		if ( rightNow.equals("Currently safe.") ) {
			warning.setFill(Color.GREEN);
		}
		if ( rightNow.contains("I can smell the wind.") ) {
			warning.setFill(Color.GOLDENROD);
		}
		if ( rightNow.contains("I smell something foul.") ) {
			warning.setFill(Color.GOLDENROD);
		}
		if ( rightNow.equals("You fell down a bottomless pit. You lose.") ) {
			warning.setFill(Color.RED);
		}
		if ( rightNow.equals("You walked into the Wumpus. You lose.") ) {
			warning.setFill(Color.RED);
		}
		if ( rightNow.equals("You just shot yourself. You lose.") ) {
			warning.setFill(Color.RED);
		}
		if ( rightNow.equals("Your arrow hit the wumpus. You win.") ) {
			warning.setFill(Color.BLUE);
		}
		
	}
	
	/**
	 * This method styles the arrow buttons
	 */
	private void styleArrowButtons() {
		
		Font twoFont = new Font("Courier New", 24);
		
		north.setFont(twoFont); 
		south.setFont(twoFont);
		east.setFont(twoFont); 
		west.setFont(twoFont);
		
		north.setOnAction(new ArrowButton());
		south.setOnAction(new ArrowButton());
		east.setOnAction(new ArrowButton());
		west.setOnAction(new ArrowButton());
		
	}
	
//––––––––––––––––––––––––––––––––––––––––––––––––––
// Event Handling
	
	/**
	 * This private inner class is for the buttons for shooting the arrow
	 */
	private class ArrowButton implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			String direction = ((Button) event.getSource()).getText().toLowerCase();
			gameStillRunning = visibleMap.shootGraphicalArrow(theGameBoard,direction);
			ImageView.gameStillRunning = TextView.gameStillRunning;
			visibleMap.changeGraphicalState();
		}
		
	}
	
	/**
	 * This private inner class is for moving the play with the arrow keys
	 */
	private class ArrowKey implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			
			if ( event.getCode() == KeyCode.UP ) {
				gameStillRunning = visibleMap.updateGraphicalMap(theGameBoard,"n");
			}
			if ( event.getCode() == KeyCode.LEFT ) {
				gameStillRunning = visibleMap.updateGraphicalMap(theGameBoard,"w");
			}
			if ( event.getCode() == KeyCode.RIGHT ) {
				gameStillRunning = visibleMap.updateGraphicalMap(theGameBoard,"e");
			}
			if ( event.getCode() == KeyCode.DOWN ) {
				gameStillRunning = visibleMap.updateGraphicalMap(theGameBoard,"s");
			}
			ImageView.gameStillRunning = TextView.gameStillRunning;
			visibleMap.changeGraphicalState();
			
		}
		
	}
	
	/**
	 * This method disables the arrows buttons once the game finishes
	 */
	public void disableArrowButtons() {
		
		north.setDisable(true);
		south.setDisable(true);
		east.setDisable(true);
		west.setDisable(true);
		
	}
	
	// The update method from the Observer interface
	@Override
	public void update(Observable observable, Object object) {
		warning.setText(visibleMap.getWarning());
		styleWarningText();
		if (gameStillRunning) {
			stringDisplay = new TextArea(visibleMap.toString());
			styleTextArea();
			setLeft(stringDisplay);
			BorderPane.setMargin(stringDisplay,new Insets(10,10,0,10));
		}
		else {
			stringDisplay = new TextArea(visibleMap.createEndString(theGameBoard));
			styleTextArea();
			this.setOnKeyReleased(null);
			disableArrowButtons();
		}
	}

}
