package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;


import nz.ac.vuw.ecs.swen225.gp20.persistence.Parser;
import nz.ac.vuw.ecs.swen225.gp20.renderer.renderer;
import nz.ac.vuw.ecs.swen225.gp20.application.GameController;
import nz.ac.vuw.ecs.swen225.gp20.application.GameInfoModel;
import nz.ac.vuw.ecs.swen225.gp20.application.GameInfoRenderer;
import nz.ac.vuw.ecs.swen225.gp20.maze.ActorName;
import nz.ac.vuw.ecs.swen225.gp20.maze.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.Door;
import nz.ac.vuw.ecs.swen225.gp20.maze.Location;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.TileName;
import nz.ac.vuw.ecs.swen225.gp20.maze.Variation;



/**
 * Class to record game play and replay it.
 * @author 300457588 Jasmine Liang
 *
 */
public class RecordAndReplay {
	private static String fileName;
	private static String board;
	private static ArrayList<KeyEvent> actionList = new ArrayList<>();
	private static ArrayList<String> directionList = new ArrayList<>();
	
	private static boolean isRecording = false;
	private static boolean isReplaying = false;
	private static boolean isStepReplay = false;
	private static boolean isAutoReplay = false;
	
	private static int chipsLeft = 11;
	private static int level = 1;
	private static Map<String,Integer> inventoryMap = new HashMap<String,Integer>();
	
	
	private static int delay = 700;

	/**
	 * To start a new record
	 * @param name the file name to save
	 * @param gc current game controller
	 */
	public static void startNewRecord(String name, GameController gc) {
		if(!isRecording && !isReplaying) {
			// initialize
			actionList.clear();
			board = null;
			directionList.clear();
			isRecording = false;
			isReplaying = false;
			isStepReplay = false;
			isAutoReplay = false;
			delay = 700;
			// read game state and game info
			isRecording = true;
			fileName = name;
			board = getGameState(gc);
			chipsLeft = gc.getMaze().getChap().getTotalChips() - gc.getMaze().getChap().getChips();
			level = gc.getInfoModel().getLevel();
			inventoryMap = gc.getMaze().getChap().getInventory();
		}
	}
	
	
	
	/**
	 * Add actions to the action list
	 * @param e the actions need to save
	 */
	public static void addAction(KeyEvent e) {
		if(isRecording) {
			actionList.add(e);
		}
	}
	
