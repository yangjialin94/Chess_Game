package Test;

import Pieces.*;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test Case for King.java
 */
public class King_test {
	
   /**
    * Constructor for King.java
    */
	King test = new King(4, 3, 1);
	
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
		boolean r3 = test.canMove(5, 4);	// Diagonal 1 step
		assertEquals(r3, true);
		boolean r4 = test.canMove(6, 5);	// Diagonal 2 steps
		assertEquals(r4, false);
		boolean r5 = test.canMove(5, 3);	// Vertical 1 step
		assertEquals(r5, true);
		boolean r6 = test.canMove(6, 3);	// Vertical 2 steps
		assertEquals(r6, false);
		boolean r7 = test.canMove(4, 2);	// Horizontal 1 step
		assertEquals(r7, true);
		boolean r8 = test.canMove(4, 1);	// Horizontal 2 steps
		assertEquals(r8, false);
		boolean r9 = test.canMove(4, 3);	// Not moving
		assertEquals(r9, false);
	}
}
