package Pieces;

import javax.swing.ImageIcon;

/**
* Creation and Logic of piece Knight
* Subclass of Class Piece
*/
public class Knight extends Piece {

   /**
	* Knight Constructor
	*
	* @param  R       row number assigned to Knight
	* @param  C       column number assigned to Knight
	* @param  player  player assigned to Bishop (Black / White)
	*/
	public Knight(int R, int C, int player) {
		this.position[0] = R;
		this.position[1] = C;
		this.player = player;
		
		// Image
		if (player == 1) {
			this.icon = new ImageIcon("src/Images/Black_Knight.png");
		} else {
			this.icon = new ImageIcon("src/Images/White_Knight.png");
		}
	}
		
   /**
	* Return the current position of current Knight.
	*
	* @return  current position of current Knight
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
	* Check if Knight can move to position (R, C) or not.
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
		
		if (diff_r == 1 && diff_c == 2 || diff_r == 2 && diff_c == 1) 
			return true;
		else
			return false;
	}
			
   /**
	* Make a copy of Knight to create a new Knight at new position (R, C).
	* Called when pieces move to a new position.
	* 
	* @param  R       new row number assigned to the new Knight
	* @param  C       new column number assigned to the new Knight
	* @param  player  player assigned to new Knight (Black / White)
	* @return         new Knight just created
	*/
	public Piece copy(int R, int C, int player) {
		Knight new_Knight = new Knight(R, C, player);
		return new_Knight;
	}
}
