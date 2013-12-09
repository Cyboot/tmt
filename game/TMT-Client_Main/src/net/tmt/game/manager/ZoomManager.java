package net.tmt.game.manager;

import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;

import org.lwjgl.input.Keyboard;

public class ZoomManager {
	/**
	 * the minimum targeted resolution for the game (lower will work too of
	 * course, but the game becomes smaller on screen)
	 */
	public static final int		RECOMMENDED_RESOLUTION_WIDTH	= 1600;
	public static final int		RECOMMENDED_RESOLUTION_HEIGTH	= 900;

	public static final double	ZOOM_001						= 0.01;
	public static final double	ZOOM_005						= 0.05;
	public static final double	ZOOM_010						= 0.1;
	public static final double	ZOOM_025						= 0.25;
	public static final double	ZOOM_050						= 0.5;
	public static final double	ZOOM_075						= 0.75;
	public static final double	ZOOM_100						= 1;
	public static final double	ZOOM_150						= 1.5;
	public static final double	ZOOM_200						= 2;
	public static final double	ZOOM_400						= 4;

	private static final double	ZOOM_SPEED						= 1;
	private static final double	MIN_ZOOM_DIFF					= 0.95;
	private static final double	DEFAULT_ZOOM					= 1;

	private static double		windowZoomFactor				= 1;
	private static boolean		isFixed							= false;
	private static double		zoom							= DEFAULT_ZOOM;
	private static double		fixedZoom						= DEFAULT_ZOOM;

	static void update(final double delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_0))
			setFixedZoom(ZOOM_001);
		if (Keyboard.isKeyDown(Keyboard.KEY_1))
			setFixedZoom(ZOOM_005);
		if (Keyboard.isKeyDown(Keyboard.KEY_2))
			setFixedZoom(ZOOM_010);
		if (Keyboard.isKeyDown(Keyboard.KEY_3))
			setFixedZoom(ZOOM_025);
		if (Keyboard.isKeyDown(Keyboard.KEY_4))
			setFixedZoom(ZOOM_050);
		if (Keyboard.isKeyDown(Keyboard.KEY_5))
			setFixedZoom(ZOOM_100);
		if (Keyboard.isKeyDown(Keyboard.KEY_6))
			setFixedZoom(ZOOM_200);
		if (Keyboard.isKeyDown(Keyboard.KEY_7))
			setFixedZoom(ZOOM_400);

		if (zoom != fixedZoom) {
			adjustZoom(delta);
		}
		Graphics.getInstance().setScale(zoom);
	}

	/**
	 * adjust zoom slowly to the targetzoom
	 * 
	 * @param delta
	 */
	private static void adjustZoom(final double delta) {
		if (fixedZoom < zoom) {
			zoom *= 1 - ZOOM_SPEED * delta;
		} else {
			zoom *= 1 + ZOOM_SPEED * delta;
		}
		if (Math.min(fixedZoom / zoom, zoom / fixedZoom) > MIN_ZOOM_DIFF)
			zoom = fixedZoom;

	}

	/**
	 * set zoom to the given target
	 * 
	 * @param fixedZoom
	 */
	public static void setFixedZoom(final double fixedZoom) {
		ZoomManager.fixedZoom = fixedZoom * windowZoomFactor;
		isFixed = true;
	}

	/**
	 * resets zoom to the default
	 */
	public static void resetZoom() {
		zoom = DEFAULT_ZOOM * windowZoomFactor;
		fixedZoom = DEFAULT_ZOOM * windowZoomFactor;
		isFixed = false;
	}

	public static double getZoom() {
		return zoom;
	}

	public static double getZoomInverse() {
		return 1 / zoom;
	}

	/**
	 * gets the width of the displayed game. <br>
	 * <i>Use this as default instead of<b> GameEngine.WIDTH</b></i>
	 * 
	 * @return the window width multiplied by zoomfactor
	 */
	public static int getWidthZoomed() {
		return (int) (GameEngine.WIDTH * getZoomInverse());
	}

	/**
	 * gets the height of the displayed game. <br>
	 * <i>Use this as default instead of<b> GameEngine.HEIGHT</b></i>
	 * 
	 * @return the window height multiplied by zoomfactor
	 */
	public static int getHeightZoomed() {
		return (int) (GameEngine.HEIGHT * getZoomInverse());
	}

	/**
	 * set the Default zoom corresponding to the window-size
	 */
	public static void init() {
		windowZoomFactor = Math.min((double) GameEngine.WIDTH / RECOMMENDED_RESOLUTION_WIDTH,
				(double) GameEngine.HEIGHT / RECOMMENDED_RESOLUTION_HEIGTH);
		zoom = windowZoomFactor;
		fixedZoom = windowZoomFactor;
	}
}
