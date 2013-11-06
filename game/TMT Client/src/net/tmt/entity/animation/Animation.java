package net.tmt.entity.animation;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.other.DecayComponent;
import net.tmt.util.Vector2d;

public class Animation extends Entity2D {

	public Animation(final Vector2d pos, final double lifetime) {
		super(pos);

		removeAllComponents();
		addComponent(new DecayComponent(lifetime));
	}
}
