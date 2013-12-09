package net.tmt.map;

public class SpaceMap extends WorldMap {
	public static final int	CHUNK_SIZE	= 2048;

	private static SpaceMap	instance	= null;

	public static SpaceMap getInstance() {
		if (instance == null)
			instance = new SpaceMap();
		return instance;
	}

	public SpaceMap() {
		setChunkSize(CHUNK_SIZE);

	}
}
