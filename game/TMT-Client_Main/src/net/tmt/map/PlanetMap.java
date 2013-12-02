package net.tmt.map;

import net.tmt.entity.statics.Planet;


public class PlanetMap extends WorldMap {
	private int				uniqueId;
	public static final int	CHUNK_SIZE	= 64;

	public PlanetMap(final Terrain baseTerrain, final int uniqueId) {
		setChunkSize(CHUNK_SIZE);
		setBaseTerrain(baseTerrain);
		this.uniqueId = uniqueId;
	}

	public PlanetMap(final Planet planet) {
		this(planet.getBaseTerrain(), planet.getPos().hashCode());
	}

	@Override
	public int hashCode() {
		return uniqueId;
	}
}
