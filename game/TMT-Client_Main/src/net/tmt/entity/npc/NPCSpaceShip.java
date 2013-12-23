package net.tmt.entity.npc;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.move.Move2TargetComponent;
import net.tmt.entityComponents.other.OnHoverComponent;
import net.tmt.game.factory.ComponentFactory;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

public abstract class NPCSpaceShip extends Entity2D {
	protected double	speed;
	protected double	roationSpeed;
	private Entity2D	target;

	public NPCSpaceShip(final Vector2d pos, final double speed, final double rotationSpeed, final double radius) {
		super(pos);

		this.speed = speed;
		this.roationSpeed = rotationSpeed;

		ComponentFactory.addDefaultMove(this, 0, speed, rotationSpeed);
		addComponent(new Move2TargetComponent());
		addComponent(new OnHoverComponent());

		dispatchValue(Move2TargetComponent.TARGET_REACHED, true);
	}

	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		if ((boolean) getValue(Move2TargetComponent.TARGET_REACHED)) {
			// TODO new way to get waypoints
			// target = ((SpaceMap) world.getMap()).getNextWaypoint(target);
			dispatchValue(Move2TargetComponent.SET_TARGET, target.getPos());
		}

		super.update(caller, world, delta);
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
	}
}
