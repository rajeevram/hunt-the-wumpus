package tests;

/**
 * This class is a JUnit testing suite for the Wumpus game.
 * 
 * CSC 335 Project #4, TA: Junting Lye
 * @author Rajeev Ram
 */

import static org.junit.Assert.*;
import org.junit.Test;
import model.Map;

public class MapTest {
	
	final private static Map fixed = new Map("fixed");
	final private static Map visible = new Map("visible");

	@Test
	public void testMoveSouth() {
		
		fixed.setHunter(1,1);
		
		visible.makeHunterVisible(fixed);
		char[][] array = visible.getGameBoard();
		assertEquals(array[1][1],'O');
		
		visible.updateConsoleMap(fixed.getGameBoard(), "s");
		assertEquals(array[1][1],' ');
		assertEquals(array[2][1],'O');
		
		visible.updateConsoleMap(fixed.getGameBoard(), "s");
		assertEquals(array[1][1],' ');
		assertEquals(array[2][1],' ');
		assertEquals(array[3][1],'O');
		
	}
	
	@Test
	public void testMoveEast() {
		
		fixed.setHunter(1,1);
		
		visible.makeHunterVisible(fixed);
		char[][] array = visible.getGameBoard();
		assertEquals(array[1][1],'O');
		
		visible.updateConsoleMap(fixed.getGameBoard(),"e");
		assertEquals(array[1][1],' ');
		assertEquals(array[1][2],'O');
		
		visible.updateConsoleMap(fixed.getGameBoard(), "e");
		assertEquals(array[1][1],' ');
		assertEquals(array[1][2],' ');
		assertEquals(array[1][3],'O');
		
	}
	
	@Test
	public void testMoveNorth() {
		
		fixed.setHunter(11,1);

		visible.makeHunterVisible(fixed);
		char[][] array = visible.getGameBoard();
		assertEquals(array[11][1],'O');
		
		visible.updateConsoleMap(fixed.getGameBoard(),"n");
		assertEquals(array[11][1],' ');
		assertEquals(array[10][1],'O');
		
		visible.updateConsoleMap(fixed.getGameBoard(), "n");
		assertEquals(array[11][1],' ');
		assertEquals(array[10][1],' ');
		assertEquals(array[9][1],'O');
		
	}
	
	@Test
	public void testMoveWest() {
		
		fixed.setHunter(1,11);
		
		visible.makeHunterVisible(fixed);
		char[][] array = visible.getGameBoard();
		assertEquals(array[1][11],'O');
		
		visible.updateConsoleMap(fixed.getGameBoard(),"w");
		assertEquals(array[1][11],' ');
		assertEquals(array[1][10],'O');
		
		visible.updateConsoleMap(fixed.getGameBoard(), "w");
		assertEquals(array[1][11],' ');
		assertEquals(array[1][10],' ');
		assertEquals(array[1][9],'O');
		
	}
	
	@Test
	public void testShootArrowWin() {
		
		fixed.setHunter(6,10);
		visible.makeHunterVisible(fixed);
		
		assertTrue(visible.shootConsoleArrow(fixed.getGameBoard(),"e"));
		assertTrue(visible.shootConsoleArrow(fixed.getGameBoard(),"w"));
		
		fixed.setHunter(6,0);
		visible.makeHunterVisible(fixed);
		
		assertTrue(visible.shootConsoleArrow(fixed.getGameBoard(),"e"));
		assertTrue(visible.shootConsoleArrow(fixed.getGameBoard(),"w"));
		
		fixed.setHunter(5,6);
		visible.makeHunterVisible(fixed);
		
		assertTrue(visible.shootConsoleArrow(fixed.getGameBoard(),"n"));
		assertTrue(visible.shootConsoleArrow(fixed.getGameBoard(),"s"));
		
		fixed.setHunter(8,6);
		visible.makeHunterVisible(fixed);
		
		assertTrue(visible.shootConsoleArrow(fixed.getGameBoard(),"n"));
		assertTrue(visible.shootConsoleArrow(fixed.getGameBoard(),"s"));
		
	}
	
	@Test
	public void testShootArrowLose() {
		
		fixed.setHunter(10,6);
		visible.makeHunterVisible(fixed);
		
		assertFalse(visible.shootConsoleArrow(fixed.getGameBoard(),"e"));
		assertFalse(visible.shootConsoleArrow(fixed.getGameBoard(),"w"));
		
		fixed.setHunter(0,6);
		visible.makeHunterVisible(fixed);
		
		assertFalse(visible.shootConsoleArrow(fixed.getGameBoard(),"e"));
		assertFalse(visible.shootConsoleArrow(fixed.getGameBoard(),"w"));
		
		fixed.setHunter(6,5);
		visible.makeHunterVisible(fixed);
		
		assertFalse(visible.shootConsoleArrow(fixed.getGameBoard(),"n"));
		assertFalse(visible.shootConsoleArrow(fixed.getGameBoard(),"s"));
		
		fixed.setHunter(6,8);
		visible.makeHunterVisible(fixed);
		
		assertFalse(visible.shootConsoleArrow(fixed.getGameBoard(),"n"));
		assertFalse(visible.shootConsoleArrow(fixed.getGameBoard(),"s"));
		
	}
	
	@Test
	public void testSlimePitOne() {
		
		fixed.setHunter(2,2);
		visible.makeHunterVisible(fixed);
		
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"w"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"w"));
		
		fixed.setHunter(10,10);
		visible.makeHunterVisible(fixed);
		
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"w"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"w"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"w"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		
		
	}
	
	@Test
	public void testSlimePitTwo() {
		
		fixed.setHunter(10,2);
		visible.makeHunterVisible(fixed);
		
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"w"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		
		fixed.setHunter(2,10);
		visible.makeHunterVisible(fixed);
		
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"w"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"w"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		
	}
	
	@Test
	public void testWumpusOne() {
		
		fixed.setHunter(5,5);
		visible.makeHunterVisible(fixed);
		
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"w"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"w"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"w"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		
	}
	
	@Test
	public void testWumpusTwo() {
		
		fixed.setHunter(6,4);
		visible.makeHunterVisible(fixed);
		
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"w"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"w"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"e"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		assertTrue(visible.updateConsoleMap(fixed.getGameBoard(),"n"));
		assertFalse(visible.updateConsoleMap(fixed.getGameBoard(),"s"));
		
	}
	
}
