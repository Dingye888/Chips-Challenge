package nz.ac.vuw.ecs.swen225.gp20.persistence;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import nz.ac.vuw.ecs.swen225.gp20.maze.ActorName;
import nz.ac.vuw.ecs.swen225.gp20.maze.Location;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.TileName;
import nz.ac.vuw.ecs.swen225.gp20.maze.Variation;

/**
 * Load the game map to an 2d-array.
 * 
 * @author Biru Lin 300456889
 *
 */
public class Parser {
	/**
	 * the map for game.
	 */
	public Location[][] map;

	/**
	 * remaining game time.
	 */
	public int time;

	/**
	 * the number of chips Chap've collected.
	 */
	public int Chips;

	/**
	 * the level of saved game.
	 */
	public int currLevel;

	/**
	 * the inventory for Chap.
	 */
	public Map<String, Integer> inventory = new HashMap<String, Integer>();

	protected String Info1 = "Chap needs to pick up all the chips in time to pass this level.\r\n"
			+ "Chips are distributed around the map and some of them are locked in rooms.\r\n"
			+ "To open the doors, Chap needs to collect corresponding colored keys.";
	protected String Info2 = "When Chap falls into Lava, he will die.\r\n"
			+ "Also, if Chap touches the bug, he will die as well.";

	/**
	 * Constructor for Parser.
	 * 
	 * @param filename
	 */
	public Parser(String filename) {
		load(filename);

	}

	/**
	 * load the given file.
	 * 
	 * @param filename
	 */
	private void load(String filename) {
		FileReader file;
		JsonArray arr;
		int row = 0;
		int col = 0;
		try {
			file = new FileReader(filename);
			JsonParser parser = Json.createParser(file);
			Event event = parser.next();
			arr = parser.getArray();
			row = arr.size();
			for (JsonValue v : arr) {
				if (v.getValueType() == ValueType.OBJECT)
					row--;
			}
			col = arr.get(0).asJsonArray().size();
			// load the map part
			loadMap(row, col, arr);
			// load the information part
			loadInfo(arr);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void loadInfo(JsonArray arr) {
		for (JsonValue obj : arr) {
			if (obj.getValueType() == ValueType.OBJECT) {
				JsonObject object = obj.asJsonObject();
				time = object.getInt("Time", 30);
				Chips = object.getInt("NumOfChips", 9);
				currLevel = object.getInt("Level", 1);

				if (object.containsKey("Inventory")) {
					JsonObject invObj = object.get("Inventory").asJsonObject();
					for (String keys : invObj.keySet()) {
						inventory.put(keys, invObj.getInt(keys));
					}
				}
			}
		}
	}

	/**
	 * load map
	 * 
	 * @param row : int
	 * @param col : int
	 * @param arr : jsonarray
	 */
	private void loadMap(int row, int col, JsonArray arr) {
		map = new Location[row][col];
		for (int i = 0; i < row; i++) {
			JsonArray rowArr = arr.get(i).asJsonArray();
			for (int j = 0; j < col; j++) {
				int obj = rowArr.getInt(j);
				switch (obj) {
				// Chap's initial place
				case 0:
					map[i][j] = new Location(i, j, ActorName.CHAP);
					break;
				// wall tile
				case 2:
					map[i][j] = new Location(i, j, TileName.WALL);
					break;
				// chips
				case 3:
					map[i][j] = new Location(i, j, TileName.CHIP);
					break;
				// Yellow Key
				case 4:
					map[i][j] = new Location(i, j, TileName.KEY, Variation.YELLOW);
					break;
				// Red Key
				case 5:
					map[i][j] = new Location(i, j, TileName.KEY, Variation.RED);
					break;
				// Green Key
				case 6:
					map[i][j] = new Location(i, j, TileName.KEY, Variation.GREEN);
					break;
				// Blue Key
				case 7:
					map[i][j] = new Location(i, j, TileName.KEY, Variation.BLUE);
					break;
				// Yellow Door
				case 8:
					map[i][j] = new Location(i, j, TileName.DOOR, Variation.YELLOW);
					break;
				// Red Door
				case 9:
					map[i][j] = new Location(i, j, TileName.DOOR, Variation.RED);
					break;
				// Green Door
				case 10:
					map[i][j] = new Location(i, j, TileName.DOOR, Variation.GREEN);
					break;
				// Blue Door
				case 11:
					map[i][j] = new Location(i, j, TileName.DOOR, Variation.BLUE);
					break;
				// Gate
				case 12:
					map[i][j] = new Location(i, j, TileName.GATE);
					break;
				// Portal
				case 13:
					map[i][j] = new Location(i, j, TileName.EXIT);
					break;
				// Level 1 Help
				case 14:
					map[i][j] = new Location(i, j, TileName.INFO, getInfo1());
					break;
				// Level 2 Help
				case 17:
					map[i][j] = new Location(i, j, TileName.INFO, getInfo2());
					break;
				// Bug
				case 15:
					map[i][j] = new Location(i, j, ActorName.BOT);
					break;
				// Lava
				case 16:
					map[i][j] = new Location(i, j, TileName.LAVA);
					break;
				default:
					map[i][j] = new Location(i, j);
					break;
				}
			}
		}
	}

	/**
	 * get level 1 info
	 * 
	 * @return level 1 info
	 */
	public String getInfo1() {
		return Info1;
	}

	/**
	 * set level 1 info
	 * 
	 * @param info1
	 */
	public void setInfo1(String info1) {
		Info1 = info1;
	}

	public String getInfo2() {
		return Info2;
	}

	public void setInfo2(String info2) {
		Info2 = info2;
	}
}
