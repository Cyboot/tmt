package net.tmt.entity;

import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class BackgroundBody extends Entity2D {
	// private static final float SIZE = 3;

	private Sprite	sprite;

	// private Color color;


	public BackgroundBody(final Vector2d pos) {
		super(pos);

		switch (RandomUtil.intRange(0, 4)) {
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

		// color = new Color(RandomUtil.intRange(150, 255),
		// RandomUtil.intRange(150, 255), RandomUtil.intRange(150, 255),
		// 125);
		removeAllComponents();
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
	}

	@Override
	public void render(final Graphics g) {
		double x = pos.x - World.getInstance().getOffset().x;
		double y = pos.y - World.getInstance().getOffset().y;

		g.onGui().drawSprite(Vector2d.tmp1.set(x / 16, y / 16), sprite);
		// g.setColor(color);
		// g.onGui().fillCircle(x / 16, y / 16, SIZE);
	}
}
