package com.vector.vtime.stopwatch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BuildInfo {

	private static final String VERSION = "project.version";
	private static final String GROUP_ID = "project.groupId";
	private static final String ARTIFACT_ID = "project.artifactId";
	private static final String NAME = "project.name";
	private static Properties props;
	
	static {
		InputStream resourceStream = BuildInfo.class.getResourceAsStream("/build-info.properties");
		props = new Properties();
		try {
			props.load(resourceStream);
		} catch (IOException io) {
			System.err.println("Unable to read build info");
		}
	}
	
	public static String getVersion() {
		return props.getProperty(VERSION);
	}
	public static String getGroupId() {
		return props.getProperty(GROUP_ID);
	}
	public static String getArtefactId() {
		return props.getProperty(ARTIFACT_ID);
	}
	public static String getName() {
		return props.getProperty(NAME);
	}
}
