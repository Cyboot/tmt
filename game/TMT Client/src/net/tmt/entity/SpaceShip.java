package net.tmt.entity;

import net.tmt.entity.component.MoveComponent;
import net.tmt.game.Controls;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;


public class SpaceShip extends AbstractEntity2D {
	private static double	ACCL	= 0.05;
	private static double	DEACCL	= 0.4;

	public SpaceShip() {
		super(new Vector2d(100, 100));

		addComponent(new MoveComponent.Builder().pos(pos).dir(new Vector2d()).accl(ACCL).deaccl(DEACCL).build());
		setSprite(new Sprite("ship_64"));
	}

	@Override
	public void update(final double delta) {
		if (Controls.pressed(Controls.LEFT))
			sendValueToComponent(MoveComponent.IS_ROTATE_LEFT, true);
		if (Controls.pressed(Controls.RIGHT))
			sendValueToComponent(MoveComponent.IS_ROTATE_RIGHT, true);

		if (Controls.pressed(Controls.UP))
			sendValueToComponent(MoveComponent.IS_ACCELERATING, true);

		super.update(delta);
	}

}
