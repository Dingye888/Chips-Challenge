package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp20.maze.Direction;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.RecordAndReplay;
import nz.ac.vuw.ecs.swen225.gp20.renderer.renderer;

/**
 * 
 * @author Wang Conglang 300472254
 *
 */
public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	private GameController controller = new GameController();
	private renderer mazeRenderer;
	private GameInfoView gameInfoView = new GameInfoView();
	private Menu menu = new Menu();

	public Main() {
		init();
		equiptController();
		setVisible(true);
	}

	private void init() {
		setTitle("Chap's Challenge");
		setSize(800, 600);
		setJMenuBar(menu);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		JPanel mazeContainer = new JPanel();
		mazeContainer.setBackground(Color.BLACK);
		mazeRenderer = new renderer(this);
		mazeContainer.add(mazeRenderer.getCanvas());
		this.add(mazeContainer);
		JPanel infoContainer = new JPanel();
		infoContainer.setBackground(Color.BLACK);
		infoContainer.add(gameInfoView);
		infoContainer.setPreferredSize(new Dimension(270, 600));
		this.add(infoContainer, BorderLayout.EAST);
		this.setFocusable(true);
		this.addKeyListener(new KeyAdapter() {
			private AtomicBoolean isBusy = new AtomicBoolean(false);
			@Override
			public void keyPressed(KeyEvent e) {
				if (isBusy.compareAndSet(false, true)) {
					SwingUtilities.invokeLater(() -> {
						switch (e.getKeyCode()) {
						case KeyEvent.VK_UP:
							controller.move(Direction.NORTH);
							break;
						case KeyEvent.VK_DOWN:
							controller.move(Direction.SOUTH);
							break;
						case KeyEvent.VK_LEFT:
							controller.move(Direction.WEST);
							break;
						case KeyEvent.VK_RIGHT:
							controller.move(Direction.EAST);
							break;
						default:
							break;
						}
						if(RecordAndReplay.isRecording()) { //
							RecordAndReplay.addAction(e);
						}
						isBusy.set(false);
					});
				}
			}
		});
	}

	private void equiptController() {
		menu.setController(controller);
		gameInfoView.setController(controller);
		controller.setMazeRenderer(mazeRenderer);
		GameInfoRendererImpl gameInfoRenderer = new GameInfoRendererImpl();
		gameInfoRenderer.setView(gameInfoView);
		controller.setGameInfoRenderer(gameInfoRenderer);
		menu.setWindow(this);
		menu.initHelpPage();
		gameInfoView.setWindow(this);
	}
	public static void main(String[] args) {
		   Main m = new Main();
		   
	   }

	
	/**
	 * 
	 * Getter of GameController for testing
	 *
	 */
	public GameController getGameController() {
		return this.controller;
	}
	
	/**
	 * 
	 * Getter of GameInfoView for testing
	 *
	 */
	public GameInfoView getGameInfoView() {
		return this.gameInfoView;
	}
}
