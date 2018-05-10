package Pieces;

import javax.swing.ImageIcon;

/**
* Creation and Logic of piece Rook
* Subclass of Class Piece
*/
public class Rook extends Piece {

   /**
	* Rook Constructor
	*
	* @param  R       row number assigned to Rook
	* @param  C       column number assigned to Rook
	* @param  player  player assigned to Rook (Black / White)
	*/
	public Rook(int R, int C, int player) {
		this.position[0] = R;
		this.position[1] = C;
		this.player = player;
		
		// Image
		if (player == 1) {
			this.icon = new ImageIcon("src/Images/Black_Rook.png");
		} else {
			this.icon = new ImageIcon("src/Images/White_Rook.png");
		}
	}
		
   /**
	* Return the current position of current Rook.
	*
	* @return  current position of current Rook
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
	* Check if Rook can move to position (R, C) or not.
	* Only check basic logic.
	* Situation does not count: already have a piece on (R, Y).
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
		if (diff_r != 0 && diff_c != 0)
			return false;
		
		// Invalid to move to the same position
		if (R == this.position[0] && C == this.position[1])
			return false;
		
		// Valid Move
		return true;
	}
	
   /**
	* Make a copy of Rook to create a new Rook at new position (R, C).
	* Called when pieces move to a new position.
	* 
	* @param  R       new row number assigned to the new Rook
	* @param  C       new column number assigned to the new Rook
	* @param  player  player assigned to new Rook (Black / White)
	* @return         new Rook just created
	*/
	public Piece copy(int R, int C, int player) {
		Rook new_Rook = new Rook(R, C, player);
		return new_Rook;
	}
}
