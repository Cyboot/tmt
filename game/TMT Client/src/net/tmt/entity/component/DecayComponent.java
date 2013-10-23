package net.tmt.entity.component;

import net.tmt.util.CountdownTimer;

/**
 * Kills the entity after a given lifetime
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public class DecayComponent extends Component {
	private CountdownTimer	timer;

	public DecayComponent(final double lifetime) {
		timer = new CountdownTimer(lifetime);
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {

		if (timer.isTimeleft(delta)) {
			caller.getOwner().kill();
		}
	}
}
