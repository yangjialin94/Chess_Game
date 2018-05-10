package Pieces;

import javax.swing.ImageIcon;

/**
* Creation and Logic of piece Pawn
* Subclass of Class Piece
*/
public class Pawn  extends Piece {

   /**
	* Pawn Constructor
	*
	* @param  R       row number assigned to Pawn
	* @param  C       column number assigned to Pawn
	* @param  player  player assigned to Pawn (Black / White)
	*/
	public Pawn(int R, int C, int player) {
		this.position[0] = R;
		this.position[1] = C;
		this.player = player;
		
		// Image
		if (player == 1) {
			this.icon = new ImageIcon("src/Images/Black_Pawn.png");
		} else {
			this.icon = new ImageIcon("src/Images/White_Pawn.png");
		}
	}
		
   /**
	* Return the current position of current Pawn.
	*
	* @return  current position of current Pawn
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
	* Check if Pawn can move to position (R, C) or not.
	* Only check basic logic.
	* Situations do not count: already have a piece on (R, C); move diagonal only when capture opponent's pieces.
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
		int diff_r = R - this.position[0];
		int diff_c = C - this.position[1];
		
		if (this.player == 1) {
			if (this.position[0] == 1) {									// First Step
				if (diff_r==1 && diff_c==0 || diff_r==2 && diff_c==0 || Math.abs(diff_c) == 1 && diff_r == 1)
					return true;	
				else
					return false;
			} else {
				if ((diff_r == 1) && (diff_c == 0 || diff_c == -1 || diff_c == 1))
					return true;
				else
					return false;
			}
		}
		else {
			if (this.position[0] == 6) {									// First Step
				if (diff_r==-1 && diff_c==0 || diff_r==-2 && diff_c==0 || Math.abs(diff_c) == 1 && diff_r == -1)
					return true;
				else
					return false;
			} else {
				if ((diff_r == -1) && (diff_c == 0 || diff_c == -1 || diff_c == 1))
					return true;
				else
					return false;
			}
		}
	}
				
   /**
	* Make a copy of Pawn to create a new Pawn at new position (R, C).
	* Called when pieces move to a new position.
	* 
	* @param  R       new row number assigned to the new Pawn
	* @param  C       new column number assigned to the new Pawn
	* @param  player  player assigned to new Pawn (Black / White)
	* @return         new Pawn just created
	*/
	public Piece copy(int R, int C, int player) {
		Pawn new_Pawn = new Pawn(R, C, player);
		return new_Pawn;
	}
}
