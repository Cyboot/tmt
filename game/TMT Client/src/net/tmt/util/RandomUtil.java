package net.tmt.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class RandomUtil {


	private static final int	SEED	= 42;
	private static Random		random	= new Random(SEED);

	public static double doubleRange(final double min, final double max) {
		double rand = random.nextDouble();

		return rand * (max - min) + min;
	}

	/**
	 * returns a random int in the given range.<br>
	 * <i>intRange(0,3)</i> can return 0,1,2,3
	 * 
	 * @param min
	 *            inclusive
	 * @param max
	 *            inclusive
	 * @return
	 */
	public static int intRange(final int min, final int max) {
		return random.nextInt(max - min + 1) + min;
	}

	/**
	 * random boolean value
	 * 
	 * @return
	 */
	public static boolean randBoolean() {
		return random.nextBoolean();
	}

	/**
	 * random boolean value with the given probobility<br>
	 * 
	 * @param probability
	 *            between 0 - 1
	 * @return
	 */
	public static boolean randBoolean(final double probability) {
		return random.nextDouble() < probability;
	}

	/**
	 * set a seed to make the next random number predicable (to the seed) but
	 * still random
	 * 
	 * @param seed
	 */
	public static void setSeed(final int seed) {
		messageDigest.update(String.valueOf(seed).getBytes());
		int hashedSeed = new String(messageDigest.digest()).hashCode();
		random.setSeed(hashedSeed);
	}

	public static MessageDigest	messageDigest;
	static {
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
