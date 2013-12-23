package net.tmt.game.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.tmt.entity.Entity2D;
import net.tmt.game.interfaces.Renderable;
import net.tmt.gfx.Graphics;
import net.tmt.gui.Gui;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

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
public class EntityManager implements Renderable {
	public static final short				LAYER_COUNT			= 128;
	/** Map<Layer , List<Entity>> */
	private Map<Integer, List<Entity2D>>	renderMap			= new HashMap<>();
	private List<Entity2D>					entityList			= new ArrayList<>();

	private CollisionManager				collisionManager	= new CollisionManager();
	private AddRemove						addremove			= new AddRemove();
	private Vector2d						worldOffset;

	private double							renderDistance;
	private double							updateDistance;
	private int								visibleEntitiesCount;


	public EntityManager() {
		// Init Entitymap
		for (int i = 0; i < LAYER_COUNT; i++) {
			renderMap.put(i, new ArrayList<Entity2D>());
		}
	}

	public void update(final World world, final double delta) {
		worldOffset = world.getOffset().copy();

		double w = ZoomManager.getWidthZoomed();
		double h = ZoomManager.getHeightZoomed();
		renderDistance = Math.sqrt(w * w + h * h) + 512;
		updateDistance = Math.max(world.getMap().getChunkSize() * 5, renderDistance * 2);

		for (Entry<Integer, List<Entity2D>> entry : renderMap.entrySet()) {
			Integer key = entry.getKey();
			List<Entity2D> list = entry.getValue();

			Vector2d tmp = new Vector2d();
			for (Entity2D e : list) {
				// don't update entities far way
				Vector2d pos = e.getPos();
				if (pos.distanceTo(worldOffset) > updateDistance)
					continue;

				if (e.isAlive()) {
					tmp.set(e.getPos());
					e.update(this, world, delta);
				} else {
					addremove.remove(e, key);
				}
			}
		}
		collisionManager.update(delta);

		GuiManager.getInstance().dispatch(Gui.DEBUG_ENTITY_COUNT, getEntityCount());
	}

	@Override
	public void render(final Graphics g) {
		visibleEntitiesCount = 0;

		for (int i = 0; i < LAYER_COUNT; i++) {
			renderLayer(i, g);
		}

		GuiManager.getInstance().dispatch(Gui.DEBUG_INFO_4, "Visible Entities: " + visibleEntitiesCount);

		// manages the addition and removal of entities
		// must be called here in end of render() to make sure entitiy.update()
		// is called before entitiy.render() even for newly added Entities
		addremove.update();
	}

	private void renderLayer(final int layer, final Graphics g) {
		List<Entity2D> list = renderMap.get(layer);
		if (list.isEmpty())
			return;

		for (Entity2D e : list) {
			// don't render entities far way
			Vector2d pos = e.getPos();
			if (pos.distanceTo(worldOffset) > renderDistance)
				continue;

			e.render(g);
			visibleEntitiesCount++;
		}
	}

	/**
	 * adds Entity, on Default layer: <b>LAYER_2_MEDIUM</b>
	 * 
	 * @param entity
	 */
	public void addEntity(final Entity2D entity) {
		addremove.add(entity, entity.getZ());
	}


	public CollisionManager getCollisionManager() {
		return collisionManager;
	}

	public int getEntityCount() {
		int result = 0;
		for (List<Entity2D> list : renderMap.values())
			result += list.size();

		return result;
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
			for (int i = 0; i < LAYER_COUNT; i++) {
				entitiesToAdd.put(i, new ArrayList<Entity2D>());
				entitiesToDelete.put(i, new ArrayList<Entity2D>());
			}
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
				for (int key = 0; key < LAYER_COUNT; key++) {
					List<Entity2D> list = entitiesToAdd.get(key);

					entityList.addAll(list);
					renderMap.get(key).addAll(list);

					list.clear();
				}
				dirtyAdd = false;
			}

			// remove Entities from Map/Lists
			if (dirtyRemove) {
				for (int key = 0; key < LAYER_COUNT; key++) {
					List<Entity2D> list = entitiesToDelete.get(key);

					entityList.removeAll(list);
					renderMap.get(key).removeAll(list);

					list.clear();
				}
				dirtyRemove = false;
			}
		}
	}
}
