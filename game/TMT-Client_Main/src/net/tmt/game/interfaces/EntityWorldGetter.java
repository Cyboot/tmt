package net.tmt.game.interfaces;

import net.tmt.game.manager.EntityManager;
import net.tmt.map.World;

public interface EntityWorldGetter {
	public World getWorld();

	public EntityManager getEntityManager();
}
