package SnakeGamePackage;

import java.awt.Frame;

import javax.swing.*;
/*****************************************************
 * Class that runs the full SnakeGame program
 * @author Mitchell
 * @version 9/17/2014
 ****************************************************/

public class Snake {
	public static void main(String [] args){
		JFrame frame = new JFrame("Snake Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SnakePanel panel = new SnakePanel();
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		SnakePanel.promptStart();
	}

}
