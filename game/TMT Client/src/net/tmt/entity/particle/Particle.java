package net.tmt.entity.particle;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.DecayComponent;
import net.tmt.entity.component.MoveComponent;
import net.tmt.util.Vector2d;

public class Particle extends Entity2D {

	public Particle(final Vector2d pos, final double lifetime, final double speed, final double roation) {
		super(pos);

		addComponent(new DecayComponent(lifetime));

		Vector2d dir = Vector2d.fromAngle(Math.toRadians(roation));
		addComponent(new MoveComponent.Builder().speed(speed).dir(dir).build());
	}
}
