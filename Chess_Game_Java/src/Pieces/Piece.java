package Pieces;

import javax.swing.ImageIcon;

/**
* Super class of all types of pieces
*/
public abstract class Piece {
	
   /**
	* Declarations for player, position, and King's checkmate status.
	*
	* @return  current position of current Piece
	*/
	public int player;
	public int [] position = new int [2];
	public ImageIcon icon;
	public boolean gotCheckMate;
	
   /**
	* Abstract method
	* Return the current position of current Piece.
	*
	* @return  current position of current Piece
	*/
	public abstract int[] getPosition();
	
   /**
	* Abstract method
	* Return the image of current Piece.
	*
	* @return  image of current Piece
	*/
	public abstract ImageIcon getImage();
	
   /**
	* Abstract method
	* Check if Piece can move to position (R, C) or not.
	* Only check basic logic.
	* Situation does not count: already have a piece on (R, C).
	* 
	* @param  R  row number to check on
	* @param  C  column number to check on
	* @return    true / false
	*/
	abstract boolean canMove(int R, int C);
	
   /**
	* Abstract method
	* Make a copy of Piece to create a new Piece at new position (R, C).
	* Called when pieces move to a new position.
	* 
	* @param  R       new row number assigned to the new Piece
	* @param  C       new column number assigned to the new Piece
	* @param  player  player assigned to new Piece (Black / White)
	* @return         new Piece just created
	*/
	abstract Piece copy(int R, int C, int Player);
	
   /**
	* Check if position (R, C) is outside the board
	* 
	* @param  R  row number of the position to check on
	* @param  C  column number of the position to check on
	* @return    true / false
	*/
	public boolean out_of_bound(int R, int C) {
		if (R < 0 || C < 0 || R > 7 || C > 7)
			return true;
		else
			return false;
	}
}


