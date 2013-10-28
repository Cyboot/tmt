package net.tmt.entity.component.util;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.CollisionComponent;
import net.tmt.entity.component.SimpleHealthComponent;

public class ComponentFactory {

	public static void addDefaultCollision(final Entity2D entity, final double radius, final double health,
			final Entity2D ignoredEntity) {
		entity.addComponent(new CollisionComponent(radius, ignoredEntity));
		entity.addComponent(new SimpleHealthComponent(health));
	}

	public static void addDefaultCollision(final Entity2D entity, final double radius, final double health) {
		addDefaultCollision(entity, radius, health, null);
	}
}
