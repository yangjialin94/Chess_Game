package Graphic;

import Pieces.Piece;
import Pieces.Chess_Board;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Stack;

import javax.swing.*;

/**
* Board Class
* Create a GUI for the Chess Board 
*/
public class Board extends JPanel {
	
   /**
	* Add default serial version ID
	*/
	private static final long serialVersionUID = 1L;
	
	
///////////////////////////////////////////////  Model  ///////////////////////////////////////////////
	
   /**
	* Declarations
	*/
	private JFrame frame;								// Frame
	private JButton grid[][] = new JButton[8][8];		// Grids
	private JPanel panel = new JPanel();  				// Chess board Panel
	private JPanel name_panel = new JPanel();  		    // Name Panel
	private JPanel score_panel = new JPanel();  		// Score Panel
	private Chess_Board temp_board;
	public Piece cur_piece = null;						// Current piece that want to move
	Piece [][] board;
	
	// Name Bar
	private JLabel player_black;
	private JLabel player_white;
	private JTextField name_black;
	private JTextField name_white;
	private String black_name;
	private String white_name;
	
	// Score Bar
	private JLabel player_black2;
	private JLabel player_white2;
	private JLabel score_black;
	private JLabel score_white;
	private int player_black_score = 0;
	private int player_white_score = 0;
	
	// Other variables
	private int cur_player;
	private Stack<Chess_Board> black_stack;
	private Stack<Chess_Board> white_stack;
	private boolean black_redoed;
	private boolean white_redoed;
	private boolean game_done;
	private boolean normal_game;
	

   /**
	* Board Constructor
	* Add the Menu Bar on the top.
	* Add the Score Bar at the bottom.
	* Create a new chess board with all pieces at current position.
	* Frame Size: 640 * 700
	*/
    public Board() {
    	// Create a Frame
        frame = new JFrame("Chess Game");
        frame.setSize(640, 700);
        frame.setLocationRelativeTo(null);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(name_panel, BorderLayout.NORTH);
        frame.add(score_panel, BorderLayout.SOUTH);

        // Create new board and initialize variables
        temp_board = new Chess_Board(true);
        cur_player = 0;
        black_stack = new Stack<Chess_Board>();
        white_stack = new Stack<Chess_Board>();
        black_redoed = false;
        white_redoed = false;
        game_done = false;
        
        // Setup bars and Pieces
        setUpMenu();
        setUpNamebar();
        setUpScorebar();
        updatePieces();
        
        // Set visibility and close option
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Black first
    	JOptionPane.showMessageDialog(frame, "White player move first");
    }
    
   /**
    * Initialize a new board
    * Called when players want to start a new game.
    */
    public void new_board(boolean normal) {
    	// Start a new board
    	temp_board = new Chess_Board(normal);
    	cur_player = 0; 
    	black_stack = new Stack<Chess_Board>();
        white_stack = new Stack<Chess_Board>();
        black_redoed = false;
        white_redoed = false;
        game_done = false;
        normal_game = normal;
    	updatePieces();
    	
    	// Black first
    	JOptionPane.showMessageDialog(frame, "White player move first");
    }
    

///////////////////////////////////////////////  Controller handles  ///////////////////////////////////////////////
    
