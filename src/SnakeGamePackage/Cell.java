package SnakeGamePackage;

/******************************************************
 * Represents each "cell" in the "board" of the game
 * @author Mitchell Couturier
 * @version 9/17/2014
 ******************************************************/

public class Cell {

	/**determines whether or not this cell is part of the Snake **/
	private boolean isSnake;
	
	/**determines whether or not this cell is an Orb to expand the snake **/
	private boolean isOrb;
	
	/**if a snake, determines how far this cell is from the tail (1 = tail) **/
	private int snakeCount;
	
	/************************************************
	 * Constructor with default values
	 ************************************************/
	public Cell(){
		isSnake = false;
		isOrb = false;
		snakeCount = 0;
	}
	
	/************************************************
	 * Constructor with entered values
	 ************************************************/
	public Cell(boolean snake, boolean orb, int count){
		isSnake = snake;
		isOrb = orb;
		snakeCount = count;
	}
	
	/************************************************
	 * returns if the Cell is part of the Snake or not
	 ************************************************/
	public boolean isSnake(){
		return isSnake;
	}
	
	/************************************************
	 * sets the value of isSnake
	 ************************************************/
	public void setSnake (boolean snake){
		isSnake = snake;
	}
	
	/************************************************
	 * returns if the Cell is an Orb
	 ************************************************/
	public boolean isOrb(){
		return isOrb;
	}
	
	/************************************************
	 * sets the value of isOrb
	 ************************************************/
	public void setOrb(boolean orb){
		isOrb = orb;
	}
	
	/************************************************
	 * returns the snakeCount number
	 ************************************************/
	public int getSnakeCount(){
		return snakeCount;
	}
	
	/************************************************
	 * sets the snakeCount
	 ************************************************/
	public void setSnakeCount(int count){
		snakeCount = count;
	}
	
	/************************************************
	 * decrements the snakeCount by 1
	 ************************************************/
	public void decSnakeCount(){
		snakeCount--;
	}
}
