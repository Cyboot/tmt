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
	private static final int				GRID_SIZE			= 2048;

	public static final int					LAYER_0_FAR_BACK	= 0;
	public static final int					LAYER_1_BACK		= 1;
	public static final int					LAYER_2_MEDIUM		= 2;						// default
	public static final int					LAYER_3_FRONT		= 3;
	public static final int					LAYER_4_GUI			= 4;

	/** Map<Layer , List<Entity>> */
	private Map<Integer, List<Entity2D>>	entityMap			= new HashMap<>();

	private AddRemove						addremove			= new AddRemove();
	private Collision						collision			= new Collision();
	private Vector2d						worldOffset;

	private double							renderDistance;
	private double							updateDistance;
	private int								visibleEntities;
	private CollisionsManager				collisionsManager	= new CollisionsManager();

	public EntityManager() {
		// Init Entitymap
		entityMap.put(LAYER_0_FAR_BACK, new ArrayList<Entity2D>());
		entityMap.put(LAYER_1_BACK, new ArrayList<Entity2D>());
		entityMap.put(LAYER_2_MEDIUM, new ArrayList<Entity2D>());
		entityMap.put(LAYER_3_FRONT, new ArrayList<Entity2D>());
		entityMap.put(LAYER_4_GUI, new ArrayList<Entity2D>());
	}

	public void update(final World world, final double delta) {
		worldOffset = world.getOffset().copy();

		double w = ZoomManager.getWidthZoomed();
		double h = ZoomManager.getHeightZoomed();
		renderDistance = Math.sqrt(w * w + h * h) + 512;
		updateDistance = Math.max(world.getMap().getChunkSize() * 5, renderDistance * 2);

		for (Entry<Integer, List<Entity2D>> entry : entityMap.entrySet()) {
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
					if (e.isCollisable())
						collision.move(tmp, e.getPos(), e);
				} else {
					addremove.remove(e, key);
				}
			}
		}
		collisionsManager.update(delta);

		GuiManager.getInstance().dispatch(Gui.DEBUG_ENTITY_COUNT, getEntityCount());
	}

	@Override
	public void render(final Graphics g) {
		visibleEntities = 0;
		renderLayer(LAYER_0_FAR_BACK, g);
		renderLayer(LAYER_1_BACK, g);
		renderLayer(LAYER_2_MEDIUM, g);
		renderLayer(LAYER_3_FRONT, g);
		renderLayer(LAYER_4_GUI, g);
		GuiManager.getInstance().dispatch(Gui.DEBUG_INFO_4, "Visible Entities: " + visibleEntities);

		// manages the addition and removal of entities
		// must be called here in end of render() to make sure entitiy.update()
		// is called before entitiy.render() even for newly added Entities
		addremove.update();
	}

	private void renderLayer(final int layer, final Graphics g) {
		for (Entity2D e : entityMap.get(layer)) {
			// don't render entities far way
			Vector2d pos = e.getPos();
			if (pos.distanceTo(worldOffset) > renderDistance)
				continue;

			e.render(g);
			visibleEntities++;
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

	public CollisionsManager getCollisionsManager() {
		return collisionsManager;
	}

	public List<List<Entity2D>> getCollidableEntities(final Vector2d v) {
		List<List<Entity2D>> result = new ArrayList<>();

		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				Vector2d vector = Vector2d.tmp1.set(v);
				long key = collision.getKey(vector.add(GRID_SIZE * x, GRID_SIZE * y));


				result.add(collision.getSubAreaForCollidable(key));
			}
		}

		return result;
	}

	public int getEntityCount() {
		int result = 0;
		for (List<Entity2D> list : entityMap.values())
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
						if (e.isCollisable()) {
							collision.add(e);
						}

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

					for (Entity2D e : list) {
						if (e.isCollisable())
							collision.remove(e);
					}


					list.clear();
				}
				dirtyRemove = false;
			}
		}
	}

	private class Collision {
		private Map<Long, List<Entity2D>>	collidableEntities	= new HashMap<>();

		private long getKey(final Vector2d v) {
			long x = v.x() > 0 ? v.x() : Integer.MAX_VALUE + v.x();
			long y = v.y() > 0 ? v.y() : Integer.MAX_VALUE + v.y();
			x /= GRID_SIZE;
			y /= GRID_SIZE;

			y <<= 32;

			return x | y;
		}


		public void move(final Vector2d oldPos, final Vector2d newPos, final Entity2D entity) {
			long keyOld = getKey(oldPos);
			long keyNew = getKey(newPos);

			if (keyOld != keyNew) {
				remove(entity);
				add(entity);
			}
		}

		public void remove(final Entity2D e) {
			getSubAreaForCollidable(getKey(e.getPos())).remove(e);
		}


		public void add(final Entity2D e) {
			getSubAreaForCollidable(getKey(e.getPos())).add(e);
		}

		private List<Entity2D> getSubAreaForCollidable(final long key) {
			List<Entity2D> result = collidableEntities.get(key);

			if (result == null) {
				List<Entity2D> value = new ArrayList<>();
				collidableEntities.put(key, value);
				return value;
			} else
				return result;
		}
	}

}
