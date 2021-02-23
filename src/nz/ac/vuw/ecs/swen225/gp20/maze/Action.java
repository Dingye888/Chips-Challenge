package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Action enums to represent what was the last sucsessful action.
 * 
 * @author wangding1 300422014
 *
 */
public enum Action {
	/**
	 * Valid move
	 */
	MOVE,
	/**
	 * Invalid move
	 */
	WALL,
	/**
	 * Opened a door
	 */
	DOOR,
	/**
	 * Picked up an Item
	 */
	ITEM,
	/**
	 * Stepped on info
	 */
	INFO,
	/**
	 * Reached exit
	 */
	EXIT,
	/**
	 * Chap died
	 */
	DIE,
	/**
	 * Bot Valid move
	 */
	BOT_MOVE,
	/**
	 * Bot died
	 */
	BOT_DIE,
	/**
	 * for testing purposes
	 */
	INVALID
}
