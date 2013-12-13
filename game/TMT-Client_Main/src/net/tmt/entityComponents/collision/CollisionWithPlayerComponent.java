package net.tmt.entityComponents.collision;

import net.tmt.game.interfaces.Playable;

public class CollisionWithPlayerComponent extends CollisionComponent {

	public CollisionWithPlayerComponent(final double radius) {
		super(radius);
		super.setCollidableEntityClass(Playable.class);
	}
}
