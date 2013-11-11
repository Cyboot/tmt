package net.tmt.map.generator;

import net.tmt.map.Chunk;
import net.tmt.map.Coordinate;
import net.tmt.map.World;
import net.tmt.map.WorldMap;

public class ChunkFormer {

	public static Chunk formChunk(final World world, final Coordinate coord, final WorldMap map) {
		if (map.getType() == WorldMap.TYPE_SPACE)
			return formSpaceChunk(world, coord, map);
		else if (map.getType() == WorldMap.TYPE_PLANET)
			return formPlanetChunk(world, coord, map);
		else {
			// TODO throw an exception — maybe?
			return null;
		}
	}

	private static Chunk formSpaceChunk(final World world, final Coordinate coord, final WorldMap map) {
		int chance = (int) Math.rint(Math.random() * 2);
		Chunk c = null;
		switch (chance) {
		case 1:
			c = new Chunk(coord, WorldMap.TERRAIN_VOID, map.getChunkSize());
			VoidChunkFiller.fill(world, c);
			break;
		case 0:
			c = new Chunk(coord, WorldMap.TERRAIN_ASTEROIDS, map.getChunkSize());
			break;
		case 2:
			c = new Chunk(coord, WorldMap.TERRAIN_DEBRIS, map.getChunkSize());
			break;
		}
		GeneralSpaceChunkFiller.fill(world, c);
		return c;
	}

	private static Chunk formPlanetChunk(final World world, final Coordinate coord, final WorldMap map) {
		int chance = (int) Math.rint(Math.random() * 5);
		Chunk c = null;
		switch (chance) {
		case 0:
			c = new Chunk(coord, WorldMap.TERRAIN_GRASS, map.getChunkSize());
			break;
		case 1:
			c = new Chunk(coord, WorldMap.TERRAIN_WATER, map.getChunkSize());
			break;
		case 2:
			c = new Chunk(coord, WorldMap.TERRAIN_DESERT, map.getChunkSize());
			break;
		case 3:
			c = new Chunk(coord, WorldMap.TERRAIN_SNOW, map.getChunkSize());
			break;
		case 4:
			c = new Chunk(coord, WorldMap.TERRAIN_FOREST, map.getChunkSize());
			break;
		case 5:
			c = new Chunk(coord, WorldMap.TERRAIN_SWAMP, map.getChunkSize());
			break;
		}
		return c;
	}

}
