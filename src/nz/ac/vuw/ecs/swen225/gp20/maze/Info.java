package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * The Info class which extends the Tile class, it should act like a free tile
 * but will also show a help message to the player when walked on
 * 
 * @author wangding1 300422014
 *
 */
public class Info extends Tile {
	private String txt;

	/**
	 * Constructor which creates the Info tile.
	 * 
	 * @param tileName - tileName enum
	 * @param location - location object
	 * @param moveOn   - if the tile can be moved on
	 * @param pickUp   - if the tile can be picked up
	 */
	public Info(TileName tileName, Location location, boolean moveOn, boolean pickUp) {
		super(tileName, location, moveOn, pickUp);
	}

	/**
	 * Constructor which creates the Info tile. Also contains a String param for
	 * storing help info.
	 * 
	 * @param tileName - tileName enum
	 * @param location - location object
	 * @param moveOn   - if the tile can be moved on
	 * @param pickUp   - if the tile can be picked up
	 * @param txt      - help message
	 */
	public Info(TileName tileName, Location location, boolean moveOn, boolean pickUp, String txt) {
		super(tileName, location, moveOn, pickUp);
		this.txt = txt;
	}

	@Override
	protected Action interact(Actor actor) {
		if (actor instanceof Chap)
			return Action.INFO;
		return null;
	}

	/**
	 * Returns a help message that is used for this tile.
	 * 
	 * @return txt - String message
	 */
	public String getInfo() {
		return txt;
	}

}
