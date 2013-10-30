package net.tmt.entity.particle;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.DecayComponent;
import net.tmt.util.Vector2d;

public class Particle extends Entity2D {

	public Particle(final Vector2d pos, final double lifetime) {
		super(pos);

		removeAllComponents();
		addComponent(new DecayComponent(lifetime));
	}
}
