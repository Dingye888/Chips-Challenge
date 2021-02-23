package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Location class which represents a single grid in the game which consists of a
 * Tile and an Actor class object. They both can be null for representing their
 * occupancy.
 * 
 * @author wangding1 300422014
 *
 */
public class Location {

	// 2D array coordinates
	private int row, col;

	// Grid components
	private Tile tile;
	private Actor actor;

	/**
	 * Empty tile constructor.
	 * 
	 * @param row - the row coordinate in the 2D array.
	 * @param col - the col coordinate in the 2D array.
	 */
	public Location(int row, int col) {
		this.row = row;
		this.col = col;
		tile = null;
		actor = null;

	}

	/**
	 * Default Tile constructor.
	 * 
	 * @param row      - the row coordinate in the 2D array.
	 * @param col      - the col coordinate in the 2D array.
	 * @param tileName - the specified Enum TileName.
	 */
	public Location(int row, int col, TileName tileName) {
		this.row = row;
		this.col = col;
		this.actor = null;

		// Creating Tile object: Tile("name","location","canMoveOn","canPickUp")
		switch (tileName) {
		case WALL:
			this.tile = new Wall(tileName, this, false, false);
			break;
		case CHIP:
			this.tile = new Chip(tileName, this, true, true);
			break;
		case GATE:
			this.tile = new Gate(tileName, this, false, false);
			break;
		case EXIT:
			this.tile = new Exit(tileName, this, true, false);
			break;
		case INFO:
			this.tile = new Info(tileName, this, true, false);
			break;
		case LAVA:
			this.tile = new Lava(tileName, this, true, false);
			break;
		default:
			this.tile = null;
		}
	}

	/**
	 * Default Tile constructor with variation.
	 * 
	 * @param row       - the row coordinate in the 2D array.
	 * @param col       - the col coordinate in the 2D array.
	 * @param tileName  - the specified Enum TileName.
	 * @param variation - the specified Enum variation, null if no need.
	 */
	public Location(int row, int col, TileName tileName, Variation variation) {
		this.row = row;
		this.col = col;
		this.actor = null;

		// Creating Tile object:
		// Tile("name","Location","canMoveOn","canPickUp","variation")
		switch (tileName) {
		case KEY:
			this.tile = new Key(tileName, this, true, true, variation);
			break;
		case DOOR:
			this.tile = new Door(tileName, this, false, false, variation);
			break;
		default:
			this.tile = null;
		}
	}

	/**
	 * Default Info constructor for constructing an Info file with help message.
	 * 
	 * @param row      - the row coordinate in the 2D array.
	 * @param col      - the col coordinate in the 2D array.
	 * @param tileName - the specified Enum TileName.
	 * @param txt      - the help message that will get stored.
	 */
	public Location(int row, int col, TileName tileName, String txt) {
		this.row = row;
		this.col = col;
		this.actor = null;

		// Creating Tile object:
		// Tile("name",:location","canMoveOn","canPickUp","variation")
		switch (tileName) {
		case INFO:
			this.tile = new Info(tileName, this, true, false, txt);
			break;
		default:
			this.tile = null;
		}
	}

	/**
	 * Default Actor Location constructor.
	 * 
	 * @param row       - the row coordinate in the 2D array
	 * @param col       - the col coordinate in the 2D array
	 * @param actorName - the specified Enum ActorName
	 */
	public Location(int row, int col, ActorName actorName) {
		this.row = row;
		this.col = col;
		this.tile = null;

		// Creating actor object
		switch (actorName) {
		case CHAP:
			this.actor = new Chap(actorName, this);
			break;
		case BOT:
			this.actor = new Bot(actorName, this);
			break;
		default:
			this.actor = null;
		}
	}

	/**
	 * Default Actor Location constructor.
	 * 
	 * @param row       - the row coordinate in the 2D array
	 * @param col       - the col coordinate in the 2D array
	 * @param actorName - the specified Enum ActorName
	 * @param d         - Direction enum
	 */
	public Location(int row, int col, ActorName actorName, Direction d) {
		this.row = row;
		this.col = col;
		this.tile = null;

		// Creating actor object
		switch (actorName) {
		case CHAP:
			this.actor = new Chap(actorName, this, d);
			break;
		case BOT:
			this.actor = new Bot(actorName, this, d);
			break;
		default:
			this.actor = null;
		}
	}

	/**
	 * Returns the row position
	 * 
	 * @return row - int row position
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the col position
	 * 
	 * @return col - int col position
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Returns the Tile object for this Location
	 * 
	 * @return tile - Tile object or null for empty
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * Sets the Location with a Tile
	 * 
	 * @param tile - Tile object or null for empty
	 */
	public void setTile(Tile tile) {
		this.tile = tile;
	}

	/**
	 * Returns the Actor object for this Location
	 * 
	 * @return actor - Actor object or null for empty
	 */
	public Actor getActor() {
		return actor;
	}

	/**
	 * Sets the Actor object for this Location
	 * 
	 * @param actor - Actor object or null for empty
	 */
	public void setActor(Actor actor) {
		this.actor = actor;
	}

	/**
	 * testing for x and y location
	 */
	public String toString() {
		String s = "";
		if (actor != null)
			s = actor.toString();
		else if (tile != null)
			s = tile.toString();
		else
			s = "_";
		return s;
	}

}
