package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Wall class represents a Tile that cannot be moved on to.
 * 
 * @author wangding1 300422014
 *
 */
public class Wall extends Tile {

	/**
	 * Constructor which creates the Wall tile.
	 * 
	 * @param tileName  - tileName enum
	 * @param location  - location object
	 * @param canMoveOn - boolean if the player can be moved on
	 * @param canPickUp - boolean if the player can pick up
	 */
	public Wall(TileName tileName, Location location, boolean canMoveOn, boolean canPickUp) {
		super(tileName, location, canMoveOn, canPickUp);
	}

	@Override
	protected Action interact(Actor actor) {
		return Action.WALL;
	}

}
