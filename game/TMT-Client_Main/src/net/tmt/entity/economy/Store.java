package net.tmt.entity.economy;

import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class Store extends Building {

	public Store(final Vector2d pos) {
		super(pos);

		setSprite(new Sprite("icon_store", Building.SIZE, Building.SIZE));
	}

}
