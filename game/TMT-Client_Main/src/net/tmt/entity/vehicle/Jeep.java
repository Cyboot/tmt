package net.tmt.entity.vehicle;

import net.tmt.entityComponents.move.MoveComponent;
import net.tmt.entityComponents.move.RotateComponent;
import net.tmt.game.Controls;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class Jeep extends Vehicle {
	private static final double	MAX_SPEED	= 500;
	private static final double	SIZE		= 64;

	private double				speed		= 0;
	private double				friction	= 0.8;

	public Jeep(final Vector2d pos) {
		super(pos, SIZE);

		setSprite(new Sprite("jeep", 64, 64));
	}

	@Override
	protected void onDrive(final double delta) {
		if (Controls.pressed(Controls.HERO_UP)) {
			speed += 250 * delta;
		} else if (Controls.pressed(Controls.HERO_DOWN)) {
			speed -= 250 * delta;
		} else
			speed *= 1 - friction * delta;

		if (speed > MAX_SPEED)
			speed = MAX_SPEED;
		if (speed < -MAX_SPEED / 2)
			speed = -MAX_SPEED / 2;

		if (Controls.pressed(Controls.HERO_LEFT)) {
			dispatchValue(RotateComponent.IS_ROTATE_LEFT, true);
		}
		if (Controls.pressed(Controls.HERO_RIGHT)) {
			dispatchValue(RotateComponent.IS_ROTATE_RIGHT, true);
		}

		dispatchValue(MoveComponent.SPEED, speed);
	}
}
