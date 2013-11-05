package net.tmt.entity;

import net.tmt.entity.component.AcceleratingComponent;
import net.tmt.entity.component.EngineAnimationComponent;
import net.tmt.entity.component.JetTrailComponent;
import net.tmt.entity.component.MoveComponent;
import net.tmt.entity.component.OnHoverComponent;
import net.tmt.entity.component.RotateComponent;
import net.tmt.entity.component.ShieldComponent;
import net.tmt.entity.component.SimpleHealthComponent;
import net.tmt.entity.component.util.ComponentFactory;
import net.tmt.game.Controls;
import net.tmt.game.GameEngine;
import net.tmt.game.manager.EntityManager;
import net.tmt.game.manager.GuiManager;
import net.tmt.gfx.Sprite;
import net.tmt.gui.SpaceGui;
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
		addComponent(new EngineAnimationComponent());

		ComponentFactory.addDefaultCollision(this, 32, 1000000);
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
		updateInput();
		updateShoot(caller, delta);


		super.update(caller, delta);
		GuiManager.getInstance().dispatch(SpaceGui.GUI_SHIP_HEALTH, getValue(SimpleHealthComponent.HEALTH));
		GuiManager.getInstance().dispatch(SpaceGui.GUI_SHIP_SPEED, getValue(MoveComponent.SPEED));
	}

	private void updateShoot(final EntityManager caller, final double delta) {
		// shoot color
		if (Keyboard.isKeyDown(Keyboard.KEY_1))
			shootColor = Color.CYAN;
		if (Keyboard.isKeyDown(Keyboard.KEY_2))
			shootColor = new Color(RandomUtil.intRange(0, 255), RandomUtil.intRange(0, 255),
					RandomUtil.intRange(0, 255));


		// shoot LaserShoot
		if (timerShoot.isTimeleft(delta) && Controls.pressed(Controls.SHIP_FIRE)) {
			timerShoot.reset();
			caller.addEntity(new LaserShoot(pos.copy(), (double) getValue(MoveComponent.ROTATION_ANGLE), shootColor,
					this));
		}
	}

	private void updateInput() {
		// Shield
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			dispatchValue(ShieldComponent.SET_ACTIVE, true);
		else
			dispatchValue(ShieldComponent.SET_ACTIVE, false);

		// default speed
		if (Controls.pressed(Controls.SHIP_BACK_SLOW_ACCL))
			dispatchValue(AcceleratingComponent.IS_ACCELERATING, true);
		if (Controls.pressed(Controls.SHIP_BACK_SLOW_DEACCL))
			dispatchValue(AcceleratingComponent.IS_DEACCELERATING, true);

		// rotating
		if (Controls.pressed(Controls.SHIP_ROTATE_LEFT))
			dispatchValue(RotateComponent.IS_ROTATE_LEFT, true);
		if (Controls.pressed(Controls.SHIP_ROTATE_RIGHT))
			dispatchValue(RotateComponent.IS_ROTATE_RIGHT, true);

		// extra speed
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			dispatchValue(AcceleratingComponent.IS_ACCELERATING, true);
			dispatchValue(AcceleratingComponent.ACCL_FACTOR, ACCL * 8);
			dispatchValue(EngineAnimationComponent.ENGINE_ACTIVE, true);
		} else {
			dispatchValue(AcceleratingComponent.ACCL_FACTOR, ACCL);
			dispatchValue(EngineAnimationComponent.ENGINE_ACTIVE, false);
		}
	}
}
