package net.tmt.entity.component;

import java.util.List;

import net.tmt.entity.DestroyAnimation;
import net.tmt.entity.Entity2D;

public class SimpleHealthComponent extends Component {
	public static final String	HEALTH			= "HEALTH";

	private static final double	COLLISON_DAMAGE	= 100;

	private double				health;

	public SimpleHealthComponent(final double health) {
		this.health = health;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (caller.isSet(CollisionComponent.IS_COLLISION)
				&& ((boolean) caller.getValue(CollisionComponent.IS_COLLISION) == true)) {

			// iterate over all collidable entities
			for (Entity2D e : ((List<Entity2D>) caller.getValue(CollisionComponent.COLLISION_ENTITIES))) {
				health -= COLLISON_DAMAGE * delta;
			}
		}

		// kill owner if health < 0
		if (health <= 0) {
			owner.kill();
			caller.getEntityManager().addEntity(new DestroyAnimation(owner.getPos().copy()));
		}
		caller.dispatch(HEALTH, health);
	}
}
