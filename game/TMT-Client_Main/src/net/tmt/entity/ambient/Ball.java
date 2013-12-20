package net.tmt.entity.ambient;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.move.PhysicsComponent.Builder;
import net.tmt.game.manager.CollisionManager;
import net.tmt.gfx.Sprite;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class Ball extends Entity2D {

	public Ball(final Vector2d pos, final CollisionManager collisionsManager) {
		super(pos);

		int size = 0;
		switch (RandomUtil.intRange(1, 3)) {
		case 1:
			size = 16;
			break;
		case 2:
			size = 32;
			break;
		case 3:
			size = 64;
			break;
		}
		setSprite(new Sprite("ball", size, size));

		Builder builder = new Builder(collisionsManager, pos);
		builder.circleShape(size / 2 / CollisionManager.PIXEL_PER_METER);
		builder.density(0.1f);
		addComponent(builder.create());
	}
}
