package de.death.keybroadcaster;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class GlobalScreenListener implements NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener {

	private Runnable resetMouseRunnable;
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private ScheduledFuture<?> schedule;

	public GlobalScreenListener() {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		setGlobalScreenLogLevel();

		GlobalScreen.addNativeKeyListener(this);
		GlobalScreen.addNativeMouseListener(this);
		GlobalScreen.addNativeMouseMotionListener(this);
		GlobalScreen.addNativeMouseWheelListener(this);

		resetMouseRunnable = () -> {
			pressedMouse = 0;
			updateText();
		};
	}

	private void setGlobalScreenLogLevel() {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		logger.setUseParentHandlers(false);
	}

	public void setViewController(ViewController viewController) {
		this.viewController = viewController;
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		final String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
		System.out.println("Key Pressed: " + keyText);
		if (!pressedKeys.contains(keyText)) {
			pressedKeys.add(keyText);
			updateText();
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		final String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
		pressedKeys.remove(keyText);
		System.out.println("Key Released: " + keyText);
		updateText();
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		cancelMouseResetIfActive();
		final int button = e.getButton();
		pressedMouse = button;
		System.out.println("Mouse Pressed: " + button);
		updateText();
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		final int button = e.getButton();
		pressedMouse = 0;
		System.out.println("Mouse Released: " + button);
		updateText();
	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
		cancelMouseResetIfActive();

		final int wheelRotation = e.getWheelRotation();
		System.out.println("Mouse Wheel Moved: " + wheelRotation);
		if (wheelRotation > 0) {
			pressedMouse = 4;
		} else {
			pressedMouse = 5;
		}
		updateText();

		schedule = executor.schedule(resetMouseRunnable, 250, TimeUnit.MILLISECONDS);
	}

	public void cancelMouseResetIfActive() {
		if (schedule != null && !schedule.isDone()) {
			schedule.cancel(false);
		}
	}

	List<String> pressedKeys = new ArrayList<>();
	int pressedMouse = 0;
	ViewController viewController;

	private void updateText() {
		String msg = String.join(" + ", pressedKeys);
		if (viewController != null) {
			viewController.setKeys(msg);
			viewController.setMouse(pressedMouse);
		}

	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		// Not used
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		// Not used
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// Not used
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		// Not used
	}
}
