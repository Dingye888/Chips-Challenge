package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Gate class which represents a Locked door but it will only open when chap has
 * collected all the chips.
 * 
 * @author wangding1 300422014
 *
 */
public class Gate extends Tile {

	/**
	 * Constructor which creates Gate tile.
	 * 
	 * @param tileName  - name
	 * @param location  - location object
	 * @param canMoveOn - boolean if can be moved on
	 * @param canPickup - boolean if can be picked up
	 */
	public Gate(TileName tileName, Location location, boolean canMoveOn, boolean canPickup) {
		super(tileName, location, canMoveOn, canPickup);
	}

	@Override
	protected Action interact(Actor actor) {
		if (actor instanceof Chap) {
			Chap c = (Chap) actor;
			if (c.getChips() == c.getTotalChips()) {
				kill();
				return Action.DOOR;
			}
			return Action.WALL;
		}
		// not chap
		return null;
	}

}
