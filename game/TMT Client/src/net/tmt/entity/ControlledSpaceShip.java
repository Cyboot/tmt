package net.tmt.entity;

import net.tmt.entity.component.MoveComponent;
import net.tmt.game.Controls;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;


public class ControlledSpaceShip extends Entity2D {
	private static double	ACCL		= 10;
	private static double	FRICTION	= 0.4;

	public ControlledSpaceShip() {
		super(new Vector2d(300, 300));

		addComponent(new MoveComponent.Builder().pos(pos).accl(ACCL).friction(FRICTION).build());
		setSprite(new Sprite("ship_back_64"));
	}

	@Override
	public void update(final double delta) {
		if (Controls.pressed(Controls.LEFT))
			sendValueToComponent(MoveComponent.IS_ROTATE_LEFT, true);
		if (Controls.pressed(Controls.RIGHT))
			sendValueToComponent(MoveComponent.IS_ROTATE_RIGHT, true);

		if (Controls.pressed(Controls.UP))
			sendValueToComponent(MoveComponent.IS_ACCELERATING, true);
		if (Controls.pressed(Controls.DOWN))
			sendValueToComponent(MoveComponent.IS_DEACCELERATING, true);

		super.update(delta);
	}

}
