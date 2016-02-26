package com.vector.vtime.stopwatch.config;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stopwatchConfig")
public class PersistentConfig {

	/**
	 * if the window should be on Top of not
	 */
	private boolean onTop;
	
	/**
	 * The seconds of work added after work has been stopped
	 */
	private int secondsAfterWork;
	
	/**
	 * The seconds of work added before work is started
	 */
	private int secondsBeforeWork;

	public boolean isOnTop() {
		return onTop;
	}

	public void setOnTop(boolean onTop) {
		this.onTop = onTop;
	}

	public int getSecondsAfterWork() {
		return secondsAfterWork;
	}

	public void setSecondsAfterWork(int secondsAfterWork) {
		this.secondsAfterWork = secondsAfterWork;
	}

	public int getSecondsBeforeWork() {
		return secondsBeforeWork;
	}

	public void setSecondsBeforeWork(int secondsBeforeWork) {
		this.secondsBeforeWork = secondsBeforeWork;
	}
}
