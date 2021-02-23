package nz.ac.vuw.ecs.swen225.gp20.renderer;

import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp20.maze.ActorName;
import nz.ac.vuw.ecs.swen225.gp20.maze.Door;
import nz.ac.vuw.ecs.swen225.gp20.maze.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.Location;
import nz.ac.vuw.ecs.swen225.gp20.maze.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.Variation;

/**
 * Class for create the canvas for maze.
 * @author Phoenix Xie
 *
 */

public class Canvas extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final int VIEW_SIZE = 9;

	private JLabel[][] displayTiles = new JLabel[VIEW_SIZE][VIEW_SIZE];
	private Location[][] map = null;
	// private Location chapL=null;

	private double width = 500;
	private double height = 500;

	/**
	 * Initialize the tile name to be display.
	 */
	public Canvas() {
		this.setLayout(new GridLayout(9, 9, 2, 2));

		
		for (int i = 0; i < VIEW_SIZE; i++) {
			for (int j = 0; j < VIEW_SIZE; j++) {
				displayTiles[i][j] = new JLabel();
				this.add(displayTiles[i][j]);
			}
		}

		this.setVisible(true);

	}

	/**
	 * Painting tiles.
	 */
	public void paintTiles() {
		if (map == null || width == 0 || height == 0) {
			return;
		}
		for (int i = 0; i < VIEW_SIZE; i++) {
			for (int j = 0; j < VIEW_SIZE; j++) {

				displayTiles[i][j].setIcon(new ImageIcon(getImage(map[i][j])));
			}
		}
	}

	/**
	 * Method for getting the image for current location.
	 * @param l Current location
	 * @return icon image add to canvas
	 */
	private Image getImage(Location l) {
		if (l.getActor() != null) {
			if (l.getActor().getActorName() == ActorName.CHAP) {
				URL url = getClass().getResource("/CHAP.png");
				Image icon = new ImageIcon(url).getImage();

				return icon.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			} else if (l.getActor().getActorName() == ActorName.BOT) {
				URL url = getClass().getResource("/bug.png");
				Image icon = new ImageIcon(url).getImage();

				return icon.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			}
		}
		if (l == null || l.getTile() == null && l.getActor() == null) {
			return new ImageIcon(getClass().getResource("/floor.png")).getImage().getScaledInstance(50, 50,
					Image.SCALE_DEFAULT);
		}
		Tile tile = l.getTile();
		if (tile instanceof Key) {
			Key key = (Key) tile;
			Variation variation = key.getVariation();
			if (variation == Variation.BLUE) {
				return new ImageIcon(getClass().getResource("/blue_key.png")).getImage().getScaledInstance(50, 50,
						Image.SCALE_DEFAULT);
			} else if (variation == Variation.YELLOW) {
				return new ImageIcon(getClass().getResource("/yellow_key.png")).getImage().getScaledInstance(50, 50,
						Image.SCALE_DEFAULT);
			} else if (variation == Variation.GREEN) {
				return new ImageIcon(getClass().getResource("/green_key.png")).getImage().getScaledInstance(50, 50,
						Image.SCALE_DEFAULT);
			} else if (variation == Variation.RED) {
				return new ImageIcon(getClass().getResource("/red_key.png")).getImage().getScaledInstance(50, 50,
						Image.SCALE_DEFAULT);
			}
		}
		if (tile instanceof Door) {
			Door door = (Door) tile;
			Variation variation = door.getVariation();
			if (variation == Variation.BLUE) {
				return new ImageIcon(getClass().getResource("/blue_door.png")).getImage().getScaledInstance(50, 50,
						Image.SCALE_DEFAULT);
			} else if (variation == Variation.YELLOW) {
				return new ImageIcon(getClass().getResource("/yellow_door.png")).getImage().getScaledInstance(50, 50,
						Image.SCALE_DEFAULT);
			} else if (variation == Variation.GREEN) {
				return new ImageIcon(getClass().getResource("/green_door.png")).getImage().getScaledInstance(50, 50,
						Image.SCALE_DEFAULT);
			} else if (variation == Variation.RED) {
				return new ImageIcon(getClass().getResource("/red_door.png")).getImage().getScaledInstance(50, 50,
						Image.SCALE_DEFAULT);
			}
		}

		String name = l.getTile().getTileName().toString();
		String s = name + ".png";

		URL url = getClass().getResource("/" + s);
		Image icon = new ImageIcon(url).getImage();

		return icon.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
	}

	/**
	 * Set the current tiles.
	 * @param tiles
	 */
	public void setGameTiles(Location[][] tiles) {
		this.map = tiles;
		paintTiles();
	}

}
