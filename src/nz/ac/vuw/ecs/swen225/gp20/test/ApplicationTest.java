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
public class ApplicationTest {
	//Application
	@Test
	public void WindowDim() throws Exception, InstantiationException, IllegalAccessException{
		Main ma = new Main();
		
	    assertEquals(800,ma.getWidth());
	    assertEquals(600,ma.getHeight());
	}
	
	@Test
	public void hasNextLevel() throws Exception, InstantiationException, IllegalAccessException{
		Main m = new Main();
		GameController gc = m.getGameController();
		gc.startLevel1();
		assertEquals(true , gc.hasNextLevel());
	}
	
	@Test
	public void notNextLevel() throws Exception, InstantiationException, IllegalAccessException{
		Main m = new Main();
		GameController gc = m.getGameController();
		gc.startNextLevel();
		gc.startNextLevel();

		assertEquals(false , gc.hasNextLevel());
	}
	
	@Test
	public void startUnfinishedGame() throws Exception, InstantiationException, IllegalAccessException{
		Main m = new Main();
		GameController gc = m.getGameController();
		GameInfoView gv = m.getGameInfoView();
		gc.startLevel1();
		gc.startLastUnfinishedGame();
		
		assertEquals(gv.getLevelText(), "1");
	}
	
	@Test
	public void Pause_test() throws Exception, InstantiationException, IllegalAccessException{
		Main m = new Main();
		GameController gc = m.getGameController();
;
		gc.startLevel1();
		gc.pause(); 
		
	    Timer t = gc.getInfoRenderer().getTimer();
		
		assertTrue(t.isRunning());
	}
	
	@Test
	public void TimeOut_test() throws Exception, InstantiationException, IllegalAccessException{
		Main m = new Main();
		GameController gc = m.getGameController();
		GameInfoView gv = m.getGameInfoView();
		
		gc.startLevel1();
		//GameInfoModel model = gc.getGameInfoModel();
		//gv.setTimeText("0");
	    
	    //model.setTime(0);
		//gc.getGameInfoRenderer().render(model);
		Timer t = gc.getInfoRenderer().getTimer();
		Thread.sleep(100000);
		assertFalse(t.isRunning());
		assertTrue(gc.getGameStatus().equals(GameStatus.LEVEL_FINISHED));
	}
	
