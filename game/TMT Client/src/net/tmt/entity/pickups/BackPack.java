package net.tmt.entity.pickups;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.collision.CollisionComponent;
import net.tmt.entity.component.other.PickUpComponent;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class BackPack extends Entity2D {

	public BackPack(final Vector2d pos) {
		super(pos);
		Sprite s = new Sprite("backpack");
		setSprite(s);
		addComponent(new PickUpComponent(new Vector2d(0, 0)));
		addComponent(new CollisionComponent(10));
	}

}
