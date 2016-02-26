package com.vector.vtime.stopwatch.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.CodeSource;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.vector.vtime.stopwatch.StopwatchMain;

public class ConfigHandler {

	private static final String CONFIG_FILE_NAME = "config.xml";
	private static final String DEFAULT_CONFIG_FILE = "config/" + CONFIG_FILE_NAME;
	private static Marshaller marshaller;
	private static Unmarshaller unmarshaller;

	static {
		try {
			JAXBContext ctx = JAXBContext.newInstance(PersistentConfig.class);
			marshaller = ctx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			unmarshaller = ctx.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void save() {
		File file = getConfigFile();
		save(Config.getConfig(), file);
	}

	public static void save(PersistentConfig config, File file) {
		try {
			marshaller.marshal(config, file);
			System.out.println("Saved config to " + file.getAbsolutePath());
		} catch (JAXBException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save config");
			alert.setContentText("Could not save config to file:\n"
					+ file.getPath() + " Error: " + e.getMessage());

			alert.showAndWait();
		}
	}

	private static File getConfigFile() {
		File configFile = null;
		
		configFile = new File(getWorkDir(), CONFIG_FILE_NAME);
		if (null == configFile || !configFile.exists()) {
			System.out.println("Unable to load config. Loading default");
			configFile = getDefaultConfigFile(configFile);
		}
		return configFile;
	}

	private static File getDefaultConfigFile(File newFile) {
		File configFile = newFile;
		
		try (InputStream is = ConfigHandler.class.getResourceAsStream("/" + DEFAULT_CONFIG_FILE)){
			Path path = Paths.get(configFile.getAbsolutePath());
			System.out.println(path);
			Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return configFile;
	}

	public static void load() {
		File configFile = getConfigFile();
		PersistentConfig config = null;
		try {
			if (null != configFile && configFile.exists()) {
				System.out.println("Loading config file " + configFile.getAbsolutePath());
				config = (PersistentConfig) unmarshaller.unmarshal(configFile);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		if (null == config) {
			config = new PersistentConfig();
		}
		Config.setConfig(config);
	}
	
	private static String getWorkDirPath() {
		CodeSource cs = StopwatchMain.class.getProtectionDomain().getCodeSource();
		return cs.getLocation().getPath();
	}
	
	private static File getWorkDir() {
		return new File(getWorkDirPath()).getParentFile();
	}
}
