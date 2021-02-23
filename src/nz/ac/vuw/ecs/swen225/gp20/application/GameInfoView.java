package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

/**
 * 
 * @author Wang Conglang 300472254
 *
 */
public class GameInfoView extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final String LABEL_STYLE = " style=\"color: red; font-size: 18px;\"";
	private static final int TEXT_FIELD_HEIGHT = 40;
	private Main window;
	private GameController controller;
	private JTextField levelText;
	private JTextField timeText;
	private JTextField chipsLeftText;
	private InventoryBag keysCollected;
	public GameInfoView() {
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(250, 500));
		setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel levelLabel = new JLabel("<html><br/><br/><div"+LABEL_STYLE+">LEVEL</div></html>");
		this.add(levelLabel);
		levelText = new JTextField("1");
		levelText.setEditable(false);
		levelText.setPreferredSize(new Dimension(250, TEXT_FIELD_HEIGHT));
		levelText.setMaximumSize(levelText.getPreferredSize());
		levelText.setHorizontalAlignment(SwingConstants.RIGHT);
		levelText.setFont(new Font(Font.SERIF, Font.BOLD, 28));
		levelText.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK), "");
		this.add(levelText);
		JLabel timeLabel = new JLabel("<html><br/><br/><div"+LABEL_STYLE+">TIME</div></html>");
		this.add(timeLabel);
		timeText = new JTextField("100");
		timeText.setEditable(false);
		timeText.setPreferredSize(new Dimension(250, TEXT_FIELD_HEIGHT));
		timeText.setMaximumSize(levelText.getPreferredSize());
		timeText.setHorizontalAlignment(SwingConstants.RIGHT);
		timeText.setFont(new Font(Font.SERIF, Font.BOLD, 28));
		timeText.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK), "");
		this.add(timeText);
		JLabel chipsLeftLabel = new JLabel("<html><br/><br/><div"+LABEL_STYLE+">CHIPS<br/>LEFT</div></html>");
		this.add(chipsLeftLabel);
		chipsLeftText = new JTextField("10");
		chipsLeftText.setEditable(false);
		chipsLeftText.setPreferredSize(new Dimension(250, TEXT_FIELD_HEIGHT));
		chipsLeftText.setMaximumSize(levelText.getPreferredSize());
		chipsLeftText.setHorizontalAlignment(SwingConstants.RIGHT);
		chipsLeftText.setFont(new Font(Font.SERIF, Font.BOLD, 28));
		chipsLeftText.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK), "");
		this.add(chipsLeftText);
		JLabel keysCollectedLabel = new JLabel("<html><br/><br/><div"+LABEL_STYLE+"> </div></html>");
		this.add(keysCollectedLabel);
		keysCollected = new InventoryBag();
		this.add(keysCollected);
	}
	public void setController(GameController controller) {
		this.controller = controller;
	}
	public void setLevelText(String value) {
		levelText.setText(value);
	}
	public String getLevelText() {
		return levelText.getText();
	}
	public String getTimeText() {
		return timeText.getText();
	}
	public void setTimeText(String timeText) {
		this.timeText.setText(timeText);
	}
	public String getChipsLeftText() {
		return chipsLeftText.getText();
	}
	public void setChipsLeftText(String chipsLeftText) {
		this.chipsLeftText.setText(chipsLeftText);
	}
	public String getKeysCollected() {
		return keysCollected.getText();
	}
	public void setKeysCollected(Map<String, Integer> inventory) {
		this.keysCollected.setInventory(inventory);
	}
	public void setWindow(Main window) {
		this.window = window;
	}
	public void showPauseDialog() {
		JOptionPane.showMessageDialog(window, "Game Paused. ESC or click on OK to resume.");
	}
	public void showLevelFailedDialog() {
		int confirm = JOptionPane.showConfirmDialog(window, "Time is up. Would you like to retry level?", "Level Failed", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (confirm == JOptionPane.YES_OPTION) {
			controller.startLastUnfinishedGame();
		}
	}
	public void showLevelSuccessfulDialog() {
		if (controller.hasNextLevel()) {
			JOptionPane.showMessageDialog(window, "Contatulations! Moving to next level!");
			controller.startNextLevel();
		} else {
			JOptionPane.showMessageDialog(window, "Contatulations! All levels clear!");
		}
	}
	public void showChapDieDialog() {
		int confirm = JOptionPane.showConfirmDialog(window, "Chap is dead. Would you like to retry level?", "Level Failed", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (confirm == JOptionPane.YES_OPTION) {
			controller.startLastUnfinishedGame();
		}
	}
	public void popupInfo(String info) {
		JLayeredPane layeredPane = window.getLayeredPane();
		JPanel panel = new JPanel();
		JLabel label = new JLabel("<html><div style=\"color: purple; font-size: 26px;\">Don't let the bugs hurt you.</div></html>");
		panel.add(label);
		panel.setPreferredSize(new Dimension(100, 100));
		layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);
//		this.add(layeredPane);
		window.revalidate();
//		this.revalidate();
	}
}
