package net.tmt.entity;

import net.tmt.entity.component.MoveComponent;
import net.tmt.game.Controls;
import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;


public class ControlledSpaceShip extends Entity2D {
	private static double	ACCL			= 10;
	private static double	FRICTION		= 0.4;
	private static double	ROTATION_SPEED	= 180;

	public ControlledSpaceShip() {
		super(new Vector2d(GameEngine.WIDTH / 2, GameEngine.HEIGHT / 2));

		addComponent(new MoveComponent.Builder().pos(pos).accl(ACCL).friction(FRICTION).rotationSpeed(ROTATION_SPEED)
				.build());
		setSprite(new Sprite("ship_back_64"));
	}

	@Override
	public void update(final double delta) {
		if (Controls.pressed(Controls.LEFT))
			dispatchValue(MoveComponent.IS_ROTATE_LEFT, true);
		if (Controls.pressed(Controls.RIGHT))
			dispatchValue(MoveComponent.IS_ROTATE_RIGHT, true);

		if (Controls.pressed(Controls.UP))
			dispatchValue(MoveComponent.IS_ACCELERATING, true);
		if (Controls.pressed(Controls.DOWN))
			dispatchValue(MoveComponent.IS_DEACCELERATING, true);

		super.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		// TODO Auto-generated method stub
		super.render(g);
	}

}
