package net.tmt.map;


public class PlanetMap extends Map {

	public static final int	CHUNK_SIZE	= 300;

	public PlanetMap(final int base) {
		setType(Map.TYPE_PLANET);
		chunkSize = CHUNK_SIZE;
		setBaseTerrain(base);
	}

}
