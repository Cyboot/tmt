package net.tmt.entity.component.util;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.CollisionComponent;
import net.tmt.entity.component.EngineAnimationComponent;
import net.tmt.entity.component.KillAnimationComponent;
import net.tmt.entity.component.MoveComponent;
import net.tmt.entity.component.RotateComponent;
import net.tmt.entity.component.SimpleHealthComponent;
import net.tmt.util.Vector2d;

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
	 * Default Collision (CollisionComponent + SimpleHealthComponent +
	 * KillAnimation)
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
		entity.addComponent(new KillAnimationComponent());
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


	/**
	 * Adds EngineAnmiation for MainEngine and Left and Right
	 * 
	 * @param entity
	 * @param offsetEngineMain
	 * @param offsetEngineRight
	 * @param offsetEngineLeft
	 */
	public static void add3EngineAnimation(final Entity2D entity, final Vector2d offsetEngineMain,
			final Vector2d offsetEngineRight, final Vector2d offsetEngineLeft) {
		entity.addComponent(new EngineAnimationComponent(offsetEngineMain, true, 16, EngineAnimationComponent.ENGINE_1));
		entity.addComponent(new EngineAnimationComponent(offsetEngineLeft, false, 12, EngineAnimationComponent.ENGINE_2));
		entity.addComponent(new EngineAnimationComponent(offsetEngineRight, false, 12,
				EngineAnimationComponent.ENGINE_3));

	}
}
