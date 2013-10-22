package net.tmt.entity;

import net.tmt.entity.component.MoveComponent;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.ReadableColor;

public class LaserShoot extends Entity2D {
	private double			speed	= 1000;
	private ReadableColor	color;

	public LaserShoot(final Vector2d pos, final double roation, final ReadableColor cyan) {
		super(pos);

		this.color = cyan;

		Vector2d dir = Vector2d.fromAngle(Math.toRadians(roation));
		addComponent(new MoveComponent.Builder().pos(pos).speed(speed).dir(dir).build());
	}

	@Override
	public void render(final Graphics g) {
		Vector2d dir = ((Vector2d) getValue(MoveComponent.DIR)).copy().normalize();
		int length = 30;

		g.setColor(color);
		g.setLineWidth(2);
		g.drawLine(pos.x, pos.y, pos.x + dir.x * length, pos.y + dir.y * length);
	}
}
