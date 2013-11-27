package net.tmt.entity;

import net.tmt.entity.component.animation.EngineAnimationComponent;
import net.tmt.entity.component.animation.JetTrailComponent;
import net.tmt.entity.component.collision.SimpleHealthComponent;
import net.tmt.entity.component.move.AcceleratingComponent;
import net.tmt.entity.component.move.MoveComponent;
import net.tmt.entity.component.move.RotateComponent;
import net.tmt.entity.component.other.OnHoverComponent;
import net.tmt.entity.component.other.RocketLauncherComponent;
import net.tmt.entity.component.other.ShieldComponent;
import net.tmt.entity.component.other.TargetSearchComponent;
import net.tmt.entity.weapons.LaserShoot;
import net.tmt.game.Controls;
import net.tmt.game.GameEngine;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.game.interfaces.Playable;
import net.tmt.game.manager.EntityManager;
import net.tmt.game.manager.GuiManager;
import net.tmt.gfx.Sprite;
import net.tmt.global.achievments.Achievments;
import net.tmt.global.stats.Stats;
import net.tmt.gui.SpaceGui;
import net.tmt.util.CountdownTimer;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;


public class PlayerSpaceShip extends Entity2D implements Playable {
	private static double	ACCL			= 50;
	private static double	FRICTION		= 0.4;
	private static double	ROTATION_SPEED	= 90;

	private CountdownTimer	timerShoot		= CountdownTimer.createManualResetTimer(0.2);
	private ReadableColor	shootColor		= Color.RED;
	private boolean			shieldToggle;
	private boolean			mainEngineToggle;

	public PlayerSpaceShip() {
		super(new Vector2d(GameEngine.WIDTH / 2, GameEngine.HEIGHT / 2));
		setSprite(new Sprite("ship_back_64"));

		ComponentFactory.addDefaultMove(this, 0, 0, ROTATION_SPEED);
		addComponent(new AcceleratingComponent(ACCL, FRICTION));
		addComponent(new ShieldComponent(80, ShieldComponent.COLOR_YELLOW));

		addComponent(new OnHoverComponent());
		addComponent(new JetTrailComponent());
		addComponent(new TargetSearchComponent());
		addComponent(new RocketLauncherComponent());

		ComponentFactory.add3EngineAnimation(this, new Vector2d(1, 24), new Vector2d(-16, 20), new Vector2d(16, 20));
		ComponentFactory.addDefaultCollision(this, 32, 1000000);
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
		updateInput();
		updateShoot(caller, delta);

		updateStats(delta);

		super.update(caller, delta);
		GuiManager.getInstance().dispatch(SpaceGui.GUI_SHIP_HEALTH, getValue(SimpleHealthComponent.HEALTH));
		GuiManager.getInstance().dispatch(SpaceGui.GUI_SHIP_SPEED, getValue(MoveComponent.SPEED));
	}

	private void updateStats(final double delta) {
		double speed = (double) getValue(MoveComponent.SPEED);

		if (speed > 500)
			Achievments.achiev(Achievments.Space.SPEED_500);

		Stats.add(Stats.Space.DISTANCE_TRAVELLED, speed * delta);
	}

	private void updateShoot(final EntityManager caller, final double delta) {
		// shoot color
		if (Keyboard.isKeyDown(Keyboard.KEY_1))
			shootColor = Color.CYAN;
		if (Keyboard.isKeyDown(Keyboard.KEY_2))
			shootColor = new Color(RandomUtil.intRange(0, 255), RandomUtil.intRange(0, 255),
					RandomUtil.intRange(0, 255));


		// shoot LaserShoot
		if (timerShoot.isTimeUp(delta) && Controls.pressed(Controls.SHIP_FIRE)) {
			timerShoot.reset();
			caller.addEntity(new LaserShoot(pos.copy(), (double) getValue(MoveComponent.ROTATION_ANGLE_LOOK),
					shootColor, this));
			Stats.add(Stats.Space.SHOOTS_FIRED, 1);
			Achievments.achiev(Achievments.Space.FIRE_10_LASER);
		}
	}

	private void updateInput() {
		// Shield
		if (Controls.wasTyped(Controls.SHIP_SHIELD))
			shieldToggle = !shieldToggle;
		dispatchValue(ShieldComponent.SET_ACTIVE, shieldToggle);

		dispatchValue(EngineAnimationComponent.ENGINE_1, false);
		dispatchValue(EngineAnimationComponent.ENGINE_2, false);
		dispatchValue(EngineAnimationComponent.ENGINE_3, false);

		// default speed
		if (Controls.pressed(Controls.SHIP_BACK_SLOW_ACCL)) {
			dispatchValue(AcceleratingComponent.IS_ACCELERATING, true);
			dispatchValue(EngineAnimationComponent.ENGINE_2, true);
			dispatchValue(EngineAnimationComponent.ENGINE_3, true);
		}
		if (Controls.pressed(Controls.SHIP_BACK_SLOW_DEACCL))
			dispatchValue(AcceleratingComponent.IS_DEACCELERATING, true);


		// rotating
		if (Controls.pressed(Controls.SHIP_ROTATE_LEFT)) {
			dispatchValue(EngineAnimationComponent.ENGINE_2, true);
			dispatchValue(RotateComponent.IS_ROTATE_LEFT, true);
		}
		if (Controls.pressed(Controls.SHIP_ROTATE_RIGHT)) {
			dispatchValue(EngineAnimationComponent.ENGINE_3, true);
			dispatchValue(RotateComponent.IS_ROTATE_RIGHT, true);
		}
		if (Controls.pressed(Controls.SHIP_FAST_ROTATE))
			dispatchValue(RotateComponent.FAST_ROTATE, true);


		// extra speed
		if (Controls.wasTyped(Controls.SHIP_MAIN_ENGINE)) {
			mainEngineToggle = !mainEngineToggle;
		}
		if (mainEngineToggle) {
			dispatchValue(AcceleratingComponent.IS_ACCELERATING, true);
			dispatchValue(AcceleratingComponent.ACCL_FACTOR, ACCL * 8);
			dispatchValue(EngineAnimationComponent.ENGINE_1, true);
		} else {
			dispatchValue(AcceleratingComponent.ACCL_FACTOR, ACCL * 4);
		}

		// TargetSearch
		if (Controls.wasTyped(Controls.CHANGE_TARGET)) {
			dispatchValue(TargetSearchComponent.CHANGE_TARGET, true);
		}
		if (Controls.wasTyped(Controls.WEAPON_PRIMARY)) {
			dispatchValue(RocketLauncherComponent.LAUNCH_TYPE_1, true);
		}
	}
}
