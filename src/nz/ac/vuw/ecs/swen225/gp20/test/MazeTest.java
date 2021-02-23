package nz.ac.vuw.ecs.swen225.gp20.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.junit.Test;

import nz.ac.vuw.ecs.swen225.gp20.application.GameController;
import nz.ac.vuw.ecs.swen225.gp20.application.GameInfoModel;
import nz.ac.vuw.ecs.swen225.gp20.application.GameInfoRenderer;
import nz.ac.vuw.ecs.swen225.gp20.application.GameInfoView;
import nz.ac.vuw.ecs.swen225.gp20.application.GameStatus;
import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.maze.Action;
import nz.ac.vuw.ecs.swen225.gp20.maze.ActorName;
import nz.ac.vuw.ecs.swen225.gp20.maze.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.Location;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.TileName;
import nz.ac.vuw.ecs.swen225.gp20.maze.Variation;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.RecordAndReplay;
import nz.ac.vuw.ecs.swen225.gp20.renderer.renderer;
/**
 * 
 * @author Shiyi Liang 300502349
 *
 */

public class MazeTest {
	@Test
	public void EmptyMap() {
	    Maze m = makeMaze();
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getChap() == null);
		assertTrue(m.getAction() == null);
	}
	
	//Move
	@Test
	public void MoveUp() {
	    Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ C _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.MOVE));
	}
	
	@Test
	public void MoveDown() {
	    Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.SOUTH);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ C _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.MOVE));
	}
	
	@Test
	public void MoveLeft() {
	    Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.WEST);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "C _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.MOVE));
	}
	
	@Test
	public void MoveRight() {
	    Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.EAST);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ C _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.MOVE));
	}
	
	@Test
	public void InvalidMove_01() {
	    Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][0] = new Location(3, 0, ActorName.CHAP);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.WEST);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "C _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.INVALID));
	}
	
	@Test
	public void InvalidMove_02() {
	    Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][4] = new Location(3, 4, ActorName.CHAP);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.EAST);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ C \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.INVALID));
	}
	
	@Test
	public void InvalidMove_03() {
	    Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[0][0] = new Location(0, 0, ActorName.CHAP);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	   
		String map = "C _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.INVALID));
	}
	
	@Test
	public void InvalidMove_04() {
	    Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[4][0] = new Location(4, 0, ActorName.CHAP);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.SOUTH);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "C _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.INVALID));
	}
	
	//Touch Wall
	@Test
	public void TouchWall() {
	    Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][0] = new Location(3, 0, TileName.WALL);
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.WEST);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "w C _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.WALL));
	}
	
	//Get the Key
	@Test
	public void GetRedKey() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.KEY, Variation.RED);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ C _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.ITEM));
		assertTrue(m.getChap().getInventory().containsKey("KEY RED"));
		assertTrue(m.getChap().getInventory().containsValue(1));
		assertTrue(m.getChap().getInventory().size() == 1);
	}
	
	@Test
	public void GetGreenKey() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.KEY, Variation.GREEN);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ C _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.ITEM));
		assertTrue(m.getChap().getInventory().containsKey("KEY GREEN"));
		assertTrue(m.getChap().getInventory().containsValue(1));
		assertTrue(m.getChap().getInventory().size() == 1);
	}
	
	@Test
	public void GetBlueKey() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.KEY, Variation.BLUE);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ C _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.ITEM));
		assertTrue(m.getChap().getInventory().containsKey("KEY BLUE"));
		assertTrue(m.getChap().getInventory().containsValue(1));
		assertTrue(m.getChap().getInventory().size() == 1);
	}
	
	@Test
	public void GetYellowKey() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.KEY, Variation.YELLOW);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ C _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.ITEM));
		assertTrue(m.getChap().getInventory().containsKey("KEY YELLOW"));
		assertTrue(m.getChap().getInventory().containsValue(1));
		assertTrue(m.getChap().getInventory().size() == 1);
	}
	
	//Get keys
	@Test
	public void GetRedKeys() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.KEY, Variation.RED);
	    loc[2][2] = new Location(2, 2, TileName.KEY, Variation.RED);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	    m.moveChap(Direction.EAST);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ C _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.ITEM));
		assertTrue(m.getChap().getInventory().containsKey("KEY RED"));
		assertTrue(m.getChap().getInventory().containsValue(2));
		assertTrue(m.getChap().getInventory().size() == 1);
	}
	
	@Test
	public void GetDiffKeys() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.KEY, Variation.RED);
	    loc[2][2] = new Location(2, 2, TileName.KEY, Variation.BLUE);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	    m.moveChap(Direction.EAST);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ C _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.ITEM));
		assertTrue(m.getChap().getInventory().containsKey("KEY RED"));
		assertTrue(m.getChap().getInventory().containsKey("KEY BLUE"));
		assertTrue(m.getChap().getInventory().containsValue(1));
		assertFalse(m.getChap().getInventory().containsValue(2));
		assertTrue(m.getChap().getInventory().size() == 2);
	}
	
	// Door
	@Test
	public void OpenDoor_01() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.KEY, Variation.RED);
	    loc[2][0] = new Location(2, 0, TileName.KEY, Variation.RED);
	    loc[2][2] = new Location(2, 2, TileName.DOOR, Variation.RED);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	    m.moveChap(Direction.WEST);
	    m.moveChap(Direction.EAST);
	    m.moveChap(Direction.EAST);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ C _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.DOOR));
		assertTrue(m.getChap().getInventory().size() == 1);
	}
	
	// Door
		@Test
		public void OpenDoor_02() {
			Maze m = makeMaze();
		    Location[][] loc = m.getLocation();
		    loc[3][1] = new Location(3, 1, ActorName.CHAP);
		    loc[2][1] = new Location(2, 1, TileName.KEY, Variation.RED);
		    loc[2][2] = new Location(2, 2, TileName.DOOR, Variation.RED);
		    m.setLevel(loc);
		    //move
		    m.moveChap(Direction.NORTH);
		    m.moveChap(Direction.EAST);
		   
			String map = "_ _ _ _ _ \n" +
						 "_ _ _ _ _ \n" +
						 "_ _ C _ _ \n" +
						 "_ _ _ _ _ \n" +
						 "_ _ _ _ _ \n";
			assertTrue(m.toString().equals(map));
			assertTrue(m.getAction().equals(Action.DOOR));
			assertTrue(m.getChap().getInventory().size() == 0);
		}
	
	// Open invalid door
	@Test
	public void WrongKey() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.KEY, Variation.RED);
	    loc[2][2] = new Location(2, 2, TileName.DOOR, Variation.BLUE);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	    m.moveChap(Direction.EAST);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ C d _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.WALL));
		assertTrue(m.getChap().getInventory().size() == 1);
	}
	
	@Test
	public void NoKey() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][2] = new Location(2, 2, TileName.DOOR, Variation.RED);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	    m.moveChap(Direction.EAST);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ C d _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.WALL));
		assertTrue(m.getChap().getInventory().size() == 0);
	}
	
	//Chip
	@Test
	public void GetChip() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.CHIP);
	    loc[2][2] = new Location(2, 2, TileName.DOOR, Variation.RED);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ C d _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.ITEM));
		assertTrue(m.getChap().getChips() == 1);
	}
	
	//Gate
	@Test
	public void OpenGate() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.CHIP);
	    loc[2][2] = new Location(2, 2, TileName.GATE);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	    m.moveChap(Direction.EAST);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ C _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.DOOR));
		assertTrue(m.getChap().getChips() == 1);
	}
	
	@Test
	public void OpenInvalidGate() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.CHIP);
	    loc[1][3] = new Location(1, 3, TileName.CHIP);
	    loc[2][2] = new Location(2, 2, TileName.GATE);
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	    m.moveChap(Direction.EAST);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ c _ \n" +
					 "_ C g _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.WALL));
		assertTrue(m.getChap().getChips() == 1);
	}
	
	// Info
	@Test
	public void GetInfo_01() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.INFO, "Some infomation");
	   
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ C _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.INFO));
	}
	
	@Test
	public void GetInfo_02() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.INFO);
	    loc[2][2] = new Location(2, 2, TileName.EXIT);
	   
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ C e _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.INFO));
	}
	
	//Exit
	@Test
	public void ToExit() {
		Maze m = makeMaze();
	    Location[][] loc = m.getLocation();
	    loc[3][1] = new Location(3, 1, ActorName.CHAP);
	    loc[2][1] = new Location(2, 1, TileName.EXIT);
	    loc[2][2] = new Location(2, 2, TileName.INFO);
	    loc[1][1] = new Location(1, 1, TileName.KEY, Variation.BLUE);
	    
	    m.setLevel(loc);
	    //move
	    m.moveChap(Direction.NORTH);
	   
		String map = "_ _ _ _ _ \n" +
					 "_ k _ _ _ \n" +
					 "_ C i _ _ \n" +
					 "_ _ _ _ _ \n" +
					 "_ _ _ _ _ \n";
		assertTrue(m.toString().equals(map));
		assertTrue(m.getAction().equals(Action.EXIT));
	}
	
	private Maze  makeMaze() {
		Maze m = new Maze();
		int x = 5;
		int y = 5;
		Location[][] l = new Location[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				// Empty tile
				l[i][j] = new Location(i, j);
			}
		}
		m.setLevel(l);
		return m;
	}
	
}
