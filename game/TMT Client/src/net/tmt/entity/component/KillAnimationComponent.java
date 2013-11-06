package net.tmt.entity.component;

import net.tmt.entity.DestroyAnimation;

public class KillAnimationComponent extends Component {

	public void onKilled(final ComponentDispatcher caller) {
		caller.getEntityManager().addEntity(new DestroyAnimation(owner.getPos().copy()));
	}
}
