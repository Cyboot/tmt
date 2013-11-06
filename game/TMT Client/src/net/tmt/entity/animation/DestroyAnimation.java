package net.tmt.entity.animation;

import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.util.CountdownTimer;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class DestroyAnimation extends Animation {
	private final static double	LIFETIME		= 1.2;

	private CountdownTimer		timerAnimation	= new CountdownTimer(0.05);

	private double				radius;

	public DestroyAnimation(final Vector2d pos) {
		super(pos, LIFETIME);
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
		super.update(caller, delta);

		if (timerAnimation.isTimeleft(delta)) {
			radius += 3;
		}
	}

	@Override
	public void render(final Graphics g) {
		g.setColor(Color.ORANGE);
		g.drawCircle(pos.x, pos.y, radius % 25);
		g.drawCircle(pos.x, pos.y, (radius + 10) % 15);
	}
}
