package net.tmt.map;


public class PlanetMap extends WorldMap {

	public static final int	CHUNK_SIZE	= 64;

	public PlanetMap(final Terrain baseTerrain) {
		setChunkSize(CHUNK_SIZE);
	}

}
