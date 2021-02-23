package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Wang Conglang 300472254
 *
 */
public class InventoryBag extends JPanel {
	private static final long serialVersionUID = 1L;
	private Map<String, Integer> inventory;
	private JLabel[] bags;
	public InventoryBag() {
		this.setLayout(new GridLayout(2, 4, 1, 1));
		bags = new JLabel[8];
		for (int i = 0; i < bags.length; i++) {
			bags[i] = new JLabel();
			bags[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
			bags[i].setHorizontalTextPosition(JLabel.CENTER);
			bags[i].setVerticalTextPosition(JLabel.CENTER);
			this.add(bags[i]);
		}
	}

	public String getText() {
		return inventory == null ? null : inventory.toString();
	}

	public void setInventory(Map<String, Integer> inventory) {
		this.inventory = inventory;
		for (JLabel l : bags) {
			l.setIcon(null);
			l.setText("");
		}
		int i = 0;
		for (String key : inventory.keySet()) {
			if ("KEY RED".equalsIgnoreCase(key)) {
				Image keyImg = new ImageIcon(getClass().getResource("/red_key.png")).getImage().getScaledInstance(49, 49, Image.SCALE_DEFAULT);
				bags[i].setIcon(new ImageIcon(keyImg));
			} else if ("KEY BLUE".equalsIgnoreCase(key)) {
				Image keyImg = new ImageIcon(getClass().getResource("/blue_key.png")).getImage().getScaledInstance(49, 49, Image.SCALE_DEFAULT);
				bags[i].setIcon(new ImageIcon(keyImg));
			} else if ("KEY YELLOW".equalsIgnoreCase(key)) {
				Image keyImg = new ImageIcon(getClass().getResource("/yellow_key.png")).getImage().getScaledInstance(49, 49, Image.SCALE_DEFAULT);
				bags[i].setIcon(new ImageIcon(keyImg));
			} else if ("KEY GREEN".equalsIgnoreCase(key)) {
				Image keyImg = new ImageIcon(getClass().getResource("/green_key.png")).getImage().getScaledInstance(49, 49, Image.SCALE_DEFAULT);
				bags[i].setIcon(new ImageIcon(keyImg));
			}
			bags[i].setText("<html><span style=\"color: white;\"><br/><br/>" + inventory.get(key).toString() + "</span></html>");
			i++;
		}
	}

}
