package net.tmt.map;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.ControlledSpaceShip;
import net.tmt.entity.Entity2D;
import net.tmt.entity.Star;
import net.tmt.entity.Waypoint;
import net.tmt.game.GameEngine;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class World implements Updateable, Renderable {
	private static final double	RATIO			= 1.8;
	private double				MOVE_DIFF_WIDTH;
	private double				MOVE_DIFF_HEIGHT;
	private double				MOVE_MAX_WIDTH;
	private double				MOVE_MAX_HEIGTH;

	private static World		instance;

	private EntityManager		entityManager;
	private MapController		mapController	= MapController.getInstance();

	private Vector2d			tmp				= new Vector2d();
	private Vector2d			offset			= new Vector2d();

	// DEBUG waypoints in World
	private List<Entity2D>		waypoints		= new ArrayList<>();

	private ControlledSpaceShip	player;

	public World(final EntityManager entityManager) {
		this.entityManager = entityManager;

		// TODO: quick & dirty: Worldoffset
		MOVE_MAX_WIDTH = (GameEngine.WIDTH / 2) * 1 / 50.;
		MOVE_DIFF_WIDTH = (GameEngine.WIDTH / 2 - MOVE_MAX_WIDTH) * RATIO;

		MOVE_MAX_HEIGTH = (GameEngine.HEIGHT / 2) * 1 / 50.;
		MOVE_DIFF_HEIGHT = (GameEngine.HEIGHT / 2 - MOVE_MAX_HEIGTH) * RATIO;

		final int DISTANCE = 200;
		final int MAX_MAP_SIZE = 20 * 1000;

		for (int x = -MAX_MAP_SIZE; x < MAX_MAP_SIZE; x += DISTANCE) {
			for (int y = -MAX_MAP_SIZE; y < MAX_MAP_SIZE; y += DISTANCE) {
				double vx = x + RandomUtil.doubleRange(-DISTANCE / 2, DISTANCE / 2);
				double vy = y + RandomUtil.doubleRange(-DISTANCE / 2, DISTANCE / 2);

				this.entityManager.addEntity(new Star(new Vector2d(vx, vy)), EntityManager.LAYER_0_FAR_BACK);
			}
		}

		addWaypoint(new Waypoint(new Vector2d(200, 600)));
		addWaypoint(new Waypoint(new Vector2d(900, 400)));
		addWaypoint(new Waypoint(new Vector2d(100, 200)));
	}

	private void addWaypoint(final Waypoint waypoint) {
		waypoints.add(waypoint);
		entityManager.addEntity(waypoint, EntityManager.LAYER_1_BACK);
	}

	@Override
	public void update(final double delta) {
		centerAroundShip(delta * 1000);
	}

	private void centerAroundShip(final double delta) {
		double dx = getOffsetCentered().x - player.getPos().x;
		double dy = getOffsetCentered().y - player.getPos().y;

		if (dx > MOVE_MAX_WIDTH || dx < -MOVE_MAX_WIDTH) {
			double dxInner = Math.abs(dx) - MOVE_MAX_WIDTH;
			double factor = dxInner / MOVE_DIFF_WIDTH;
			factor = Math.pow(factor, 3);

			if (dx > 0)
				offset.x -= factor * dxInner * delta;
			else
				offset.x += factor * dxInner * delta;
		}

		if (dy > MOVE_MAX_HEIGTH || dy < -MOVE_MAX_HEIGTH) {
			double dyInner = Math.abs(dy) - MOVE_MAX_HEIGTH;
			double factor = dyInner / MOVE_DIFF_HEIGHT;
			factor = Math.pow(factor, 3);

			if (dy > 0)
				offset.y -= factor * dyInner * delta;
			else
				offset.y += factor * dyInner * delta;
		}
	}

	public Vector2d getOffset() {
		return offset;
	}

	private Vector2d getOffsetCentered() {
		tmp.x = offset.x + GameEngine.WIDTH / 2;
		tmp.y = offset.y + GameEngine.HEIGHT / 2;
		return tmp;
	}

	public static World getInstance() {
		return instance;
	}

	public static void init(final EntityManager entityManager) {
		instance = new World(entityManager);
	}

	public void setPlayer(final ControlledSpaceShip ship) {
		this.player = ship;
	}

	/**
	 * get the next waypoint in list (looping)
	 * 
	 * @param waypoint
	 *            current waypoint
	 * @return
	 */
	public Entity2D getNextWaypoint(final Entity2D waypoint) {
		int indexOf = 0;
		indexOf = waypoint == null ? -1 : waypoints.indexOf(waypoint);
		indexOf = ++indexOf % waypoints.size();

		return waypoints.get(indexOf);
	}

	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void render(final Graphics g) {
		g.setColor(Color.GREY);

		// TODO: use submap
		SpaceMap sm = mapController.getSpaceMap(player.getPos());
		for (int x = sm.minX; x <= sm.maxX; x++) {
			for (int y = sm.minY; y <= sm.maxY; y++) {
				Coordinate coord = new Coordinate(x, y);
				if (sm.chunks.containsKey(coord)) {
					g.drawRect(x * sm.chunkSize, y * sm.chunkSize, sm.chunkSize, sm.chunkSize);
				}
			}
		}
	}

}
