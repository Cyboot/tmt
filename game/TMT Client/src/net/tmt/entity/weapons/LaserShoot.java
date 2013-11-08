package net.tmt.entity.weapons;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.collision.CollisionComponent;
import net.tmt.entity.component.collision.SimpleHealthComponent;
import net.tmt.entity.component.move.MoveComponent;
import net.tmt.entity.component.other.DecayComponent;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.ReadableColor;

public class LaserShoot extends Entity2D {
	private static double	lifetime	= 3;

	private double			speed		= 1000;
	private ReadableColor	color;

	public LaserShoot(final Vector2d pos, final double roation, final ReadableColor color, final Entity2D owner) {
		super(pos);

		this.owner = owner;
		this.color = color;
		removeAllComponents();

		addComponent(new MoveComponent(speed, roation));
		addComponent(new DecayComponent(lifetime));

		addComponent(new CollisionComponent(8, owner));
		addComponent(new SimpleHealthComponent(0.01, 25));
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		Vector2d dir = ((Vector2d) getValue(MoveComponent.MOVE_DIR)).copy().normalize();
		int length = 25;

		g.setColor(color);
		g.setLineWidth(2);
		g.drawLine(pos.x, pos.y, pos.x - dir.x * length, pos.y - dir.y * length);
	}
}
