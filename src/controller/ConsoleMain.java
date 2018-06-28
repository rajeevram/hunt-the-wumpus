package controller;
import java.util.*;
import model.Map;

/**
 * This class is the driver for the console version of the Wumpus game. It uses a while loop and a Scanner 
 * to collect input from the user and display game updates to the console.
 * 
 * CSC 335 Project #4, TA: Junting Lye
 * @author Rajeev Ram
 */
public class ConsoleMain {

	// The random map and its corresponding visible map
	private static Map theGameMap;
	private static Map visibleMap;

	// This version of the game is played on the console
	public static void main(String[] args) {
		playTheGame();
	}

	/**
	 * This method initializes the game by creating a random and visible map.
	 */
	public static void initializeGame() {
		theGameMap = new Map("random");
		visibleMap = new Map("visible");
		visibleMap.makeHunterVisible(theGameMap);
	}

	/**
	 * This method is the Scanner-while loop that runs the console version.
	 */
	public static void playTheGame() {
		
		// Initialize the game and print out the map
		initializeGame();
		System.out.println(visibleMap);
		System.out.println();

		Scanner scanner = new Scanner(System.in);
		boolean gameStillRunning = true;

		do {

			System.out.print("Move (N,E,S,W,Arrow)? ");

			// Check for proper input format
			String move = null;
			try {
				move = scanner.next();
				move = move.toLowerCase();
			}
			catch (InputMismatchException e) {
				System.err.println("Invalid input.");
			}

			// Move in the direction entered
			switch (move) {
			case "n":
				gameStillRunning = visibleMap.updateConsoleMap(theGameMap.getGameBoard(),"n");
				break;
			case "e":
				gameStillRunning = visibleMap.updateConsoleMap(theGameMap.getGameBoard(),"e");
				break;
			case "s":
				gameStillRunning = visibleMap.updateConsoleMap(theGameMap.getGameBoard(),"s");
				break;
			case "w":
				gameStillRunning = visibleMap.updateConsoleMap(theGameMap.getGameBoard(),"w");
				break;
			case "arrow":
				System.out.print("Shoot (N,E,S,W)? ");
				break;
			default:
				System.out.println("Invalid move. Try again.");
				System.out.println();
			}

			// Shoot the arrow
			if ( move.equalsIgnoreCase("arrow") ) {

				// Check for proper input format
				try {
					move = scanner.next();
					move = move.toLowerCase();
				}
				catch (InputMismatchException e) {
					System.err.println("Invalid input.");
				}

				gameStillRunning = false;

				// Shoot in the direction entered
				switch (move) {
				case "n":
					visibleMap.shootConsoleArrow(theGameMap.getGameBoard(),"n");
					break;
				case "e":
					visibleMap.shootConsoleArrow(theGameMap.getGameBoard(),"e");
					break;
				case "s":
					visibleMap.shootConsoleArrow(theGameMap.getGameBoard(),"s");
					break;
				case "w":
					visibleMap.shootConsoleArrow(theGameMap.getGameBoard(),"w");
					break;
				default:
					gameStillRunning = true;
					System.out.println("Invalid move. Try again.");
					System.out.println();
				}

			}	

		} while ( gameStillRunning ); // The end of game condition

		scanner.close();
		System.exit(0);

	}

}
