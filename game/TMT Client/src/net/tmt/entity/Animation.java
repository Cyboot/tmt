package net.tmt.entity;

import net.tmt.entity.component.DecayComponent;
import net.tmt.util.Vector2d;

public class Animation extends Entity2D {

	public Animation(final Vector2d pos, final double lifetime) {
		super(pos);

		removeAllComponents();
		addComponent(new DecayComponent(lifetime));
	}
}
