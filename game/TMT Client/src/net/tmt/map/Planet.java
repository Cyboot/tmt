package net.tmt.map;


public class Planet {

	private int			id;
	private Coordinate	coord;
	private PlanetMap	map;

	public Planet(final int i, final Coordinate c, final int baseTerrain) {
		id = i;
		coord = c;
		map = new PlanetMap(baseTerrain);
	}

	public PlanetMap getMap() {
		return map;
	}

	public int getId() {
		return id;
	}

}
