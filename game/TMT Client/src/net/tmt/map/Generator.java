package net.tmt.map;

public class Generator {

	public static Map generateAround(final Coordinate coord, final Map map, final int radius) {
		for (int x = coord.x - radius; x <= coord.x + radius; x++) {
			for (int y = coord.y - radius; y <= coord.y + radius; y++) {
				// for every chunk in radius
				boolean exists = map.chunks.containsKey(coord);
				if (!exists) {
					// TODO: don't just add random terrain ;)
					int terrain = map.baseTerrain;
					if (Math.random() > 0.8) {
						if (map.type == Map.TYPE_SPACE) {
							terrain = Map.TERRAIN_ASTEROIDS;
						}
						if (map.type == Map.TYPE_PLANET) {
							terrain = Map.TERRAIN_WATER;
						}
					}
					map.addChunk(terrain, new Coordinate(x, y));
				}
			}
		}
		return map;
	}
}
