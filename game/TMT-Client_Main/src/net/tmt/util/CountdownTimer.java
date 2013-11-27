package net.tmt.util;

/**
 * Easy to use class to check if a certain time has already passed.<br>
 * Use the {@link CountdownTimer#isTimeUp(double)} to update and check if time
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
	 * get the ratio of the passed time of this countdown
	 * 
	 * @return a value between 0 - 1 <br>
	 *         (0 = isTimeleft() was true before, 1 = isTimeLeft will be true
	 *         next call)
	 */
	public double getTimeleftRatio() {
		return timer / timeleftIntervall;
	}

	/**
	 * updates the timer and return if timer reached zero yet.<br>
	 * if <i>autoReset</i> == true (default) it will automatically reset the
	 * timer
	 * 
	 * @param delta
	 * @return if time has passed yet
	 */
	public boolean isTimeUp(final double delta) {
		timer -= delta;

		if (timer <= 0) {
			if (autoReset)
				timer += timeleftIntervall;
			return true;
		}
		return false;
	}

	/**
	 * get the timeleft
	 * 
	 * @return timeleft in seconds
	 */
	public double getTimeleft() {
		return timer;
	}

	public void setTimer(final double timer) {
		this.timer = timer;
	}

	/**
	 * resets the timer manually
	 */
	public void reset() {
		timer = timeleftIntervall;
	}

	/**
	 * @param timeleftIntervall
	 *            the new timerintervall (in seconds)
	 */
	public void setIntervall(final double timeleftIntervall) {
		this.timeleftIntervall = timeleftIntervall;
	}

	public static CountdownTimer createManualResetTimer(final double timeleftIntervall) {
		CountdownTimer result = new CountdownTimer(timeleftIntervall);
		result.autoReset = false;
		return result;
	}

	@Override
	public String toString() {
		return "Timer " + StringFormatter.format(timer) + "/" + StringFormatter.format(timeleftIntervall);
	}
}