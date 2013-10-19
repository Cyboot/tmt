package net.tmt.map;

import java.util.ArrayList;

import net.tmt.util.Vector2d;

public class MapController {

	private static final int		PRELOAD_RADIUS	= 100;
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

	public SpaceMap getSpaceMap(final Vector2d position) {
		/*
		 * TODO: check if map for requested position does exist, if not ->
		 * generate
		 */
		return spaceMap;
	}

	public PlanetMap getPlanetMap(final int planetId, final Vector2d position) {
		return null;
	}

}
