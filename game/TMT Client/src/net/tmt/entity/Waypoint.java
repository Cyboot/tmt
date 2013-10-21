package net.tmt.entity;

import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class Waypoint extends Entity2D {

	public Waypoint(final Vector2d pos) {
		super(pos);

		setSprite(new Sprite("waypoint_64", 32, 32));
	}

}
