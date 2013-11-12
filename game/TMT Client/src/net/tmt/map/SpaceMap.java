package net.tmt.map;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.Entity2D;
import net.tmt.entity.statics.Waypoint;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;


public class SpaceMap extends WorldMap {
	public static final int	CHUNK_SIZE	= 248;

	private static SpaceMap	instance	= null;

	private List<Waypoint>	waypoints	= new ArrayList<>();

	/**
	 * Returns the one single SpaceMap that contains all chunks. The constructor
	 * SpaceMap() is only used for sub maps.
	 * 
	 * @return the whole SpaceMap
	 */
	public static SpaceMap getInstance() {
		if (instance == null)
			instance = new SpaceMap();
		return instance;
	}


	/**
	 * This constructor should only ever be used to create sub maps. To get the
	 * "real" SpaceMap use the static getInstance() method.
	 * 
	 * @return an empty SpaceMap
	 */
	public SpaceMap() {
		setChunkSize(SpaceMap.CHUNK_SIZE);
		setBaseTerrain(Terrain.SPACE_VOID);

		// DEBUG: debug stuff
		addWaypoint(new Waypoint(new Vector2d(200, 800)));
		addWaypoint(new Waypoint(new Vector2d(700, 400)));
		addWaypoint(new Waypoint(new Vector2d(1300, 800)));
		addWaypoint(new Waypoint(new Vector2d(1400, 200)));
		addWaypoint(new Waypoint(new Vector2d(500, 100)));
	}

	private void addWaypoint(final Waypoint waypoint) {
		waypoints.add(waypoint);
		addStaticEntity(waypoint);
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
		indexOf = waypoint == null ? RandomUtil.intRange(-1, waypoints.size() - 1) : waypoints.indexOf(waypoint);
		indexOf = ++indexOf % waypoints.size();

		return waypoints.get(indexOf);
	}
}
