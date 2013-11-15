package net.tmt.map.generator;

import net.tmt.map.Chunk;
import net.tmt.map.Coordinate;
import net.tmt.map.PlanetChunk;
import net.tmt.map.PlanetMap;
import net.tmt.map.SpaceMap;
import net.tmt.map.Terrain;
import net.tmt.map.WorldMap;
import net.tmt.util.RandomUtil;

class ChunkFormer {

	public static Chunk formChunk(final Coordinate coord, final WorldMap map) {
		if (map instanceof SpaceMap)
			return formSpaceChunk(coord, map);
		else if (map instanceof PlanetMap)
			return formPlanetChunk(coord, map);
		else {
			// TODO throw an exception — maybe?
			return null;
		}
	}

	private static Chunk formSpaceChunk(final Coordinate coord, final WorldMap map) {
		RandomUtil.setSeed(coord.hashCode());
		int chance = RandomUtil.intRange(0, 0);
		Chunk chunk = null;
		switch (chance) {
		case 0:
			chunk = new Chunk(coord, Terrain.SPACE_PLANET, map.getChunkSize());
			ChunkFiller.fillPlanet(chunk);
			break;
		case 1:
			chunk = new Chunk(coord, Terrain.SPACE_VOID, map.getChunkSize());
			break;
		case 2:
			chunk = new Chunk(coord, Terrain.SPACE_DEBRIS, map.getChunkSize());
			break;
		case 3:
			chunk = new Chunk(coord, Terrain.SPACE_NEBULA, map.getChunkSize());
			break;
		case 4:
			chunk = new Chunk(coord, Terrain.SPACE_ASTEROIDS_BELT, map.getChunkSize());
			break;
		case 5:
			chunk = new Chunk(coord, Terrain.SPACE_ASTEROIDS_FLOATING, map.getChunkSize());
			break;
		}
		ChunkFiller.fillDefault(chunk);
		return chunk;
	}

	private static PlanetChunk formPlanetChunk(final Coordinate coord, final WorldMap map) {
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
