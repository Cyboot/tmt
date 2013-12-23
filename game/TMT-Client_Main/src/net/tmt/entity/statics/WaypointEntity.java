package net.tmt.entity.statics;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.other.OnHoverComponent;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class WaypointEntity extends Entity2D {

	public WaypointEntity(final Vector2d pos) {
		super(pos);

		addComponent(new OnHoverComponent());
		setSprite(new Sprite("waypoint_64", 32, 32));
	}
}