   /**
    * Movement get and update
    * Called when pieces got clicked.
    */  
    private ActionListener Movement(final int R, final int C) {
        return new ActionListener() {
    	
			public void actionPerformed(ActionEvent e) { 
				
				// Didn't put names?
				if (name_black.getText().equals("") || name_white.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Please enter your name first!");
					return;
				}	
				else if (name_black.getText().equals(name_white.getText())) {				// different names?
					JOptionPane.showMessageDialog(frame, "Please enter different names!");
					return;
				}
				else {											// get name and update names in score bar
					black_name = name_black.getText();
					white_name = name_white.getText();
					updateScorebar();
				}
				
				// Already finished the game? Can't clicked the board.
				if (game_done) {
					JOptionPane.showMessageDialog(frame, "Game already done, start a new game!");
					return;
				}
				
				// Store board into stack. Saved for "Undo"
    			if (cur_player == 0) {
    				if (temp_board.nomal_board)
    					white_stack.add(temp_board.copy(true));
    				else
    					white_stack.add(temp_board.copy(false));
    			}
    			
    			if (cur_player == 1) {
    				if (temp_board.nomal_board)
    					black_stack.push(temp_board.copy(true));
    				else
    					black_stack.push(temp_board.copy(false));
    			}
				
		    	// Get the piece on the button clicked
				Piece cliked_piece = board[R][C];
		    		
	    		// First time to press a piece button
	    		if (cur_piece == null) {		// first click
	    			if (cliked_piece != null) {		// empty?
	    				if ((cliked_piece.player == 1 && cur_player == 0) || (cliked_piece.player == 0 && cur_player == 1)) {  // Opponent's piece?
	    					JOptionPane.showMessageDialog(frame, "Don't move other piece!");
	    				}
	    				else {
	    					cur_piece = cliked_piece;			// save the starting piece
	    				}
	    			}
	    			else {   // can't clicked empty space the on the first click
	    				JOptionPane.showMessageDialog(frame, "Don't always click empty space!");
	    			}
	    			
	    			return;
	    		}
	    		
	    		// Cannot move to the same place
	    		if (cur_piece == cliked_piece) {
	    			JOptionPane.showMessageDialog(frame, "You can't move to the same place");
	    			cur_piece = null;
					return;
				}
	    		
	    		// Get piece position and target position
	    		int r = cur_piece.getPosition()[0];
	    		int c = cur_piece.getPosition()[1];
	    		int t_r = R; 
	    		int t_c = C;
	    		
	    		// Can move?
	    		if (temp_board.valid_move(r, c, t_r,t_c, cur_player)) {
	    			
	    			// Check if current will make cur_player in check
	    			// If cur_player is still in check after this move, can't move! 
    				Chess_Board test_board = temp_board.copy(normal_game);
					test_board.move(r, c, R, C, cur_player);
					
					if (test_board.inChecked(cur_player)) {
						test_board = null;
						JOptionPane.showMessageDialog(frame, "Invalid move. You will be checked!");
		    			cur_piece = null;
						return;
					}
					else {
						test_board = null;
					}
	    			
	    			// Move 
	    			temp_board.move(r, c, t_r,t_c, cur_player);
	    			
	    			// Is it check? show the message
	    			if (temp_board.black_check) {
	    				JOptionPane.showMessageDialog(frame, "Balck Check !");
	    				temp_board.black_check = false;
	    			}
	    			
	    			if (temp_board.white_check) {
	    				JOptionPane.showMessageDialog(frame, "White Check !");
	    				temp_board.white_check = false;
	    			}
	    			
	    			// update flag for "Undo" function
	    			if (cur_player == 0)
	    		        white_redoed = false;
	    			else
	    				black_redoed = false;
	    			
	    			// switch player
	    			cur_player = Math.abs(cur_player - 1);		// switch turns
	    			
	    			// update GUI
	    			updatePieces();
	    			cur_piece = null; 			
	    			
	    			// Check ending condition. Show the message
	    			if (temp_board.theEnd(cur_player)) {
	    				
	    				boolean black = temp_board.Black_win;
	    				boolean white = temp_board.White_win;
	    				boolean draw = temp_board.Draw;
	    				
	    				// update scores and score bar
	    				game_done = true;
	    				
	    				if (black) {
	    					player_black_score++;
	    					updateScorebar();
	    					JOptionPane.showMessageDialog(frame, "Checkmate! Balck Win. Hit the Button above to Continue");
	    				}
	    				
	    				if (white) {
	    					player_white_score++;
	    					updateScorebar();
	    					JOptionPane.showMessageDialog(frame, "Checkmate! White Win. Hit the Button above to Continue");
	    				}
	    				
	    				if (draw) {
	    					player_black_score++;
	    					player_white_score++;
	    					updateScorebar();
	    					JOptionPane.showMessageDialog(frame, "Stalemate! Draw. Hit the Button above to Continue");
	    				}
	    			}
	    		}
	    		else {
	    			JOptionPane.showMessageDialog(frame, "Invalid Move !!!");  // Invalid move, show the message
	    			cur_piece = null;
	    		}
			}
        };
    }  
    
