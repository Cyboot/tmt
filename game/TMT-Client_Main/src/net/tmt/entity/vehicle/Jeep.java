package net.tmt.entity.vehicle;

import net.tmt.entityComponents.move.AcceleratingComponent;
import net.tmt.entityComponents.move.RotateComponent;
import net.tmt.game.Controls;
import net.tmt.game.manager.CollisionManager;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class Jeep extends Vehicle {
	private static final int	SIZE		= 64;
	private static final double	MAX_SPEED	= 350;
	private static final double	ACCL		= 250;

	public Jeep(final Vector2d pos, final CollisionManager collisionsManager) {
		super(pos, SIZE, collisionsManager);
		setSprite(new Sprite("jeep", SIZE, SIZE));

		AcceleratingComponent component = new AcceleratingComponent(ACCL, 0.8, MAX_SPEED, true);
		addComponent(component);
	}


	@Override
	protected void onDrive(final double delta) {
		if (Controls.pressed(Controls.HERO_UP)) {
			dispatchValue(AcceleratingComponent.IS_ACCELERATING, true);
		} else if (Controls.pressed(Controls.HERO_DOWN)) {
			dispatchValue(AcceleratingComponent.IS_DEACCELERATING, true);
		}


		if (Controls.pressed(Controls.HERO_LEFT)) {
			dispatchValue(RotateComponent.IS_ROTATE_LEFT, true);
		}
		if (Controls.pressed(Controls.HERO_RIGHT)) {
			dispatchValue(RotateComponent.IS_ROTATE_RIGHT, true);
		}
	}
}
