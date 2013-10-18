package net.tmt.entity;

import net.tmt.entity.component.MoveComponent;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;


public class SpaceShip extends AbstractEntity2D {
	double	rotation	= 0;

	public SpaceShip() {
		super(new Vector2d(100, 100));

		addComponent(new MoveComponent.Builder().pos(pos).dir(new Vector2d()).build());
		setSprite(new Sprite("ship_64"));
	}

	@Override
	public void update(final double delta) {
		sendValueToComponent(MoveComponent.IS_ROTATE_LEFT, true);
		// rotation += delta * 32;
		// sendValueToComponent(MoveComponent.ROTATION_ANGLE, rotation);

		super.update(delta);
	}

}
