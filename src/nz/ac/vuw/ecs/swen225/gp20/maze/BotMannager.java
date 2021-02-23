package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * The BotMannager class handles all other Actors thats not Chap ,and will
 * update their movements based on its own nextLoaction method.
 * 
 * @author wangding1 300422014
 *
 */
public class BotMannager {
	private Maze maze;
	private HashSet<Actor> actors;

	/**
	 * The constructor takes a Set of actors which are all unique and the maze
	 * 
	 * @param actors - HashSet of actors
	 * @param maze   - Maze object
	 */
	public BotMannager(HashSet<Actor> actors, Maze maze) {
		this.actors = actors;
		this.maze = maze;
	}

	/**
	 * Advance the game by 1 tick, which will update all other actors positions
	 * based on there own algorithms.
	 */
	public void tick() {
		// no other actors
		if (actors.isEmpty())
			return;

		// moves all actors
		ArrayList<Actor> turn = new ArrayList<>(actors); // bots turn

		// Since we can't remove lists in a for loop, we can remove it in while loop
		while (!turn.isEmpty()) {

			Actor a = turn.get(0); // gets a random actor first
			turn.remove(a);

			// if actor was dead
			if (a.isDead()) {
				actors.remove(a);
				continue;
			}

			moveBot(a, turn); // moves bots recursively if its going to move into another actor

			// if actor just died
			if (a.isDead())
				actors.remove(a);
		}
	}

	/**
	 * Recursive helper method for moving all the Bots. If the bots are going to
	 * move into another actor, calls the recursive function on the new actor to
	 * move that actor first and so on so they avoid collision even though they
	 * don't finish on the same tile.
	 * 
	 * @param a    - Actor object
	 * @param turn - ArrayList of the Actors which haven't moved
	 */
	private void moveBot(Actor a, ArrayList<Actor> turn) {
		Location nl = a.nextLocation(maze);
		if (nl == null)
			return;

		// If there is a new Location, and has an actor that haven't moved, Recurse move
		if (nl.getActor() != null && turn.contains(nl.getActor())) {
			turn.remove(nl.getActor());
			moveBot(nl.getActor(), turn);
		}
		if (!a.isDead())
			maze.move(a, a.getDir());
	}

	/**
	 * Returns the set of actors
	 * 
	 * @return actors - HashSet
	 */
	public HashSet<Actor> getActors() {
		return actors;
	}

}
