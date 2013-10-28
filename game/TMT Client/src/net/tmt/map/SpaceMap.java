package net.tmt.map;

public class SpaceMap extends Map {

	public static final int	CHUNK_SIZE	= 2000;

	public SpaceMap() {
		type = Map.TYPE_SPACE;
		chunkSize = SpaceMap.CHUNK_SIZE;
		baseTerrain = Map.TERRAIN_VOID;
	}

}
