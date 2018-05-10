package Pieces;

/**
* Chess_Board Class
* Create chess board at the beginning stage.
* Logic of piece movement, capture, and game ending conditions.
* In case that I don't mess up with the correct codes that I wrote last time, I am not going to change (X, Y) to (R, C) in this class.
*/
public class Chess_Board {
	
   /**
    * Declarations for board, Kings' position, and outcomes
    * Board Size: 8 * 8
    */
	public Piece [][] board;
	public boolean nomal_board;
	public int [] King_B = new int [2];
	public int [] King_W = new int [2];
	public boolean Black_win;
	public boolean White_win;
	public boolean Draw;
	public boolean black_check;
	public boolean white_check;
	
   /**
	* Chess_Board Constructor
	* Create a new chess board with all pieces at starting position.
	*/
	public Chess_Board(boolean normal) {	
		// Create the Chess Board
		board = new Piece [8][8];
		nomal_board = normal;
		
		// Initialize Player One's Piece: Black(1)
		board[0][0] = new Rook(0, 0, 1);
		board[0][1] = new Knight(0, 1, 1);
		board[0][2] = new Bishop(0, 2, 1);
		board[0][3] = new Queen(0, 3, 1);
		board[0][4] = new King(0, 4, 1);
		board[0][5] = new Bishop(0, 5, 1);
		board[0][6] = new Knight(0, 6, 1);
		board[0][7] = new Rook(0, 7, 1);
		
		for (int i = 0; i <= 7; i++) {
			board[1][i] = new Pawn(1, i, 1);
		}
		
		King_B[0] = 0;
		King_B[1] = 4;
		
		// Initialize Player Two's Piece: White(0)
		board[7][0] = new Rook(7, 0, 0);
		board[7][1] = new Knight(7, 1, 0);
		board[7][2] = new Bishop(7, 2, 0);
		board[7][3] = new Queen(7, 3, 0);
		board[7][4] = new King(7, 4, 0);
		board[7][5] = new Bishop(7, 5, 0);
		board[7][6] = new Knight(7, 6, 0);
		board[7][7] = new Rook(7, 7, 0);
				
		for (int i = 0; i <= 7; i++) {
			board[6][i] = new Pawn(6, i, 0);
		}
		
		King_W[0] = 7;
		King_W[1] = 4;
		
		Black_win = false;
		White_win = false;
		Draw = false;
		
		black_check = false;
		white_check = false;
		
		if (normal == false) {
			// Custom Pieces for Black
			board[3][3] = new Rock(3, 3, 1);
			board[2][0] = new Hammer(2, 0, 1);
			
			// Custom Pieces for Black
			board[4][4] = new Rock(4, 4, 0);
			board[5][7] = new Hammer(5, 7, 0);
		}
	}

