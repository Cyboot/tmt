package net.tmt.util;

/**
 * easy to use class to check if a certain time has already passed.<br>
 * Use the {@link CountdownTimer#isTimeleft(double)} to update and check if time
 * has passed yet
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public class CountdownTimer {
	private boolean	autoReset	= true;
	private double	timeleftIntervall;
	private double	timer;

	/**
	 * @param timeleftIntervall
	 *            in seconds
	 * @param starttimeleft
	 *            in seconds
	 */
	public CountdownTimer(final double timeleftIntervall, final double starttimeleft) {
		this.timeleftIntervall = timeleftIntervall;
		this.timer = starttimeleft;
	}


	/**
	 * @param timeleftIntervall
	 *            in seconds
	 */
	public CountdownTimer(final double timeleftIntervall) {
		this(timeleftIntervall, timeleftIntervall);
	}

	/**
	 * updates the timer and return if timer reached zero yet.<br>
	 * if <i>autoReset</i> == true (default) it will automatically reset the
	 * timer
	 * 
	 * @param delta
	 * @return if time has passed yet
	 */
	public boolean isTimeleft(final double delta) {
		timer -= delta;

		if (timer <= 0) {
			if (autoReset)
				timer += timeleftIntervall;
			return true;
		}
		return false;
	}

	/**
	 * resets the timer manually
	 */
	public void reset() {
		timer = timeleftIntervall;
	}

	public static CountdownTimer createManuelResetTimer(final double timeleftIntervall) {
		CountdownTimer result = new CountdownTimer(timeleftIntervall);
		result.autoReset = false;
		return result;
	}
}