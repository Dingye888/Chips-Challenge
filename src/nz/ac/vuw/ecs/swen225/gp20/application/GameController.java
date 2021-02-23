package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import nz.ac.vuw.ecs.swen225.gp20.maze.Action;
import nz.ac.vuw.ecs.swen225.gp20.maze.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.Info;
import nz.ac.vuw.ecs.swen225.gp20.maze.Location;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Parser;
import nz.ac.vuw.ecs.swen225.gp20.persistence.SaveGame;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.RecordAndReplay;
import nz.ac.vuw.ecs.swen225.gp20.renderer.renderer;

/**
 * GameController is responsible for connecting all modules,
 * especially connection view modules with engine modules.
 * 
 * @author Wang Conglang 300472254
 *
 */
public class GameController {
	/**
	 * Mapping level number with their time limits.
	 * For example, level 1 100 seconds, level 2 60 seconds, etc.
	 */
	private static Map<Integer, Integer> LEVEL_TIME = new HashMap<>();

	// Ideally level time information is stored together with level map in level json files.
	// As levels json files are not designed in that way initially, the level time information is temporarily
	// stored in memory.
	static {
		LEVEL_TIME.put(1, 100);
		LEVEL_TIME.put(2, 60);
		LEVEL_TIME.put(3, 90);
		LEVEL_TIME.put(4, 200);
		LEVEL_TIME.put(5, 250);
	}

	/**
	 * This is the file name which stores the game state when the player hits Save & Quit.
	 */
	private static final String SAVE_LEVEL_FILE_NAME = "SavedMap.json";

	/**
	 * gameInfoRenderer is responsible for rendering the panel on the right side of the window.
	 * It effectively changes level number, time count-down, chips number and keys held.
	 */
	private GameInfoRenderer gameInfoRenderer;
	/**
	 * mazeRenderer is responsible for rendering the main part of the game - the game map.
	 * It holds a Maze object and render a portion of the maze around the chap.
	 * It repaints after the chap successfully moves.
	 * It also repaints after a tick successfully moves Actors (Game Bots, aka bugs).
	 */
	private renderer mazeRenderer;

	/**
	 * A Maze instance is the engine of the Game.
	 * Players operations will be passed to maze to change the game state.
	 * Renderer will then repaint the game map according to maze instance.
	 */
	private Maze maze = new Maze();

	/**
	 * Enum GameStatus helps to decide whether a player's operation should be reacted.
	 * A player can only move the chap when GameStatus is LEVEL_STARTED.
	 * tickTimer will also only ask maze to move Bots when GameStatus is LEVEL_STARTED.
	 */
	private GameStatus status = GameStatus.NOT_STARTED;
	/**
	 * Represents the current level the player is playing.
	 * It is used to determine the next level when level is successful,
	 * or what level should be restarted when level is unsuccessful.
	 */
	private int currentLevel = 1;

	/**
	 * tickTimer is an auto timer that ticks maze, which will move game BOTs around.
	 */
	private Timer tickTimer = null;

	private GameInfoModel gameInfoModel = new GameInfoModel(); ////

	/**
	 * Starts the game from level 1.
	 */
	public void startLevel1() {
		startLevel(currentLevel = 1);
	}

	/**
	 * Saves the current level state into a file, so next time the player can start from exactly where they quit.
	 */
	public void saveLevel() {
		SaveGame sg = new SaveGame(maze, currentLevel);
		sg.save(SAVE_LEVEL_FILE_NAME, 30);
	}

	/**
	 * Start game from last save point.
	 */
	public void resumeSavedGame() {
		
	}

	public void startLastUnfinishedGame() {
		startLevel(currentLevel);
	}

	public void startNextLevel() {
		startLevel(++currentLevel);
	}

	public boolean hasNextLevel() {
		return Files.exists(Paths.get("levels/level" + (currentLevel + 1) + ".json"));
	}

