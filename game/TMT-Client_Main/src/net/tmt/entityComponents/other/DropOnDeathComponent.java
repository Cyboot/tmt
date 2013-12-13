package net.tmt.entityComponents.other;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;
import net.tmt.entityComponents.collision.SimpleHealthComponent;
import net.tmt.game.manager.EntityManager;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class DropOnDeathComponent extends Component {

	private List<Entity2D>	drops	= new ArrayList<Entity2D>();

	public DropOnDeathComponent(final List<Entity2D> drops) {
		this.drops = drops;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		SimpleHealthComponent shc = caller.getComponent(SimpleHealthComponent.class);
		if (shc != null && caller.isSet(SimpleHealthComponent.HEALTH)) {
			double health = (double) caller.getValue(SimpleHealthComponent.HEALTH);
			if (health <= 0) {
				drop(caller);
			}
		}
	}

	private void drop(final ComponentDispatcher caller) {
		Vector2d dropCenter = caller.getOwner().getPos();
		EntityManager em = caller.getEntityManager();
		for (Entity2D e : drops) {
			double xSpread = RandomUtil.doubleRange(-3, 3);
			double ySpread = RandomUtil.doubleRange(-3, 3);
			double x = dropCenter.x + xSpread;
			double y = dropCenter.y + ySpread;
			e.getPos().set(x, y);
			em.addEntity(e);
		}
	}
}
