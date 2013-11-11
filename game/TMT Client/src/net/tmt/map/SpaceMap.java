package net.tmt.map;

import net.tmt.entity.statics.Waypoint;
import net.tmt.util.Vector2d;


public class SpaceMap extends WorldMap {

	public static final int	BASE_TERRAIN_SPACE	= WorldMap.TERRAIN_VOID;
	public static final int	CHUNK_SIZE			= 2000;

	private static SpaceMap	instance			= null;
	private World			world				= World.getInstance();

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
		setType(WorldMap.TYPE_SPACE);
		chunkSize = SpaceMap.CHUNK_SIZE;
		setBaseTerrain(BASE_TERRAIN_SPACE);
		// DEBUG: debug stuff
		addWaypoint(new Waypoint(new Vector2d(200, 800)));
		addWaypoint(new Waypoint(new Vector2d(700, 400)));
		addWaypoint(new Waypoint(new Vector2d(1300, 800)));
		addWaypoint(new Waypoint(new Vector2d(1400, 200)));
		addWaypoint(new Waypoint(new Vector2d(500, 100)));
	}

	public void addWaypoint(final Waypoint waypoint) {
		world.addWaypoint(waypoint);
		addStaticEntity(world, waypoint);
	}

}
