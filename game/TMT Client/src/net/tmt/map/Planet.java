package net.tmt.map;

import net.tmt.util.Vector2d;

public class Planet {

	private Vector2d	position;
	private int			id;
	private PlanetMap	map;

	public Planet(final Vector2d pos, final int baseTerrain) {
		position = pos;
		map = new PlanetMap(baseTerrain);
	}

}
