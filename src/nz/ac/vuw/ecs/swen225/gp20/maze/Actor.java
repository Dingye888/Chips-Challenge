package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Actor class which represents an entity that may move. When two actors collide
 * with each other, both of them will be killed before interacting with the tile
 * it stands on.
 * 
 * @author wangding1 300422014
 *
 */
public abstract class Actor {

	// Location and Direction of the current Actor
	private Location location;
	protected Direction dir = Direction.SOUTH;

	// State and name of the current Actor
	private ActorName actorName;
	private boolean dead = false;

	/**
	 * Constructor for creating an actor on a single location.
	 * 
	 * @param actorName - Enum for type of actor.
	 * @param location  - Location in which the actor is currently on.
	 */
	public Actor(ActorName actorName, Location location) {
		this.actorName = actorName;
		this.location = location;
	}

	/**
	 * Actor constructor with direction parameter
	 * 
	 * @param actorName - Enum for type of actor.
	 * @param location  - location in which the actor is currently on.
	 * @param d         - Direction enum
	 */
	public Actor(ActorName actorName, Location location, Direction d) {
		this.actorName = actorName;
		this.location = location;
		this.dir = d;
	}

	/**
	 * Manages the interaction between the current actor and new location actors and
	 * tiles.
	 * 
	 * @param t         - Tile object
	 * @param a         - Actor object
	 * @param direction - Direction enum
	 * @return Action enum
	 */
	protected abstract Action interact(Tile t, Actor a, Direction direction);

	/**
	 * Returns the next valid location of the inherited actor based on its own move
	 * algorithm
	 * 
	 * @param maze - Maze object
	 * @return Location - next location the Actor should go
	 */
	protected abstract Location nextLocation(Maze maze);

	/**
	 * Moves the actor to the new Location if its valid
	 * 
	 * @param newLocation - existing Location
	 * @param direction   - Direction enum
	 * @return Action enum
	 */
	public Action move(Location newLocation, Direction direction) {
		Actor a = newLocation.getActor();
		Tile t = newLocation.getTile();
		Action action = interact(t, a, direction);
		t = newLocation.getTile();

		// if the actor no longer has a location reference then its dead
		if (dead)
			return action;
		if (t == null || t.canMoveOn())
			doMove(newLocation, direction);
		return action;
	}

	/**
	 * A valid move, moves the Actor into the new Location.
	 * 
	 * @param newLocation - new valid Location object
	 * @param direction   - Direction enum
	 */
	private void doMove(Location newLocation, Direction direction) {
		newLocation.setActor(this);
		location.setActor(null);
		location = newLocation;
		dir = direction;
	}

	/**
	 * Kills an actor by remove its reference
	 */
	public void kill() {
		dead = true;
		location.setActor(null);
		location = null;

	}

	/**
	 * Returns the location object
	 * 
	 * @return location object
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Returns the actorName enum
	 * 
	 * @return actorName enum
	 */
	public ActorName getActorName() {
		return actorName;
	}

	/**
	 * Returns the state of weather chap is dead
	 * 
	 * @return dead boolean
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Returns the current direction that its facing
	 * 
	 * @return Direction enum its facing
	 */
	public Direction getDir() {
		return dir;
	}

	/**
	 * To string method for testing
	 */
	public String toString() {
		String s = "";
		switch (actorName) {
		case CHAP:
			s = "C";
			break;
		case BOT:
			s = "B";
			break;
		default:
			s = "X";
		}
		return s;
	}

}