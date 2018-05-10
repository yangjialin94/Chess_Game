package Pieces;

import javax.swing.ImageIcon;

/**
* Creation and Logic of piece Queen
* Subclass of Class Piece
*/
public class Queen extends Piece {

   /**
	* Queen Constructor
	*
	* @param  R       row number assigned to Queen
	* @param  C       column number assigned to Queen
	* @param  player  player assigned to Queen ( Black(1) / White(0) )
	*/
	public Queen(int R, int C, int player) {
		this.position[0] = R;
		this.position[1] = C;
		this.player = player;
		
		// Image
		if (player == 1) {
			this.icon = new ImageIcon("src/Images/Black_Queen.png");
		} else {
			this.icon = new ImageIcon("src/Images/White_Queen.png");
		}
	}
			
   /**
	* Return the current position of current Queen.
	*
	* @return  current position of current Queen
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
	* Check if Queen can move to position (R, C) or not.
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
		
		if (diff_r == 0 && diff_c != 0)
			return true;
		
		if (diff_c == 0 && diff_r != 0)
			return true;
		
		if (diff_c == diff_r && diff_c != 0)
			return true;
		
		return false;
	}
				
   /**
	* Make a copy of Queen to create a new Queen at new position (R, C).
	* Called when pieces move to a new position.
	* 
	* @param  R       new row number assigned to the new Queen
	* @param  C       new column number assigned to the new Queen
	* @param  player  player assigned to new Queen (Black / White)
	* @return         new Queen just created
	*/
	public Piece copy(int R, int C, int player) {
		Queen new_Queen = new Queen(R, C, player);
		return new_Queen;
	}
}
