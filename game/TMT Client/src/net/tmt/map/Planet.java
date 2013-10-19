package net.tmt.map;

import net.tmt.util.Vector2d;

public class Planet {

	private int			id;
	private Vector2d	position;
	private PlanetMap	map;

	public Planet(final int i, final Vector2d pos, final int baseTerrain) {
		id = i;
		position = pos;
		map = new PlanetMap(baseTerrain);
	}

	public PlanetMap getMap() {
		return map;
	}

}
