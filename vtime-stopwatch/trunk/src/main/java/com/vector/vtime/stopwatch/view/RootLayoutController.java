package com.vector.vtime.stopwatch.view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.vector.vtime.stopwatch.BuildInfo;
import com.vector.vtime.stopwatch.StopwatchMain;
import com.vector.vtime.stopwatch.config.ConfigHandler;

public class RootLayoutController implements Initializable {

	private StopwatchMain mainApp;

	@FXML
	private Label currentTime;

	@FXML
	private Label currentUser;
	
	@FXML
	private MenuItem menuStart;
	
	@FXML
	private MenuItem menuPause;

	@FXML
	private MenuItem menuResume;
	
	@FXML
	private MenuItem menuStop;
	
	public void initialize(URL url, ResourceBundle rb) {
		setCurrentUser();
		setClock();
	}

	private void setCurrentUser() {
		currentUser.setText(System.getProperty("user.name"));
	}

	private void setClock() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
				new EventHandler<ActionEvent>() {

					public void handle(ActionEvent arg0) {
						currentTime.setText(LocalDateTime.now().format(
								StopwatchMain.DATE_TIME_FORMATTER));
					}
				}), new KeyFrame(Duration.seconds(1)));

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	/**
	 * Opens an about dialog.
	 */
	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("vTime Stopwatch");
		alert.setHeaderText("About");
		alert.setContentText("Stopwatch for vTime\r\n" +
				"Vector Informatik GmbH\r\n" + "Version: "
				+ BuildInfo.getVersion() + "\r\n 2016 All rights reserved");

		alert.showAndWait();
	}

	/**
	 * Closes the application.
	 */
	@FXML
	public void handleClose(Event event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Close");
		alert.setHeaderText("Are you sure?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			ConfigHandler.save();
			Platform.exit();
		} else {
			event.consume();
		}
	}

	/**
	 * Open the config dialog.
	 */
	@FXML
	private void openConfig() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/ConfigDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the person into the controller.
			ConfigDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.loadConfig();
			controller.setMainApp(mainApp);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void handleStart() {
		menuStart.setDisable(true);
		menuStop.setDisable(false);
		menuPause.setDisable(false);
		mainApp.getContentController().start();
	}
	
	@FXML
	private void handlePause() {
		menuPause.setVisible(false);
		menuResume.setVisible(true);
		mainApp.getContentController().pause();
	}
	
	@FXML
	private void handleResume() {
		menuPause.setVisible(true);
		menuResume.setVisible(false);
		mainApp.getContentController().resume();
	}
	
	@FXML
	private void handleStop(Event evt) {
		menuStart.setDisable(false);
		menuPause.setDisable(true);
		menuStop.setDisable(true);
		mainApp.getContentController().stop();
	}
	
	public void setMainApp(StopwatchMain mainApp) {
		this.mainApp = mainApp;
	}
}
