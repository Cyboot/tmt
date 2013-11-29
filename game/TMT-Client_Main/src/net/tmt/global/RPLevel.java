package net.tmt.global;

public class RPLevel {
	private static int	level		= 0;
	private static int	rp			= 0;

	private static int	rpTarget	= 1000;
	private static int	lastRPGain;

	/**
	 * @param level
	 * @return <b>true</b> if given level >= current level
	 */
	public static boolean hasLevel(final int level) {
		return level >= RPLevel.level;
	}

	/**
	 * adds RP
	 * 
	 * @param rp
	 */
	public static void addRP(final int rp) {
		lastRPGain = rp;
		RPLevel.rp += rp;

		checkLevel();
	}

	private static void checkLevel() {
		if (rp > rpTarget) {
			rp %= rpTarget;
			level++;

			// increase rp for next level. Try to fit to "round" numbers (e.g
			// ...250 or at least ...200)
			int deltaRP = (int) ((rpTarget * 1.1) - rpTarget);
			rpTarget += deltaRP;
			if (deltaRP > 250)
				rpTarget += 250 - rpTarget % 250;
			else
				rpTarget += 100 - rpTarget % 100;
		}
	}

	/**
	 * the current level as String.<br>
	 * use {@link RPLevel#hasLevel(int)} to check for the level
	 * 
	 * @return
	 */
	public static String getLevel() {
		return String.valueOf(level);
	}

	/**
	 * gets the target RP for the current Level
	 * 
	 * @return
	 */
	public static int getRPtarget() {
		return rpTarget;
	}

	/**
	 * gets the achieved RP for the current Level
	 * 
	 * @return
	 */
	public static int getRPcurrent() {
		return rp;
	}

	/**
	 * gets the missing RP for the current Level
	 * 
	 * @return
	 */
	public static int getRPmissing() {
		return rpTarget - rp;
	}

	/**
	 * the last gained RP
	 * 
	 * @return
	 */
	public static int getLastRPGain() {
		return lastRPGain;
	}
}
