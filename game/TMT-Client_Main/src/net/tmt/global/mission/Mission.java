package net.tmt.global.mission;

import java.util.ArrayList;
import java.util.List;

import net.tmt.game.interfaces.Updateable;
import net.tmt.global.Money;
import net.tmt.global.RPLevel;
import net.tmt.util.CountdownTimer;

public abstract class Mission implements Updateable {
	private static final double	DEFAULT_BURNOUT_TIME	= 10;
	private static final double	DEFAULT_OFFER_TIME		= 15;
	private State				state					= State.OFFERED;
	private String				desc;
	private String				title;

	private List<Object>		callerObjects			= new ArrayList<>();
	private CountdownTimer		timerOffer;
	private CountdownTimer		timerBurnout;

	private int					moneyForGold;
	private int					moneyForSilver;
	private int					moneyForBronze;
	private int					rpForGold;
	private int					rpForSilver;
	private int					rpForBronze;

	public static enum State {
		OFFERED, BURNOUT, REFUSED, ACTIVE, FINISHED
	}

	public static enum Medal {
		GOLD, SILVER, BRONZE, NONE
	}

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
			if (timerOffer.isTimeUp(delta))
				nextState = State.BURNOUT;
			break;
		case BURNOUT:
			if (timerBurnout.isTimeUp(delta))
				nextState = State.REFUSED;
			break;
		default:
			break;
		}
		state = nextState;

		for (Object obj : callerObjects)
			onAction(obj);
		callerObjects.clear();
	}

	/**
	 * start the Mission
	 */
	public void start() {
		state = State.ACTIVE;
	}

	/**
	 * finish the Mission, adds Money & RP rewards
	 */
	protected void finish(final Medal medal) {
		state = State.FINISHED;

		switch (medal) {
		case GOLD:
			RPLevel.addRP(rpForGold);
			Money.addMoney(moneyForGold);
			break;
		case SILVER:
			RPLevel.addRP(rpForSilver);
			Money.addMoney(moneyForSilver);
			break;
		case BRONZE:
			RPLevel.addRP(rpForBronze);
			Money.addMoney(moneyForBronze);
			break;
		default:
			break;
		}
	}

	protected void setRewardMoney(final int moneyForGold, final int moneyForSilver, final int moneyForBronze) {
		this.moneyForGold = moneyForGold;
		this.moneyForSilver = moneyForSilver;
		this.moneyForBronze = moneyForBronze;
	}

	protected void setRewardRP(final int rpForGold, final int rpForSilver, final int rpForBronze) {
		this.rpForGold = rpForGold;
		this.rpForSilver = rpForSilver;
		this.rpForBronze = rpForBronze;
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

	/**
	 * call from outside to inform the mission
	 * 
	 * @param caller
	 */
	public final void action(final Object caller) {
		callerObjects.add(caller);
	}

	protected void onAction(final Object caller) {
	}
}
