package net.tmt.map;

public class Generator {

	public static Map generateAround(final Coordinate coord, final Map map, final int radius) {
		for (int x = coord.x - radius; x <= coord.x + radius; x++) {
			for (int y = coord.y - radius; y <= coord.y + radius; y++) {
				// for every chunk in radius
				Coordinate check = new Coordinate(x, y);
				boolean exists = false;
				for (int i = 0; i < map.chunks.size(); i++) {
					// for every already existing chunk
					exists = (exists || check.equals(map.chunks.get(i)));
				}
				if (!exists)
					// TODO: don't just add the base terrain ;)
					map.addChunk(map.baseTerrain, new Coordinate(x, y));
			}
		}
		return map;
	}

}
