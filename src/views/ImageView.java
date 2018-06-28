package views;
import java.util.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
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
public class ImageView extends BorderPane implements Observer {
	
	/* These are the instance variables for the driver */
	
	// These are the maps for the game.
	private Map theGameMap;
	private char[][] theGameBoard;
	private Map visibleMap;
	private char[][] theViewBoard;
	
	// The main canvas on which the game is played in image form
	private Canvas imageDisplay;
	private GraphicsContext drawArea;
	
	// An extra feature that I decided to add
	private HBox emojiBox;
	private Canvas emojiCanvas;
	private GraphicsContext emojiArea;
	
	// Is the game over?
	static boolean gameStillRunning = true;
	
	// The buttons for shooting the arrow
	final private Button north = new Button("N");
	final private Button south = new Button("S");
	final private Button east = new Button("E");
	final private Button west = new Button("W");
	
	// The images for the main canvas
	private final Image B = new Image("file:images/blood.png", 46, 46, false, false);
	private final Image S = new Image("file:images/slime.png", 46, 46, false, false);
	private final Image G = new Image("file:images/goop.png", 46, 46, false, false);
	private final Image C = new Image("file:images/stone.png", 46, 46, false, false);
	private final Image H = new Image("file:images/hunter.png", 46, 46, false, false);
	private final Image P = new Image("file:images/pit.png", 46, 46, false, false);
	private final Image W = new Image("file:images/monster.png", 46, 46, false, false);
	
	// The images for the emoji box
	private final Image safe = new Image("file:images/safe.png", 80, 80, false, false);
	private final Image neutral = new Image("file:images/neutral.png", 80, 80, false, false);
	private final Image danger = new Image("file:images/danger.png", 80, 80, false, false);
	private final Image lose = new Image("file:images/lose.png", 80, 80, false, false);
	private final Image win = new Image("file:images/win.png", 80, 80, false, false);

//––––––––––––––––––––––––––––––––––––––––––––––––––
// Construction Methods

	public ImageView(Map theRealMap, Map theViewMap) {
		theGameMap = theRealMap;
		theGameBoard = theGameMap.getGameBoard();
		visibleMap = theViewMap;
		theViewBoard = visibleMap.getGameBoard();
		createViewPane();
	}
	
	/**
	 * This method creates the basic layout for the image display
	 */
	private void createViewPane() {
		
		imageDisplay = new Canvas(552,552);		// new canvas and graphics context
		createTheCanvas();
		setLeft(imageDisplay);
		BorderPane.setMargin(imageDisplay,new Insets(8,10,0,10));
		this.setOnKeyReleased(new ArrowKey());	// listener for arrow keys
		
		GridPane sideBox = new GridPane();		// right side of image display
		BorderPane.setAlignment(sideBox, Pos.CENTER_RIGHT);
		BorderPane.setMargin(sideBox, new Insets(90, 8, 0, -8));
		setRight(sideBox);
		
		GridPane arrowPanel = new GridPane();	// arrow buttons
		arrowPanel.add(north,1,0);
		arrowPanel.add(south,1,2);
		arrowPanel.add(east,3,1);
		arrowPanel.add(west,0,1);
		styleArrowButtons();
		sideBox.add(arrowPanel,0,1);
		
		emojiBox = new HBox();					// emojis
		emojiCanvas = new Canvas(80,80);
		emojiArea = emojiCanvas.getGraphicsContext2D();
		emojiArea.drawImage(safe,0,0);
		emojiBox.getChildren().add(emojiCanvas);
		sideBox.add(emojiBox,0,2);
		sideBox.setVgap(50);
		GridPane.setMargin(emojiBox, new Insets(0, 0, 0, 20));
		
	}

//––––––––––––––––––––––––––––––––––––––––––––––––––
// Styling Methods
	
