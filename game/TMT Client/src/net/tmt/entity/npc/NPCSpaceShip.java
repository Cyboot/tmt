package net.tmt.entity.npc;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.Move2TargetComponent;
import net.tmt.entity.component.MoveComponent;
import net.tmt.entity.component.OnHoverComponent;
import net.tmt.entity.component.util.ComponentFactory;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

abstract class NPCSpaceShip extends Entity2D {
	private World		world	= World.getInstance();

	protected double	speed;
	protected double	roationSpeed;
	private Entity2D	target;

	public NPCSpaceShip(final Vector2d pos, final double speed, final double rotationSpeed, final double radius) {
		super(pos);

		this.speed = speed;
		this.roationSpeed = rotationSpeed;

		addComponent(new MoveComponent.Builder().pos(pos).speed(speed).rotationSpeed(roationSpeed).build());
		addComponent(new Move2TargetComponent());
		addComponent(new OnHoverComponent());

		ComponentFactory.addDefaultCollision(this, radius, 100);
	}

	@Override
	public void update(final EntityManager caller, final double delta) {
		if ((boolean) getValue(Move2TargetComponent.TARGET_REACHED)) {
			target = world.getNextWaypoint(target);
			dispatchValue(Move2TargetComponent.SET_TARGET, target.getPos());
		}

		super.update(caller, delta);
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
	}
}
