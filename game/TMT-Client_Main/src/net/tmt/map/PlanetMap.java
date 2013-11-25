package net.tmt.map;

import net.tmt.entity.statics.Planet;


public class PlanetMap extends WorldMap {

	private int				uniqueId;
	public static final int	CHUNK_SIZE	= 64;

	public PlanetMap(final Planet planet) {
		setChunkSize(CHUNK_SIZE);
		setBaseTerrain(planet.getBaseTerrain());
		uniqueId = planet.getPos().hashCode();
	}

	@Override
	public int hashCode() {
		return uniqueId;
	}
}