	/**
	 * Save recording to json file
	 * @param gc current game controller
	 */
	public static void saveRecording(GameController gc) {
		if(isRecording && !isReplaying) {
			/** if using the saveGame in persistence package
			 * SaveGame s = new SaveGame(maze, currentState);
			 * gameState = s.save();
			*/
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			// add actions to array builder
			for (int i = 0; i < actionList.size(); i++) {
				KeyEvent e = actionList.get(i);
				String action = null;
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					action = "North";
					break;
				case KeyEvent.VK_DOWN:
					action = "South";
					break;
				case KeyEvent.VK_LEFT:
					action = "West";
					break;
				case KeyEvent.VK_RIGHT:
					action = "East";
					break;
				default:
					break;
				}
		        JsonObjectBuilder builder = Json.createObjectBuilder()
		            .add("move", action);
		        arrayBuilder.add(builder.build());
		      }
			
			/**JsonBuilderFactory factory = Json.createBuilderFactory(inventory);
			JsonArrayBuilder arrayBuilder2 = Json.createArrayBuilder();
			for(String s:inventory.keySet()) {
				JsonObjectBuilder value = factory.createObjectBuilder()
								.add("name",s)
								.add("number", inventory.get(s));
				arrayBuilder2.add(value);	
			}*/
			
			// add game state and action array to a new builder
			JsonObjectBuilder builder = Json.createObjectBuilder()
					.add("game", board)
					.add("chips", chipsLeft)
					.add("level", level)
			        .add("moves", arrayBuilder);
			// write text to json file
			Writer writer = new StringWriter();
			Json.createWriter(writer).write(builder.build());
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			    bw.write(writer.toString());
			    bw.close();
			    } catch (IOException e) {
			      throw new Error("Failed to save record");
			    }
			isRecording = false;
			
		}
	}
	
	/**
	 * Load a recording from json file
	 * @param name name of file to load
	 * @param gc gamestate
	 */
	public static void loadRecording(String name, GameController gc) {
		if(!isRecording) {
			isReplaying = true;
			fileName = name;
			if(level==1) gc.stopCountdown();
			// load game state
			// select the gamestate from json file
			String line;
			JsonObject gameState = null;
			try {
				BufferedReader buffReader1 = new BufferedReader(new FileReader(fileName));
				JsonReader jsonReader1 = Json.createReader(new StringReader(buffReader1.readLine()));
				buffReader1.close();
				gameState = jsonReader1.readObject();
			} catch (IOException e){
				System.out.println("Error reading file: " + e);
				return;
			}
			String buffer = gameState.getJsonString("game").toString();
			// try to write the game state to a new json file can be read by parser
			// adjust to fit the  pattern of the board
			ArrayList<String> output = new ArrayList<String>();
			int start = 0, end = 0;
			for(int i=0; i<buffer.length(); i++) {
				if(buffer.charAt(i)=='[') {
					start = i;
				}
				if(buffer.charAt(i)==']') {
					end = i;
				}
				if(end>start) {
					String arr = buffer.substring(start,end-2);
					arr += "]";
					start = 0;
					end = 0;
					output.add(arr);
				}
			}
			// write to json file
			buffer+="]";    
			try {
				File map = new File("board.json");
				FileWriter fileWritter = new FileWriter(map.getName());
				for(int i=0; i<output.size(); i++) {
					if(i==0) {
						fileWritter.write("["); 
					}
					fileWritter.write(output.get(i));
					if(i==output.size()-1) {
						fileWritter.write("]");
					}else {
						fileWritter.write("\n,");
					}
				}
				fileWritter.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			// update the game state
			Parser parser = new Parser("board.json");
			Maze maze = new Maze();
			renderer mazeRenderer = gc.getRenderer();
			maze.setLevel(parser.map);
			mazeRenderer.setMaze(maze.getLocation());
			mazeRenderer.playerPos = maze.getChap().getLocation();
			mazeRenderer.Corner();
			gc.setMazeRenderer(mazeRenderer);
			gc.setMaze(maze);

			// load actions
			// select the actions from json file
			JsonObject object = null;
			try {
				BufferedReader buffReader2 = new BufferedReader(new FileReader(fileName));
				JsonReader jsonReader2 = Json.createReader(new StringReader(buffReader2.readLine()));
				buffReader2.close();
				object = jsonReader2.readObject();
			} catch (IOException e) {
				System.out.println("Error reading file: " + e);
				return;
			}
			JsonArray movesJson = object != null ? object.getJsonArray("moves") : null;
			if (movesJson != null) {
				for (int i = 0; i < movesJson.size(); i++) {
					JsonObject object2 = movesJson.getJsonObject(i);
					directionList.add(object2.getString("move"));
				}
			}

			// load game information
			if(object!=null) {
				chipsLeft = object.getInt("chips");
				level = object.getInt("level");
				}
			GameInfoModel model = gc.getInfoModel();
			model.setChipsLeft(chipsLeft);
			if(level==1) model.setTime(100);
			if(level==2) model.setTime(60);
			if(level==3) model.setTime(90);
			model.setLevel(level);
			model.setInventory(inventoryMap);
			GameInfoRenderer infoRenderer = gc.getInfoRenderer();
			infoRenderer.render(model);
			infoRenderer.update(model);
			gc.setInfoModel(model);
			gc.setGameInfoRenderer(infoRenderer);

			// replay the actions
			if(!isStepReplay) {
				autoReplay(gc);
			}
			if(directionList.size()==0) isReplaying = false;
		}
	}
	
	/**
	 * Replay by step
	 * @param gc game controller
	 */
	public static void stepReplay(GameController gc) {
		isStepReplay = true;
		isAutoReplay = false;
		if(isReplaying && !isRecording) {
			if(directionList.size()>0) {
				replay(gc);
			}else {
				isReplaying = false;
			}
		}
	}
	
	/**
	 * Auto replay
	 * @param gc game controller
	 */
	public static void autoReplay(GameController gc) {
		if(!isAutoReplay) {
		isAutoReplay = true;
		isStepReplay = false;
		if(isReplaying && !isRecording) {
			if(directionList.size()>0) {
				Runnable runnable = () -> {
					while (directionList.size() > 0 && !isStepReplay) {
						try {
							Thread.sleep(delay);
							replay(gc);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};
				Thread thread = new Thread(runnable);
				thread.start();
			}else {
				isReplaying = false;
			}
		}
		}
	}
	
	/**
	 * Replay the load actions
	 * @param gc game controller
	 */
	private static void replay(GameController gc) {
		String direction = directionList.get(0);
		AtomicBoolean isBusy = new AtomicBoolean(false);
		if (isBusy.compareAndSet(false, true)) {
			SwingUtilities.invokeLater(() -> {
				switch (direction) {
				case "West":
					gc.move(Direction.WEST);
					break;
				case "East":
					gc.move(Direction.EAST);
					break;
				case "North":
					gc.move(Direction.NORTH);
					break;
				case "South":
					gc.move(Direction.SOUTH);
					break;
				default:
					break;
				}
				isBusy.set(false);
			});
		}
		directionList.remove(0);
	}
	
	/**
	 * Set delay number to change the speed of replay
	 * @param d speed times
	 */
	public static void setDelay(double d) {
		if(d==0.5) {
			delay = 1400;
		}else if(d==1) {
			delay = 700;
		}else {
			delay = 350;
		}
	}
	
	/**
	 * Get the game state
	 * @param gc game controller
	 * @return String game state
	 */
	private static String getGameState(GameController gc) {
		String gamestate = "";
		Location[][]map = gc.getMaze().getLocation();
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				String gs = "";
				if(j==0 && i!=0) {
					gamestate+="]\n[";
				}
				if(j==0 && i==0) {
					gamestate+="[";
				}
				Location object = map[i][j];
				// Blank
				if(object.toString().equals("_")) {
					gs = "1, ";
					gamestate = add(gamestate,gs);
					continue;
				}
				// get tile name of the cell
				TileName tileName = null;
				if(object.getTile()!=null) {
					tileName = object.getTile().getTileName();
				}
				// get actor name of the cell
				ActorName actorName = null;
				if(object.getActor()!=null) {
					actorName = object.getActor().getActorName();
					switch(actorName) {
					// Chap
					case CHAP:
						gs = "0, ";
						gamestate = add(gamestate,gs);
						continue;
					// Bot
					case BOT:
						gs = "15, ";
						gamestate = add(gamestate,gs);
						continue;
					
					}
				}
				switch (tileName) {
				// Wall
				case WALL:
					gs = "2, ";
					gamestate = add(gamestate,gs);
					continue;
				// Chip
				case CHIP:
					gs = "3, ";
					gamestate = add(gamestate,gs);
					continue;
				// Key
				case KEY:
					Key k = (Key) object.getTile();
					Variation v = k.getVariation();
					switch(v) {
					case YELLOW:
						gs = "4, ";
						gamestate = add(gamestate,gs);
						continue;
					case RED:
						gs = "5, ";
						gamestate = add(gamestate,gs);
						continue;
					case GREEN:
						gs = "6, ";
						gamestate = add(gamestate,gs);
						continue;
					case BLUE:
						gs = "7, ";
						gamestate = add(gamestate,gs);
						continue;
					}
				// Door
				case DOOR:
					Door d = (Door) object.getTile();
					Variation v2 = d.getVariation();
					switch(v2) {
					case YELLOW:
						gs = "8, ";
						gamestate = add(gamestate,gs);
						continue;
					case RED:
						gs = "9, ";
						gamestate = add(gamestate,gs);
						continue;
					case GREEN:
						gs = "10, ";
						gamestate = add(gamestate,gs);
						continue;
					case BLUE:
						gs = "11, ";
						gamestate = add(gamestate,gs);
						continue;
					}
				// Gate
				case GATE:
					gs = "12, ";
					gamestate = add(gamestate,gs);
					continue;
				// Portal
				case EXIT:
					gs = "13, ";
					gamestate = add(gamestate,gs);
					continue;
				// Help
				case INFO:
					gs = "14, ";
					gamestate = add(gamestate,gs);
					continue;
				// Lava
				case LAVA:
					gs = "16, ";
					gamestate = add(gamestate,gs);
					continue;
				}
			}
			if(i==map.length-1) {
				gamestate += "]";
			}
		}
		return gamestate;
	}
	
	
	
	/**
	 * Help method for adding two strings
	 * @param s1
	 * @param s2
	 * @return 
	 */
	private static String add(String s1, String s2) {
		return s1+s2;
	}
	
	/**
	 * Get isRecording
	 * @return boolean isRecording
	 */
	public static boolean isRecording() {
		return isRecording;
	}
	
	/**
	 * Get isReplaying
	 * @return boolean isReplaying
	 */
	public static boolean isReplaying() {
		return isReplaying;
	}
	
	/**
	 * Get isStepReplay
	 * @return boolean isStepReplay
	 */
	public static boolean isStepReplay() {
		return isStepReplay;
	}
	
	/**
	 * Get isAutoReplay
	 * @return boolean isAutoReplay
	 */
	public static boolean isAutoReplay() {
		return isAutoReplay;
	}
	
	
	/**
	 * Get actions list
	 * @return actionList
	 */
	public ArrayList<KeyEvent> getActionList(){
		return actionList;
	}
	
	/**
	 * Get directions list
	 * @return directionList
	 */
	public ArrayList<String> getDirectionList(){
		return directionList;
	}
	
	/**
	 * Get file name
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Get board
	 * @return board
	 */
	public String getBoard() {
		return board;
	}
	
	/**
	 * Get delay
	 * @return delay
	 */
	public int getDelay() {
		return delay;
	}
	
}
