package net.tmt.util;

public class RandomUtil {

	public static double doubleRange(final double min, final double max) {
		double rand = Math.random();

		return rand * (max - min) + min;
	}

	public static int intRange(final int min, final int max) {
		double rand = Math.random();

		return (int) (rand * (max - min) + min);
	}

}
