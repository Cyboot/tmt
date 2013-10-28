package net.tmt.map;

import java.util.ArrayList;

import net.tmt.util.Vector2d;

public class MapController {

	private static final int		PRELOAD_RADIUS	= 3;						// in
																				// chunks
	private static MapController	instance		= null;
	private SpaceMap				spaceMap;
	private ArrayList<Planet>		planets			= new ArrayList<Planet>();

	private MapController() {
		spaceMap = new SpaceMap();
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
		// FIXME: debug code below
		this.getSpaceMap(new Vector2d(0, 0));
		spaceMap.getChunk(coord).addMapObject(new Planet(pid, coord, terrain));
		return pid;
	}

	public Planet getPlanet(final int id) {
		return planets.get(id);
	}

	private Map getMap(final Coordinate coord, final Map map) {
		if (map.existsAround(coord, MapController.PRELOAD_RADIUS)) {
			return map;
		} else {
			return Generator.generateAround(coord, map, MapController.PRELOAD_RADIUS);
		}
	}

	public SpaceMap getSpaceMap() {
		return spaceMap;
	}

	public SpaceMap getSpaceMap(final Vector2d position) {
		Coordinate coord = pos2chunk(position, spaceMap);
		return (SpaceMap) getMap(coord, spaceMap);
	}

	public SpaceMap getSubSpaceMap(final Vector2d position, final int r) {
		Coordinate coord = pos2chunk(position, spaceMap);
		return getMap(coord, spaceMap).subSpaceMap(coord, r);
	}

	public PlanetMap getPlanetMap(final int planetId, final Vector2d position) {
		Coordinate coord = pos2chunk(position, planets.get(planetId).getMap());
		return (PlanetMap) getMap(coord, planets.get(planetId).getMap());
	}

	public PlanetMap getSubPlanetMap(final int planetId, final Vector2d position, final int r) {
		Map map = planets.get(planetId).getMap();
		Coordinate coord = pos2chunk(position, map);
		return getMap(coord, map).subPlanetMapMap(coord, r, planetId);
	}

	public Coordinate pos2chunk(final Vector2d pos, final Map map) {
		return pos2chunk(pos, map.chunkSize);
	}

	public Coordinate pos2chunk(final Vector2d pos, final int chunkSize) {
		int newX = (int) Math.ceil((pos.x / chunkSize) - .5);
		int newY = (int) Math.ceil((pos.y / chunkSize) - .5);
		return new Coordinate(newX, newY);
	}

	public Vector2d chunkCenter2pos(final Coordinate coord, final Map map) {
		return chunkCenter2pos(coord, map.chunkSize);
	}

	public Vector2d chunkCenter2pos(final Coordinate coord, final int chunkSize) {
		double newX = coord.x * chunkSize;
		double newY = coord.y * chunkSize;
		return new Vector2d(newX, newY);
	}
}
