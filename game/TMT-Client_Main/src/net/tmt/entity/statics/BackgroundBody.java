package net.tmt.entity.statics;

import net.tmt.entity.Entity2D;
import net.tmt.game.GameEngine;
import net.tmt.game.manager.EntityManager;
import net.tmt.game.manager.ZoomManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class BackgroundBody extends Entity2D {
	private Sprite		sprite;

	private Vector2d	offset;
	private double		imageSize;
	private int			backgroundFactor;

	public BackgroundBody(final Vector2d pos, final int backgroundFactor) {
		super(pos);

		this.backgroundFactor = backgroundFactor;

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
		imageSize = sprite.getWidth();

		removeAllComponents();
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		offset = world.getOffset();
	}

	@Override
	public void render(final Graphics g) {
		double dx = pos.x - offset.x;
		double dy = pos.y - offset.y;
		dx /= backgroundFactor * ZoomManager.getZoomInverse();
		dy /= backgroundFactor * ZoomManager.getZoomInverse();

		double x = GameEngine.WIDTH / 2 + dx;
		double y = GameEngine.HEIGHT / 2 + dy;

		sprite.setWidth(ZoomManager.getZoom() * imageSize);
		sprite.setHeight(ZoomManager.getZoom() * imageSize);

		g.onGui().drawSprite(Vector2d.tmp1.set(x, y), sprite);
	}
}
