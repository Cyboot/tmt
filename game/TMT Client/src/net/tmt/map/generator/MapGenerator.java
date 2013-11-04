package net.tmt.map.generator;

import net.tmt.map.Chunk;
import net.tmt.map.Coordinate;
import net.tmt.map.WorldMap;

public class MapGenerator {

	public static WorldMap generateAround(final Coordinate coord, final WorldMap map, final int radius) {
		Coordinate coordTmp = new Coordinate(coord.x, coord.y);

		// for every chunk in radius
		for (int x = coord.x - radius; x <= coord.x + radius; x++) {
			for (int y = coord.y - radius; y <= coord.y + radius; y++) {
				coordTmp.x = x;
				coordTmp.y = y;
				Chunk chunk = map.getChunk(coordTmp);

				// chunk == null --> generate new chunk
				if (chunk == null) {
					map.addChunk(new Coordinate(x, y), ChunkFormer.formChunk(coordTmp, map));
				}
			}
		}
		return map;
	}
}
