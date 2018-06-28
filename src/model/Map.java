package model;
import java.util.*;

/**
 * This class is the Map object for the Wumpus game. The Map objects serve as the point of cross-talk among
 * all the object involved in the game. There are three types of Maps:
 * 
 * (1) random – generated every time a new game is started; it contains all the information and locations
 * of the hazards and warnings
 * 
 * (2) visible – the version of the game that the console displays and the user interacts with when he or
 * she is playing
 * 
 * (3) fixed – a static map used for testing
 * 
 * CSC 335 Project #4, TA: Junting Lye
 * @author Rajeev Ram
 */
public class Map extends Observable {

	/* These are the instance variables for the Map class */
	
	// player and game board
	private Hunter hunter;
	private char[][] gameBoard = new char[12][12];

	// some character names
	private final static char pit = 'P';
	private final static char slime = 'S';
	private final static char blood = 'B';
	private final static char wumpus = 'W';
	private final static char goop = 'G';
	private final static char player = 'O';
	
	// the warning message
	private String warning = "Currently safe.";
	
	// is the game over yet?
	private boolean stillAlive = true;
	private boolean wumpusKilled = false;

	/**
	 * The constructor for the Map object. We use a String to specify what 
	 * type of board to create.
	 */
	public Map(String type) {
		createGameBoard(type);
	}

//––––––––––––––––––––––––––––––––––––––––––––––––––
// Getter And Setter Methods
	public char[][] getGameBoard() {
		return gameBoard;
	}
	
	public Hunter getHunter() {
		return hunter;
	}
	
	public void setHunter(int x, int y) {
		hunter = new Hunter(x,y);
		gameBoard[x][y]=player;
	}
	
	public String getWarning() {
		return warning;
	}

//––––––––––––––––––––––––––––––––––––––––––––––––––
// Board Creation Methods
	
	/**
	 * This creates different types of boards: (1) a random one for playing,
	 * (2) a fixed one for testing, and (3) a visible one for the console
	 * view and user-interface
	 */
	private void createGameBoard(String type) {
		if ( type.equalsIgnoreCase("random") ) {
			createRandomBoard();
		}
		if ( type.equalsIgnoreCase("fixed") ) {
			createFixedBoard();
		}
		if ( type.equalsIgnoreCase("visible") ) {
			createVisibleBoard();
		}
	}
	
