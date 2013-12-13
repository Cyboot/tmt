package net.tmt.entityComponents.collision;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;
import net.tmt.gfx.Graphics;
import net.tmt.util.DebugUtil;

import org.lwjgl.util.Color;

public class CollisionComponent extends Component {
	public static final String	IS_COLLISION		= "COLLISION";
	public static final String	COLLISION_ENTITIES	= "COLLISION_ENTITIES";

	private double				radius;
	private Entity2D			ignoredEntity;
	private Class				ignoredClass;
	private Class<?>			collidableEntity	= null;
	private boolean				isCollision;
	private List<Entity2D>		collisonEntities	= new ArrayList<>();

	public CollisionComponent(final double radius, final Entity2D ignoredEntity) {
		this.radius = radius;
		this.ignoredEntity = ignoredEntity;
	}

	public CollisionComponent(final Class ignoredClass, final double radius) {
		this.radius = radius;
		this.ignoredClass = ignoredClass;
	}

	public CollisionComponent(final double radius) {
		this(radius, null);
	}

	@Override
	public void render(final ComponentDispatcher caller, final Graphics g) {
		if (!DebugUtil.renderCollision)
			return;

		if (isCollision)
			g.setColor(Color.YELLOW);
		else
			g.setColor(Color.PURPLE);

		g.drawCircle(pos.x, pos.y, radius);
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		List<List<Entity2D>> entityList = caller.getEntityManager().getCollidableEntities(pos);

		isCollision = false;
		collisonEntities.clear();

		for (List<Entity2D> entities : entityList) {
			for (Entity2D e : entities) {
				// don't collide with yourself
				if (e == owner || owner == e.getOwner())
					continue;
				// don't collide with other entities that the collidableEntity
				// (if
				// is set)
				if (collidableEntity != null && !collidableEntity.isInstance(e))
					continue;
				// don't collide with ignored entity
				if (collidableEntity == null && e == ignoredEntity)
					continue;
				if (collidableEntity == null && e.getClass() == ignoredClass)
					continue;

				double radius2 = e.getCollisionComponent().getRadius();

				double dist = pos.distanceTo(e.getPos());
				if (dist < radius2 + radius) {
					isCollision = true;
					collisonEntities.add(e);
				}
			}
		}

		caller.dispatch(IS_COLLISION, isCollision);
		caller.dispatch(COLLISION_ENTITIES, collisonEntities);
	}

	public double getRadius() {
		return radius;
	}

	public void setCollidableEntityClass(final Class<?> collidableEntity) {
		this.collidableEntity = collidableEntity;
	}
}
