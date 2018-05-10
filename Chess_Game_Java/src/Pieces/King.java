package Pieces;

import javax.swing.ImageIcon;

/**
* Creation and Logic of piece King
* Subclass of Class Piece
*/
public class King extends Piece {

   /**
	* King Constructor
	*
	* @param  R             row number assigned to King
	* @param  C             column number assigned to King
	* @param  player        player assigned to King (Black / White)
	* @param  gotCheckMate  status of got Checkmate assigned to King 
	*/
	public King(int R, int C, int player) {
		this.position[0] = R;
		this.position[1] = C;
		this.player = player;
		
		// Image
		if (player == 1) {
			this.icon = new ImageIcon("src/Images/Black_King.png");
		} else {
			this.icon = new ImageIcon("src/Images/White_King.png");
		}
	}
		
   /**
	* Return the current position of current King.
	*
	* @return  current position of current King
	*/
	public int[] getPosition() {
		return this.position;
	}
	
   /**
	* Return the image of current Piece.
	*
	* @return  image of current Piece
	*/
	public ImageIcon getImage() {
		return this.icon;
	}
			
   /**
	* Check if King can move to position (R, C) or not.
	* Only check basic logic.
	* Situation does not count: already have a piece on (R, C).
	* 
	* @param  R  row number to check on
	* @param  C  column number to check on
	* @return    true / false
	*/
	public boolean canMove(int R, int C) {
		// Out of bound?
		if (out_of_bound(R, C))
			return false;
		
		// Invalid Move?
		if (Math.abs(R - this.position[0]) >= 2 || Math.abs(C - this.position[1]) >= 2)
			return false;
		
		// Invalid to move to the same position
		if (R == this.position[0] && C == this.position[1])
			return false;
		
		// Valid Move
		return true;
	}
	
   /**
	* Make a copy of King to create a new King at new position (R, C).
	* Called when pieces move to a new position.
	* Assigned King's gotCheckMate option to false.
	* 
	* @param  R       new row number assigned to the new King
	* @param  C       new column number assigned to the new King
	* @param  player  player assigned to new King (Black / White)
	* @return         new King just created
	*/
	public Piece copy(int R, int C, int player) {
		King new_King = new King(R, C, player);
		return new_King;
	}
}
