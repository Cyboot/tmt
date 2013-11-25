package net.tmt.global.mission;

import net.tmt.game.interfaces.Updateable;
import net.tmt.util.CountdownTimer;

public abstract class Mission implements Updateable {
	private static final double	DEFAULT_BURNOUT_TIME	= 10;
	private static final double	DEFAULT_OFFER_TIME		= 15;
	private State				state					= State.OFFERED;
	private String				desc;
	private String				title;

	private CountdownTimer		timerOffer;
	private CountdownTimer		timerBurnout;

	public Mission(final String title, final String desc) {
		this.title = title;
		this.desc = desc;
		timerOffer = new CountdownTimer(DEFAULT_OFFER_TIME);
		timerBurnout = new CountdownTimer(DEFAULT_BURNOUT_TIME);
	}

	@Override
	public void update(final double delta) {
		State nextState = state;

		switch (state) {
		case OFFERED:
			if (timerOffer.isTimeleft(delta))
				nextState = State.BURNOUT;
			break;
		case BURNOUT:
			if (timerBurnout.isTimeleft(delta))
				nextState = State.REFUSED;
			break;
		default:
			break;
		}
		state = nextState;
	}

	public void start() {
		state = State.ACTIVE;
	}

	public String getTitle() {
		return title;
	}

	public String getDesc() {
		return desc;
	}

	public State getState() {
		return state;
	}

	protected void finish() {
		state = State.FINISHED;
	}

	public static enum State {
		OFFERED, BURNOUT, REFUSED, ACTIVE, FINISHED
	}

	public void refuse() {
		state = State.BURNOUT;
	}

	public double getOfferTimeleft() {
		return timerOffer.getTimeleft();
	}

	@Override
	public String toString() {
		return "Mission: " + title + " [state=" + state + "]";
	}

	public void offer() {
		state = State.OFFERED;
		timerOffer = new CountdownTimer(DEFAULT_OFFER_TIME);
		timerBurnout = new CountdownTimer(DEFAULT_BURNOUT_TIME);
	}
}
