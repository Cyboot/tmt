package net.tmt.entity;

import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class Hero extends Entity2D {

	public Hero(final Vector2d pos) {
		super(pos);
		setSprite(new Sprite("arrow_up"));
	}

}
