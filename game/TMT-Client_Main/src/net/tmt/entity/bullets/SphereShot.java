package net.tmt.entity.bullets;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.move.MoveComponent;
import net.tmt.entityComponents.other.DecayComponent;
import net.tmt.util.Vector2d;

import org.lwjgl.util.ReadableColor;

public class SphereShot extends Entity2D {

	private static double	lifetime	= 3;

	private double			speed		= 700;
	private ReadableColor	color;

	public SphereShot(final Vector2d pos, final double roation, final ReadableColor color, final Entity2D owner) {
		super(pos);
		this.owner = owner;
		this.color = color;

		removeAllComponents();

		addComponent(new MoveComponent(speed, roation));
		addComponent(new DecayComponent(lifetime));
		// addComponent(new AnimatedRenderComponent(sprites, looptime));
	}


}
