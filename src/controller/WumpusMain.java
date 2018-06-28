package controller;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.util.*;
import model.Map;
import views.*;

/**
 * This class is the driver for the GUI version the Wumpus game. It implements both a TextView and an
 * ImageView. The TextView is an interactive toString() version on the game with printed warnings. The
 * ImageView employs a Canvas to draw pictures of warnings. Both views use the arrow keys to move
 * and buttons to shoot the arrow.
 * 
 * CSC 335 Project #4, TA: Junting Lye
 * @author Rajeev Ram
 */
public class WumpusMain extends Application {
	
	/* These are the instance variables for the driver */
	
	// JavaFX outside setup variables
	private static BorderPane pane;
	private static Scene scene;
	
	// JavaFX views that will be implemented
	private static Observer currentView;
	private static Observer textView;
	private static Observer imageView;
	private static MenuBar viewMenuBar;
	
	// The random map and its corresponding visible map
	private static Map theGameMap;
	private static Map visibleMap;

	// The version of the game will be played on a GUI
	public static void main(String[] args) {
		launch(args);
	}
	
	// This is the start method called by launch in main
	@Override
	public void start(Stage stage) {
		pane = new BorderPane();
		scene = new Scene(pane, 700, 600);
		startNewGame();
		stage.setScene(scene);
		stage.show();
		((Node)currentView).requestFocus();
	}
	
	/**
	 * This method initializes the game by creating a random and visible map.
	 */
	private static void startNewGame() {
		createMenuBar();									// place the menu bar
		pane.setTop(viewMenuBar);
		
		theGameMap = new Map("random");					// instantiate the maps
		visibleMap = new Map("visible");
		visibleMap.makeHunterVisible(theGameMap);
		
		textView = new TextView(theGameMap,visibleMap);	// instantiate the views
		imageView = new ImageView(theGameMap,visibleMap);
		
		visibleMap.addObserver(textView);				// add views as observers
		visibleMap.addObserver(imageView);
		
		if (currentView==null) {							// set the current view
			pane.setCenter((Node)textView);
			currentView = textView;
		}
		else if (currentView instanceof TextView) {
			pane.setCenter((Node)textView);
			currentView = textView;
		}
		else if (currentView instanceof ImageView) {
			pane.setCenter((Node)imageView);
			currentView = imageView;
		}
		
	}
	
	/**
	 * This method create a menu and options and puts it together
	 */
	private static void createMenuBar() {
		
		viewMenuBar = new MenuBar();
		Menu changeViews = new Menu("Change The View");
		MenuItem textOption = new MenuItem("Text View");
		MenuItem imageOption = new MenuItem("Image View");
		MenuItem newGame = new MenuItem("New Game");
		changeViews.getItems().addAll(imageOption,textOption,newGame);
		changeViews.setStyle("-fx-font-family:Verdana");
		viewMenuBar.getMenus().add(changeViews);
		
		MenuEvent listener = new MenuEvent();	// add an event listener
		textOption.setOnAction(listener);
		imageOption.setOnAction(listener);
		newGame.setOnAction(listener);
		

	}
	
	/**
	 * This private inner class is a listener for the menu
	 */
	private static class MenuEvent implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			String text = ((MenuItem) event.getSource()).getText();
			
			if ( text.equals("Text View") ) {
				currentView = textView;
				pane.setCenter((Node)textView);
				((Node)currentView).requestFocus();
			}
			if ( text.equals("Image View") ) {
				currentView = imageView;
				pane.setCenter((Node)imageView);
				((Node)currentView).requestFocus();
			}
			if ( text.equals("New Game") ) {
				startNewGame();
				((Node)currentView).requestFocus();
			}
	
		}
		
	}
	
}