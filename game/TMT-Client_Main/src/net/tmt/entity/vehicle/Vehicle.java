package net.tmt.entity.vehicle;

import net.tmt.entity.Entity2D;
import net.tmt.entity.statics.area.Area;
import net.tmt.game.Controls;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.game.interfaces.Playable;
import net.tmt.game.manager.EntityManager;
import net.tmt.game.manager.GameManager;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

public abstract class Vehicle extends Entity2D implements Playable {
	private static final double	ROTATION_SPEED	= 90;
	private EnterRange			enterRange;
	private boolean				isInRange		= false;
	private boolean				isControlled;
	private Entity2D			player;

	public Vehicle(final Vector2d pos, final double size) {
		super(pos);

		ComponentFactory.addDefaultMove(this, 0, 0, ROTATION_SPEED);
		ComponentFactory.addDefaultCollision(this, size, 9999);
		enterRange = new EnterRange(getPos(), size * 1.5);
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		isInRange = false;
		enterRange.update(caller, world, delta);

		if (isControlled) {
			onDrive(delta);

			player.getPos().set(pos);

			if (Controls.wasReleased(Controls.HERO_USE)) {
				exitVehicle();
				isInRange = false;
			}
		}

		if (isInRange && Controls.wasReleased(Controls.HERO_USE)) {
			Entity2D player = GameManager.getInstance().getActiveGamestate().getPlayer();
			enterVehicle(player);
		}

		super.update(caller, world, delta);
	}

	protected abstract void onDrive(double delta);

	private void exitVehicle() {
		System.out.println("exit vehicle");

		((Playable) player).enableControls();
		disableControls();
		player = null;
	}

	private void enterVehicle(final Entity2D player) {
		System.out.println("enter vehicle");

		((Playable) player).disableControls();
		this.player = player;
		enableControls();
	}

	@Override
	public void disableControls() {
		isControlled = false;
	}

	@Override
	public void enableControls() {
		isControlled = true;
	}

	private class EnterRange extends Area {
		public EnterRange(final Vector2d pos, final double radius) {
			super(pos, radius);
		}

		@Override
		protected void onCollide() {
			isInRange = true;
		}
	}
}
