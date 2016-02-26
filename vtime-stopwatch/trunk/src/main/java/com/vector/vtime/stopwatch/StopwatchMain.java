package com.vector.vtime.stopwatch;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.vector.vtime.stopwatch.config.ConfigHandler;
import com.vector.vtime.stopwatch.view.ContentLayoutController;
import com.vector.vtime.stopwatch.view.RootLayoutController;

public class StopwatchMain extends Application {

	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern("dd.MM.uuuu HH:mm:ss");
	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	private BorderPane rootLayout;
	private Stage primaryStage;
	private ContentLayoutController contentController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		initRootLayout();
		initContentLayout();
		ConfigHandler.load();
	}

	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/RootLayout.fxml"));
			rootLayout = loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			final RootLayoutController controller = loader
					.getController();
			controller.setMainApp(this);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				public void handle(WindowEvent event) {
					controller.handleClose(event);
				}
			});
			primaryStage.setTitle(BuildInfo.getName() + " v" + BuildInfo.getVersion());
			primaryStage.getIcons().add(
					new Image("/images/stopwatch.png"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initContentLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/ContentLayout.fxml"));
			AnchorPane personOverview = loader.load();

			rootLayout.setCenter(personOverview);

			contentController = loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * The main() method is ignored in correctly deployed JavaFX application.
	 * main() serves only as fallback in case the application can not be
	 * launched through deployment artifacts, e.g., in IDEs with limited FX
	 * support. NetBeans ignores main().
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setOnTop(boolean onTop) {
		primaryStage.setAlwaysOnTop(onTop);
	}

	public ContentLayoutController getContentController() {
		return contentController;
	}
}
