package net.tmt.map.generator;

import net.tmt.game.manager.EntityManager;
import net.tmt.map.Chunk;
import net.tmt.map.Coordinate;
import net.tmt.map.PlanetChunk;
import net.tmt.map.PlanetMap;
import net.tmt.map.Terrain;
import net.tmt.map.WorldMap;
import net.tmt.util.RandomUtil;

class ChunkFormer {

	public static Chunk formChunk(final Coordinate coord, final WorldMap map, final EntityManager entityManager) {
		if (map instanceof PlanetMap)
			return formPlanetChunk(coord, map, entityManager);
		else {
			return null;
			// throw new IllegalArgumentException("Maptype is not known");
		}
	}

	private static PlanetChunk formPlanetChunk(final Coordinate coord, final WorldMap map,
			final EntityManager entityManager) {
		RandomUtil.setSeed(coord.hashCode() ^ map.hashCode());

		PlanetChunk chunk = null;
		if (RandomUtil.randBoolean(0.75)) {
			chunk = new PlanetChunk(coord, map.getBaseTerrain(), map.getChunkSize());
		} else {
			int chance = RandomUtil.intRange(0, 5);
			switch (chance) {
			case 0:
				chunk = new PlanetChunk(coord, Terrain.PLANET_GRASS, map.getChunkSize());
				break;
			case 1:
				chunk = new PlanetChunk(coord, Terrain.PLANET_WATER, map.getChunkSize());
				break;
			case 2:
				chunk = new PlanetChunk(coord, Terrain.PLANET_DESERT, map.getChunkSize());
				break;
			case 3:
				chunk = new PlanetChunk(coord, Terrain.PLANET_SNOW, map.getChunkSize());
				break;
			case 4:
				chunk = new PlanetChunk(coord, Terrain.PLANET_FOREST, map.getChunkSize());
				break;
			case 5:
				chunk = new PlanetChunk(coord, Terrain.PLANET_SWAMP, map.getChunkSize());
				break;
			}
		}
		return chunk;
	}

}
