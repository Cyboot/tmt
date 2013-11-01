package net.tmt.entity.component.util;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.CollisionComponent;
import net.tmt.entity.component.MoveComponent;
import net.tmt.entity.component.RotateComponent;
import net.tmt.entity.component.SimpleHealthComponent;

public class ComponentFactory {


	/**
	 * Default Move (MoveComponent + RotateComponent)
	 * 
	 * @param entity
	 * @param rotation
	 * @param speed
	 * @param rotationSpeed
	 */
	public static void addDefaultMove(final Entity2D entity, final double rotation, final double speed,
			final double rotationSpeed) {
		entity.addComponent(new RotateComponent(rotation, rotationSpeed));
		entity.addComponent(new MoveComponent(speed, rotation));
	}

	/**
	 * Default Collision (CollisionComponent + SimpleHealthComponent)
	 * 
	 * @param entity
	 * @param radius
	 * @param health
	 * @param ignoredEntity
	 */
	public static void addDefaultCollision(final Entity2D entity, final double radius, final double health,
			final Entity2D ignoredEntity) {
		entity.addComponent(new CollisionComponent(radius, ignoredEntity));
		entity.addComponent(new SimpleHealthComponent(health));
	}

	/**
	 * {@link ComponentFactory#addDefaultCollision(Entity2D, double, double, Entity2D)}
	 * 
	 * @param entity
	 * @param radius
	 * @param health
	 */
	public static void addDefaultCollision(final Entity2D entity, final double radius, final double health) {
		addDefaultCollision(entity, radius, health, null);
	}
}
