package Pieces;

import javax.swing.ImageIcon;

/**
* Creation and Logic of piece Rock
* Subclass of Class Piece
* 
* Rock is a custom piece that its initial position is at (3, 3) for Black and (4, 4) for White.
* Rock acts like an obstacle on the board, and it cannot move at all.
* Rock cannot be captured by any pieces beside Hammer (another custom chess piece).
*/
public class Rock extends Piece {
	
	/**
	* Rock Constructor
	*
	* @param  R       row number assigned to Rock
	* @param  C       column number assigned to Rock
	* @param  player  player assigned to Rock (Black / White)
	*/
	public Rock(int R, int C, int player) {
		this.position[0] = R;
		this.position[1] = C;
		this.player = player;
		
		// Image
		if (player == 1) {
			this.icon = new ImageIcon("src/Images/Black_Rock.png");
		} else {
			this.icon = new ImageIcon("src/Images/White_Rock.png");
		}
	}
		
   /**
	* Return the current position of current Rock.
	*
	* @return  current position of current Rock
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
	* Check if Rock can move to position (R, C) or not.
	* Which will always return false.
	* 
	* @param  R  row number to check on
	* @param  C  column number to check on
	* @return    true / false
	*/
	public boolean canMove(int R, int C) {
		return false;
	}
	
	/**
	* Simply create an empty method because its super class has this method as abstract.
	* 
	* @param  R       new row number assigned to the new Rook
	* @param  C       new column number assigned to the new Rook
	* @param  player  player assigned to new Rook (Black / White)
	* @return         new Rook just created
	*/
	public Piece copy(int R, int C, int player) {
		System.out.println("Cannot Create a New Rock !!!");
		return this;
	}
}
