package net.tmt.entity;

import net.tmt.entity.component.AcceleratingComponent;
import net.tmt.entity.component.JetTrailComponent;
import net.tmt.entity.component.MoveComponent;
import net.tmt.entity.component.OnHoverComponent;
import net.tmt.entity.component.RotateComponent;
import net.tmt.entity.component.ShieldComponent;
import net.tmt.entity.component.util.ComponentFactory;
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


public class PlayerSpaceShip extends Entity2D {
	private static double	ACCL			= 50;
	private static double	FRICTION		= 0.4;
	private static double	ROTATION_SPEED	= 180;

	private CountdownTimer	timerShoot		= CountdownTimer.createManuelResetTimer(0.2);
	private ReadableColor	shootColor		= Color.RED;

	public PlayerSpaceShip() {
		super(new Vector2d(GameEngine.WIDTH / 2, GameEngine.HEIGHT / 2));
		setSprite(new Sprite("ship_back_64"));

		ComponentFactory.addDefaultMove(this, 0, 0, ROTATION_SPEED);
		addComponent(new AcceleratingComponent(ACCL, FRICTION));
		addComponent(new ShieldComponent(80, ShieldComponent.COLOR_YELLOW));

		addComponent(new OnHoverComponent());
		addComponent(new JetTrailComponent());

		ComponentFactory.addDefaultCollision(this, 32, 1000000);
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			dispatchValue(ShieldComponent.SET_ACTIVE, true);
		else
			dispatchValue(ShieldComponent.SET_ACTIVE, false);

		if (Controls.pressed(Controls.LEFT))
			dispatchValue(RotateComponent.IS_ROTATE_LEFT, true);
		if (Controls.pressed(Controls.RIGHT))
			dispatchValue(RotateComponent.IS_ROTATE_RIGHT, true);

		if (Controls.pressed(Controls.UP))
			dispatchValue(AcceleratingComponent.IS_ACCELERATING, true);
		if (Controls.pressed(Controls.DOWN))
			dispatchValue(AcceleratingComponent.IS_DEACCELERATING, true);

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			dispatchValue(AcceleratingComponent.IS_ACCELERATING, true);
			dispatchValue(AcceleratingComponent.ACCL_FACTOR, ACCL * 8);
		} else {
			dispatchValue(AcceleratingComponent.ACCL_FACTOR, ACCL);
		}


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