	/**
	 * The initializer method for all game board types
	 */
	private void initializeGameBoard() {
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				gameBoard[i][j] = ' ';
			}
		}
	}
	
	//––––––––––––––––––––––––––––––
	// The methods for a random type board
	
	/**
	 * The method to create a new and completely random board for
	 * game play. This is paired up with a visible board in the
	 * controller.
	 */
	private void createRandomBoard() {
		initializeGameBoard();	
		addSlimePits();
		addWumpus();
		addHunter();
	}
	
	/**
	 * This method is for adding anywhere from 3 to 5 slime pits and
	 * their corresponding warnings on the board. Their locations are 
	 * randomly determined at the start of each game play.
	 */
	private void addSlimePits() {

		// generate three to five slime pits
		Random random = new Random();
		int slimePitsCount = random.nextInt(3)+3;
		
		int newX, newY, upX, downX, leftY, rightY;

		for (int i = 0; i < slimePitsCount; i++) {

			// coordinates for new the pit
			newX = random.nextInt(12);
			newY = random.nextInt(12);
			
			// do not place pit on an existing pit
			while (gameBoard[newX][newY]==pit) {
				newX = random.nextInt(12);
				newY = random.nextInt(12);
			}
			gameBoard[newX][newY]=pit;
			
			// coordinates for slime warnings
			downX = (newX+1)%12;
			upX = (newX+11)%12;
			leftY = (newY+1)%12;
			rightY = (newY+11)%12;
				
			// do not place slime warnings on existing pit
			if ( gameBoard[upX][newY]!=pit ) {
				gameBoard[upX][newY]=slime;
			}
			if ( gameBoard[downX][newY]!=pit ) {
				gameBoard[downX][newY]=slime;
			}
			if ( gameBoard[newX][leftY]!=pit ) {
				gameBoard[newX][leftY]=slime;
			}
			if ( gameBoard[newX][rightY]!=pit ) {
				gameBoard[newX][rightY]=slime;
			}

		}
	}
	
	/**
	 * This method is for adding the Wumpus and its corresponding warnings 
	 * on the board. Its location is  randomly determined at the start of 
	 * each game play.
	 */
	private void addWumpus() {
		
		// generate random place for wumpus
		Random random = new Random();
		int wumpusX = random.nextInt(12);
		int wumpusY = random.nextInt(12);
		
		// do not place wumpus on top of pit
		while ( gameBoard[wumpusX][wumpusY]==pit ) {
			wumpusX = random.nextInt(12);
			wumpusY = random.nextInt(12);
		}
		gameBoard[wumpusX][wumpusY]=wumpus;

		// row numbers for blood warnings
		int wDownOneX = (wumpusX+1)%12;
		int wDownTwoX = (wumpusX+2)%12;
		int wUpOneX = (wumpusX+11)%12;
		int wUpTwoX = (wumpusX+10)%12;
		
		// column numbers for blood warnings
		int wRightOneY = (wumpusY+1)%12;
		int wRightTwoY = (wumpusY+2)%12;
		int wLeftOneY = (wumpusY+11)%12;
		int wLeftTwoY = (wumpusY+10)%12;

		// north one of the wumpus
		if ( gameBoard[wUpOneX][wumpusY]!=pit ) {
			if ( gameBoard[wUpOneX][wumpusY]==slime ) {
				gameBoard[wUpOneX][wumpusY]=goop;
			}
			else {
				gameBoard[wUpOneX][wumpusY]=blood;
			}
		}
		// south one of the wumpus
		if ( gameBoard[wDownOneX][wumpusY]!=pit ) {
			if ( gameBoard[wDownOneX][wumpusY]==slime ) {
				gameBoard[wDownOneX][wumpusY]=goop;
			}
			else {
				gameBoard[wDownOneX][wumpusY]=blood;
			}
		}
		// west one of the wumpus
		if ( gameBoard[wumpusX][wLeftOneY]!=pit ) {
			if ( gameBoard[wumpusX][wLeftOneY]==slime ) {
				gameBoard[wumpusX][wLeftOneY]=goop;
			}
			else {
				gameBoard[wumpusX][wLeftOneY]=blood;
			}
		}
		// east one of the wumpus
		if ( gameBoard[wumpusX][wRightOneY]!=pit ) {
			if ( gameBoard[wumpusX][wRightOneY]==slime ) {
				gameBoard[wumpusX][wRightOneY]=goop;
			}
			else {
				gameBoard[wumpusX][wRightOneY]=blood;
			}
		}
		// north two of the wumpus
		if ( gameBoard[wUpTwoX][wumpusY]!=pit ) {
			if ( gameBoard[wUpTwoX][wumpusY]==slime ) {
				gameBoard[wUpTwoX][wumpusY]=goop;
			}
			else {
				gameBoard[wUpTwoX][wumpusY]=blood;
			}
		}
		// south two of the wumpus
		if ( gameBoard[wDownTwoX][wumpusY]!=pit ) {
			if ( gameBoard[wDownTwoX][wumpusY]==slime ) {
				gameBoard[wDownTwoX][wumpusY]=goop;
			}
			else {
				gameBoard[wDownTwoX][wumpusY]=blood;
			}
		}
		// west two of the wumpus
		if ( gameBoard[wumpusX][wLeftTwoY]!=pit ) {
			if ( gameBoard[wumpusX][wLeftTwoY]==slime ) {
				gameBoard[wumpusX][wLeftTwoY]=goop;
			}
			else {
				gameBoard[wumpusX][wLeftTwoY]=blood;
			}
		}
		// east two of the wumpus
		if ( gameBoard[wumpusX][wRightTwoY]!=pit ) {
			if ( gameBoard[wumpusX][wRightTwoY]==slime ) {
				gameBoard[wumpusX][wRightTwoY]=goop;
			}
			else {
				gameBoard[wumpusX][wRightTwoY]=blood;
			}
		}
		// southwest of the wumpus
		if ( gameBoard[wDownOneX][wLeftOneY]!=pit ) {
			if ( gameBoard[wDownOneX][wLeftOneY]==slime ) {
				gameBoard[wDownOneX][wLeftOneY]=goop;
			}
			else {
				gameBoard[wDownOneX][wLeftOneY]=blood;
			}
		}
		// southeast of the wumpus
		if ( gameBoard[wDownOneX][wRightOneY]!=pit ) {
			if ( gameBoard[wDownOneX][wRightOneY]==slime ) {
				gameBoard[wDownOneX][wRightOneY]=goop;
			}
			else {
				gameBoard[wDownOneX][wRightOneY]=blood;
			}
		}
		// northwest of the wumpus
		if ( gameBoard[wUpOneX][wLeftOneY]!=pit ) {
			if ( gameBoard[wUpOneX][wLeftOneY]==slime ) {
				gameBoard[wUpOneX][wLeftOneY]=goop;
			}
			else {
				gameBoard[wUpOneX][wLeftOneY]=blood;
			}
		}
		// northeast of the wumpus
		if ( gameBoard[wUpOneX][wRightOneY]!=pit ) {
			if ( gameBoard[wUpOneX][wRightOneY]==slime ) {
				gameBoard[wUpOneX][wRightOneY]=goop;
			}
			else {
				gameBoard[wUpOneX][wRightOneY]=blood;
			}
		}

	}
	
	/**
	 * This method is for adding the Hunter to the board. He or she
	 * always starts on a random safe space each game.
	 */
	private void addHunter() {
		
		// start hunter in random place
		Random random = new Random();
		int startX  = random.nextInt(12);
		int startY = random.nextInt(12);
		
		// make sure it is a safe spot
		while ( gameBoard[startX][startY]!=' ' ) {
			startX  = random.nextInt(12);
			startY = random.nextInt(12);
		}
		
		setHunter(startX,startY);
		
	}
	
	//––––––––––––––––––––––––––––––
	// The methods for a visible type board
	
	/**
	 * The method to create a new visible type board. That is, the
	 * one printed in the console-based, user-interface.
	 */
	private void createVisibleBoard() {
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				gameBoard[i][j] = 'X';
			}
		}
	}
	
	/**
	 * The method to make the hunter visible as an 'O' on the visible version 
	 * of the game board
	 * @param theRealMap – the random map that is paired with the visible map
	 */
	public void makeHunterVisible(Map theRealMap) {
		int hunterX = theRealMap.getHunter().getX();
		int hunterY = theRealMap.getHunter().getY();
		setHunter(hunterX,hunterY);
	}
	
	//––––––––––––––––––––––––––––––
	// The methods for a fixed type board
	
	/**
	 * This is the method to create a fixed board used for JUnit testing.
	 */
	private void createFixedBoard() {
		
		initializeGameBoard();
		
		gameBoard[3][3]=pit;
		gameBoard[3][2]=slime;
		gameBoard[3][4]=slime;
		gameBoard[2][3]=slime;
		gameBoard[4][3]=slime;
		
		gameBoard[3][9]=pit;
		gameBoard[3][8]=slime;
		gameBoard[3][10]=slime;
		gameBoard[2][9]=slime;
		gameBoard[4][9]=slime;
		
		gameBoard[9][3]=pit;
		gameBoard[9][2]=slime;
		gameBoard[9][4]=slime;
		gameBoard[8][3]=slime;
		gameBoard[10][3]=slime;
		
		gameBoard[9][9]=pit;
		gameBoard[9][8]=slime;
		gameBoard[9][10]=slime;
		gameBoard[8][9]=slime;
		gameBoard[10][9]=slime;
		
		gameBoard[6][6]=wumpus;
		
		gameBoard[6][5]=blood;
		gameBoard[6][4]=blood;
		gameBoard[6][7]=blood;
		gameBoard[6][8]=blood;
		
		gameBoard[5][6]=blood;
		gameBoard[4][6]=blood;
		gameBoard[7][6]=blood;
		gameBoard[8][6]=blood;
		
		gameBoard[5][7]=blood;
		gameBoard[5][5]=blood;
		gameBoard[7][5]=blood;
		gameBoard[7][7]=blood;
		
	}
	
