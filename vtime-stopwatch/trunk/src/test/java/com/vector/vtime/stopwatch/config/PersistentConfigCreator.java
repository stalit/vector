package com.vector.vtime.stopwatch.config;

import java.io.File;

public class PersistentConfigCreator {

	public static void main(String[] args) {
		PersistentConfig config = Config.getConfig();
		config.setSecondsAfterWork(2);
		config.setSecondsBeforeWork(2);
		config.setOnTop(false);
		File f = new File("E:\\temp\\config.xml");
		ConfigHandler.save(config, f);
	}
}
