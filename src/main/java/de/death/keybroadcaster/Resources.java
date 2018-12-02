package de.death.keybroadcaster;

import java.net.URL;

public class Resources {

	public enum RESOURCE {
		/** LAYOUTS **/
		// main
		FXML_VIEW_LAYOUT("ViewLayout.fxml"),

		// icon
		ICON_MAIN("icon.png"),

		// Mouse images
		IMAGE_MOUSE_DEFAULT("defaultMouse.png"), //
		IMAGE_MOUSE_LEFT("leftMouse.png"), //
		IMAGE_MOUSE_CENTER("centerMouse.png"), //
		IMAGE_MOUSE_RIGHT("rightMouse.png"), //
		IMAGE_MOUSE_WHEEL_UP("scrollUp.png"), //
		IMAGE_MOUSE_WHEEL_DOWN("scrollDown.png"),//

		;
		String resourceLocation;

		private RESOURCE(String resourceLocation) {
			this.resourceLocation = resourceLocation;
		}

		public String getResourceLocation() {
			return resourceLocation;
		}
	}

	public static URL getResource(RESOURCE resource) {
		return ClassLoader.getSystemResource(resource.getResourceLocation());
	}
}
