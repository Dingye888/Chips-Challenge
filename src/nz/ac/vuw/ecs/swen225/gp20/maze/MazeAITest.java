package nz.ac.vuw.ecs.swen225.gp20.maze;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

/**
 * Unit test for Bot AI interactions
 * 
 * @author wangding1 300422014
 *
 */
public class MazeAITest {

	/**
	 * Test for bots moving in to kill Chap
	 */
	@Test
	public void test_botKillChap() {
		System.out.println("Test 1______________________");

		Maze m = makeMaze();
		m.moveChap(Direction.WEST);
		System.out.println(m);

		// Bot runs into chap
		for (int i = 0; i < 3; i++) {
			m.tick();
			System.out.println(m);

		}

		// Chap is now dead
		assertTrue(m.getChap().isDead());

	}

	/**
	 * Test for chap walking into Bots
	 */
	@Test
	public void test_ChapKillSelf() {
		System.out.println("Test 2______________________");

		Maze m = makeMaze();
		System.out.println(m);

		for (int i = 0; i < 5; i++) {
			m.tick();
			System.out.println(m);

		}
		// chap runs into bot
		m.moveChap(Direction.WEST);
		System.out.println(m);

		// Checks chap is dead
		assertTrue(m.getChap().isDead());

	}

	/**
	 * Test for bots dropping into lava
	 */
	@Test
	public void test_botInLava() {
		System.out.println("Test 3______________________");

		Maze m = makeMaze();
		System.out.println(m);

		for (int i = 0; i < 10; i++) {
			m.tick();
			System.out.println(m);
		}

		assertTrue(m.getBM().getActors().size() == 0);

	}

	/**
	 * Helper method for constructing a basic maze
	 * 
	 * 
	 * @return initialed maze
	 */
	private Maze makeMaze() {
		Maze m = new Maze();
		int x = 7;
		int y = 7;
		Location[][] l = new Location[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				Location loc = null;
				// Wall tile
				if (i == 0 || i == x - 1 || j == 0 || j == y - 1)
					loc = new Location(i, j, TileName.WALL);
				// empty tile
				else {
					loc = new Location(i, j);
				}
				l[i][j] = loc;
			}
		}
		l = addTiles(l);
		m.setLevel(l);
		return m;
	}

	/**
	 * Adding some hard coded locations for testing (x,y)
	 * 
	 * @param loc 2D array of Locations
	 * @return 2D array of Locations
	 */
	private Location[][] addTiles(Location[][] loc) {
		// Chap
		loc[5][5] = new Location(5, 5, ActorName.CHAP);

		// enemy
		loc[2][2] = new Location(2, 2, ActorName.BOT);
		loc[3][2] = new Location(3, 2, ActorName.BOT);
		loc[4][2] = new Location(4, 2, ActorName.BOT);

		// Wall (middle)
		loc[2][3] = new Location(2, 3, TileName.WALL);
		loc[3][3] = new Location(3, 3, TileName.WALL);
		loc[4][3] = new Location(4, 3, TileName.WALL);

		// Lava
		loc[1][3] = new Location(1, 3, TileName.LAVA);

		return loc;
	}

}
