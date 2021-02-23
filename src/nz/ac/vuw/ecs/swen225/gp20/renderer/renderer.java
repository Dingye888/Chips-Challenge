package nz.ac.vuw.ecs.swen225.gp20.renderer;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp20.application.*;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;
import nz.ac.vuw.ecs.swen225.gp20.persistence.*;

/**
 * The rendering module is responsible for providing a simple 2D view
 * of the maze, to be embedded in the application. It should be updated after each move.
 * 
 * @author Phoenix Xie
 *
 */


public class renderer {
	private Canvas canvas;
	private Location[][] maze; //this will be received in an update object from application
	
	/*The players position*/public Location playerPos;
	/*The corner position*/ public Location cornerPos;

	
	public renderer(Main game) {
		assert(game != null);
		
		canvas = new Canvas();
		 // Send the canvas to the game
	}
	
	
	/**
	 * Get the canvas.
	 * @return	the canvas.
	 */
	public JPanel getCanvas() {
		return canvas;
	}
	

	/**
	 * Set maze.
	 * @param maze
	 */
	public void setMaze(Location[][] maze) {
		this.maze = maze;
	}


	/**
	 * Find the top left corner of the display to be shown, ensuring the 
	 * player is in the middle.
	 */
	public void Corner() {
		assert playerPos != null: "The player position should not be null";
		int centered = 4; 
		int row = playerPos.getRow() - centered;
		int col = playerPos.getCol() - centered;
		
		int mazeHeight = maze.length;	//rows
		int mazeWidth = maze[0].length; //cols
		
		//deal with invalid rows
		if(row < 0) { row = 0; }
		else if(row > mazeHeight - (canvas.VIEW_SIZE )) { row = mazeHeight - (canvas.VIEW_SIZE ); }
		
		//deal with invalid columns
		if(col < 0) { col = 0; }
		else if(col > mazeWidth - (canvas.VIEW_SIZE )) { col = mazeWidth - (canvas.VIEW_SIZE ); }
		
		cornerPos = new Location(row, col);
		allocateGameTiles();
	}
	
	/**
	 * Allocate the game tiles to be displayed currently to the canvas.
	 */
	private void allocateGameTiles() {
		Location[][] map = new Location[canvas.VIEW_SIZE][canvas.VIEW_SIZE];
				
		for(int row = 0; row < canvas.VIEW_SIZE; row++) {
			for(int col = 0; col < canvas.VIEW_SIZE; col++) {
				map[row][col] = maze[cornerPos.getRow() + row][cornerPos.getCol() + col];
			}
		}
		canvas.setGameTiles(map);
	}

	/**
	 * If a chap die re paint tiles.
	 */
	public void chapDie() {
		canvas.paintTiles();
	}

	}





