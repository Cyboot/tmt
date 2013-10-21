package net.tmt.entity.npc;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.Move2TargetComponent;
import net.tmt.entity.component.MoveComponent;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

public class NPCSpaceShip extends Entity2D {
	private World		world			= World.getInstance();

	private double		roationSpeed	= 50;
	private double		speed			= 100;
	private Entity2D	target;

	public NPCSpaceShip(final Vector2d pos) {
		super(pos);

		addComponent(new MoveComponent.Builder().pos(pos).speed(speed).rotationSpeed(roationSpeed).build());
		addComponent(new Move2TargetComponent());

		setSprite(new Sprite("ship_ends_64", 32, 32));
	}

	@Override
	public void update(final double delta) {
		if ((boolean) getValue(Move2TargetComponent.TARGET_REACHED)) {
			target = world.getNextWaypoint(target);
			dispatchValue(Move2TargetComponent.SET_TARGET, target.getPos());
		}

		super.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
	}
}
