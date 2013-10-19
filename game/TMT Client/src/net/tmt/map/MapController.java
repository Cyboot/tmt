package net.tmt.map;

import java.util.ArrayList;

import net.tmt.util.Vector2d;

public class MapController {

	private static final int		PRELOAD_RADIUS	= 3;	// in chunks
	private static MapController	instance		= null;
	private SpaceMap				spaceMap;
	private ArrayList<Planet>		planets;

	private MapController() {
		spaceMap = new SpaceMap();
		planets = new ArrayList<Planet>();
	}

	public static MapController getInstance() {
		if (instance == null) {
			instance = new MapController();
		}
		return instance;
	}

	private Map getMap(final Vector2d position, final Map map) {
		Coordinate currChunk = pos2chunk(position);
		if (spaceMap.existsAround(currChunk, MapController.PRELOAD_RADIUS)) {
			return map;
		} else {
			return Generator.generateAround(currChunk, map, MapController.PRELOAD_RADIUS);
		}
	}

	public SpaceMap getSpaceMap(final Vector2d position) {
		return (SpaceMap) getMap(position, spaceMap);
	}

	public PlanetMap getPlanetMap(final int planetId, final Vector2d position) {
		return (PlanetMap) getMap(position, planets.get(planetId).getMap());
	}

	private Coordinate pos2chunk(final Vector2d pos) {
		int newX = (int) (pos.x > 0 ? Math.ceil(pos.x) : Math.floor(pos.x));
		int newY = (int) (pos.y > 0 ? Math.ceil(pos.y) : Math.floor(pos.y));
		return new Coordinate(newX, newY);
	}
}
