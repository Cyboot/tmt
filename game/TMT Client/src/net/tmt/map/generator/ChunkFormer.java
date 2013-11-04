package net.tmt.map.generator;

import net.tmt.map.Chunk;
import net.tmt.map.Coordinate;
import net.tmt.map.WorldMap;

import org.lwjgl.util.Color;

public class ChunkFormer {

	public static Chunk formChunk(final Coordinate coord, final WorldMap map) {
		if (map.getType() == WorldMap.TYPE_SPACE)
			return formSpaceChunk(coord, map);
		else if (map.getType() == WorldMap.TYPE_PLANET)
			return formPlanetChunk(coord, map);
		else {
			// TODO throw an exception — maybe?
			return null;
		}
	}

	private static Chunk formSpaceChunk(final Coordinate coord, final WorldMap map) {
		int chance = (int) Math.rint(Math.random() * 2);
		Chunk c = null;
		switch (chance) {
		case 1:
			c = new Chunk(coord, WorldMap.TERRAIN_VOID, map.getChunkSize());
			c.setColor(Color.RED);
			VoidChunkFormer.fill(c);
			break;
		case 0:
			c = new Chunk(coord, WorldMap.TERRAIN_ASTEROIDS, map.getChunkSize());
			c.setColor(Color.GREEN);
			break;
		case 2:
			c = new Chunk(coord, WorldMap.TERRAIN_DEBRIS, map.getChunkSize());
			c.setColor(Color.BLUE);
			break;
		}
		return c;
	}

	private static Chunk formPlanetChunk(final Coordinate coord, final WorldMap map) {
		int chance = (int) Math.random() * 5;
		Chunk c = null;
		switch (chance) {
		case 0:
			c = new Chunk(coord, WorldMap.TERRAIN_GRASS, map.getChunkSize());
			c.setColor(Color.GREEN);
			break;
		case 1:
			c = new Chunk(coord, WorldMap.TERRAIN_WATER, map.getChunkSize());
			c.setColor(Color.BLUE);
			break;
		case 2:
			c = new Chunk(coord, WorldMap.TERRAIN_DESERT, map.getChunkSize());
			c.setColor(Color.YELLOW);
			break;
		case 3:
			c = new Chunk(coord, WorldMap.TERRAIN_SNOW, map.getChunkSize());
			c.setColor(Color.WHITE);
			break;
		case 4:
			c = new Chunk(coord, WorldMap.TERRAIN_FOREST, map.getChunkSize());
			c.setColor(Color.DKGREY);
			break;
		case 5:
			c = new Chunk(coord, WorldMap.TERRAIN_SWAMP, map.getChunkSize());
			c.setColor(Color.PURPLE);
			break;
		}
		return c;
	}

}
