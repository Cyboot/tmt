package net.tmt.entity.statics;

import net.tmt.entity.Entity2D;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class BackgroundBody extends Entity2D {
	private Sprite		sprite;

	private Vector2d	offset;

	public BackgroundBody(final Vector2d pos) {
		super(pos);

		switch (RandomUtil.intRange(0, 3)) {
		case 0:
			sprite = new Sprite("galaxie_orange_64");
			break;
		case 1:
			sprite = new Sprite("galaxie_red_64");
			break;
		case 2:
			sprite = new Sprite("galaxie_violett_64");
			break;
		case 3:
			sprite = new Sprite("nebula_green_256");
			break;
		}
		sprite.setAlpha(75);

		removeAllComponents();
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
		offset = caller.getWorld().getOffset();
	}

	@Override
	public void render(final Graphics g) {
		double x = pos.x - offset.x;
		double y = pos.y - offset.y;

		g.onGui().drawSprite(Vector2d.tmp1.set(x / 16, y / 16), sprite);
	}
}