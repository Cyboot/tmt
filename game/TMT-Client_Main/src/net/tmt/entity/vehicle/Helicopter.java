package net.tmt.entity.vehicle;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.move.AcceleratingComponent;
import net.tmt.entityComponents.move.PlayerMoveComponent;
import net.tmt.entityComponents.move.RotateComponent;
import net.tmt.game.Controls;
import net.tmt.game.manager.CollisionsManager;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.CountdownTimer;
import net.tmt.util.Vector2d;

public class Helicopter extends Vehicle {
	private static final int	SIZE				= 196;
	private static final double	MAX_SPEED			= 500;
	private static final double	ACCL				= 250;
	private static final int	MAX_ROTOR_SPEED		= 1500;
	private static final double	ROTATION_SPEED		= 180;

	private CountdownTimer		timerRotorSlowdown	= CountdownTimer.createManualResetTimer(7.5);
	private CountdownTimer		timerRotorAccl		= CountdownTimer.createManualResetTimer(1.5);
	private Sprite				rotor;

	public Helicopter(final Vector2d pos, final CollisionsManager collisionsManager) {
		super(pos, SIZE, ROTATION_SPEED, collisionsManager);
		setSprite(new Sprite("helicopter", SIZE, SIZE));
		rotor = new Sprite("helicopter_rotor", SIZE, SIZE);
		timerRotorSlowdown.setTimer(0);

		PlayerMoveComponent component = new PlayerMoveComponent(ACCL, 0.8, MAX_SPEED);
		addComponent(component);
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		super.update(caller, world, delta);

		double rotation = 0;
		if (isControlled) {
			if (timerRotorAccl.isTimeUp(delta))
				rotation = MAX_ROTOR_SPEED;
			else
				rotation = timerRotorAccl.getTimeleftRatio() * MAX_ROTOR_SPEED;
		} else {
			if (timerRotorSlowdown.isTimeUp(delta))
				rotation = 0;
			else
				rotation = Math.pow((1 - timerRotorSlowdown.getTimeleftRatio()), 3) * MAX_ROTOR_SPEED;
		}
		rotor.rotate(rotation * delta);
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		g.drawSprite(pos, rotor);
	}

	@Override
	protected void onEnterVehicle(final Entity2D player2) {
		timerRotorSlowdown.reset();
		timerRotorAccl.reset();
	}

	@Override
	protected void onExitVehicle() {
		timerRotorSlowdown.reset();
		timerRotorAccl.reset();
	}


	@Override
	protected void onDrive(final double delta) {
		if (!timerRotorAccl.isTimeUp(0))
			return;


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
