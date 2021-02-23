package nz.ac.vuw.ecs.swen225.gp20.maze;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

/**
 * Unit test for the Maze packages integrity. With all tile and chap
 * interactions.
 * 
 * @author wangding1 300422014
 *
 */
public class MazeIntegrityTest {

	/**
	 * Testing for init maze setup.
	 */
	@Test
	public void test_setLocation() {
		Maze m = makeMaze();

		// Checks
		assertTrue(m.getChap() != null);
		assertTrue(m.getAction() == null);
	}

	/**
	 * Test for maze correctness
	 */
	@Test
	public void test_stringMap() {
		Maze m = makeMaze();
		String map = "w w w w w w w w \n" + "w d _ _ _ _ d w \n" + "w k _ _ _ _ k w \n" + "w _ _ _ _ _ _ w \n"
				+ "w c c _ _ _ l w \n" + "w _ _ _ _ C i w \n" + "w _ _ _ e _ g w \n" + "w w w w w w w w \n";
		// Checks
		assertTrue(m.toString().equals(map));
		assertTrue(m.getChap() != null);
		assertTrue(m.getAction() == null);
	}

	/**
	 * Test for simple move
	 */
	@Test
	public void test_move() {
		Maze m = makeMaze();

		// Moves
		Location l = m.getChap().getLocation();
		m.moveChap(Direction.NORTH);

		// Checks
		assertTrue(l != m.getChap().getLocation()); // Valid move, location can't be the same
		assertTrue(m.getAction() == Action.MOVE);
	}

	/**
	 * Testing for info Tile
	 */
	@Test
	public void test_moveToInfo() {
		Maze m = makeMaze();

		// Moves
		Location l = m.getChap().getLocation();
		m.moveChap(Direction.EAST);

		// Checks
		assertTrue(l != m.getChap().getLocation()); // Valid move, location can't be the same
		assertTrue(m.getAction() == Action.INFO);
	}

	/**
	 * Testing for Exit tile
	 */
	@Test
	public void test_moveToExit() {
		Maze m = makeMaze();

		// Moves
		m.moveChap(Direction.SOUTH);
		Location l = m.getChap().getLocation();
		m.moveChap(Direction.WEST);

		// Checks
		assertTrue(l != m.getChap().getLocation()); // Valid move, location can't be the same
		assertTrue(m.getAction() == Action.EXIT);
	}

	/**
	 * Testing for wall interaction
	 */
	@Test
	public void test_moveToWall() {
		Maze m = makeMaze();

		// Moves
		m.moveChap(Direction.SOUTH);
		Location l = m.getChap().getLocation(); // Location just b4 checking
		m.moveChap(Direction.SOUTH);

		// Checks
		assertTrue(m.getAction() == Action.WALL);
		assertTrue(l == m.getChap().getLocation()); // can't move, location have to be the same

	}

	/**
	 * Testing for gate interaction without chips
	 */
	@Test
	public void test_moveToGate() {
		Maze m = makeMaze();

		// Moves
		m.moveChap(Direction.SOUTH);
		Location l = m.getChap().getLocation(); // Location just b4 checking
		m.moveChap(Direction.EAST);

		// Checks
		assertTrue(m.getAction() == Action.WALL); // Gets blocked by the door
		assertTrue(l == m.getChap().getLocation()); // can't move, location have to be the same
	}

	/**
	 * Testing for interaction with Door without key
	 */
	@Test
	public void test_moveToDoor() {
		Maze m = makeMaze();

		// Moves
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		Location l = m.getChap().getLocation(); // Location just b4 checking
		m.moveChap(Direction.WEST);

		// Checks
		assertTrue(m.getAction() == Action.WALL); // Gets blocked by the door
		assertTrue(l == m.getChap().getLocation()); // can't move, location have to be the same
	}

	/**
	 * Testing for picking up Chip tile
	 */
	@Test
	public void test_moveToChips() {
		Maze m = makeMaze();

		// Moves
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);

		Location l = m.getChap().getLocation(); // Location just b4 checking
		m.moveChap(Direction.WEST);

