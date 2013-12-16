package net.tmt.map.generator;

import net.tmt.game.manager.EntityManager;
import net.tmt.main.LoaderTimer;
import net.tmt.map.Chunk;
import net.tmt.map.Coordinate;
import net.tmt.map.WorldMap;

public class MapGenerator {

	public static void generateSpaceMap(final WorldMap map, final EntityManager entityManager) {
		LoaderTimer.start("Map");
		new MapGeneratorSpace().generateSpace(map, entityManager);
		LoaderTimer.stop("Map");
	}

	public static void generateAround(final Coordinate coord, final WorldMap map, final EntityManager entityManager,
			final int radius) {
		Coordinate coordTmp = new Coordinate(coord.x, coord.y);

		for (int x = coord.x - radius; x <= coord.x + radius; x++) {
			for (int y = coord.y - radius; y <= coord.y + radius; y++) {
				coordTmp.x = x;
				coordTmp.y = y;
				Chunk chunk = map.getChunk(coordTmp);

				if (chunk == null) {
					map.addChunk(new Coordinate(x, y), ChunkFormer.formChunk(coordTmp, map, entityManager));
				}
			}
		}
	}
}