   /**
	* Action for starting a new normal game
	*/  
    private ActionListener NewNormalGame() {
    	return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new_board(true);
			}
    	};
    }
    
   /**
	* Action for starting a new custom game
	*/  
    private ActionListener NewCustomGame() {
    	return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new_board(false);
			}
    	};
    }
    
   /**
	* Action for restarting the current game
	*/  
    private ActionListener RestartGame() {
    	return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Already finished the game? Can't restart the game
				if (game_done) {
					JOptionPane.showMessageDialog(frame, "Game already done, start a new game!");
					return;
				}
				
				// Restart game if both players agree
	            int answer;
	            
	            if (cur_player == 0) {
	            	answer = JOptionPane.showConfirmDialog(frame, "White choose to restart. Do you agree?", "Restart", JOptionPane.YES_NO_OPTION);
	            }
	            else {
	            	answer = JOptionPane.showConfirmDialog(frame, "Black choose to restart. Do you agree?", "Restart", JOptionPane.YES_NO_OPTION);
	            }
	            
	            if (answer == JOptionPane.YES_OPTION ) {
	            	if (temp_board.nomal_board) {
						black_stack = new Stack<Chess_Board>();
				        white_stack = new Stack<Chess_Board>();
						new_board(true);
					}
					else {
						black_stack = new Stack<Chess_Board>();
				        white_stack = new Stack<Chess_Board>();
						new_board(false);
					}
	            }
			}
    	};
    }
    
   /**
	* Action for forfeiting the current game
	* Update the score and start a new game.
	*/  
    private ActionListener ForfeitGame() {
    	return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Didn't put names?
				if (name_black.getText().equals("") || name_white.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Please enter your name first!");
					return;
				}
				else if (name_black.getText().equals(name_white.getText())) {
					JOptionPane.showMessageDialog(frame, "Please enter different names!");
					return;
				}
				else {
					black_name = name_black.getText();
					white_name = name_white.getText();
					updateScorebar();
				}
				
				// update 
				if (cur_player == 1)
					player_white_score++;
				
				if (cur_player == 0)
					player_black_score++;
				
				cur_player = 0;
				updateScorebar();
				
				// new board
				if (temp_board.nomal_board) {
					new_board(true);
				}
				else {
					new_board(false);
				}
			}
    	};
    }
    
   /**
	* Action for undo button
	*/  
    private ActionListener Undo() {
    	return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// end game? can't undo
				if (game_done) {
					JOptionPane.showMessageDialog(frame, "Game is over!");
					return;
				}
				
				// white
				if (cur_player == 0) {
					// stack empty? can't undo in the first round
					if (white_stack.empty()) {
						JOptionPane.showMessageDialog(frame, "You can't undo in the first round!");
						return;
					}
					else if (white_redoed == true) {   // can't undo more than once in a single round
						JOptionPane.showMessageDialog(frame, "You can't undo more than once same round!");
						return;
					}
					else {
						if (temp_board.nomal_board) {	// undo the last round for current player
							temp_board = white_stack.pop().copy(true);
							updatePieces();
						}
						else {
							temp_board = white_stack.pop().copy(false);
							updatePieces();
						}
						
						white_redoed = true;
					}
				}
				
				// black
				if (cur_player == 1) {
					// stack empty?
					if (black_stack.empty()) {
						JOptionPane.showMessageDialog(frame, "You can't undo in the first round!");
						return;
					}
					else if (black_redoed == true) {
						JOptionPane.showMessageDialog(frame, "You can't undo more than once same round!");
						return;
					}
					else {
						if (temp_board.nomal_board) {
							temp_board = black_stack.pop().copy(true);
							updatePieces();
						}
						else {
							temp_board = black_stack.pop().copy(false);
							updatePieces();
						}
						
						black_redoed = true;
					}
				}
			}
    	};
    }
    
   /**
	* Action for switching player 
	* Add names for new players, reset the score bar, and start a new game.
	*/  
    private ActionListener SwitchPlayer() {
    	return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// new board
				if (temp_board.nomal_board) {
					black_stack = new Stack<Chess_Board>();
			        white_stack = new Stack<Chess_Board>();
					new_board(true);
				}
				else {
					black_stack = new Stack<Chess_Board>();
			        white_stack = new Stack<Chess_Board>();
					new_board(false);
				}
				
				// update
				refreshBar();
			}
    	};
    }
    
   /**
	* Action for Normal Game Rules button
	* Open the wiki page.
	*/  
    private ActionListener WikiHelp() {
    	return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Open wiki website
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://en.wikipedia.org/wiki/Chess"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
    	};
    }
    
   /**
	* Action for Normal Game Rules button
	* Open the message box that shows rules for custom game.
	*/  
    private ActionListener CustomHelp() {
    	return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Create the instruction 
				String instruction = "Here is the Custom Chess Game Rules:\n\n";
				instruction += "1. Rock: It is a piece that cannot move, capture, and check.\n";
				instruction += "		 It can be only captured by opponent's Hammer.\n";
				instruction += "		 It acts like a obstacle all the time during game.\n\n";
				instruction += "2. Hammer: It is a piece that act like a Queen.\n";
				instruction += "		   It is the only piece that can capture opponent's Rock.\n\n";
				instruction += "3. Other: Everything else is same as normal chess game.";
				
				// Show Message Dialog
				JOptionPane.showMessageDialog(frame, instruction);
			}
    	};
    }
    
    
