package net.tmt.entity;

import net.tmt.entity.component.CollisionComponent;
import net.tmt.entity.component.MoveComponent;
import net.tmt.entity.component.ShieldComponent;
import net.tmt.game.Controls;
import net.tmt.game.GameEngine;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.util.CountdownTimer;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;


public class ControlledSpaceShip extends Entity2D {
	private static double	ACCL			= 10;
	private static double	FRICTION		= 0.4;
	private static double	ROTATION_SPEED	= 180;

	private CountdownTimer	timerShoot		= CountdownTimer.createManuelResetTimer(0.2);
	private ReadableColor	shootColor		= Color.RED;

	public ControlledSpaceShip() {
		super(new Vector2d(GameEngine.WIDTH / 2, GameEngine.HEIGHT / 2));

		addComponent(new MoveComponent.Builder().pos(pos).accl(ACCL).friction(FRICTION).rotationSpeed(ROTATION_SPEED)
				.build());
		setSprite(new Sprite("ship_back_64"));
		addComponent(new ShieldComponent(pos, 80, ShieldComponent.COLOR_YELLOW));
		addComponent(new CollisionComponent(32));
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			dispatchValue(ShieldComponent.SET_ACTIVE, true);
		else
			dispatchValue(ShieldComponent.SET_ACTIVE, false);

		if (Controls.pressed(Controls.LEFT))
			dispatchValue(MoveComponent.IS_ROTATE_LEFT, true);
		if (Controls.pressed(Controls.RIGHT))
			dispatchValue(MoveComponent.IS_ROTATE_RIGHT, true);

		if (Controls.pressed(Controls.UP))
			dispatchValue(MoveComponent.IS_ACCELERATING, true);
		if (Controls.pressed(Controls.DOWN))
			dispatchValue(MoveComponent.IS_DEACCELERATING, true);

		if (Keyboard.isKeyDown(Keyboard.KEY_1))
			shootColor = Color.CYAN;
		if (Keyboard.isKeyDown(Keyboard.KEY_2))
			shootColor = Color.ORANGE;
		if (Keyboard.isKeyDown(Keyboard.KEY_3))
			shootColor = Color.GREEN;
		if (Keyboard.isKeyDown(Keyboard.KEY_4))
			shootColor = Color.PURPLE;
		if (Keyboard.isKeyDown(Keyboard.KEY_5))
			shootColor = Color.RED;
		if (Keyboard.isKeyDown(Keyboard.KEY_6))
			shootColor = Color.WHITE;
		if (Keyboard.isKeyDown(Keyboard.KEY_7))
			shootColor = Color.YELLOW;
		if (Keyboard.isKeyDown(Keyboard.KEY_8))
			shootColor = Color.WHITE;
		if (Keyboard.isKeyDown(Keyboard.KEY_9))
			shootColor = Color.GREY;
		if (Keyboard.isKeyDown(Keyboard.KEY_0))
			shootColor = new Color(RandomUtil.intRange(0, 255), RandomUtil.intRange(0, 255),
					RandomUtil.intRange(0, 255));


		if (timerShoot.isTimeleft(delta) && Controls.pressed(Controls.FIRE)) {
			timerShoot.reset();
			caller.addEntity(new LaserShoot(pos.copy(), (double) getValue(MoveComponent.ROTATION_ANGLE), shootColor,
					this));
		}

		super.update(caller, delta);
	}
}
