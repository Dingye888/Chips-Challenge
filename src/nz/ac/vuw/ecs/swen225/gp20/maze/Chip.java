package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Chip class represents the chips that chap can collect in order to open the
 * final game and progress to the next level.
 * 
 * @author wangding1 300422014
 *
 */
public class Chip extends Tile {

	/**
	 * Constructor which creates the Chip tile.
	 * 
	 * @param tileName  - tileName enum
	 * @param location  - location object
	 * @param canMoveOn - boolean if can be moved on
	 * @param canPickup - boolean if can be picked up
	 */
	public Chip(TileName tileName, Location location, boolean canMoveOn, boolean canPickup) {
		super(tileName, location, canMoveOn, canPickup);
	}

	@Override
	protected Action interact(Actor actor) {
		if (actor instanceof Chap) {
			((Chap) actor).addChips();
			kill();
			return Action.ITEM;
		}
		// not chap
		return null;
	}

}
