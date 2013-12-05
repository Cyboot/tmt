package net.tmt.util;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

public class ColorUtil {
	public static final ReadableColor	TRANSPARENT			= new Color(0, 0, 0, 0);
	public static final ReadableColor	BLACK_ALPHA_50		= ColorUtil.blackAlpha(50);
	public static final ReadableColor	BLACK_ALPHA_100		= ColorUtil.blackAlpha(100);
	public static final ReadableColor	BLACK_ALPHA_150		= ColorUtil.blackAlpha(150);
	public static final ReadableColor	BLACK_ALPHA_200		= ColorUtil.blackAlpha(200);
	public static final ReadableColor	WHITE_ALPHA_50		= ColorUtil.whiteAlpha(50);
	public static final ReadableColor	WHITE_ALPHA_100		= ColorUtil.whiteAlpha(100);
	public static final ReadableColor	WHITE_ALPHA_150		= ColorUtil.whiteAlpha(150);
	public static final ReadableColor	WHITE_ALPHA_200		= ColorUtil.whiteAlpha(200);

	// primary Color for Gui
	public static final ReadableColor	GUI_CYAN			= new Color(0, 150, 200);
	public static final ReadableColor	GUI_CYAN_DARK_100	= new Color(0, 100, 150);
	public static final ReadableColor	GUI_CYAN_DARK_50	= new Color(0, 50, 90);
	public static final ReadableColor	GUI_CYAN_DARK_30	= new Color(0, 30, 50);

	public static final ReadableColor	GUI_ORANGE			= new Color(255, 150, 0);
	public static final ReadableColor	GUI_ORANGE_BRIGHT	= new Color(255, 200, 100);

	/**
	 * returns a brigher color. <b>Attention</b> Modifies the given color
	 * parameter
	 * 
	 * @param color
	 * @param factor
	 * @return
	 */
	public static void brighter(final Color color, final double factor) {
		color.setRed(MathUtil.clamp((int) (color.getRed() * factor), 0, 255));
		color.setGreen(MathUtil.clamp((int) (color.getGreen() * factor), 0, 255));
		color.setBlue(MathUtil.clamp((int) (color.getBlue() * factor), 0, 255));
	}

	/**
	 * returns a darker color. <b>Attention</b> Modifies the given color
	 * parameter
	 * 
	 * @param color
	 * @param factor
	 * @return
	 */
	public static void darker(final Color color, final double factor) {
		brighter(color, 1 / factor);
	}

	/**
	 * a black Color with the given Alpha value
	 * 
	 * @param alpha
	 * @return
	 */
	public static ReadableColor blackAlpha(final int alpha) {
		return new Color(0, 0, 0, alpha);
	}

	/**
	 * a black Color with the given Alpha value
	 * 
	 * @param alpha
	 * @return
	 */
	public static ReadableColor whiteAlpha(final int alpha) {
		return new Color(255, 255, 255, alpha);
	}
}
