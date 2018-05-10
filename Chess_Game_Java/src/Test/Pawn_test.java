package Test;

import Pieces.*;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test Case for Pawn.java
 */
public class Pawn_test {
	
   /**
    * Constructor for Pawn.java
    */
	Pawn test = new Pawn(4, 3, 1);
	Pawn new_test = new Pawn(6, 6, 0);
	
   /**
    * Test Case for getPosition_test()
    */
	@Test
	public void getPosition_test() {
		int [] position = test.getPosition();
		assertEquals(position.length, 2);
		assertEquals(position[0], 4);
		assertEquals(position[1], 3);
		
		int [] new_position = new_test.getPosition();
		assertEquals(new_position.length, 2);
		assertEquals(new_position[0], 6);
		assertEquals(new_position[1], 6);
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
		boolean r3 = test.canMove(5, 4);	// Diagonal Forward
		assertEquals(r3, true);
		boolean r4 = test.canMove(3, 2);	// Diagonal Backward
		assertEquals(r4, false);
		boolean r5 = test.canMove(5, 3);	// Vertical Forward 1 step
		assertEquals(r5, true);
		boolean r6 = test.canMove(6, 3);	// Vertical Forward 2 steps
		assertEquals(r6, false);
		boolean r7 = test.canMove(3, 3);	// Vertical Backward
		assertEquals(r7, false);
		boolean r8 = test.canMove(4, 2);	// Horizontal
		assertEquals(r8, false);
		boolean r9 = test.canMove(4, 3);	// Not moving
		assertEquals(r9, false);
		
		boolean r10 = new_test.canMove(4, 6);	// Vertical Forward 2 steps
		assertEquals(r10, true);
	}
}
