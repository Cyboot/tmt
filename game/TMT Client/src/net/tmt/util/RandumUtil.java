package net.tmt.util;

public class RandumUtil {

	public static double doubleRange(final double min, final double max) {
		double rand = Math.random();

		return rand * (max - min) + min;
	}

}
