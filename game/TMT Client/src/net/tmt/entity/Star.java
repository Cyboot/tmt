package net.tmt.entity;

import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class Star extends Entity2D {
	private static final float	SIZE	= 1;

	public Star(final Vector2d pos) {
		super(pos);

		removeAllComponents();
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
	}

	@Override
	public void render(final Graphics g) {
		g.setColor(Color.WHITE);
		g.drawPoint(pos.x, pos.y, SIZE);
	}
}