package net.tmt.entity.bullets;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.move.Move2TargetComponent;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

public class TargetRocket extends Rocket {
	private static double	speed			= 500;
	private static double	rotationSpeed	= 180;
	private Entity2D		target;

	public TargetRocket(final Vector2d pos, final double rotation, final Entity2D target, final Entity2D owner) {
		super(pos, rotation, speed, owner, 3, rotationSpeed);

		this.target = target;

		setSprite(new Sprite("rocket_32", 32, 32));
		addComponent(new Move2TargetComponent(16));
	}

	@Override
	public void update(final EntityManager caller, World world, final double delta) {
		if (isSet(Move2TargetComponent.TARGET_REACHED) && (boolean) getValue(Move2TargetComponent.TARGET_REACHED))
			kill();

		if (target != null)
			dispatchValue(Move2TargetComponent.SET_TARGET, target.getPos());

		super.update(caller, world, delta);
	}
}
