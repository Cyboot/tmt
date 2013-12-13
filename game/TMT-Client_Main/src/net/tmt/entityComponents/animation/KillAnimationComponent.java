package net.tmt.entityComponents.animation;

import net.tmt.entity.animation.DestroyAnimation;
import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;

public class KillAnimationComponent extends Component {

	public void onKilled(final ComponentDispatcher caller) {
		caller.getEntityManager().addEntity(new DestroyAnimation(owner.getPos().copy()));
	}
}
