package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * The Bot class which extends Actor and will move by itself using BotMannager.
 * This bot will constantly move by sticking to its left wall.
 * 
 * @author wangding1 300422014
 *
 */
public class Bot extends Actor {

	/**
	 * Chap actor constructor
	 * 
	 * @param actorName - ActorName enum
	 * @param loaction  - Location object
	 */
	public Bot(ActorName actorName, Location loaction) {
		super(actorName, loaction);
	}

	/**
	 * Chap actor constructor
	 * 
	 * @param actorName - ActorName enum
	 * @param location  - location object
	 * @param d         - Direction enum
	 */
	public Bot(ActorName actorName, Location location, Direction d) {
		super(actorName, location, d);
	}

	@Override
	protected Action interact(Tile t, Actor a, Direction dir) {
		// collided with another actor, kill each other
		if (a != null) {
			kill();
			a.kill();
			return Action.BOT_DIE;
		}
		// has a tile
		if (t != null) {
			return t.interact(this); // performs actor tile interaction
		}
		return Action.BOT_MOVE;
	}

	@Override
	protected Location nextLocation(Maze maze) {
		int face = 0;
		Location l = maze.findNewLocation(this, left());

		while (l.getTile() != null && !l.getTile().canMoveOn() && face < 4) {
			l = maze.findNewLocation(this, right());
			face++;
		}
		return l;
	}

	/**
	 * Returns the direction enum to its Left and face it
	 * 
	 * @return Direction enum to its left
	 */
	private Direction left() {
		dir = dir.left(dir);
		return dir;
	}

	/**
	 * Returns the direction enum to its Right and face it
	 * 
	 * @return Direction enum to its Right
	 */
	private Direction right() {
		dir = dir.right(dir);
		return dir;
	}

}
