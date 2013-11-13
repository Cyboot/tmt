package net.tmt.entity.component.collision;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.gfx.Graphics;
import net.tmt.util.DebugUtil;

import org.lwjgl.util.Color;

public class CollisionComponent extends Component {
	public static final String	IS_COLLISION		= "COLLISION";
	public static final String	COLLISION_ENTITIES	= "COLLISION_ENTITIES";

	private double				radius;
	private Entity2D			ignoredEntity;
	private boolean				isCollision;
	private List<Entity2D>		collisonEntities	= new ArrayList<>();

	public CollisionComponent(final double radius, final Entity2D ignoredEntity) {
		this.radius = radius;
		this.ignoredEntity = ignoredEntity;
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
		List<Entity2D> entities = caller.getEntityManager().getCollidableEntities();

		isCollision = false;
		collisonEntities.clear();
		for (Entity2D e : entities) {
			// don't collide with yourself and ignored Entity
			if (e == owner || e == ignoredEntity || owner == e.getOwner())
				continue;

			double radius2 = e.getComponent(CollisionComponent.class).getRadius();

			double dist = pos.distanceTo(e.getPos());
			if (dist < radius2 + radius) {
				isCollision = true;
				collisonEntities.add(e);
			}
		}
		caller.dispatch(IS_COLLISION, isCollision);
		caller.dispatch(COLLISION_ENTITIES, collisonEntities);
	}

	public double getRadius() {
		return radius;
	}
}