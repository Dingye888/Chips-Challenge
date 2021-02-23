package nz.ac.vuw.ecs.swen225.gp20.application;

import java.util.Map;

/**
 * 
 * @author Wang Conglang 300472254
 *
 */
public class GameInfoModel {
	private Integer level;
	private Integer time;
	private Integer chipsLeft;
	private Map<String, Integer> inventory;
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Integer getChipsLeft() {
		return chipsLeft;
	}
	public void setChipsLeft(Integer chipsLeft) {
		this.chipsLeft = chipsLeft;
	}
	public Map<String, Integer> getInventory() {
		return inventory;
	}
	public void setInventory(Map<String, Integer> inventory) {
		this.inventory = inventory;
	}
	
}
