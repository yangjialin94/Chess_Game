package Pieces;

import javax.swing.ImageIcon;

/**
* Creation and Logic of piece Hammer
* Subclass of Class Piece
* 
* Hammer is a custom piece that its initial position is at (2, 0) for Black and (5, 7) for White.
* Hammer moves like a Queen, and it is the only piece that can captures Rock (another custom chess piece).
* Hammer can do every things except crossing the obstacles.
*/
public class Hammer extends Piece {

   /**
	* Hammer Constructor
	*
	* @param  R       row number assigned to Bishop
	* @param  C       column number assigned to Bishop
	* @param  player  player assigned to Bishop (Black / White)
	*/
	public Hammer(int R, int C, int player) {
		this.position[0] = R;
		this.position[1] = C;
		this.player = player;
		
		// Image
		if (player == 1) {
			this.icon = new ImageIcon("src/Images/Black_Hammer.png");
		} else {
			this.icon = new ImageIcon("src/Images/White_Hammer.png");
		}
	}
	
   /**
	* Return the current position of current Hammer.
	*
	* @return  current position of current Hammer
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
	* Check if Hammer can move to position (R, C) or not.
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
	* Make a copy of Hammer to create a new Hammer at new position (R, C).
	* Called when pieces move to a new position.
	* 
	* @param  R       new row number assigned to the new Hammer
	* @param  C       new column number assigned to the new Hammer
	* @param  player  player assigned to new Bishop (Black / White)
	* @return         new Bishop just created
	*/
	public Piece copy(int R, int C, int player) {
		Hammer new_Hammer = new Hammer(R, C, player);
		return new_Hammer;
	}
}
