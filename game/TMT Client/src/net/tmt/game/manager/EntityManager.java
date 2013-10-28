package net.tmt.game.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.tmt.entity.Entity2D;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.gfx.Graphics;

/**
 * <pre>
 * manages Entities: 
 *   - updates
 *   - renders (according to LAYER)
 *   - adds new entities
 *   - removes dead entities
 * </pre>
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public class EntityManager implements Updateable, Renderable {
	public static final int					LAYER_0_FAR_BACK	= 0;
	public static final int					LAYER_1_BACK		= 1;
	public static final int					LAYER_2_MEDIUM		= 2;					// default
	public static final int					LAYER_3_FRONT		= 3;
	public static final int					LAYER_4_GUI			= 4;

	/** Map<Layer , List<Entity>> */
	private Map<Integer, List<Entity2D>>	entityMap			= new HashMap<>();
	private List<Entity2D>					collidableEntities	= new ArrayList<>();

	private AddRemove						addremove			= new AddRemove();

	public EntityManager() {
		// Init Entitymap
		entityMap.put(LAYER_0_FAR_BACK, new ArrayList<Entity2D>());
		entityMap.put(LAYER_1_BACK, new ArrayList<Entity2D>());
		entityMap.put(LAYER_2_MEDIUM, new ArrayList<Entity2D>());
		entityMap.put(LAYER_3_FRONT, new ArrayList<Entity2D>());
		entityMap.put(LAYER_4_GUI, new ArrayList<Entity2D>());
	}

	@Override
	public void update(final double delta) {
		for (Entry<Integer, List<Entity2D>> entry : entityMap.entrySet()) {
			Integer key = entry.getKey();
			List<Entity2D> list = entry.getValue();

			for (Entity2D e : list) {
				if (e.isAlive()) {
					e.update(delta, this);
				} else {
					addremove.remove(e, key);
				}
			}
		}

		// manages the addition and removal of entities
		addremove.update();
	}

	@Override
	public void render(final Graphics g) {
		renderLayer(LAYER_0_FAR_BACK, g);
		renderLayer(LAYER_1_BACK, g);
		renderLayer(LAYER_2_MEDIUM, g);
		renderLayer(LAYER_3_FRONT, g);
		renderLayer(LAYER_4_GUI, g);
	}

	private void renderLayer(final int layer, final Graphics g) {
		for (Entity2D e : entityMap.get(layer)) {
			e.render(g);
		}
	}

	/**
	 * adds Entity, on Default layer: <b>LAYER_2_MEDIUM</b>
	 * 
	 * @param entity
	 */
	public void addEntity(final Entity2D entity) {
		addEntity(entity, LAYER_2_MEDIUM);
	}

	/**
	 * adds Entity to given layer
	 * 
	 * @param entity
	 * @param layer
	 */
	public void addEntity(final Entity2D entity, final int layer) {
		addremove.add(entity, layer);
	}

	/**
	 * manages the addition and removal of entities
	 */
	private class AddRemove {
		private Map<Integer, List<Entity2D>>	entitiesToAdd		= new HashMap<>();
		private Map<Integer, List<Entity2D>>	entitiesToDelete	= new HashMap<>();
		private boolean							dirtyRemove			= false;
		private boolean							dirtyAdd			= false;

		public AddRemove() {
			entitiesToAdd.put(LAYER_0_FAR_BACK, new ArrayList<Entity2D>());
			entitiesToAdd.put(LAYER_1_BACK, new ArrayList<Entity2D>());
			entitiesToAdd.put(LAYER_2_MEDIUM, new ArrayList<Entity2D>());
			entitiesToAdd.put(LAYER_3_FRONT, new ArrayList<Entity2D>());
			entitiesToAdd.put(LAYER_4_GUI, new ArrayList<Entity2D>());

			entitiesToDelete.put(LAYER_0_FAR_BACK, new ArrayList<Entity2D>());
			entitiesToDelete.put(LAYER_1_BACK, new ArrayList<Entity2D>());
			entitiesToDelete.put(LAYER_2_MEDIUM, new ArrayList<Entity2D>());
			entitiesToDelete.put(LAYER_3_FRONT, new ArrayList<Entity2D>());
			entitiesToDelete.put(LAYER_4_GUI, new ArrayList<Entity2D>());
		}

		public void remove(final Entity2D e, final int key) {
			entitiesToDelete.get(key).add(e);
			dirtyRemove = true;
		}

		public void add(final Entity2D entity, final int layer) {
			entitiesToAdd.get(layer).add(entity);
			dirtyAdd = true;
		}

		public void update() {
			// Add Entities to Map/Lists
			if (dirtyAdd) {
				for (Entry<Integer, List<Entity2D>> entry : entitiesToAdd.entrySet()) {
					Integer key = entry.getKey();
					List<Entity2D> list = entry.getValue();

					for (Entity2D e : list) {
						// Entity is collidable? --> add to collidable List
						if (e.isCollisable())
							collidableEntities.add(e);

						entityMap.get(key).add(e);
					}
					list.clear();
				}
				dirtyAdd = false;
			}

			// remove dead Entities
			if (dirtyRemove) {
				for (Entry<Integer, List<Entity2D>> entry : entitiesToDelete.entrySet()) {
					Integer key = entry.getKey();
					List<Entity2D> list = entry.getValue();

					// remove dead entities from all Lists
					entityMap.get(key).removeAll(list);
					collidableEntities.removeAll(list);
					list.clear();
				}
				dirtyRemove = false;
			}
		}
	}
}
