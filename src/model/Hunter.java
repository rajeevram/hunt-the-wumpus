package model;

/**
 * This class is the Hunter object for the Wumpus game. It is a very basic object, only storing
 * an X and Y position that corresponds to the game board. Most of the hunter's actions are
 * called on by the Map object to which it belongs. 
 * 
 * CSC 335 Project #4, TA: Junting Lye
 * @author Rajeev Ram
 */

public class Hunter {
	
	/* These are the instance variables for the Map class */
	private int hunterX;
	private int hunterY;
	
	/**
	 * This is the constructor for a Hunter object
	 * @param x – the starting X coordinate, or row
	 * @param y – the starting Y coordinate, or column
	 */
	public Hunter(int x, int y) {
		this.hunterX = x;
		this.hunterY = y;
	}

//––––––––––––––––––––––––––––––––––––––––––––––––––
// Getter And Setter Methods
	public int getX() {
		return hunterX;
	}
	public int getY() {
		return hunterY;
	}
	
	public void setX(int x) {
		this.hunterX = x;
	}
	public void setY(int y) {
		this.hunterY = y;
	}

//––––––––––––––––––––––––––––––––––––––––––––––––––
// Movement Methods 
	
	/**
	 * The method that moves the Hunter when the controller notifies the Map
	 * object for a change in state.
	 */
	public void moveDirection(String direction) {
		
		switch (direction) {
			case "n":
				setX( (hunterX+11)%12 );
				break;
			case "e":
				setY( (hunterY+1)%12 );
				break;
			case "s":
				setX( (hunterX+1)%12 );
				break;
			case "w":
				setY( (hunterY+11)%12 );
				break;
			default:
				break;
		}

	}
	
}