		// Checks
		assertTrue(m.getAction() == Action.ITEM); // Takes the chips
		assertTrue(l != m.getChap().getLocation()); // Valid move, location can't to be the same
		assertTrue(m.getChap().getChips() == 1); // should have a chip

	}

	/**
	 * Testing for picking up key
	 */
	@Test
	public void test_moveToKey() {
		Maze m = makeMaze();

		// Moves
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		Location l = m.getChap().getLocation(); // Location just b4 checking
		m.moveChap(Direction.WEST);

		// Checks
		assertTrue(m.getAction() == Action.ITEM); // Takes the chips
		assertTrue(l != m.getChap().getLocation()); // Valid move, location can't to be the same
		assertTrue(m.getChap().getInventory().size() == 1); // should have a entry item
	}

	/**
	 * Testing for moving into lava
	 */
	@Test
	public void test_moveToLava() {
		Maze m = makeMaze();

		// Moves
		m.moveChap(Direction.NORTH);
		Location l = m.getChap().getLocation(); // Location just b4 checking
		m.moveChap(Direction.EAST);

		System.out.println(m.toString());

		// Checks
		assertTrue(m.getAction() == Action.DIE); // Dies
		assertTrue(l != m.getChap().getLocation()); // Valid move, location can't to be the same

	}

	/**
	 * Testing for opening gate with all the chips
	 */
	@Test
	public void test_openGate() {
		Maze m = makeMaze();

		// Moves
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.EAST);
		m.moveChap(Direction.EAST);
		m.moveChap(Direction.EAST);
		m.moveChap(Direction.EAST);
		m.moveChap(Direction.SOUTH);
		m.moveChap(Direction.SOUTH);
		Location l = m.getChap().getLocation(); // Location just b4 checking
		m.moveChap(Direction.EAST);

		// Checks
		assertTrue(m.getAction() == Action.DOOR); // opens the Gate
		assertTrue(l != m.getChap().getLocation()); // Valid move, location can't to be the same
	}

	/**
	 * Testing for opening doors with correct key
	 */
	@Test
	public void test_openDoor() {
		Maze m = makeMaze();

		// Moves
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);

		m.moveChap(Direction.WEST);
		Location l = m.getChap().getLocation(); // Location just b4 checking
		m.moveChap(Direction.NORTH);

		// Checks
		assertTrue(m.getAction() == Action.DOOR); // Opens the door
		assertTrue(l != m.getChap().getLocation()); // Valid move, location can't to be the same
		assertTrue(m.getChap().getInventory().size() == 0); // should have used the key
	}

	/**
	 * Test for opening door with the wrong key
	 */
	@Test
	public void test_openWrongDoors() {
		Maze m = makeMaze();

		// Moves
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.EAST);
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.NORTH);
		Location l = m.getChap().getLocation(); // Location just b4 checking
		m.moveChap(Direction.WEST);

		// Checks
		assertTrue(m.getAction() == Action.WALL); // Opens the door
		assertTrue(l == m.getChap().getLocation()); // can't move, location have to be the same
		assertTrue(m.getChap().getInventory().size() == 1); // shoulden't have used the key
	}

	/**
	 * Test for opening correct door with relative key
	 */
	@Test
	public void test_openRightDoors() {
		Maze m = makeMaze();

		// Moves
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.NORTH);
		m.moveChap(Direction.EAST);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		m.moveChap(Direction.WEST);
		Location l = m.getChap().getLocation(); // Location just b4 checking
		m.moveChap(Direction.NORTH);

		// Checks
		assertTrue(m.getAction() == Action.DOOR); // Opens the door
		assertTrue(l != m.getChap().getLocation()); // Valid move, location can't to be the same
		assertTrue(m.getChap().getInventory().size() == 1); // shoulden't have used the key
		assertTrue(m.getChap().getInventory().get("KEY RED") == 1); // shoulden't have used the key and its red

	}

	/**
	 * Helper method for constructing a basic maze
	 * 
	 * 
	 * @return initialed maze
	 */
	private Maze makeMaze() {
		Maze m = new Maze();
		int x = 8;
		int y = 8;
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

		// door and key red
		loc[1][6] = new Location(1, 6, TileName.DOOR, Variation.RED);
		loc[2][6] = new Location(2, 6, TileName.KEY, Variation.RED);

		// door and key blue
		loc[1][1] = new Location(1, 1, TileName.DOOR, Variation.BLUE);
		loc[2][1] = new Location(2, 1, TileName.KEY, Variation.BLUE);

		// Chips
		loc[4][1] = new Location(4, 1, TileName.CHIP);
		loc[4][2] = new Location(4, 2, TileName.CHIP);

		// Gate
		loc[6][6] = new Location(6, 6, TileName.GATE);

		// Info
		loc[5][6] = new Location(5, 6, TileName.INFO);

		// Lava
		loc[4][6] = new Location(4, 6, TileName.LAVA);

		// exit
		loc[6][4] = new Location(6, 4, TileName.EXIT);

		return loc;
	}

}
