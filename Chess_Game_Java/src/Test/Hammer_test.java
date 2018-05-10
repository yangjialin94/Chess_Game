package Test;

import Pieces.*;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test Case for Hammer.java
 */
public class Hammer_test {

   /**
    * Constructor for Queen.java
    */
	Hammer test = new Hammer(4, 3, 1);
	
   /**
    * Test Case for getPosition_test()
    */
	@Test
	public void getPosition_test() {
		int [] position = test.getPosition();
		assertEquals(position.length, 2);
		assertEquals(position[0], 4);
		assertEquals(position[1], 3);
	}

   /**
    * Test Case for canMove(int X, int Y)
    */
	@Test
	public void canMove_test() {	
		boolean r1 = test.canMove(7, 8);	// Out of bound
		assertEquals(r1, false);
		boolean r2 = test.canMove(6, 4);	// L 
		assertEquals(r2, false);
		boolean r3 = test.canMove(6, 5);	// Diagonal
		assertEquals(r3, true);
		boolean r4 = test.canMove(0, 3);	// Vertical
		assertEquals(r4, true);
		boolean r5 = test.canMove(4, 0);	// Horizontal
		assertEquals(r5, true);
		boolean r6 = test.canMove(4, 3);	// Not moving
		assertEquals(r6, false);
	}
}
