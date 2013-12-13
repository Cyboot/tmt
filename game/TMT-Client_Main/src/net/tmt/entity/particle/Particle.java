package net.tmt.entity.particle;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.other.DecayComponent;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.util.Vector2d;

public class Particle extends Entity2D {

	public Particle(final Vector2d pos, final double lifetime, final double speed, final double roation) {
		super(pos);

		addComponent(new DecayComponent(lifetime));

		ComponentFactory.addDefaultMove(this, roation, speed, 0);
	}
}
