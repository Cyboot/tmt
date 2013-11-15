package net.tmt.entity;

import net.tmt.game.Controls;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class Hero extends Entity2D {

	private double	speed	= 64;

	public Hero(final Vector2d pos) {
		super(pos);
		setSprite(new Sprite("arrow_up"));
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
		super.update(caller, delta);

		if (Controls.pressed(Controls.HERO_UP))
			pos.y -= delta * speed;
		if (Controls.pressed(Controls.HERO_DOWN))
			pos.y += delta * speed;
		if (Controls.pressed(Controls.HERO_LEFT))
			pos.x -= delta * speed;
		if (Controls.pressed(Controls.HERO_RIGHT))
			pos.x += delta * speed;
	}
}
