package net.tmt.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tmt.entity.Entity2D;
import net.tmt.entity.PlayerSpaceShip;
import net.tmt.entity.statics.Waypoint;
import net.tmt.game.GameEngine;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.game.manager.EntityManager;
import net.tmt.game.manager.GameManager;
import net.tmt.gamestate.PlanetGamestate;
import net.tmt.gfx.Graphics;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class World implements Updateable, Renderable {
	private static final double		RATIO		= 1.8;
	private double					MOVE_DIFF_WIDTH;
	private double					MOVE_DIFF_HEIGHT;
	private double					MOVE_MAX_WIDTH;
	private double					MOVE_MAX_HEIGTH;

	private static World			instance;

	private EntityManager			entityManager;
	private SpaceMap				spaceMap;
	private WorldMap				currentMap;
	private Map<Integer, PlanetMap>	planetMaps	= new HashMap<Integer, PlanetMap>();

	private Vector2d				tmp			= new Vector2d();
	private Vector2d				offset		= new Vector2d();

	// DEBUG waypoints in World
	private List<Entity2D>			waypoints	= new ArrayList<>();

	private PlayerSpaceShip			player;

	public World() {
		// TODO: quick & dirty: Worldoffset
		MOVE_MAX_WIDTH = (GameEngine.WIDTH / 2) * 1 / 50.;
		MOVE_DIFF_WIDTH = (GameEngine.WIDTH / 2 - MOVE_MAX_WIDTH) * RATIO;

		MOVE_MAX_HEIGTH = (GameEngine.HEIGHT / 2) * 1 / 50.;
		MOVE_DIFF_HEIGHT = (GameEngine.HEIGHT / 2 - MOVE_MAX_HEIGTH) * RATIO;
	}

	void addWaypoint(final Waypoint waypoint) {
		waypoints.add(waypoint);
	}

	@Override
	public void update(final double delta) {
		centerAroundShip(delta * 1000);
		currentMap.update(this, getOffsetCentered(), player.getPos());
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
		if (instance == null)
			instance = new World();
		return instance;
	}

	public void init() {
		spaceMap = SpaceMap.getInstance();
		currentMap = spaceMap;
	}

	public void setPlayer(final PlayerSpaceShip ship) {
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
		indexOf = waypoint == null ? RandomUtil.intRange(-1, waypoints.size()) : waypoints.indexOf(waypoint);
		indexOf = ++indexOf % waypoints.size();

		return waypoints.get(indexOf);
	}

	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void render(final Graphics g) {
		currentMap.render(g);
	}

	public void setCurrentMap(final WorldMap m) {
		currentMap = m;
	}

	public void registerPlanetmap(final int id, final PlanetMap map) {
		planetMaps.put(Integer.valueOf(id), map);
	}

	public void changeToPlanetGameState(final int planetId) {
		GameManager gm = GameManager.getInstance();
		gm.pause(gm.getActiveGamestate());
		gm.resume(PlanetGamestate.class);

		PlanetMap map = planetMaps.get(Integer.valueOf(planetId));
		// TODO: don't just use the "pace position"
		map.update(this, getOffset(), player.getPos());
		setCurrentMap(map);
	}
}
