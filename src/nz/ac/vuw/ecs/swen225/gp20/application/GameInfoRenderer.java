package nz.ac.vuw.ecs.swen225.gp20.application;

/**
 * 
 * @author Wang Conglang 300472254
 *
 */
public interface GameInfoRenderer {
	void render(GameInfoModel model);

	void pause();

	void update(GameInfoModel model);

	/**
	 * Runnable doesn't imply that it is used for a thread.
	 * Runnable only means it is a FunctionalInterface which takes no argument and returns no value.
	 * @param timeoutProcedure
	 */
	void countdown(Runnable timeoutProcedure);

	void levelFinished();

	void chapDie();

	void popupInfo(String info);

	void stopCountdown();

}
