package net.tmt.util;

import org.lwjgl.util.Color;

public class ColorUtil {

	/**
	 * returns a brigher color. <b>Attention</b> Modifies the given color
	 * parameter
	 * 
	 * @param color
	 * @param factor
	 * @return
	 */
	public static void brighter(final Color color, final double factor) {
		color.setRed((int) (color.getRed() * factor));
		color.setGreen((int) (color.getGreen() * factor));
		color.setBlue((int) (color.getBlue() * factor));
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
}
