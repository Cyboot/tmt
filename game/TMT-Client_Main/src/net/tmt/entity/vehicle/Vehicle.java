package net.tmt.entity.vehicle;

import net.tmt.entity.Entity2D;
import net.tmt.entity.statics.area.Area;
import net.tmt.entityComponents.move.MoveComponent;
import net.tmt.entityComponents.move.PhysicsComponent.Builder;
import net.tmt.game.Controls;
import net.tmt.game.interfaces.Playable;
import net.tmt.game.manager.CollisionManager;
import net.tmt.game.manager.EntityManager;
import net.tmt.game.manager.GameManager;
import net.tmt.game.manager.GuiManager;
import net.tmt.game.manager.ZoomManager;
import net.tmt.gui.elements.Label;
import net.tmt.map.World;
import net.tmt.util.ColorUtil;
import net.tmt.util.MathUtil;
import net.tmt.util.Vector2d;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.lwjgl.util.Color;

public abstract class Vehicle extends Entity2D {
	private static final double	DEFAULT_ROTATION_SPEED	= 90;

	protected boolean			isControlled;
	private EnterRange			enterRange;
	private boolean				isInRange				= false;
	private Entity2D			player;

	private double				size;

	public Vehicle(final Vector2d pos, final double size, final double rotationSpeed,
			final CollisionManager collisionsManager) {
		super(pos);
		this.size = size;

		// ComponentFactory.addDefaultMove(this, 0, 0, rotationSpeed);
		// ComponentFactory.addDefaultCollision(this, size / 2, 9999);
		Builder builder = new Builder(collisionsManager, pos);
		builder.setShape(getCollisionShape());
		builder.density(1);
		addComponent(builder.create());

		enterRange = new EnterRange(getPos(), size * 1.1);
	}

	public Vehicle(final Vector2d pos, final int size, final CollisionManager collisionsManager) {
		this(pos, size, DEFAULT_ROTATION_SPEED, collisionsManager);
	}

	@Override
	protected Shape getCollisionShape() {
		float width = MathUtil.toBox2d(size / 2);
		float height = MathUtil.toBox2d(size / 2);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);

		return shape;
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		isInRange = false;
		enterRange.update(caller, world, delta);
		if (isInRange && !isControlled) {
			Label tooltipp = new Label(world.getVectorOnScreen(pos), "Press 'E' to Enter");
			tooltipp.setBackgroundColor((Color) ColorUtil.BLACK_ALPHA_50);
			tooltipp.setForegroundColor((Color) ColorUtil.GUI_ORANGE);

			GuiManager.getInstance().setTooltip(tooltipp);
		}


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
		if (isControlled)
			ZoomManager.setFreeZoomBySpeed((double) getValue(MoveComponent.SPEED));
	}

	protected abstract void onDrive(double delta);

	private void exitVehicle() {
		System.out.println("exit vehicle");

		((Playable) player).enableControls();
		isControlled = false;
		player = null;
		onExitVehicle();
	}

	private void enterVehicle(final Entity2D player) {
		System.out.println("enter vehicle");

		((Playable) player).disableControls();
		this.player = player;
		isControlled = true;
		onEnterVehicle(player);
	}

	protected void onExitVehicle() {
	}

	protected void onEnterVehicle(final Entity2D player2) {
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
