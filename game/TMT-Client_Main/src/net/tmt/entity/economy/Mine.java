package net.tmt.entity.economy;

import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class Mine extends Building {

	public Mine(final Vector2d pos) {
		super(pos);

		setSprite(new Sprite("icon_mine", Building.SIZE, Building.SIZE));
	}

}
