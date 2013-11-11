package net.tmt.entity.economy;

import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class Base extends Building {

	public Base(final Vector2d pos) {
		super(pos);

		setSprite(new Sprite("icon_base", 32, 32));
	}

}
