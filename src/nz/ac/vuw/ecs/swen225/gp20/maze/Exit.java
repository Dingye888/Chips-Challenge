package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * The Exit class which represents a Exit on the map, when chap has stepped onto
 * the exit the level should end.
 * 
 * @author wangding1 300422014
 *
 */
public class Exit extends Tile {

	/**
	 * Constructor which creates the Exit tile.
	 * 
	 * @param tileName  - name
	 * @param location  - location object
	 * @param canMoveOn - boolean if can be moved on
	 * @param canPickup - boolean if can be picked up
	 */
	public Exit(TileName tileName, Location location, boolean canMoveOn, boolean canPickup) {
		super(tileName, location, canMoveOn, canPickup);
	}

	@Override
	protected Action interact(Actor actor) {
		if (actor instanceof Chap) {
			return Action.EXIT;
		}
		return null;
	}

}
