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

	public int addPlanet(final Coordinate coord) {
		int pid = planets.size();
		int terrain = (int) Math.random() * 5 + Map.TYPE_PLANET;
		Planet p = new Planet(planets.size(), coord, terrain);
		planets.add(p);
		return pid;
	}

	public Planet getPlanet(final int id) {
		return planets.get(id);
	}

	private Map getMap(final Vector2d position, final Map map) {
		Coordinate currChunk = pos2chunk(position, map);
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

	public Coordinate pos2chunk(final Vector2d pos, final Map map) {
		int newX = (int) Math.ceil((pos.x / map.chunkSize) - .5);
		int newY = (int) Math.ceil((pos.y / map.chunkSize) - .5);
		return new Coordinate(newX, newY);
	}
}
