package net.tmt.map;


public class PlanetMap extends WorldMap {

	public static final int	CHUNK_SIZE	= 300;

	public PlanetMap(final int base) {
		setType(WorldMap.TYPE_PLANET);
		chunkSize = CHUNK_SIZE;
		setBaseTerrain(base);
	}

}