	/**
	 * This method styles the arrow buttons
	 */
	private void styleArrowButtons() {
		
		Font twoFont = new Font("Courier New", 20);
		
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
// Drawing Methods
	
	/**
	 * This method draws all the images to the graphics context for the first time.
	 */
	private void createTheCanvas() {
		
		drawArea = imageDisplay.getGraphicsContext2D();
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				if (theViewBoard[i][j]=='X') {
					drawArea.drawImage(C,j*46,i*46);
					drawArea.setFill(new Color(0,0,0,0.4));
					drawArea.fillRect(j*46,i*46,46,46);
				}
				if (theViewBoard[i][j]=='O') {
					drawArea.setFill(Color.BLACK);
					drawArea.fillRect(j*46,i*46,46,46);
					drawArea.drawImage(H,j*46,i*46);
				}
			}
		}
	}
	
	/**
	 * This method updates the canvas as the game is continued to be played.
	 */
	private void updateTheCanvas() {
		
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				if (theViewBoard[i][j]=='O') {
					drawArea.setFill(Color.BLACK);
					drawArea.fillRect(j*46,i*46,46,46);
					if (theGameBoard[i][j]=='S') {
						drawArea.drawImage(S,j*46,i*46);
					}
					if (theGameBoard[i][j]=='B') {
						drawArea.drawImage(B,j*46,i*46);
					}
					if (theGameBoard[i][j]=='G') {
						drawArea.drawImage(G,j*46,i*46);
					}
					drawArea.drawImage(H,j*46,i*46);
				}
				if (theViewBoard[i][j]==' ') {
					drawArea.setFill(Color.BLACK);
					drawArea.fillRect(j*46,i*46,46,46);
				}
				if (theViewBoard[i][j]=='S') {
					drawArea.setFill(Color.BLACK);
					drawArea.fillRect(j*46,i*46,46,46);
					drawArea.drawImage(S,j*46,i*46);
				}
				if (theViewBoard[i][j]=='B') {
					drawArea.setFill(Color.BLACK);
					drawArea.fillRect(j*46,i*46,46,46);
					drawArea.drawImage(B,j*46,i*46);
				}
				if (theViewBoard[i][j]=='G') {
					drawArea.setFill(Color.BLACK);
					drawArea.fillRect(j*46,i*46,46,46);
					drawArea.drawImage(G,j*46,i*46);
				}
			}
		}
		
	}
	
	/**
	 * This method draws the final image display after the game is finished.
	 */
	private void finishTheCanvas() {
		
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				if (theViewBoard[i][j]=='X') {
					drawArea.setFill(new Color(0,0,0,0.6));
					drawArea.fillRect(j*46,i*46,46,46);
				}
				if (theGameBoard[i][j]=='S') {
					drawArea.drawImage(S,j*46,i*46);
					if (theViewBoard[i][j]=='O') {
						drawArea.drawImage(H,j*46,i*46);
					}
				}
				if (theGameBoard[i][j]=='B') {
					drawArea.drawImage(B,j*46,i*46);
					if (theViewBoard[i][j]=='O') {
						drawArea.drawImage(H,j*46,i*46);
					}
				}
				if (theGameBoard[i][j]=='G') {
					drawArea.drawImage(G,j*46,i*46);
					if (theViewBoard[i][j]=='O') {
						drawArea.drawImage(H,j*46,i*46);
					}
				}
				if (theGameBoard[i][j]=='P') {
					drawArea.drawImage(P,j*46,i*46);
					if (theViewBoard[i][j]=='O') {
						drawArea.drawImage(H,j*46,i*46);
					}
				}
				if (theGameBoard[i][j]=='W') {
					drawArea.drawImage(W,j*46,i*46);
					if (theViewBoard[i][j]=='O') {
						drawArea.drawImage(H,j*46,i*46);
					}
				}
				
			}
		}	
			
	}
	
	/**
	 * This method updates the emoji based on the current warning status.
	 */
	private void updateEmojiBox() {
		
		emojiArea.clearRect(0,0,80,80);
		
		if ( visibleMap.getWarning().equals("Currently safe.") ) {
			emojiArea.drawImage(safe,0,0);
		}
		if ( visibleMap.getWarning().equals("I can smell the wind.") ) {
			emojiArea.drawImage(danger,0,0);
		}
		if ( visibleMap.getWarning().contains("I smell something foul.") ) {
			emojiArea.drawImage(neutral,0,0);
		}
		if ( visibleMap.getWarning().contains("You fell down a bottomless pit. You lose.") ) {
			emojiArea.drawImage(lose,0,0);
		}
		if ( visibleMap.getWarning().contains("You walked into the Wumpus. You lose.") ) {
			emojiArea.drawImage(lose,0,0);
		}
		if ( visibleMap.getWarning().contains("You just shot yourself. You lose.") ) {
			emojiArea.drawImage(lose,0,0);
		}
		if ( visibleMap.getWarning().contains("Your arrow hit the wumpus. You win.") ) {
			emojiArea.drawImage(win,0,0);
		}
		
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
			TextView.gameStillRunning = ImageView.gameStillRunning;
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
			TextView.gameStillRunning = ImageView.gameStillRunning;
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
		if (gameStillRunning) {
			updateTheCanvas();
			updateEmojiBox();
		}
		else {
			updateTheCanvas();
			finishTheCanvas();
			updateEmojiBox();
			this.setOnKeyReleased(null);
			disableArrowButtons();
		}
		
	}

}
