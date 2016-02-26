package com.vector.vtime.stopwatch.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.vector.vtime.stopwatch.StopwatchMain;
import com.vector.vtime.stopwatch.config.Config;
import com.vector.vtime.stopwatch.config.ConfigHandler;

public class ConfigDialogController {

	@FXML
	private CheckBox onTop;

	@FXML
	private TextField minutesAfterWork;
	
	@FXML
	private TextField minutesBeforeWork;
	
	private Stage dialogStage;

	private StopwatchMain mainApp;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	private void cancel() {
		dialogStage.close();
	}
	
	@FXML
	private void ok() {
		if (saveConfig()) {
			ConfigHandler.save();
			mainApp.setOnTop(Config.isOnTop());
			dialogStage.close();
		}
	}

	private boolean saveConfig() {
		
		Config.setOnTop(onTop.isSelected());
		
		boolean valide = true;
		String message = null;
		
		String mawText = minutesAfterWork.getText();
		try {
			Config.setSecondsAfterWork(getInt(mawText));
		} catch (NumberFormatException e) {
			message = e.getMessage();
		}
		
		String mbwText = minutesBeforeWork.getText();
		try {
			Config.setSecondsBeforeWork(getInt(mbwText));
		} catch (NumberFormatException e) {
			message = e.getMessage();
		}
		
		if (null != message) {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(message);
			valide = false;
			alert.showAndWait();
		}
		return valide;
	}
	
	private int getInt(String value) {
		return Integer.parseInt(value);
	}

	public void loadConfig() {
		onTop.setSelected(Config.isOnTop());
		minutesAfterWork.setText(String.valueOf(Config.getSecondsAfterWork()));
		minutesBeforeWork.setText(String.valueOf(Config.getSecondsBeforeWork()));
	}

	public void setMainApp(StopwatchMain mainApp) {
		this.mainApp = mainApp;
	}
}
