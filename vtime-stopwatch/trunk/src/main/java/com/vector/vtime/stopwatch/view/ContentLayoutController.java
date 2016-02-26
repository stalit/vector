package com.vector.vtime.stopwatch.view;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.util.Duration;

import com.vector.vtime.stopwatch.StopwatchMain;

public class ContentLayoutController implements Initializable {

	private Timeline timeline;
	
	private Duration workTime;
	
	@FXML
	private Label startLabel;
	
	@FXML
	private Label stopWatchLabel;
	
	@FXML
	private ToggleButton toggleButton;
	
	@FXML 
	private Button stopButton;

	public void initialize(URL location, ResourceBundle resources) {
		stop();
	}
	
	@FXML
	public void onToggle() {
		switch (getStopwatchStatus()) {
		case STOPPED:
			start();
			break;
		case RUNNING:
			pause();
			break;
		case PAUSED:
			resume();
			break;
		default:
			break;
		}
	}
	
	public void resume() {
		timeline.play();
		toggleButton.setText("Pause");
	}
	
	private Status getStopwatchStatus() {
		if (null == timeline) {
			return Status.STOPPED;
		} else {
			return timeline.getStatus();
		}
	}

	public void start() {
		startLabel.setText(StopwatchMain.DATE_TIME_FORMATTER.format(LocalDateTime.now()));
		toggleButton.setText("Pause");
		stopButton.setDisable(false);
		workTime = new Duration(0);
		
		timeline = new Timeline(new KeyFrame(Duration.seconds(1),
				new EventHandler<ActionEvent>() {

					public void handle(ActionEvent event) {
						Duration d = ((KeyFrame)event.getSource()).getTime();
						workTime = workTime.add(d);
						long hours = (long) workTime.toHours() % 24;
						long minutes = (long) workTime.toMinutes() % 60;
						long seconds = (long) workTime.toSeconds() % 60;

						stopWatchLabel.setText((String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds)));
					}

					
				}));

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	public void pause() {
		toggleButton.setText("Resume");
		timeline.pause();
	}

	@FXML
	public void stop() {
		if (null != timeline) {
			timeline.stop();
		}
		toggleButton.setSelected(false);
		toggleButton.setText("Start");
		stopButton.setDisable(true);
		startLabel.setText("");
		stopWatchLabel.setText("00:00:00");
	}
}
