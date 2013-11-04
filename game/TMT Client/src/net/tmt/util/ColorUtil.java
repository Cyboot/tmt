package net.tmt.util;

import org.lwjgl.util.Color;

public class ColorUtil {

	public static Color brighter(final Color color, final double factor) {
		color.setRed((int) (color.getRed() * factor));
		color.setGreen((int) (color.getGreen() * factor));
		color.setBlue((int) (color.getBlue() * factor));

		return color;
	}

	public static Color darker(final Color color, final double factor) {
		return brighter(color, 1 / factor);
	}
}
