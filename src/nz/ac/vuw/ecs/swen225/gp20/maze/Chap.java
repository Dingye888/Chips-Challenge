package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.HashMap;

/**
 * Chap class which extends Actor which is our main protagonist. Chap will be
 * able to transverse through-out the maze, gather chips and finish each level.
 * 
 * @author wangding1 300422014
 *
 */
public class Chap extends Actor {

	private HashMap<String, Integer> inventory;
	private int chips;
	private int totalChips;

	/**
	 * Chap actor constructor
	 * 
	 * @param actorName - Enum actorName
	 * @param location  - location
	 */
	public Chap(ActorName actorName, Location location) {
		super(actorName, location);
		this.inventory = new HashMap<>();
		this.chips = 0;
	}

	/**
	 * Chap actor constructor
	 * 
	 * @param actorName - Enum actorName
	 * @param location  - location
	 * @param d         - Direction enum
	 */
	public Chap(ActorName actorName, Location location, Direction d) {
		super(actorName, location, d);
		this.inventory = new HashMap<>();
		this.chips = 0;
	}

	@Override
	protected Action interact(Tile t, Actor a, Direction dir) {
		// collided with another actor, kill each other
		if (a != null) {
			kill();
			a.kill();
			return Action.DIE;
		}
		// has a tile
		if (t != null) {
			return t.interact(this);
		}
		return Action.MOVE;
	}

	@Override
	protected Location nextLocation(Maze maze) {
		return null;
	}

	/**
	 * Sets the total amount of chips
	 * 
	 * @param totChips - int amount of total chips
	 */
	public void setTotalChips(int totChips) {
		this.totalChips = totChips;
	}

	/**
	 * Returns the total amount of chips
	 * 
	 * @return totalChips - int of amount of chips
	 */
	public int getTotalChips() {
		return totalChips;
	}

	/**
	 * Gets the amount of chips chap currently has
	 * 
	 * @return chips - int of current chips
	 */
	public int getChips() {
		return chips;
	}

	/**
	 * Add one chip in chaps collection
	 */
	public void addChips() {
		chips++;
	}

	/**
	 * Return chaps current inventory
	 * 
	 * @return inventory - HashMap of String, Integer for name and amount
	 */
	public HashMap<String, Integer> getInventory() {
		return inventory;
	}

	/**
	 * Adds an item based on a custom string
	 * 
	 * @param s - String key
	 */
	public void addItem(String s) {
		// already has the key, +1 extra count
		if (inventory.containsKey(s))
			inventory.put(s, inventory.get(s) + 1);
		// no key yet, adds 1
		else
			inventory.put(s, 1);
	}

	/**
	 * Removes an Item from the map with a custom String
	 * 
	 * @param s - String key
	 */
	public void removeItem(String s) {
		if (inventory.get(s) > 1)
			inventory.put(s, inventory.get(s) - 1);
		else
			inventory.remove(s);
	}

}
