package net.tmt.entity;

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
	public void render(final Graphics g) {
		g.setColor(Color.WHITE);
		g.fillCircle(pos.x, pos.y, SIZE);
	}
}
