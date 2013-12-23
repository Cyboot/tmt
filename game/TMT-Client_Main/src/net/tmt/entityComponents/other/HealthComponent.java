package net.tmt.entityComponents.other;

import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;

public class HealthComponent extends Component {
	public static final String	HEALTH	= "HEALTH";
	public static final String	DAMAGE	= "DAMAGE";

	private double				health;

	public HealthComponent(final double health) {
		this.health = health;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (caller.isSet(DAMAGE)) {
			health -= (double) caller.getValue(DAMAGE);
		}

		caller.dispatch(HEALTH, health);
	}

	@Override
	public void initialDispatch(final ComponentDispatcher caller) {
		super.initialDispatch(caller);
		caller.dispatch(HEALTH, health);
	}
}
