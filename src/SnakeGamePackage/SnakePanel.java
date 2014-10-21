package SnakeGamePackage;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/*****************************************************************
 * The GUI that creates a user-friendly display for the SnakeGame
 * @author Mitchell
 * @version 9/17/14
 ****************************************************************/

public class SnakePanel extends JPanel{

	/** the game board of JButtons **/
	private JButton[][] board;
	
	/** the timer that moves the Snake across the board **/
	public static Timer timer;
	
	/** Used for choosing to play either Snake or Data Worm **/
	private static JButton sGame, dGame;
	
	/** Used for changing games while the program is running **/
	private static JButton cGame, cGame2;
	
	/** Used for Starting the game **/
	private static JButton go;
	
	/** Used for Pausing/Resuming the game **/
	private static JButton pr;
	
	/** Used for the Snake Color prompt at the beginning of the game **/
	private static JButton sBlue, sGreen, sRed, sPink;
	
	/** Used for the Orb Color prompt at the beginning of the game **/
	private static JButton oYellow, oOrange, oViolet, oTeal;
	
	/** Used for the Background Color prompt at the beginning of the game **/
	private static JButton bBlack, bGrey, bWhite, bLGreen;
	
	/** used to check the values of Cells in the game **/
	private Cell iCell;
	
	/** the actual game that the GUI uses to play SnakeGame **/
	private SnakeGame game;
	
	/** the size of the game board **/
	private int boardSize;
	
	/** panels to contain different components of the GUI **/
	private JPanel masterPanel, topPanel, mainPanel, bottomPanel;
	
	/** Button Listener for the JButtons **/
	private static ButtonListener m1;
	
	/** Timer Listener for the Timer **/
	private static TimerListener t1;
	
	/** JLabel for the title of the game **/
	private JLabel title;
	
	/** JLabel for the score of the game **/
	private JLabel score, space;
	
	/** stores the colors prompted by the user **/
	private Color sC, oC, bC;
	
	/** the current game type **/
	private String gType;
	
	/** the current state of the timer **/
	private String tState;
	
	/** holds the info for the last move made **/
	private String lastMove;

	/******************************************************
	 * Constructor for the GUI
	 ******************************************************/
	public SnakePanel(){
		
		//instantiates all instance variables
		masterPanel = new JPanel();
		mainPanel = new JPanel();
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		m1 = new ButtonListener();
		t1 = new TimerListener();
		lastMove = ("up");
		tState = ("running");
		game = new SnakeGame();
		boardSize = game.getSize();
		game.setGameType(gType);
		game.reset();
		timer = new Timer(150, t1);
		space = new JLabel("                     "); //21 spaces
		space.setFont(new Font("TimesNewRoman", Font.BOLD, 20));
		title = new JLabel(gType);
		title.setFont(new Font("TimesNewRoman", Font.BOLD, 26));
		score = new JLabel("Score: " + game.getSnakeLength());
		score.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
		
		//adds buttons the the 2-dim array board
		createBoard();
		
		//displays the board
		displayBoard();
		
		//sets the layout and color
		masterPanel.setLayout(new BorderLayout(10,10));
		masterPanel.setBackground(Color.black);
		topPanel.setBackground(Color.lightGray);
		bottomPanel.setBackground(Color.lightGray);
		setBackground(Color.black);
		
		//adds Key Bindings for the arrow Keys
		ArrowKeyBinding();
		
		//adds all panels to the contentPane
		topPanel.add(title);
		bottomPanel.add(score);
		bottomPanel.add(space);
		bottomPanel.add(pr);
		bottomPanel.add(cGame);
		masterPanel.add(BorderLayout.NORTH, topPanel);
		masterPanel.add(BorderLayout.CENTER, mainPanel);
		masterPanel.add(BorderLayout.SOUTH, bottomPanel);
		add(masterPanel);
	}
	
