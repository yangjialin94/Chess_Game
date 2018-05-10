package Test;

import Pieces.*;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test Case for Chess_Board.java
 */
public class Chess_Board_test {
	
   /**
    * Constructor for Chess_Board.java
    */
	Chess_Board test_board = new Chess_Board(true);
	
   /**
    * Test Case for copy(boolean normal)
    */
	@Test
	public void copy_test() {
		Chess_Board dup_test_board = new Chess_Board(true);
		assertTrue(dup_test_board.get_piece(0, 0) instanceof Rook);
	}

   /**
    * Test Case for get_piece(int X, int Y)
    */
	@Test
	public void get_piece_test() {
		assertTrue(test_board.get_piece(0, 0) instanceof Rook);
		assertTrue(test_board.get_piece(7, 6) instanceof Knight);
	}
	
   /**
    * Test Case for occupied_f(int X, int Y, int player)
    */
	@Test
	public void occupied_f_test() {
		assertTrue(test_board.occupied_f(0, 0, 1) == true);
		assertTrue(test_board.occupied_f(0, 0, 0) == false);
	}
		
   /**
    * Test Case for occupied_o(int X, int Y, int player)
    */
	@Test
	public void occupied_o_test() {
		assertTrue(test_board.occupied_o(7, 6, 0) == false);
		assertTrue(test_board.occupied_o(7, 6, 1) == true);
	}
	
   /**
    * Test Case for King_pos(int player)
    */
	@Test
	public void King_pos_test() {
		assertTrue(test_board.King_pos(0)[0] == 7 && test_board.King_pos(0)[1] == 4);
		assertTrue(test_board.King_pos(1)[0] == 0 && test_board.King_pos(1)[1] == 4);
	}
	
   /**
    * Test Case for obstacle(int X, int Y, int new_X, int new_Y)
    */
	@Test
	public void obstacle_test() {
		assertTrue(test_board.obstacle(0, 0, 1, 0) == false);	// Neighbor
		assertTrue(test_board.obstacle(0, 0, 3, 0) == true);	// Vertical
		assertTrue(test_board.obstacle(0, 0, 0, 3) == true);	// Horizontal
	}
	
   /**
    * Test Case for valid_move(int X, int Y, int new_X, int new_Y, int player)
    */
	@Test
	public void valid_move_test() {
		assertTrue(test_board.valid_move(0, 0, 1, 0, 1) == false);		// Occupied by friend Case
		assertTrue(test_board.valid_move(0, 1, 2, 0, 1) == true);		// Knight Case
		assertTrue(test_board.valid_move(1, 4, 2, 5, 1) == false);		// Pawn Diagonal Case
	}

   /**
    * Test Case for inChecked(int player)
    */
	public void inChecked_test() {
		assertTrue(test_board.inChecked(1) == false);
		assertTrue(test_board.inChecked(0) == false);
	}
	
   /**
    * Test Case for canMove(int player)
    */
	public void canMove_test() {
		assertTrue(test_board.canMove(1) == true);
		assertTrue(test_board.canMove(0) == true);
	}
	
   /**
    * Test Case for stalemate(int player)
    */
	public void stalemate_test() {
		assertTrue(test_board.stalemate(1) == false);
		assertTrue(test_board.stalemate(0) == false);
	}
	
   /**
    * Test Case for move(int X, int Y, int new_X, int new_Y, int player)
    */
	public void move_test() {
		test_board.move(7, 1, 5, 2, 0);
		assertTrue(test_board.get_piece(7, 1) == null);
		assertTrue(test_board.get_piece(5, 2) != null);
		assertTrue(test_board.get_piece(5, 2) instanceof Knight);
	}
	
   /**
    * Test Case for capture(int X, int Y, int new_X, int new_Y, int player)
    */
	public void capture_test() {
		test_board.move(5, 2, 3, 1, 0);
		test_board.move(1, 0, 2, 0, 1);
		test_board.capture(2, 0, 3, 1, 1);
		assertTrue(test_board.get_piece(2, 0) == null);
		assertTrue(test_board.get_piece(3, 1) instanceof Pawn);
	}
	
   /**
    * Test Case for theEnd(int player)
    */
	public void theEnd_test() {
		assertTrue(test_board.theEnd(1) == false);
		assertTrue(test_board.theEnd(0) == false);
	}
}