   /** 
    * Make a copy for current board.
    * Used in canMove() function.
    */
	public Chess_Board copy(boolean normal){
		Chess_Board dup_board = new Chess_Board(normal);
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.board[i][j] != null)
					dup_board.board[i][j] = this.board[i][j].copy(i, j, board[i][j].player);
				else
					dup_board.board[i][j] = null;
			}
		}
		
		dup_board.King_B[0] = this.King_B[0];
		dup_board.King_B[1] = this.King_B[1];
		dup_board.King_W[0] = this.King_W[0];
		dup_board.King_W[1] = this.King_W[1];

		return dup_board;
	}
	
  /**
	* Return the current board.
	* Called when updating board GUI.
	*/
	public Piece[][] getCurrentBoard(){
		return this.board;
	}
	
   /**
	* Get piece at position (X, Y).
	* 
	* @param  X  row number of the position to get 
	* @param  Y  column number of the position to get 
	* @return    Piece at position (X, Y)
	*/
	public Piece get_piece(int X, int Y) {
		if (board[X][Y] != null)
			return board[X][Y];
		else
			return null;
	}
	
   /**
	* Check if position (X, Y) is occupied by a player's friendly piece.
	* 
	* @param  X       row number to check on 
	* @param  Y       column number to check on
	* @param  player  current player
	* @return         true / false
	*/
	public boolean occupied_f(int X, int Y, int player) {
		// Not empty and same player
		if (board[X][Y] != null && board[X][Y].player == player)
			return true;
		else
			return false;
	}
	
   /**
	* Check if position (X, Y) is occupied by a player's opponent piece.
	* 
	* @param  X       row number to check on 
	* @param  Y       column number to check on
	* @param  player  current player
	* @return         true / false
	*/
	public boolean occupied_o(int X, int Y, int player) {
		// Not empty and different player
		if (board[X][Y] != null && board[X][Y].player != player)
			return true;
		else
			return false;
	}
	
   /**
	* Get King's position of player
	* 
	* @param  player  current player
	* @return         King's position of player
	*/
	public int[] King_pos(int player) {
		if (player == 0)
			return King_W;
		else
			return King_B;
	}
	
   /**
	* Check if there any pieces on the path between position (X, Y) and (new_X, new_Y) EXCLUSIVELY.
	* Conditions to check on: Vertical; Horizontal; Diagonal.
	* 
	* @param  X      row number of starting position to check on 
	* @param  Y      column number of starting position to check on 
	* @param  new_X  row number of ending position to check on
	* @param  new_Y  column number of ending position to check on
	* @return        true / false
	*/
	public boolean obstacle(int X, int Y, int new_X, int new_Y) {
		int diff_r = Math.abs(X - new_X);
		int diff_c = Math.abs(Y - new_Y);

		if (X == Y && new_X == new_Y)			// Same position?
			return false;
		
		if (diff_r <= 1 && diff_c <= 1)			// If it is neighbor?
			return false;
		
		// Vertical
		if (diff_c == 0) {
			for (int i = 1; i < diff_r; i++) {
				if (new_X > X) {
					if (board[X+i][Y] != null)
						return true;
				}
				else {
					if (board[X-i][Y] != null)
						return true;
				}
			}
		}
		
		// Horizontal
		if (diff_r == 0) {
			for (int i = 1; i < diff_c; i++) {
				if (new_Y > Y) {
					if (board[X][Y+i] != null)
						return true;
				}
				else {
					if (board[X][Y-i] != null)
						return true;
				}
			}
		}

		// Diagonal
		if (diff_c == diff_r) {
			if (new_X < X && new_Y < Y) {
				for (int i = 1; i < diff_c; i++) {
					if (board[X-i][Y-i] != null) {
						return true;
					}
				}
			} 
			
			if (new_X < X && new_Y > Y) {
				for (int i = 1; i < diff_c; i++) {
					if (board[X-i][Y+i] != null) {
						return true;
					}
				}
			}
			
			if (new_X > X && new_Y > Y) {
				for (int i = 1; i < diff_c; i++) {
					if (board[X+i][Y+i] != null) {
						return true;
					}
				}
			}

			if (new_X > X && new_Y < Y) {
				for (int i = 1; i < diff_c; i++) {
					if (board[X+i][Y-i] != null) {
						return true;
					}
				}
			}
		}
		
		// There's no obstacle in between
		return false;
	}
	 
   /**
	* Check if player's piece at position (X, Y) can move to position (new_X, new_Y).
	* Situations included: already have a friendly piece on (new_X, new_Y); obstacles on the path; Pawn's diagonal moving condition.
	* Situations did not count: have an opponent's piece on (X, Y) [Beside Pawn].
	* 
	* @param  X       row number of current position 
	* @param  Y       column number of current position 
	* @param  new_X   row number of target position
	* @param  new_Y   column number of target position
	* @param  player  current player
	* @return         true / false
	*/
	public boolean valid_move(int X, int Y, int new_X, int new_Y, int player) {
		// already have a friendly piece on (new_X, new_Y)?
		if (occupied_f(new_X, new_Y, player))
			return false;
		
		// hammer at (new_X, new_Y)?
		if (board[new_X][new_Y] instanceof Rock) {
			if (!(board[new_X][new_Y] instanceof Hammer))
				return false;
		}
		
		// more conditions to check if basic logic is correct
		if (board[X][Y].canMove(new_X, new_Y)) {
			if (board[X][Y] instanceof Knight || board[X][Y] instanceof King)			// Case for Knight
				return true;
			else if (board[X][Y] instanceof Pawn) {		// Special Case for Pawn: Diagonal Move; First Move
				int diff_r = Math.abs(X - new_X);
				int diff_c = Math.abs(Y - new_Y);
				if (diff_r == diff_c && board[new_X][new_Y] == null)
					return false;
				else if (diff_c == 0 && board[new_X][new_Y] != null)	// If [new_X][new_Y] has been occupied
					return false;
				else if (diff_c == 0 && diff_r == 2 && (new_X > X && board[X+1][Y] != null || new_X < X && board[X-1][Y] != null))	// First move cannot jump over other pieces
					return false;
				else
					return true;
			} 
			else {
				if (obstacle(X, Y, new_X, new_Y))		// Piece in the middle?
					return false;
				else
					return true;
			}
		}
		else
			return false;
	}

	/**
	* Capture opponent's piece at position (new_X, new_Y) with player's piece at position (X, Y).
	* Update King's position if needed.
	* Also check if opponent's Kings get Checkmate after movement of current turn.
	* 
	* @param  X       row number of player piece position 
	* @param  Y       column number of player piece position 
	* @param  new_X   row number for position of opponent's piece
	* @param  new_Y   column number for position of opponent's piece
	* @param  player  current player
	*/
	public void capture(int X, int Y, int new_X, int new_Y, int player) {
		// winning condition
		if (board[new_X][new_Y] instanceof King) {
			if (player == 0)
				White_win = true;
			else
				Black_win = true;
		}
		
		// Capture
		board[new_X][new_Y] = null;											// Remove opponent's piece at (new_X, new_Y)
		board[new_X][new_Y] = board[X][Y].copy(new_X, new_Y, player);		// Create a new piece at (new_X, new_Y)
		board[X][Y] = null;													// Remove old piece at (X, Y)
		
		// If it is King, update the current position
		if (board[new_X][new_Y] instanceof King) {
			if (player == 1) {
				King_B[0] = new_X;
				King_B[1] = new_Y;
			} else {
				King_W[0] = new_X;
				King_W[1] = new_Y;
			}
		}
	}

	/**
	* Move the piece from position (X, Y) to (new_X, new_Y).
	* Called capture to capture opponent's piece at position (new_X, new_Y) if there is one.
	* Update King's position if needed.
	* 
	* @param  X       row number of current position 
	* @param  Y       column number of current position 
	* @param  new_X   row number of moving position
	* @param  new_Y   column number of moving position
	* @param  player  current player
	*/
	public void move(int X, int Y, int new_X, int new_Y, int player) {
		// Moving opponent's piece?
		if (board[X][Y].player != player) {
			System.out.println("You can't mover other's Piece !!!");
			return;
		}

		// Invalid move?
		if (!valid_move(X, Y, new_X, new_Y, player)) {
			return;
		}
		else {
			// (new_X, new_Y) has been occupied by opponent? 
			if (occupied_o(new_X, new_Y, player)) {
				capture(X, Y, new_X, new_Y, player);
			} else {
				board[new_X][new_Y] = board[X][Y].copy(new_X, new_Y, player);		// Create a new piece at (new_X, new_Y)
				board[X][Y] = null;													// Remove old piece at (X, Y)
				
				// If it is King, update the current position
				if (board[new_X][new_Y] instanceof King) {
					if (player == 1) {
						King_B[0] = new_X;
						King_B[1] = new_Y;
					} else {
						King_W[0] = new_X;
						King_W[1] = new_Y;
					}
				}
			}

			// Check mate?

			// Get Opponent's King's position
			int [] Op_King = King_pos(Math.abs(player - 1));
			
			if (valid_move(new_X, new_Y, Op_King[0], Op_King[1], player)) {
				if (player == 1)
					black_check = true;
				else
					white_check = true;
			}
		}
	}
	
   /**
    * Print out the type and player at (x, y)
    * Used for test
    */
	public void print_type(int x, int y) {
		// Get piece
		Piece cur_piece = board[x][y];
		
		// Print 
		if (cur_piece.player == 1) {
			if (cur_piece instanceof King)
				System.out.println("Black King");
			else if (cur_piece instanceof Queen)
				System.out.println("Black Queen");
			else if (cur_piece instanceof Knight)
				System.out.println("Black Knight");
			else if (cur_piece instanceof Bishop)
				System.out.println("Black Bishop");
			else if (cur_piece instanceof Rook)
				System.out.println("Black Rook");
			else
				System.out.println("Black Pawn");
		}
		else {
			if (cur_piece instanceof King)
				System.out.println("White King");
			else if (cur_piece instanceof Queen)
				System.out.println("White Queen");
			else if (cur_piece instanceof Knight)
				System.out.println("White Knight");
			else if (cur_piece instanceof Bishop)
				System.out.println("White Bishop");
			else if (cur_piece instanceof Rook)
				System.out.println("White Rook");
			else
				System.out.println("White Pawn");
		}
	}

   /**
	* Check if player's King is checked by opponent's piece.
	* Called when checking the condition in stalemate and checkmate.
	*
	* @param  player  current player
	* @return         true / false
	*/
	public boolean inChecked(int player) {
		// Get King's position
		int king_x = King_pos(player)[0];
		int king_y = King_pos(player)[1];

		// Go over the board
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null && board[i][j].player != player && valid_move(i, j, king_x, king_y, 1-player)) {
					return true;
				}
			}
		}
		
		// player's king not being checked
		return false;
	}

   /**
	* Check if player's King have valid move to avoid Checkmate condition.
	* Called when checking the condition of stalemate and checkmate.
	* 
	* @param  player  current player
	* @return         true / false
	*/
	public boolean canMove(int player) {
		// Check if any possible move that will not caused checking condition
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				
				// Get player's pieces on board
				if (board[i][j] != null && board[i][j].player == player) {
					
					for (int o_i = 0; o_i < 8; o_i++) {
						for (int o_j = 0; o_j < 8; o_j++) {
							
							// if valid, make a copy and test it
							if (valid_move(i, j, o_i, o_j, player)) {
								
								// Same position?
								if (i != o_i || j != o_j) {
									
									
									// TEST
									print_type(i,j);
									System.out.println("From (" + i + "," + j + ") to (" + o_i + "," + o_j + ")");
									
								
									Chess_Board test_board = this.copy(nomal_board);
									test_board.move(i, j, o_i, o_j, player);
									
									if (test_board.inChecked(player) == false) {
										test_board = null;
										return true;
									}
									else {
										test_board = null;
									}
								}
							}
						}
					}
				}
			}
		}
		
		// No moves
		return false;
	}

   /**
	* Check if current player is being stalemate in current turn.
	* Called when checking the ending condition.
	* 
	* @param  player  current player
	* @return         true / false
	*/
	public boolean stalemate(int player) {
		
		if (inChecked(player) == false && canMove(player) == false)
			return true;
		else
			return false;
	}

   /**
	* Check if current player is being checkmate in current turn.
	* Called when checking the ending condition.
	* 
	* @param  player  current player
	* @return         true / false
	*/
	public boolean checkmate(int player) {
		
		if (inChecked(player) == true && canMove(player) == false)
			return true;
		else
			return false;
	}

   /**
	* Check if current player have chance to end the game in current turn. 
	* Check the conditions of Checkmate and Stalemate.
	* 
	* Game can be finished if current function returns true, if not then continue.
	* Print out the winner if there is one.
	* 
	* @param  player  current player
	* @return         true / false
	*/
	public boolean theEnd(int player) {
		if (stalemate(player)) {
			Draw = true;
			System.out.println("Draw");
			return true;
		}
		else if (checkmate(player)) {
			if (player == 1) {
				White_win = true;
				System.out.println("White win !");
			}
			
			if (player == 0)
			{
				Black_win = true;
				System.out.println("Black win !");
			}
			
			return true;
		}
		else
			return false;
	}
}


