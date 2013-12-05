package net.tmt.util;

public class MathUtil {

	/**
	 * calculates if it's faster to rotate clockwise or anti-clockwise to reach
	 * the target angle
	 * 
	 * @param angle
	 *            current angle (in degree)
	 * @param target
	 *            target angle (in degree)
	 * @return
	 */
	public static int nearestAngle(final double angle, final double target) {
		double diff = (target - angle + 360) % 360;

		return diff > 180 ? -1 : 1;
	}


	/**
	 * rounds a value to the given number
	 * 
	 * @param value
	 * @param round
	 * @return
	 */
	public static int roundTo(final int value, final int round) {
		int rest = value % round;

		if (rest > round / 2)
			return value - rest + round;
		else
			return value - rest;
	}


	/**
	 * returns the given value restricted to the upper and lower boundry (min,
	 * max)
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return min <= value <= max
	 */
	public static int clamp(final int value, final int min, final int max) {
		return Math.max(Math.min(value, max), min);
	}
}