//––––––––––––––––––––––––––––––––––––––––––––––––––
// Console Methods: 
// 		The following methods are called exclusively for the console version
	
	/**
	 * This method updates the visible board based on the state of the random board.
	 * @param theRealBoard – the random board with which the visible board is paired
	 * @param direction – the direction the player will move
	 */
	public boolean updateConsoleMap(char[][] theRealBoard, String direction) {
		
		// update visible board before the player moves
		int hunterX = hunter.getX();
		int hunterY = hunter.getY();
		if ( theRealBoard[hunterX][hunterY]==player ) {
			gameBoard[hunterX][hunterY]=' ';
		}
		else {
			gameBoard[hunterX][hunterY]=theRealBoard[hunterX][hunterY];
		}
		
		// move the hunter
		hunter.moveDirection(direction);
		
		// update visible board after the player moves
		hunterX = hunter.getX();
		hunterY = hunter.getY();
		gameBoard[hunterX][hunterY]=player;
		
		// print out the updated map
		System.out.println();
		System.out.print(this+"\n");
		System.out.println();
		stillAlive = true;
		
		// print out any warning messages
		if ( theRealBoard[hunterX][hunterY]==slime ) {
			System.out.println("I can smell the wind.");
			System.out.println();
		}
		if ( theRealBoard[hunterX][hunterY]==blood ) {
			System.out.println("I smell something foul.");
			System.out.println();
		}
		if ( theRealBoard[hunterX][hunterY]==goop )  {
			System.out.println("I can smell the wind.\nI smell something foul.");
			System.out.println();
		}
		if ( theRealBoard[hunterX][hunterY]==pit ) {
			System.out.println("You fell down a bottomless pit. You lose.");
			System.out.println();
			stillAlive = false;
		}
		if ( theRealBoard[hunterX][hunterY]==wumpus ) {
			System.out.println("You walked into the Wumpus. You lose.");
			System.out.println();
			stillAlive = false;
		}

		return stillAlive;
		
	}
	
	
	/**
	 * This method is for when the player decides to shoot his or her arrow.
	 * @param theRealBoard – the random board with which the visible board is paired
	 * @param direction – the direction the player will shoot the arrow
	 */
	public boolean shootConsoleArrow(char[][] theRealBoard, String direction) {
		
		wumpusKilled = false;
		
		// find out if wumpus is in the same column
		if ( direction.equals("n") || direction.equals("s") ) {
			int column = hunter.getY();
			for (int i = 0; i < 12; i++) {
				if ( theRealBoard[i][column]=='W' ) {
					wumpusKilled = true;
				}
			}	
		}
		// find out if wumpus is in the same row
		if ( direction.equals("e") || direction.equals("w") ) {
			int row = hunter.getX();
			for (int i = 0; i < 12; i++) {
				if ( theRealBoard[row][i]=='W' ) {
					wumpusKilled = true;
				}
			}
			
		}
		
		// print out the appropriate message
		if (wumpusKilled) {
			System.out.println();
			System.out.println("Your arrow hit the wumpus. You win.");	
		}
		else {
			System.out.println();
			System.out.println("You just shot yourself. You lose.");
		}
		
		return wumpusKilled;
		
	}
	
