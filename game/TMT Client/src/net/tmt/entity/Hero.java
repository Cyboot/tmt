package net.tmt.entity;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.component.other.AnimatedRenderComponent;
import net.tmt.game.Controls;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class Hero extends Entity2D {

	private double	SPEED	= 128;

	// private double ROTATION_SPEED = 90;
	// public boolean IS_ROTATE_LEFT = false;
	// public boolean IS_ROTATE_RIGHT = false;

	public Hero(final Vector2d pos) {
		super(pos);
		removeAllComponents();
		List<Sprite> aniSprites = new ArrayList<Sprite>();
		aniSprites.add(new Sprite("hero_0"));
		aniSprites.add(new Sprite("hero_1"));
		addComponent(new AnimatedRenderComponent(aniSprites, 0.2));
		// addComponent(new RotateComponent(0, ROTATION_SPEED));
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
		super.update(caller, delta);

		if (Controls.pressed(Controls.HERO_UP))
			pos.y -= delta * SPEED;
		if (Controls.pressed(Controls.HERO_DOWN))
			pos.y += delta * SPEED;
		if (Controls.pressed(Controls.HERO_LEFT))
			pos.x -= delta * SPEED;
		// IS_ROTATE_LEFT = true;
		// else
		// IS_ROTATE_LEFT = false;
		if (Controls.pressed(Controls.HERO_RIGHT))
			pos.x += delta * SPEED;
		// IS_ROTATE_RIGHT = true;
		// else
		// IS_ROTATE_RIGHT = false;

		// dispatchValue("IS_ROTATE_LEFT", IS_ROTATE_LEFT);
		// dispatchValue("IS_ROTATE_RIGHT", IS_ROTATE_RIGHT);
	}
}
