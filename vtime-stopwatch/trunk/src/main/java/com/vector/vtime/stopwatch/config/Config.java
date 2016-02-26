package com.vector.vtime.stopwatch.config;


public class Config {

	/**
	 * if the window should be on Top of not
	 */
	private static boolean onTop;
	
	/**
	 * The seconds of work added after work has been stopped
	 */
	private static int secondsAfterWork;
	
	/**
	 * The seconds of work added before work is started
	 */
	private static int secondsBeforeWork;

	public static boolean isOnTop() {
		return onTop;
	}

	public static void setOnTop(boolean onTop) {
		Config.onTop = onTop;
	}

	public static int getSecondsAfterWork() {
		return secondsAfterWork;
	}

	public static void setSecondsAfterWork(int secondsAfterWork) {
		Config.secondsAfterWork = secondsAfterWork;
	}

	public static int getSecondsBeforeWork() {
		return secondsBeforeWork;
	}

	public static void setSecondsBeforeWork(int secondsBeforeWork) {
		Config.secondsBeforeWork = secondsBeforeWork;
	}

	public static PersistentConfig getConfig() {
		PersistentConfig config = new PersistentConfig();
		config.setSecondsAfterWork(getSecondsAfterWork());
		config.setSecondsBeforeWork(getSecondsBeforeWork());
		config.setOnTop(isOnTop());
		return config;
	}
	
	public static void setConfig(PersistentConfig config) {
		setOnTop(config.isOnTop());
		setSecondsAfterWork(config.getSecondsAfterWork());
		setSecondsBeforeWork(config.getSecondsBeforeWork());
	}
}
