package net.tmt.entity.component;

import java.util.List;

import net.tmt.entity.Entity2D;

public class SimpleHealthComponent extends Component {
	public static final String	HEALTH			= "HEALTH";
	public static final String	DAMAGE			= "DAMAGE";

	private static final double	COLLISON_DAMAGE	= 10;

	private double				damage			= COLLISON_DAMAGE;
	private double				health;


	public SimpleHealthComponent(final double health, final double damage) {
		this.health = health;
		this.damage = damage;
	}

	public SimpleHealthComponent(final double health) {
		this(health, COLLISON_DAMAGE);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (caller.isSet(CollisionComponent.IS_COLLISION)
				&& ((boolean) caller.getValue(CollisionComponent.IS_COLLISION) == true)) {

			// iterate over all collidable entities
			for (Entity2D e : ((List<Entity2D>) caller.getValue(CollisionComponent.COLLISION_ENTITIES))) {
				double dmg = (double) e.getValue(DAMAGE);

				if (dmg != COLLISON_DAMAGE)
					health -= dmg;
				else
					health -= COLLISON_DAMAGE * delta;
			}
		}

		// kill owner if health < 0
		if (health <= 0) {
			owner.kill();
		}
		caller.dispatch(HEALTH, health);
	}

	@Override
	public void initialDispatch(final ComponentDispatcher caller) {
		super.initialDispatch(caller);
		caller.dispatch(DAMAGE, damage);
	}
}
