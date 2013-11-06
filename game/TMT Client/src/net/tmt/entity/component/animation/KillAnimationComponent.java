package net.tmt.entity.component.animation;

import net.tmt.entity.animation.DestroyAnimation;
import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;

public class KillAnimationComponent extends Component {

	public void onKilled(final ComponentDispatcher caller) {
		caller.getEntityManager().addEntity(new DestroyAnimation(owner.getPos().copy()));
	}
}
