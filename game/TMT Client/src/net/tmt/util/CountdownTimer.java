package net.tmt.util;

public class CountdownTimer {
	private boolean	autoReset	= true;
	private double	timeleftIntervall;
	private double	timer;

	public CountdownTimer(final double timeleftIntervall, final double starttimeleft) {
		this.timeleftIntervall = timeleftIntervall;
		this.timer = starttimeleft;
	}


	public CountdownTimer(final double timeleftIntervall) {
		this(timeleftIntervall, timeleftIntervall);
	}


	public boolean isTimeleft(final double delta) {
		timer -= delta;

		if (timer <= 0) {
			if (autoReset)
				timer += timeleftIntervall;
			return true;
		}
		return false;
	}

	public void reset() {
		timer = timeleftIntervall;
	}

	public static CountdownTimer createManuelResetTimer(final double timeleftIntervall) {
		CountdownTimer result = new CountdownTimer(timeleftIntervall);
		result.autoReset = false;
		return result;
	}
}