package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * TileName Enum class which lists the names of different tiles
 * 
 * @author wangding1 300422014
 *
 */
public enum TileName {
	/**
	 * Wall tile which cannot be moved on
	 */
	WALL,
	/**
	 * Door tile which acts like a Wall but can be opened with right key
	 */
	DOOR,
	/**
	 * Key tile which can be picked up only by chap to open doors
	 */
	KEY,
	/**
	 * Gate tile which acts like a wall but can be opened once chap has all the
	 * chips
	 */
	GATE,
	/**
	 * Chip tile which be be picked up by chap
	 */
	CHIP,
	/**
	 * Exit tile which the level should finished once interacted on
	 */
	EXIT,
	/**
	 * Info tile which should prompt a message for the player
	 */
	INFO,
	/**
	 * Lava tile which should kill any actors who interacts with
	 */
	LAVA
}