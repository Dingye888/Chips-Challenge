package nz.ac.vuw.ecs.swen225.gp20.maze;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import com.google.common.base.Preconditions;

/**
 * Maze class which stores the current level information of the game
 * 
 * @author wangding1 300422014
 *
 */
public class Maze {

	// 2D array of maze locations (row,col)
	private Location[][] locations;

	// Bot manager which will control all bot movements per tick
	private BotMannager bm = null;

	// Action enum to determine what was the last action performed
	private Action action;

	private Chap chap; // Main character Chap

	/**
	 * Sets the current level of the maze with all fields being reset.
	 * 
	 * @param locations - 2d Array of Locations (x,y)
	 */
	public void setLevel(Location[][] locations) {
		Preconditions.checkArgument(locations != null, "2D Location array cannot be null"); // Precondition check
		this.locations = locations;
		findActors(locations);
	}

	/**
	 * Performs a time tick for all the other actors that will move on its own.
	 */
	public void tick() {
		if (bm == null)
			return;
		else
			bm.tick();
	}

	/**
	 * Moves chap from invoking move method
	 * 
	 * @param direction - Direction enum
	 */
	public void moveChap(Direction direction) {
		move(chap, direction);
	}

	/**
	 * Moves the desired actor in a given direction
	 * 
	 * @param actor     - Actor object
	 * @param direction - Direction enum
	 */
	public void move(Actor actor, Direction direction) {
		Preconditions.checkArgument(actor != null, "The specified Actor object does not exist");
		action = Action.INVALID;
		Location newL = findNewLocation(actor, direction);
		if (newL != null) {
			action = actor.move(newL, direction);
		}

	}

	/**
	 * Helper method for returning a new Location based on the current actor
	 * position with a giving direction
	 * 
	 * @param actor     - Actor object
	 * @param direction - Direction enum
	 * @return Location - new Location in that direction, null if its invalid
	 */
	public Location findNewLocation(Actor actor, Direction direction) {
		int currentRow = actor.getLocation().getRow();
		int currentCol = actor.getLocation().getCol();
		Location newL = null;
		switch (direction) {
		case NORTH:
			if ((newL = getValidLocation(currentCol, currentRow - 1)) != null)
				return newL;
			break;
		case SOUTH:
			if ((newL = getValidLocation(currentCol, currentRow + 1)) != null)
				return newL;
			break;
		case EAST:
			if ((newL = getValidLocation(currentCol + 1, currentRow)) != null)
				return newL;
			break;
		case WEST:
			if ((newL = getValidLocation(currentCol - 1, currentRow)) != null)
				return newL;
			break;
		default:
			assertTrue("Direction enum is not a valid direction", true);
		}
		assertTrue("The new Location is out of bound", true);
		return null;
	}

	/**
	 * Helper method for checking 2d array bounds
	 * 
	 * @param newX - x axis
	 * @param newY - y axis
	 * @return newL - new Location
	 */
	private Location getValidLocation(int newY, int newX) {
		Location newL = null;
		if ((newX >= 0 && newX < locations.length) && (newY >= 0 && newY < locations[0].length))
			newL = locations[newX][newY];
		assertTrue("The new Location " + newX + " " + newY + " is currently out of bounds " + locations[0].length + " "
				+ locations.length, newL != null);
		return newL;
	}

	/**
	 * Helper method for finding Chap and total chips on the map, as well as other
	 * actors that may be in the level
	 * 
	 * @param level - 2d Array Locations
	 */
	private void findActors(Location[][] level) {
		HashSet<Actor> actors = new HashSet<>();
		bm = null;
		chap = null;
		int totChips = 0;
		// for each row array
		for (Location[] row : level) {

			// for each location in row
			for (Location loc : row) {
				if (loc == null) {
					continue;
				}
				Actor a = loc.getActor();
				Tile t = loc.getTile();
				if (a != null) {
					// if actor is chap, set chap
					if (a.getActorName() == ActorName.CHAP) {
						assertTrue("Multiple Chaps has been detected in the current level.", chap == null);
						chap = (Chap) a;
					}
					// adding other actors
					else {
						actors.add(a);
					}
				}
				// counting total chips
				if (t != null && t.getTileName() == TileName.CHIP)
					totChips++;
			}
		}
		// Check if thier is a Chap, then set fields
		assertTrue("No Chap has been detected in the current level.", chap != null);
		chap.setTotalChips(totChips);
		if (!actors.isEmpty())
			bm = new BotMannager(actors, this);
	}

	/**
	 * Returns Chap
	 * 
	 * @return chap - Chap object
	 */
	public Chap getChap() {
		Preconditions.checkArgument(locations != null,
				"Chap dose not currently exsist because thier is no exsisting level");
		return chap;
	}

	/**
	 * Returns the action after a move
	 * 
	 * @return Action enum
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * Returns the 2d array of Locations
	 * 
	 * @return locations - 2d Array of Location objects
	 */
	public Location[][] getLocation() {
		Preconditions.checkArgument(locations != null, "2d array Locations dose not currently exsist");
		return locations;
	}

	/**
	 * Returns the Bot mannager object
	 * 
	 * @return bm - BotMannager object
	 */
	public BotMannager getBM() {
		return bm;
	}

	/**
	 * Maze toString for debugging
	 */
	public String toString() {
		String s = "";
		// i == x
		for (int i = 0; i < locations[0].length; i++) {
			// j == y
			for (int j = 0; j < locations.length; j++) {
				s += locations[i][j].toString() + " ";
			}
			s += "\n";
		}
		return s;
	}

//	/**
//	 * testing needs
//	 * 
//	 * @param args - testing
//	 */
//	public static void main(String[] args) {
//		new Maze();
//	}

}
