package de.death.keybroadcaster;

import java.util.logging.Logger;

import de.death.keybroadcaster.Resources.RESOURCE;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class KeyBroadcaster extends Application {

	private static Logger logger = Logger.getLogger(KeyBroadcaster.class.getName());

	private ViewController mainController = null;
	private GlobalScreenListener globalScreenListener = null;

	@Override
	public void start(Stage primaryStage) {
		globalScreenListener = new GlobalScreenListener();

		try {
			Pane loginLayout;

			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Resources.getResource(RESOURCE.FXML_VIEW_LAYOUT));
			loginLayout = loader.load();
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			// Show the scene containing the root layout.
			Scene loginScene = new Scene(loginLayout, Color.TRANSPARENT);

			// Image(Resources.getResource(RESOURCE.ICON_MAIN).toString()));

			primaryStage.setTitle("KeepTime");
			primaryStage.setScene(loginScene);
			// Give the controller access to the main app.
			primaryStage.setAlwaysOnTop(true);
			mainController = loader.getController();
			mainController.setStage(primaryStage);
			globalScreenListener.setViewController(mainController);
			// primaryStage.getIcons().add(new
			// Image(Resources.getResource(RESOURCE.APP_ICON).toString()));
			primaryStage.show();

		} catch (Exception e) {
			logger.warning("Could not load main layout" + e);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
