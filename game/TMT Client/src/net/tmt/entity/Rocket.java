package net.tmt.entity;

import net.tmt.entity.component.CollisionComponent;
import net.tmt.entity.component.DecayComponent;
import net.tmt.entity.component.MoveComponent;
import net.tmt.entity.component.SimpleHealthComponent;
import net.tmt.entity.component.util.ComponentFactory;
import net.tmt.util.Vector2d;

public abstract class Rocket extends Entity2D {

	public Rocket(final Vector2d pos, final double rotation, final double speed, final Entity2D owner,
			final double lifetime, final double rotationSpeed) {
		super(pos);

		addComponent(new MoveComponent(speed, rotation));
		addComponent(new DecayComponent(lifetime));
		ComponentFactory.addDefaultMove(this, rotation, speed, rotationSpeed);

		addComponent(new CollisionComponent(32, owner));
		addComponent(new SimpleHealthComponent(0.01, 100));
	}

}
