package net.tmt.entity.statics.area;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.collision.CollisionComponent;
import net.tmt.entity.component.collision.CollisionWithPlayerComponent;
import net.tmt.game.manager.EntityManager;
import net.tmt.util.Vector2d;

public abstract class Area extends Entity2D {

	public Area(final Vector2d pos, final double radius) {
		super(pos);

		addComponent(new CollisionWithPlayerComponent(radius));
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
		super.update(caller, delta);

		if (isSet(CollisionComponent.IS_COLLISION) && (boolean) getValue(CollisionComponent.IS_COLLISION)) {
			onCollide();
		}
	}

	protected abstract void onCollide();
}