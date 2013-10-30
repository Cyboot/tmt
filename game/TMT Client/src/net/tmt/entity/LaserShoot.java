package net.tmt.entity;

import net.tmt.entity.component.CollisionComponent;
import net.tmt.entity.component.MoveComponent;
import net.tmt.entity.particle.Particle;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.ReadableColor;

public class LaserShoot extends Particle {
	private static double	lifetime	= 3;

	private double			speed		= 1000;
	private ReadableColor	color;

	public LaserShoot(final Vector2d pos, final double roation, final ReadableColor cyan, final Entity2D owner) {
		super(pos, lifetime);

		this.owner = owner;
		this.color = cyan;
		removeAllComponents();

		Vector2d dir = Vector2d.fromAngle(Math.toRadians(roation));
		addComponent(new MoveComponent.Builder().pos(pos).speed(speed).dir(dir).build());
		addComponent(new CollisionComponent(8, owner));
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		Vector2d dir = ((Vector2d) getValue(MoveComponent.DIR)).copy().normalize();
		int length = 25;

		g.setColor(color);
		g.setLineWidth(2);
		g.drawLine(pos.x, pos.y, pos.x - dir.x * length, pos.y - dir.y * length);
	}
}
