package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import nz.ac.vuw.ecs.swen225.gp20.persistence.SaveGame;

/**
 * 
 * @author Wang Conglang 300472254
 *
 */
public class GameInfoRendererImpl implements GameInfoRenderer {
	private GameInfoView view;
	private Timer timer;
	@Override
	public void render(GameInfoModel model) {
		int level = model.getLevel();
		int time = model.getTime();
		view.setLevelText("" + level);
		view.setTimeText("" + time);
		if (model.getChipsLeft() != null) {
			view.setChipsLeftText(model.getChipsLeft().toString());
		}
		if (model.getInventory() != null) {
			view.setKeysCollected(model.getInventory());
		} else {
			view.setKeysCollected(new HashMap<>());
		}
	}
	@Override
	public void update(GameInfoModel model) {
		if (model.getChipsLeft() != null) {
			view.setChipsLeftText(model.getChipsLeft().toString());
		}
		if (model.getInventory() != null) {
			view.setKeysCollected(model.getInventory());
		}
	}
	@Override
	public void countdown(Runnable timeoutProcedure) {
		if (timer != null) {
			timer.stop();
			timer = null;
		}
		CountdownAction actionListener = new CountdownAction(Integer.parseInt(view.getTimeText()), timeoutProcedure);
		timer = new Timer(1000, actionListener);
		timer.start();
	}

	@Override
	public void pause() {
		if (timer != null) {
			timer.stop();
			view.showPauseDialog();
			timer.start();
		}
	}
	
	@Override
	public void stopCountdown() { ////
		if (timer != null) {
			timer.stop();
		}
	}

	@Override
	public void levelFinished() {
		if (timer != null) {
			timer.stop();
			timer = null;
		}
		view.showLevelSuccessfulDialog();
	}

	@Override
	public void chapDie() {
		if (timer != null) {
			timer.stop();
			timer = null;
		}
		view.showChapDieDialog();
	}

	@Override
	public void popupInfo(String info) {
		view.popupInfo(info);
	}

	public GameInfoView getView() {
		return view;
	}
	public void setView(GameInfoView view) {
		this.view = view;
	}

	private class CountdownAction implements ActionListener {
		private AtomicInteger seconds;
		private Runnable timeoutProcedure;
		public CountdownAction(int seconds, Runnable timeoutProcedure) {
			this.seconds = new AtomicInteger(seconds);
			this.timeoutProcedure = timeoutProcedure;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			int timeleft = seconds.addAndGet(-1);
			if (timeleft >= 0) {
				SwingUtilities.invokeLater(() -> {
					GameInfoRendererImpl.this.view.setTimeText("" + timeleft);
				});
			} else {
				timer.stop();
				timer = null;
				timeoutProcedure.run();
				view.showLevelFailedDialog();
			}
		}
	}
}