	@Test
	public void keyPressed_Dir() throws Exception, InstantiationException, IllegalAccessException{
		Main m = new Main();
		GameController gc = m.getGameController();
		//GameInfoRenderer gir = gc.getGameInfoRenderer();
		//GameInfoView gv = m.getGameInfoView();
		//renderer r = m.getRenderer();
		gc.startLevel1();
		Maze maze = gc.getMaze();
		Location[][] ori = maze.getLocation();
		
	   	// move up
		KeyEvent key = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_UP,'Z');
		m.getKeyListeners()[0].keyPressed(key);
		Thread.sleep(3000);
		assertTrue(maze.getAction().equals(Action.INFO));
		// move down
		KeyEvent key2 = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_DOWN,'Z');
		m.getKeyListeners()[0].keyPressed(key2);
		Thread.sleep(3000);
		assertTrue(maze.getAction().equals(Action.MOVE));
		//move left
		KeyEvent key3 = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_LEFT,'Z');
		m.getKeyListeners()[0].keyPressed(key3);
		Thread.sleep(3000);
		assertTrue(maze.getAction().equals(Action.MOVE));
		//move right
		KeyEvent key4 = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_RIGHT,'Z');
		m.getKeyListeners()[0].keyPressed(key4);
		Thread.sleep(3000);
		assertTrue(maze.getAction().equals(Action.MOVE));
		// Go back to the origin location after moves
		assertTrue(maze.getLocation().equals(ori));
	}
	
	@Test
	public void keyPressed_GetChip() throws Exception, InstantiationException, IllegalAccessException{
		Main m = new Main();
		GameController gc = m.getGameController();
		GameInfoView gv = m.getGameInfoView();

		gc.startLevel1();
	    Maze maze = gc.getMaze();
	    // go left
		KeyEvent key = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_LEFT,'Z');
		m.getKeyListeners()[0].keyPressed(key);
		Thread.sleep(2000);
		assertTrue(maze.getAction().equals(Action.MOVE));
		// go left
		m.getKeyListeners()[0].keyPressed(key);
		Thread.sleep(3000);
		assertTrue(maze.getAction().equals(Action.ITEM));
		assertTrue(gv.getChipsLeftText().equals("10"));
	}
	
	@Test
	public void keyPressed_GetKey() throws Exception, InstantiationException, IllegalAccessException{
		Main m = new Main();
		GameController gc = m.getGameController();
		GameInfoView gv = m.getGameInfoView();

		gc.startLevel1();
	    Maze maze = gc.getMaze();
	    // move left
		KeyEvent key = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_LEFT,'Z');
		m.getKeyListeners()[0].keyPressed(key);
		Thread.sleep(1000);
		assertTrue(maze.getAction().equals(Action.MOVE));
		// move left
		m.getKeyListeners()[0].keyPressed(key);
		Thread.sleep(2000);
		assertTrue(maze.getAction().equals(Action.ITEM));
		System.out.print(gv.getChipsLeftText());
		assertTrue(gv.getChipsLeftText().equals("10"));
		// move down
		KeyEvent down = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_DOWN,'Z');
		m.getKeyListeners()[0].keyPressed(down);
		Thread.sleep(2000);
		assertTrue(maze.getAction().equals(Action.ITEM));
		
		assertTrue(gv.getKeysCollected().equals("{KEY BLUE=1}"));
	}
	
	@Test
	public void keyPressed_GameSuccess() throws Exception, InstantiationException, IllegalAccessException{
		Main m = new Main();
		GameController gc = m.getGameController();
		GameInfoView gv = m.getGameInfoView();

		gc.startLevel1();
	    Maze maze = gc.getMaze();
	    KeyEvent left = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_LEFT,'Z');
	    KeyEvent down = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_DOWN,'Z');
	    KeyEvent up = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_UP,'Z');
	    KeyEvent right = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_RIGHT,'Z');

		move(2, left, m);
		move(1, down, m);
		move(3, up, m);
		move(3, left, m);
		move(1, down, m);
		move(1, right, m);
		move(1, up ,m);
		move(6, right, m);
		move(4, down, m);
		move(3, right, m);
		move(1, up, m);
		move(1, left, m);
		move(1, down, m);
		move(3, left, m);		
		move(4, down, m);		
		move(4, up, m);		
		move(6, left, m);
		move(1, up, m);
		move(1, right, m);
		move(1, down, m);
		move(2, right, m);
		move(7, up, m);
		move(1, left, m);
		move(1, down, m);
		move(1, right, m);
		move(2, down, m);
		move(7, right, m);
		move(1, down, m);
		move(1, up, m);
		move(3, left, m);
		move(2, up, m);
		move(1, right, m);
		move(1, left, m);
		move(6, down, m);
		move(3, left, m);
		move(3, down, m);
		move(7, up, m);
		move(1, right, m);
		move(2, up, m);
		assertTrue(gc.getGameStatus().equals(GameStatus.LEVEL_FINISHED));
	}
	
	@Test
	public void lev2_BotDie() throws Exception, InstantiationException, IllegalAccessException{
		Main m = new Main();
		GameController gc = m.getGameController();
		GameInfoView gv = m.getGameInfoView();
		
		gc.startLevel1();
		gc.startNextLevel();
	    Maze maze = gc.getMaze();
	    KeyEvent left = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_LEFT,'Z');
	    KeyEvent up = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_UP,'Z');
	    // go left
	    move(10, left, m);
	    Thread.sleep(2000);
	    move(1, up, m);
	    Thread.sleep(2000);
	    
		assertTrue(maze.getAction().equals(Action.BOT_DIE));
		
	}

	@Test
	public void replay() throws Exception, InstantiationException, IllegalAccessException{
		Main m = new Main();
		GameController gc = m.getGameController();
		GameInfoView gv = m.getGameInfoView();
		
		gc.startLevel1();
		gc.startRecording();
		gc.startNextLevel();
	    Maze maze = gc.getMaze();
	    KeyEvent left = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_LEFT,'Z');
	    KeyEvent up = new KeyEvent(m, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,  KeyEvent.VK_UP,'Z');
	    // go left
	    move(10, left, m);
	    Thread.sleep(2000);
	    move(1, up, m);
	    Thread.sleep(1000);
	    gc.saveRecording();
	    gc.loadRecording();
	    gc.autoReplay();
	    
		assertTrue(maze.getAction().equals(Action.BOT_DIE));
		
	}
	
	private void move(int step, KeyEvent k, Main m) {
		for(int i = 0; i < step; i++) {
			m.getKeyListeners()[0].keyPressed(k);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