	/**********************************************************
	 * Creates the board for the GUI
	 **********************************************************/
	private void createBoard(){
		board = new JButton[boardSize][boardSize];
		
		//creates each JButton in the board
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				board[row][col] = new JButton ();
				board[row][col].setPreferredSize(new Dimension(27,27));
				board[row][col].setMargin(new Insets(0,0,0,0));
				board[row][col].setEnabled(false);
				mainPanel.add(board[row][col]);
			}
		}
		
		//sets the layout of the panel
		mainPanel.setLayout(new GridLayout(boardSize,boardSize));
		
		//creates the "switch game" buttons
		cGame = new JButton("");
		cGame.addActionListener(m1);
		cGame2 = new JButton("");
		cGame2.addActionListener(m1);
		setSwitchText();
		
		//creates the pause button
		pr = new JButton("Pause");
		pr.addActionListener(m1);
	}
	
	/******************************************************
	 * Sets the text of the Switch game button
	 ******************************************************/
	public void setSwitchText(){
		if(gType.equals("Snake")){
			cGame.setText("Switch to DataWorm");
			cGame2.setText("Switch to DataWorm");
		}
		else if(gType.equals("DataWorm")){
			cGame.setText("Switch to Snake");
			cGame2.setText("Switch to Snake");
		}
	}
	
	/**********************************************************
	 * Pauses / Resumes the current timer in the game
	 **********************************************************/
	public void setPauseResume(){
		if(tState.equals("paused")){
			timer.stop();
			pr.setText("Resume");
			space.setText("   PAUSED   ");
		}
		else if(tState.equals("running")){
			timer.restart();
			pr.setText("Pause");
			space.setText("                     "); //21 spaces
			requestFocusInWindow();
		}
	}

	/**********************************************************
	 * Sets an appropriate color to each JButton within the GUI
	 **********************************************************/
	private void displayBoard(){
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				iCell = game.getCell(row,col);
				board[row][col].setText("");
				if(iCell.isSnake()){
					board[row][col].setBackground(sC);
				}
				else if(iCell.isOrb()){
					board[row][col].setBackground(oC);
				}
				else{
					board[row][col].setBackground(bC);
				}
			}
		}
	}
	
	/************************************************************
	 * Prompts the user to set the colors for the game
	 ***********************************************************/
	public static void promptUser(){
		//creates all buttons
		sBlue = new JButton("Blue");
		sGreen = new JButton("Green");
		sRed = new JButton("Red");
		sPink = new JButton("Pink");
		oYellow = new JButton("Yellow");
		oOrange = new JButton("Orange");
		oViolet = new JButton("Purple");
		oTeal = new JButton("Teal");
		bBlack = new JButton("Black");
		bGrey = new JButton("Grey");
		bWhite = new JButton("White");
		bLGreen = new JButton("Dark Green");
		
		//adds listeners to the JButtons
		sBlue.addActionListener(m1);
		sGreen.addActionListener(m1);
		sRed.addActionListener(m1);
		sPink.addActionListener(m1);
		oYellow.addActionListener(m1);
		oOrange.addActionListener(m1);
		oViolet.addActionListener(m1);
		oTeal.addActionListener(m1);
		bBlack.addActionListener(m1);
		bGrey.addActionListener(m1);
		bWhite.addActionListener(m1);
		bLGreen.addActionListener(m1);
		
		JOptionPane.showOptionDialog(null, "Choose a color for the Snake", "Snake Color", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{sBlue, sGreen, sRed, sPink}, sBlue);
		JOptionPane.showOptionDialog(null, "Choose a color for the Orbs", "Orb Color", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{oYellow, oOrange, oViolet, oTeal}, oYellow);
		JOptionPane.showOptionDialog(null, "Choose a color for the Background", "Background Color", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{bBlack, bGrey, bWhite, bLGreen}, bBlack);
	}
	
	/************************************************************
	 * Prompts the User to choose a game
	 ************************************************************/
	public static void promptUser2(){
		//creates all buttons
		sGame = new JButton("Snake  (Original)");
		dGame = new JButton("Data Worm  (Multiple Orbs)");
		
		//adds listeners to the JButtons
		sGame.addActionListener(m1);
		dGame.addActionListener(m1);
		
		JOptionPane.showOptionDialog(null, "Choose which game you would like to play.", "Choose Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{sGame, dGame}, sGame);
	}
	
	/********************************************************************************************
	 * Prompts if the user is ready for the Start of the Game (used at the start of the program)
	 ********************************************************************************************/
	public static void promptStart(){
		go = new JButton("Go!");
		go.addActionListener(m1);
		JOptionPane.showOptionDialog(null, "Ready?", " ", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{go}, go);
	}

	/********************************************************************************************
	 * Prompts if the user is ready for the Start of the Game (used after the start of the program)
	 ********************************************************************************************/
	public static void promptStart2(){
		go = new JButton("Go!");
		go.addActionListener(m1);
		JOptionPane.showOptionDialog(null, "Ready?", " ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{go, cGame2}, go);
	}
	
	/************************************************************
	 * ActionListener class for the GUI
	 ************************************************************/
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//checks which Game was selected
			if(e.getSource() == sGame){
				gType = ("Snake");			
			}
			else if(e.getSource() == dGame){
				gType = ("DataWorm");
			}
			
			//switches the game type
			else if(e.getSource() == cGame || e.getSource() == cGame2){
				if(gType.equals("Snake")){
					gType = ("DataWorm");
				}
				else if(gType.equals("DataWorm")){
					gType = ("Snake");
				}
				game.setGameType(gType);
				
				setSwitchText();
				title.setText(gType);
				newGame();
				requestFocusInWindow();
			}
			
			//starts the game
			else if(e.getSource() == go){
				timer.start();
			}
			
			//pauses or resumes the game
			else if(e.getSource() == pr){
				if(tState.equals("running")){
					tState = ("paused");
				}
				else if(tState.equals("paused")){
					tState = ("running");
				}
				
				setPauseResume();
			}
			
			//checks which Snake color was selected
			else if(e.getSource()==sBlue){
				sC = Color.blue;
			}
			else if(e.getSource()==sGreen){
				sC = Color.green;
			}
			else if(e.getSource()==sRed){
				sC = Color.red;
			}
			else if(e.getSource()==sPink){
				sC = Color.pink;
			}
			
			//checks which Orb color was selected
			else if(e.getSource()==oYellow){
				oC = Color.yellow;
			}
			else if(e.getSource()==oOrange){
				oC = Color.orange;
			}
			else if(e.getSource()==oViolet){
				oC = Color.magenta;
			}
			else if(e.getSource()==oTeal){
				oC = Color.cyan;
			}
			
			//checks which Background color was selected
			else if(e.getSource()==bBlack){
				bC = Color.black;
			}
			else if(e.getSource()==bGrey){
				bC = Color.gray;
			}
			else if(e.getSource()==bWhite){
				bC = Color.white;
			}
			else if(e.getSource()==bLGreen){
				bC = new Color(0,100,0,250);
			}
			
			 Window[] windows = Window.getWindows();
	            for (Window window : windows) {
	                if (window instanceof JDialog) {
	                    JDialog dialog = (JDialog) window;
	                    if (dialog.getContentPane().getComponentCount() == 1
	                            && dialog.getContentPane().getComponent(0) instanceof JOptionPane) {
	                        dialog.dispose();
	                    }
	                }
	            }
		}
	}
	
	/****************************************************************
	 * ActionListener for the Timer
	 ***************************************************************/
	private class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int x = game.getHeadX();
			int y = game.getHeadY();
			
			//moves the snake UP
			if(lastMove.equals("up")){
				if(x == 0){
					game.setStatus(GameStatus.Lost);
					checkGameStatus();
					return;
				}
				game.MoveSnake(x - 1, y);
				checkGameStatus();
				displayBoard();
			}
			
			//moves the snake DOWN
			else if(lastMove.equals("down")){
				if(x == boardSize -1){
					game.setStatus(GameStatus.Lost);
					checkGameStatus();
					return;
				}
				game.MoveSnake(x + 1, y);
				checkGameStatus();
				displayBoard();
			}
			
			//moves the snake LEFT
			else if(lastMove.equals("left")){
				if(y == 0){
					game.setStatus(GameStatus.Lost);
					checkGameStatus();
					return;
				}
				game.MoveSnake(x, y - 1);
				checkGameStatus();
				displayBoard();
			}
			
			//moves the snake RIGHT
			else if(lastMove.equals("right")){
				if(y == boardSize -1){
					game.setStatus(GameStatus.Lost);
					checkGameStatus();
					return;
				}
				game.MoveSnake(x, y + 1);
				checkGameStatus();
				displayBoard();
			}
		}
	}
	
	/************************************************************
	 * KeyBinding for the Arrow Keys
	 ************************************************************/
	private void ArrowKeyBinding(){
		//creates Action Events
		//for Up Arrow
		Action upAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				int x = game.getHeadX();
				int y = game.getHeadY();
				if(lastMove.equals("down")){
					if(game.getSnakeLength() != 1){
						return;
					}
				}
//				game.MoveSnake(x - 1, y);
				lastMove = ("up");
//				checkGameStatus();				
//				displayBoard();
			}
		};
		//for Down Arrow
		Action downAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				int x = game.getHeadX();
				int y = game.getHeadY();
				if(lastMove.equals("up")){
					if(game.getSnakeLength() != 1){
						return;
					}
				}
