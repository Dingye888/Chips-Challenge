package nz.ac.vuw.ecs.swen225.gp20.persistence;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.Test;

import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.ActorName;
import nz.ac.vuw.ecs.swen225.gp20.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp20.maze.Door;
import nz.ac.vuw.ecs.swen225.gp20.maze.Info;
import nz.ac.vuw.ecs.swen225.gp20.maze.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.Location;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.TileName;
import nz.ac.vuw.ecs.swen225.gp20.maze.Variation;

/**
 * @author Biru Lin 300456889
 *
 */
public class PersistenceValidTests {

	Parser praser = new Parser("levels/test.json");
	Location[][] loc = praser.map;

	/**
	 * Test for a corner on the board
	 */
	@Test
	public void test_boardCorner() {
		int r = 0;
		int c = 0;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() == null);
	}

	/**
	 * Test for time remain
	 */
	@Test
	public void test_time() {
		assertTrue("The game time should be 30, but its " + praser.time, praser.time == 30);
	}

	/**
	 * Test for current level
	 */
	@Test
	public void test_level() {
		assertTrue("The game level should be 1, but its " + praser.currLevel, praser.currLevel == 1);
	}

	/**
	 * Chaps items test
	 */
	@Test
	public void test_Chap_Inventory() {
		int r = 4;
		int c = 4;
		assertTrue(loc[r][c].getActor() != null);
		assertTrue(loc[r][c].getTile() == null);
		Chap a = (Chap) loc[r][c].getActor();
		assertTrue("The size of chaps current inventory is " + a.getInventory().size() + ", But should be 2",
				a.getInventory().size() == 2);
	}

	/**
	 * Chaps chips test
	 */
	@Test
	public void test1_Chap_Chips() {
		int r = 4;
		int c = 4;
		assertTrue(loc[r][c].getActor() != null);
		assertTrue(loc[r][c].getTile() == null);
		Chap a = (Chap) loc[r][c].getActor();
		assertTrue("Chap currently have " + a.getChips() + " Chips collected, he should have 9", a.getChips() == 9);

	}

	/**
	 * 
	 */
	@Test
	public void test1() {
		int r = 0;
		int c = 1;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Tile t = loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.WALL);
	}

	/**
	 * 
	 */
	@Test
	public void test2() {
		int r = 0;
		int c = 2;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Tile t = loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.CHIP);
	}

	/**
	 * 
	 */
	@Test
	public void test3() {
		int r = 0;
		int c = 3;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Key t = (Key) loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.KEY);
		assertTrue(t.getVariation() == Variation.YELLOW);
	}

	/**
	 * 
	 */
	@Test
	public void test4() {
		int r = 0;
		int c = 4;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Key t = (Key) loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.KEY);
		assertTrue(t.getVariation() == Variation.RED);
	}

	/**
	 * 
	 */
	@Test
	public void test5() {
		int r = 1;
		int c = 0;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Key t = (Key) loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.KEY);
		assertTrue(t.getVariation() == Variation.GREEN);
	}

	/**
	 * 
	 */
	@Test
	public void test6() {
		int r = 1;
		int c = 1;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Key t = (Key) loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.KEY);
		assertTrue(t.getVariation() == Variation.BLUE);
	}

	/**
	 * 
	 */
	@Test
	public void test7() {
		int r = 1;
		int c = 2;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Door t = (Door) loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.DOOR);
		assertTrue(t.getVariation() == Variation.YELLOW);
	}

	/**
	 * 
	 */
	@Test
	public void test8() {
		int r = 1;
		int c = 3;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Door t = (Door) loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.DOOR);
		assertTrue(t.getVariation() == Variation.RED);
	}

	/**
	 * 
	 */
	@Test
	public void test9() {
		int r = 1;
		int c = 4;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Door t = (Door) loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.DOOR);
		assertTrue(t.getVariation() == Variation.GREEN);
	}

	/**
	 * 
	 */
	@Test
	public void test10() {
		int r = 2;
		int c = 0;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Door t = (Door) loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.DOOR);
		assertTrue(t.getVariation() == Variation.BLUE);
	}

	/**
	 * 
	 */
	@Test
	public void test11() {
		int r = 2;
		int c = 1;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Tile t = loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.GATE);
	}

	/**
	 * 
	 */
	@Test
	public void test12() {
		int r = 2;
		int c = 2;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Tile t = loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.EXIT);
	}

	/**
	 * 
	 */
	@Test
	public void test13() {
		int r = 2;
		int c = 3;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Info t = (Info) loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.INFO);
		assertTrue(t.getInfo().equals(praser.getInfo1()));

	}

	/**
	 * 
	 */
	@Test
	public void test14() {
		int r = 2;
		int c = 4;
		assertTrue(loc[r][c].getActor() != null);
		assertTrue(loc[r][c].getTile() == null);
		Actor a = loc[r][c].getActor();
		assertTrue(a.getActorName() == ActorName.BOT);

	}

	/**
	 * 
	 */
	@Test
	public void test15() {
		int r = 3;
		int c = 0;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Tile t = loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.LAVA);
	}

	/**
	 * 
	 */
	@Test
	public void test16() {
		int r = 3;
		int c = 1;
		assertTrue(loc[r][c].getActor() == null);
		assertTrue(loc[r][c].getTile() != null);
		Info t = (Info) loc[r][c].getTile();
		assertTrue(t.getTileName() == TileName.INFO);
		assertTrue(t.getInfo().equals(praser.getInfo2()));
	}

	/**
	 * test game save.
	 */
	@Test
	public void saveGame() {
		Location[][] l = new Location[1][1];
		l[0][0] = new Location(0, 0, ActorName.CHAP);
		Maze m = new Maze();
		m.setLevel(l);
		SaveGame s = new SaveGame(m, 1);
		s.save("SavedMap.json", 30);
		praser = new Parser("levels/SavedMap.json");
		assertTrue("The current location should have Chap but it has " + praser.map[0][0].getActor(),
				praser.map[0][0].getActor() != null);
	}

}