///////////////////////////////////////////////  View component  ///////////////////////////////////////////////
    
   /**
	* Setup the board background & filling the images of pieces in grids.
	*/
    public void updatePieces() {
    	// remove, repaint, and revalidate the panel
    	panel.removeAll();
    	panel.repaint();
    	panel.revalidate();
    	
    	// Get the current board
		board = temp_board.getCurrentBoard();
    			
    	// Fill in gray and white background colors to each grid
        panel.setLayout(new GridLayout(8, 8));
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	
            	// new JButton in each grid
            	grid[i][j] = new JButton();
            	grid[i][j].setOpaque(true);
            	grid[i][j].setBorder(null);

            	// background colors (black / white)
                if ((i + j) % 2 == 0) {
                	grid[i][j].setBackground(Color.BLACK);
                } else {
                	grid[i][j].setBackground(Color.WHITE);
                }   
                
                // add images to the grids
                if (board[i][j] != null) {
                	ImageIcon image = board[i][j].getImage(); 
                	ImageIcon img = new ImageIcon(image.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
                	grid[i][j].setIcon(img);
                }
                
                // add Actions
                grid[i][j].addActionListener(Movement(i, j));
        		
                // add grid panel to the frame
                panel.add(grid[i][j]);
            }
        }
    }
	
   /**
	* Setup the menu bar
	*/
	private void setUpMenu() {
        JMenuBar upper_menubar = new JMenuBar();
        
        JButton new_normal_button = new JButton("New NormalGmae");
        new_normal_button.addActionListener(NewNormalGame());
        new_normal_button.setSelected(false);
        
        JButton new_custom_button = new JButton("New Custom Game");
        new_custom_button.addActionListener(NewCustomGame());
        new_custom_button.setSelected(false);
        
        JMenu new_game = new JMenu("New Game");
        new_game.add(new_normal_button);
        new_game.add(new_custom_button);
        upper_menubar.add(new_game);
        
        JButton normal_help_button = new JButton("Normal Game Rules");
        normal_help_button.addActionListener(WikiHelp());
        normal_help_button.setSelected(false);
        
        JButton custom_help_button = new JButton("Custom Game Rules");
        custom_help_button.addActionListener(CustomHelp());
        custom_help_button.setSelected(false);
        
        JMenu help = new JMenu("Help");
        help.add(normal_help_button);
        help.add(custom_help_button);
        upper_menubar.add(help);
        
        JButton forfeit_button = new JButton("Forfeit");
        forfeit_button.addActionListener(ForfeitGame());
        forfeit_button.setSelected(false);
        upper_menubar.add(forfeit_button);
        
        JButton restart_button = new JButton("Restart");
        restart_button.addActionListener(RestartGame());
        restart_button.setSelected(false);
        upper_menubar.add(restart_button);
        
        JButton undo_button = new JButton("Undo");
        undo_button.addActionListener(Undo());
        undo_button.setSelected(false);
        upper_menubar.add(undo_button);
        
        JButton switch_player = new JButton("New Player");
        switch_player.addActionListener(SwitchPlayer());
        switch_player.setSelected(false);
        upper_menubar.add(switch_player);
        
        frame.setJMenuBar(upper_menubar);
    }
	
   /**
	* Setup the Name Bar
	*/
	private void setUpNamebar() {
        player_black = new JLabel("Player One (Black) Name:");
        name_panel.add(player_black);
    	name_black = new JTextField(10);
    	name_panel.add(name_black);
    	player_white = new JLabel("Player Two (White) Name:");
    	name_panel.add(player_white);
    	name_white = new JTextField(10);
    	name_panel.add(name_white);
    }
	
   /**
	* Setup the Score Bar
	*/
	private void setUpScorebar() {
		player_black2 = new JLabel("Player One Score:");
		score_panel.add(player_black2);
		
		score_black = new JLabel();	
		score_black.setText(String.valueOf(player_black_score));
		score_panel.add(score_black);
		
		player_white2 = new JLabel("Player Two Score:");
		score_panel.add(player_white2);
		
		score_white = new JLabel();	
		score_white.setText(String.valueOf(player_white_score));
		score_panel.add(score_white);
	}
	
   /**
	* Update the Score Bar
	*/
	private void updateScorebar() {
		player_black2.setText(black_name);
		player_white2.setText(white_name);
		score_black.setText(String.valueOf(player_black_score));
		score_white.setText(String.valueOf(player_white_score));
	}
	
   /**
    * refresh Name bar and Score bar
    */
	private void refreshBar() {
		// empty the score
		player_black_score = 0;
		player_white_score = 0;
		
		// empty the name
		name_black.setText("");
		name_white.setText("");
		black_name = name_black.getText();
		white_name = name_white.getText();
		
		// update info
		updateScorebar();
	}
	
	
   /**
	* Main function
	*/
	public static void main(String[] args) {
		new Board();
	}
}
