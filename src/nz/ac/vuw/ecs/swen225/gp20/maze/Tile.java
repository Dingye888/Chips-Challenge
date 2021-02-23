package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Tile class representing a single tile thats in a location. Every tile which
 * extends this will have different interactions based on its actor.
 * 
 * @author wangding1 300422014
 *
 */
public abstract class Tile {

	// Location and Name of the current Tile
	private Location location;
	private TileName tileName;

	// Booleans which handles some interation logic
	private boolean moveOn;
	private boolean pickUp;

	/**
	 * Constructor for creating an Tile on a single location
	 * 
	 * @param tileName - Name of the tile.
	 * @param location - Location of the tile.
	 * @param moveOn   - boolean to check if an entity can move on.
	 * @param pickUp   - boolean to check of Chap can pick it up.
	 */
	public Tile(TileName tileName, Location location, boolean moveOn, boolean pickUp) {
		this.tileName = tileName;
		this.location = location;
		this.moveOn = moveOn;
		this.pickUp = pickUp;
	}

	/**
	 * Performs different interactions based on the current child class of Tile
	 * 
	 * @param actor - Actor
	 * @return Action enum
	 */
	protected abstract Action interact(Actor actor);

	/**
	 * Destroys the current tile by deleting its references
	 */
	public void kill() {
		location.setTile(null);
		location = null;
	}

	/**
	 * Create an String based on the tile name and variation
	 * 
	 * @param t - TileName enum
	 * @param v - Variation enum
	 * @return returns a constructed string with tile and variation
	 */
	public String createItemName(TileName t, Variation v) {
		return t.toString() + " " + v.toString();
	}

	/**
	 * Returns the tileName
	 * 
	 * @return tileName - TileName enum
	 */
	public TileName getTileName() {
		return tileName;
	}

	/**
	 * Returns the Location for this object
	 * 
	 * @return location - Location object
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Returns the boolean if the tile can be walked on
	 * 
	 * @return moveOn - boolean if the Tile can be moved on
	 */
	public boolean canMoveOn() {
		return moveOn;
	}

	/**
	 * Returns the boolean if the tile can be picked up
	 * 
	 * @return pickUp - boolean if the Tile can be picked up
	 */
	public boolean canPickUp() {
		return pickUp;
	}

	/**
	 * String for testing
	 * 
	 * @return returns a string which best represents the location.
	 */
	public String toString() {
		String s = "";
		switch (tileName) {
		case CHIP:
			s = "c";
			break;
		case DOOR:
			s = "d";
			break;
		case KEY:
			s = "k";
			break;
		case INFO:
			s = "i";
			break;
		case EXIT:
			s = "e";
			break;
		case GATE:
			s = "g";
			break;
		case WALL:
			s = "w";
			break;
		case LAVA:
			s = "l";
			break;
		default:
			s = "Z";
			break;
		}
		return s;
	}

}
