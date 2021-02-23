package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Door class which can be opened with the corresponding color key. The door is
 * locked by default.
 * 
 * @author wangding1 300422014
 *
 */
public class Door extends Tile {

	private Variation variation;

	/**
	 * Constructor which creates the Door tile.
	 * 
	 * @param tileName  - name
	 * @param location  - location object
	 * @param canMoveOn - boolean if can be moved on
	 * @param canPickup - boolean if can be picked up
	 * @param variation - variation enum
	 */
	public Door(TileName tileName, Location location, boolean canMoveOn, boolean canPickup, Variation variation) {
		super(tileName, location, canMoveOn, canPickup);
		this.variation = variation;
	}

	@Override
	protected Action interact(Actor actor) {
		if (actor instanceof Chap) {
			Chap c = (Chap) actor;
			String item = createItemName(TileName.KEY, variation);
			if (c.getInventory().containsKey(item)) {
				c.removeItem(item);
				kill();
				return Action.DOOR;
			}
			return Action.WALL;
		}
		return null;
	}

	/**
	 * Returns the variation of this door
	 * 
	 * @return variation - variation enum
	 */
	public Variation getVariation() {
		return this.variation;
	}

}
