package net.tmt.entity.component;

import java.util.List;

import net.tmt.entity.Entity2D;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class CollisionComponent extends Component {
	public static final String	COLLISION	= "COLLISION";

	private double				radius;
	private Vector2d			pos;
	private Entity2D			ignoredEntity;
	private boolean				isCollision;

	public CollisionComponent(final double radius, final Entity2D ignoredEntity) {
		this.radius = radius;
		this.ignoredEntity = ignoredEntity;
	}

	public CollisionComponent(final double radius) {
		this(radius, null);
	}

	@Override
	public void render(final ComponentDispatcher caller, final Graphics g) {
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
		for (Entity2D e : entities) {
			// don't collide with yourself and ignored Entity
			if (e == owner || e == ignoredEntity || owner == e.getOwner())
				continue;

			double radius2 = e.getComponent(CollisionComponent.class).getRadius();

			double dist = pos.distanceTo(e.getPos());
			if (dist < radius2 + radius) {
				isCollision = true;
			}
		}
		caller.dispatch(COLLISION, isCollision);
	}

	public double getRadius() {
		return radius;
	}

	@Override
	public void initialDispatch(final ComponentDispatcher caller) {
		super.initialDispatch(caller);
		pos = owner.getPos();
	}
}
