package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Key class for creating the key Tile which can be picked up and unlocks
 * certain doors based on variation.
 * 
 * @author wangding1 300422014
 *
 */
public class Key extends Tile {
	private Variation variation;

	/**
	 * Constructor which creates the Key tile.
	 * 
	 * @param tileName  - name
	 * @param location  - location object
	 * @param canMoveOn - boolean if can be moved on
	 * @param canPickup - boolean if can be picked up
	 * @param variation - variation enum
	 */
	public Key(TileName tileName, Location location, boolean canMoveOn, boolean canPickup, Variation variation) {
		super(tileName, location, canMoveOn, canPickup);
		this.variation = variation;
	}

	@Override
	protected Action interact(Actor actor) {
		if (actor instanceof Chap) {
			((Chap) actor).addItem(createItemName(TileName.KEY, variation));
			kill();
			return Action.ITEM;
		}
		return null;
	}

	/**
	 * Returns the key variation
	 * 
	 * @return variation - variation enum
	 */
	public Variation getVariation() {
		return this.variation;
	}

}
