package Pieces;

import javax.swing.ImageIcon;

/**
* Creation and Logic of piece Bishop
* Subclass of Class Piece
*/
public class Bishop extends Piece {

   /**
	* Bishop Constructor
	*
	* @param  R       row number assigned to Bishop
	* @param  C       column number assigned to Bishop
	* @param  player  player assigned to Bishop (Black / White)
	*/
	public Bishop(int R, int C, int player) {
		this.position[0] = R;
		this.position[1] = C;
		this.player = player;
		
		// Image
		if (player == 1) {
			this.icon = new ImageIcon("src/Images/Black_Bishop.png");
		} else {
			this.icon = new ImageIcon("src/Images/White_Bishop.png");
		}
	}
	
   /**
	* Return the current position of current Bishop.
	*
	* @return  current position of current Bishop
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
	* Check if Bishop can move to position (R, C) or not.
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
		int diff_r = Math.abs(R - this.position[0]);
		int diff_c = Math.abs(C - this.position[1]);
		if (diff_r != diff_c)
			return false;
				
		// Invalid to move to the same position
		if (R == this.position[0] && C == this.position[1])
			return false;
				
		// Valid Move
		return true;
	}
		
   /**
	* Make a copy of Bishop to create a new Bishop at new position (R, C).
	* Called when pieces move to a new position.
	* 
	* @param  R       new row number assigned to the new Bishop
	* @param  C       new column number assigned to the new Bishop
	* @param  player  player assigned to new Bishop (Black / White)
	* @return         new Bishop just created
	*/
	public Piece copy(int R, int C, int player) {
		Bishop new_Bishop = new Bishop(R, C, player);
		return new_Bishop;
	}
}
