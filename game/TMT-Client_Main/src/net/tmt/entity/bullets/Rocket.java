package net.tmt.entity.bullets;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.other.DecayComponent;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.util.Vector2d;

public abstract class Rocket extends Entity2D {

	public Rocket(final Vector2d pos, final double rotation, final double speed, final Entity2D owner,
			final double lifetime, final double rotationSpeed) {
		super(pos);

		ComponentFactory.addDefaultMove(this, rotation, speed, rotationSpeed);
		addComponent(new DecayComponent(lifetime));
	}
}