//––––––––––––––––––––––––––––––––––––––––––––––––––
// Graphical Methods:
//			The following methods are called exclusively for the GUI version
	
	/**
	 * This method updates the visible board based on the state of the random board.
	 * @param theRealBoard – the random board with which the visible board is paired
	 * @param direction – the direction the player will move
	 */
	public boolean updateGraphicalMap(char[][] theRealBoard, String direction) {

		// update visible board before the player moves
		int hunterX = hunter.getX();
		int hunterY = hunter.getY();
		if ( theRealBoard[hunterX][hunterY]==player ) {
			gameBoard[hunterX][hunterY]=' ';
		}
		else {
			gameBoard[hunterX][hunterY]=theRealBoard[hunterX][hunterY];
		}

		// move the hunter
		hunter.moveDirection(direction);

		// update visible board after the player moves
		hunterX = hunter.getX();
		hunterY = hunter.getY();
		gameBoard[hunterX][hunterY]=player;

		warning = "Currently safe.";

		// update the warning message variable
		if ( theRealBoard[hunterX][hunterY]==slime ) {
			warning = "I can smell the wind.";
		}
		if ( theRealBoard[hunterX][hunterY]==blood ) {
			warning = "I smell something foul.";
		}
		if ( theRealBoard[hunterX][hunterY]==goop )  {
			warning = "I can smell the wind. I smell something foul.";
		}
		if ( theRealBoard[hunterX][hunterY]==pit ) {
			warning = "You fell down a bottomless pit. You lose.";
			stillAlive = false;
		}
		if ( theRealBoard[hunterX][hunterY]==wumpus ) {
			warning = "You walked into the Wumpus. You lose.";
			stillAlive = false;
		}

		return stillAlive;
		
	}
	
	/**
	 * This method is for when the player decides to shoot his or her arrow.
	 * @param theRealBoard – the random board with which the visible board is paired
	 * @param direction – the direction the player will shoot the arrow
	 */
	public boolean shootGraphicalArrow(char[][] theRealBoard, String direction) {
		
		wumpusKilled = false;
		
		// find out if wumpus is in the same column
		if ( direction.equals("n") || direction.equals("s") ) {
			int column = hunter.getY();
			for (int i = 0; i < 12; i++) {
				if ( theRealBoard[i][column]=='W' ) {
					wumpusKilled = true;
				}
			}	
		}
		// find out if wumpus is in the same row
		if ( direction.equals("e") || direction.equals("w") ) {
			int row = hunter.getX();
			for (int i = 0; i < 12; i++) {
				if ( theRealBoard[row][i]=='W' ) {
					wumpusKilled = true;
				}
			}
			
		}
		
		// update the warning message variable
		if (wumpusKilled) {
			warning = "Your arrow hit the wumpus. You win.";
		}
		else {
			warning = "You just shot yourself. You lose.";
		}
		
		return false;
			
	}
	
	/**
	 * This is method is called to notify the observers and change their states
	 */
	public void changeGraphicalState() {
		setChanged();
		notifyObservers();
	}

//––––––––––––––––––––––––––––––––––––––––––––––––––
// String Methods
	
	/* This is the regular string representation for the console version */
	@Override
	public String toString() {
		String gameBoardString = "";
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				gameBoardString += gameBoard[i][j]+" ";
			}
			gameBoardString = gameBoardString.substring(0, gameBoardString.length()-1);
			if (i != 11) {
				gameBoardString += "\n";
			}
		}
		return gameBoardString;
	}
	
	/* This is a special string representation for the text view GUI */
	public String createEndString(char[][] theRealBoard) {
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				if (gameBoard[i][j]=='X') {
					gameBoard[i][j] = '_';
				}
				if (theRealBoard[i][j]!=' ' && gameBoard[i][j]=='_') {
					gameBoard[i][j] = theRealBoard[i][j];
				}
			}
		}
		return this.toString();
		
	}
	
}

