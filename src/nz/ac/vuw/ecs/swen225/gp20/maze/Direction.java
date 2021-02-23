package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Direction enums for representing the 4 directions
 * 
 * @author wangding1 300422014
 *
 */
public enum Direction {
	/**
	 * North of the board view
	 */
	NORTH,
	/**
	 * South of the board view
	 */
	SOUTH,
	/**
	 * East of the board view
	 */
	EAST,
	/**
	 * West of the board view
	 */
	WEST;

	/**
	 * Returns the direction enum thats to its Left
	 * 
	 * @param dir - Direction enum
	 * @return Direction enum to its left
	 */
	public Direction left(Direction dir) {
		Direction newD = null;
		switch (dir) {
		case NORTH:
			newD = WEST;
			break;
		case EAST:
			newD = NORTH;
			break;
		case SOUTH:
			newD = EAST;
			break;
		case WEST:
			newD = SOUTH;
			break;
		default:
			break;
		}
		return newD;
	}

	/**
	 * Returns the direction enum thats to its Right
	 * 
	 * @param dir - Direction enum
	 * @return Direction enum to its Right
	 */
	public Direction right(Direction dir) {
		Direction newD = null;
		switch (dir) {
		case NORTH:
			newD = EAST;
			break;
		case EAST:
			newD = SOUTH;
			break;
		case SOUTH:
			newD = WEST;
			break;
		case WEST:
			newD = NORTH;
			break;
		default:
			break;
		}
		return newD;
	}

}
