package de.death.keybroadcaster;

import java.io.IOException;

import de.death.keybroadcaster.Resources.RESOURCE;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewController {
	@FXML
	private Pane pane;

	@FXML
	private Label label;
	@FXML
	private ImageView imageView;

	Image mouseDefaultClickImage;
	Image mouseLeftClickImage;
	Image mouseCenterClickImage;
	Image mouseRightClickImage;
	Image mouseWheelUpImage;
	Image mouseWheelDownImage;

	class Delta {
		double x, y;
	}

	Delta dragDelta = new Delta();
	boolean wasDragged = false;

	@FXML
	private void initialize() throws IOException {
		mouseDefaultClickImage = new Image(Resources.getResource(RESOURCE.IMAGE_MOUSE_DEFAULT).openStream());
		mouseLeftClickImage = new Image(Resources.getResource(RESOURCE.IMAGE_MOUSE_LEFT).openStream());
		mouseCenterClickImage = new Image(Resources.getResource(RESOURCE.IMAGE_MOUSE_CENTER).openStream());
		mouseRightClickImage = new Image(Resources.getResource(RESOURCE.IMAGE_MOUSE_RIGHT).openStream());
		mouseWheelUpImage = new Image(Resources.getResource(RESOURCE.IMAGE_MOUSE_WHEEL_UP).openStream());
		mouseWheelDownImage = new Image(Resources.getResource(RESOURCE.IMAGE_MOUSE_WHEEL_DOWN).openStream());

		// Drag stage
		pane.setOnMousePressed((mouseEvent) -> {
			// record a delta distance for the drag and drop operation.
			dragDelta.x = stage.getX() - mouseEvent.getScreenX();
			dragDelta.y = stage.getY() - mouseEvent.getScreenY();
			wasDragged = false;
		});

		pane.setOnMouseDragged((mouseEvent) -> {
			stage.setX(mouseEvent.getScreenX() + dragDelta.x);
			stage.setY(mouseEvent.getScreenY() + dragDelta.y);
			wasDragged = true;
		});

	}

	public void setKeys(final String msg) {
		Platform.runLater(() -> {
			label.setText(msg);
		});
	}

	public void setMouse(final int code) {
		Platform.runLater(() -> {
			switch (code) {
			case 0:
				imageView.setImage(mouseDefaultClickImage);
				break;
			case 1:
				imageView.setImage(mouseLeftClickImage);
				break;
			case 2:
				imageView.setImage(mouseRightClickImage);
				break;
			case 3:
				imageView.setImage(mouseCenterClickImage);
				break;
			case 4:
				imageView.setImage(mouseWheelDownImage);
				break;
			case 5:
				imageView.setImage(mouseWheelUpImage);
				break;
			}
		});
	}

	Stage stage;

	public void setStage(Stage primaryStage) {
		this.stage = primaryStage;
	}

}
