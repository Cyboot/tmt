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
}
