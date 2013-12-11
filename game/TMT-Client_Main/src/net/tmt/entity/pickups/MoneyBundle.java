package net.tmt.entity.pickups;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.collision.CollisionComponent;
import net.tmt.entity.component.other.PickUpComponent;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class MoneyBundle extends Entity2D {

	public MoneyBundle(final Vector2d pos) {
		super(pos);
		setSprite(new Sprite("money_bundle"));
		addComponent(new CollisionComponent(10));
		addComponent(new PickUpComponent(new Vector2d(0, -15), false));
	}

}
