package SnakeGamePackage;

import java.util.Random;

/******************************************************
 * Handles all of the game activities of SnakeGame
 * @author Mitchell Couturier
 *
 ******************************************************/

public class SnakeGame {

	/** a String that holds which game is being played **/
	private String wGame;
	/**a double array of Cells in the form of a game board **/
	private Cell[][] board;
	
	/**the status of the Game: Won, Lost, or NotOverYet **/
	private GameStatus status;
	
	/** holds the amount of cells that the snake currently contains **/
	private int snakeLength;
	
	/** holds the amount of orbs that should be on the game board. 
	 * (is always 1 for Snake game, and begins at 25 for Data Worm) **/
	private int orbCount;
	
	/** hold the amount of orbs actually on the game board **/
	private int orbsLaid;
	
	/**holds the x coordinate of the 'head' or 'front' or the snake **/
	private int headX;
	
	/**holds the y coordinate of the 'head' or 'front' or the snake **/
	private int headY;
	
	/** holds the color of object in snake game. 
	 * sColor is Snake. oColor is Orb. bColor is Background. **/
	private String sColor, oColor, bColor;
	
	/** holds the x and y dimensions of the game board (must be odd)**/
	private final int SIZE = 19;
	
	/** used in reducing the amount of orbs in DATA WORM **/
	int count;
	
	/*************************************************
	 * Returns the Cell asked for
	 *************************************************/
	public Cell getCell(int x, int y){
		return board[x][y];
	}
	
	/*************************************************
	 * Returns the Snake Color
	 ************************************************/
	public String getSColor(){
		return sColor;
	}
	
	/*************************************************
	 * Returns the Orb Color
	 ************************************************/
	public String getOColor(){
		return oColor;
	}
	
	/*************************************************
	 * Returns the Background Color
	 ************************************************/
	public String getBColor(){
		return bColor;
	}
	
	/*************************************************
	 * Returns the current status of the game
	 *************************************************/
	public GameStatus getStatus(){
		return status;
	}
	
	/************************************************
	 * Sets the current status of the game
	 ************************************************/
	public void setStatus(GameStatus g){
		status = g;
	}
	
	/**************************************************
	 * Sets which game will be played
	 **************************************************/
	public void setGameType(String type){
		wGame = type;
	}
	
	/*************************************************
	 * Returns the Size of the board
	 *************************************************/
	public int getSize(){
		return SIZE;
	}
	
	/*************************************************
	 * Returns the Snake Length
	 *************************************************/
	public int getSnakeLength(){
		return snakeLength;
	}
	
	/************************************************
	 * Returns headX. Used in the GUI for the 
	 * SnakeGame.moveSnake() x parameter
	 ************************************************/
	public int getHeadX(){
		return headX;
	}
	
	/************************************************
	 * Returns headY. Used in the GUI for the 
	 * SnakeGame.moveSnake() y parameter
	 ************************************************/
	public int getHeadY(){
		return headY;
	}
	
	/*************************************************
	 * Sets both headX and headY values.
	 * Used in the GUI.
	 *************************************************/
	public void setHead(int x, int y){
		headX = x;
		headY = y;
	}

	/*************************************************
	 * Constructor for SnakeGame. Sets up the game
	 * and prompts the user for colors.
	 *************************************************/
	public SnakeGame(){
		promptGame();
		reset();	
		promptColors();
	}
	
	/********************************************************************************
	 * Moves the Snake across the board. If Snake does not land on orb after moving, 
	 * subtracts one from each Snake Cell. If Snake lands on orb, Snake extends by 1.
	 ********************************************************************************/
	public void MoveSnake(int x, int y){
		//x and y are created in GUI using setHead() method
		headX = x;
		headY = y;
		
		//if(lands on snake) then status = lost. and return.
		if(board[x][y].isSnake()){
			status = GameStatus.Lost;
			return;
		}
		
		//if the SnakeHead lands on an orb, the snake extends
		else if(board[headX][headY].isOrb()){
			snakeLength++;
			//then, add the new head of the snake
			board[headX][headY].setSnake(true);
			board[headX][headY].setOrb(false);
			board[headX][headY].setSnakeCount(snakeLength);
			orbsLaid--;

			//decrements the total number of orbs every 4 times if Data Worm game
			if(wGame.equals("DataWorm")){
				count++;
				if(count == 4){
					count = 0;
					if(orbCount > 1){
						orbCount--;
					}
				}
			}
		}
		
		else{
			//decrements each snakeCount in Cell to show how long it will remain
			for(int row = 0; row < SIZE; row++){
				for(int col = 0; col < SIZE; col++){
					if(board[row][col].isSnake()){
						board[row][col].decSnakeCount();
						//if it reaches zero, it is no longer a Snake
						if(board[row][col].getSnakeCount() == 0){
							board[row][col].setSnake(false);
						}
					}
				}
			}
			//then, add the new head of the snake
			board[headX][headY].setSnake(true);
			board[headX][headY].setSnakeCount(snakeLength);
					
		}
		checkIfWon();
		layOrbs();
	}
	
	/************************************************
	 * Clears the entire board to blank Cells
	 ************************************************/
	public void setEmpty(){
		for(int row = 0; row < SIZE; row++){
			for(int col = 0; col < SIZE; col++){
				board[row][col] = new Cell(); //clears the Cell
			}
		}
	}
	
	/**********************************************************
	 * Lays Orbs in random Cells. Also used mid-game whenever 
	 * the Snake eats an orb (Unless it reaches a limit where 
	 * the orbCount changes). 
	 **********************************************************/
	public void layOrbs(){
		Random random = new Random();
		while(orbsLaid < orbCount){
			int col = random.nextInt(SIZE);
			int row = random.nextInt(SIZE);

			if(!board[row][col].isSnake() && !board[row][col].isOrb()){
				board[row][col].setOrb(true);
				orbsLaid++;
			}
		}
	}
	
	/*************************************************
	 * Checks if the user has won the game
	 *************************************************/
	public void checkIfWon(){
		for(int row = 0; row < SIZE; row++){
			for(int col = 0; col < SIZE; col++){
				if(!board[row][col].isSnake()){
					return;
				}
			}
		}
		//if all Cells are Snakes, the user has won
		status = GameStatus.Won;
	}
	
	/*************************************************
	 * Resets the board to a new game
	 *************************************************/
	public void reset(){
		status = GameStatus.NotOverYet;
		board = new Cell[SIZE][SIZE];
		
		//Orb Count varies depending on game
		if(wGame.equals("DataWorm")){
			orbCount = 25;
		}
		else if(wGame.equals("Snake")){
			orbCount = 1;
		}
		
		orbsLaid = 0;
		snakeLength = 1;
		setEmpty();
		count = 0;
		
		//centers the head of the Snake on the board
		headX = ((SIZE/2) + (SIZE/3));
		headY = (SIZE/2);
		board[headX][headY].setSnake(true);
		board[headX][headY].setSnakeCount(snakeLength);

		layOrbs();
	}
	
	/*********************************************************
	 * Prompts the colors used for the game
	 *********************************************************/
	private void promptColors(){
		SnakePanel.promptUser();
	}
	
	/*********************************************************
	 * Prompts the user to choose a game
	 *********************************************************/
	private void promptGame(){
		wGame = "";
		SnakePanel.promptUser2();
	}
}