	public void move(Direction direction) {
		if (status != GameStatus.LEVEL_STARTED) {
			return;
		}
		try {
			maze.moveChap(direction);
			Action action = maze.getAction();
			if (action == Action.DIE) {
				status = GameStatus.LEVEL_FINISHED;
				mazeRenderer.chapDie();
				gameInfoRenderer.chapDie();
				return;
			} else if (action != Action.WALL) {
				renderMap();
				if (action == Action.ITEM || action == Action.DOOR) {
					GameInfoModel model = new GameInfoModel();
					model.setChipsLeft(maze.getChap().getTotalChips() - maze.getChap().getChips());
					Map<String, Integer> inventory = maze.getChap().getInventory();
					model.setInventory(inventory);
					gameInfoRenderer.update(model);
				} else if (action == Action.EXIT) {
					status = GameStatus.LEVEL_FINISHED;
					gameInfoRenderer.levelFinished();
				} else if (action == Action.INFO) {
					Location[][] locations = maze.getLocation();
					for (Location[] loc : locations) {
						for (Location l : loc) {
							if (l.getTile() instanceof Info) {
								Info info = (Info) l.getTile();
								System.out.println(info.getInfo());
								gameInfoRenderer.popupInfo(info.getInfo());

							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pause() {
		if (tickTimer != null) {
			tickTimer.stop();
		}
		gameInfoRenderer.pause();
		resume();
	}
	
	public void stopCountdown() { ////
		if (tickTimer != null) {
			tickTimer.stop();
		}
		gameInfoRenderer.stopCountdown();
	}

	public void timeout() {
		status = GameStatus.LEVEL_FINISHED;
	}

	private void startLevel(int level) {
		Parser parser = new Parser("levels/level" + level + ".json");
		maze.setLevel(parser.map);

		// Maze - get a new Maze from level 1.
		gameInfoModel.setLevel(level);
		gameInfoModel.setTime(LEVEL_TIME.get(level));
		gameInfoModel.setChipsLeft(maze.getChap().getTotalChips() - maze.getChap().getChips());
		gameInfoRenderer.render(gameInfoModel);
		gameInfoRenderer.countdown(() -> timeout());
		renderMap();
		status = GameStatus.LEVEL_STARTED;
		if (tickTimer == null) {
			tickTimer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (maze != null && status == GameStatus.LEVEL_STARTED) {
						maze.tick();
						if (maze.getChap() == null || maze.getChap().isDead()) {
							status = GameStatus.LEVEL_FINISHED;
							mazeRenderer.chapDie();
							gameInfoRenderer.chapDie();
						} else {
							renderMap();
						}
					} else {
						tickTimer.stop();
					}
				}
			});
		}
		tickTimer.restart();
	}

	private void renderMap() {
		mazeRenderer.setMaze(maze.getLocation());
		mazeRenderer.playerPos = maze.getChap().getLocation();
		mazeRenderer.Corner();
	}

	public void startRecording() { //
		RecordAndReplay.startNewRecord("record.json", this);
		System.out.println("start record");
	}
	
	public void saveRecording() { //
		RecordAndReplay.saveRecording(this);
		System.out.println("save recording");
	}
	
	public void loadRecording() { //
		RecordAndReplay.loadRecording("record.json", this);
		System.out.println("load recording");
	}
	
	public void stepReplay() { //
		RecordAndReplay.stepReplay(this);
		System.out.println("step replay");
	}
	
	public void autoReplay() {
		RecordAndReplay.autoReplay(this);
		System.out.println("auto replay");
	}
	
	public void oneSpeed() { //
		RecordAndReplay.setDelay(1);
		System.out.println("1.0 speed replay");
	}
	
	public void halfSpeed() { //
		RecordAndReplay.setDelay(0.5);
		System.out.println("0.5 speed replay");
	}
	
	public void twiceSpeed() { //
		RecordAndReplay.setDelay(2);
		System.out.println("2.0 speed replay");
	}
	

	public void resume() {
		if (tickTimer != null) {
			tickTimer.start();
		}
		
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public void setGameInfoRenderer(GameInfoRenderer gameInfoRenderer) {
		this.gameInfoRenderer = gameInfoRenderer;
	}

	public void setMazeRenderer(renderer mazeRenderer) {
		this.mazeRenderer = mazeRenderer;
	}

	public void setMaze(Maze m) { //
		this.maze = m;
	}
	
	public void setInfoModel(GameInfoModel model) { ////
		this.gameInfoModel = model;
	}
	
	
	public Maze getMaze() { //
		return this.maze;
	}
	
	public renderer getRenderer() { //
		return this.mazeRenderer;
	}
	
	public GameInfoRenderer getInfoRenderer() { ////
		return this.gameInfoRenderer;
	}
	
	public GameInfoModel getInfoModel() { ////
		return this.gameInfoModel;
	}

	/**
	 * 
	 * Getter of GameStatus for testing
	 *
	 */
	public GameStatus getGameStatus() {
		return this.status;
	}
}
