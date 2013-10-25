package net.tmt.map;

public class SpaceMap extends Map {

	public SpaceMap() {
		type = Map.TYPE_SPACE;
		chunkSize = 2000;
		baseTerrain = Map.TERRAIN_VOID;
	}

}