//				game.MoveSnake(x + 1, y);
				lastMove = ("down");
//				checkGameStatus();				
//				displayBoard();
			}
		};
		//for Left Arrow
		Action leftAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				int x = game.getHeadX();
				int y = game.getHeadY();
				if(lastMove.equals("right")){
					if(game.getSnakeLength() != 1){
						return;
					}
				}
//				game.MoveSnake(x, y - 1);
				lastMove = ("left");
//				checkGameStatus();				
//				displayBoard();
			}
		};
		//for Right Arrow
		Action rightAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				int x = game.getHeadX();
				int y = game.getHeadY();
				if(lastMove.equals("left")){
					if(game.getSnakeLength() != 1){
						return;
					}
				}
//				game.MoveSnake(x, y + 1);
				lastMove = ("right");
//				checkGameStatus();
//				displayBoard();
			}
		};
		
		getInputMap().put(KeyStroke.getKeyStroke("UP"), "upAction");
		getActionMap().put("upAction", upAction);
		
		getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downAction");
		getActionMap().put("downAction", downAction);
		
		getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
		getActionMap().put("leftAction", leftAction);
		
		getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
		getActionMap().put("rightAction", rightAction);
		
		}
	
	/************************************************************
	 * A method that checks if the game has been won or lost
	 ************************************************************/
	public void checkGameStatus(){
		//keep score
		score.setText("Score: " + game.getSnakeLength());
		
		//checks if the user has lost or won yet
		if(game.getStatus() == GameStatus.Lost){
			JOptionPane.showMessageDialog(null, "You lose\nYour final score was: " + game.getSnakeLength());							
			newGame();
		}
		if(game.getStatus() == GameStatus.Won){
			JOptionPane.showMessageDialog(null, "Congratulations!\nYou have won the game");
			newGame();
		}
	}
	
	/********************************************************
	 * Resetting things for a new game
	 ********************************************************/
	public void newGame(){
		if(tState.equals("paused")){
			tState = "running";
			setPauseResume();
		}
		game.reset();
		lastMove = ("up");
		displayBoard();
		score.setText("Score: " + game.getSnakeLength());
		timer.stop();
		promptStart2();
	}
}

