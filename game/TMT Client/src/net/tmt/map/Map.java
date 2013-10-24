package net.tmt.map;

import java.util.HashMap;


public abstract class Map {

	public static final int					TYPE_SPACE			= 1;
	public static final int					TYPE_PLANET			= 2;
	/* spacy stuff */
	public static final int					TERRAIN_VOID		= 100;
	public static final int					TERRAIN_ASTEROIDS	= 101;
	public static final int					TERRAIN_DEBRIS		= 102;
	public static final int					TERRAIN_PLANET		= 103;
	/* planety stuff */
	public static final int					TERRAIN_GRASS		= 200;
	public static final int					TERRAIN_WATER		= 201;
	public static final int					TERRAIN_DESERT		= 202;
	public static final int					TERRAIN_SNOW		= 203;
	public static final int					TERRAIN_FOREST		= 204;
	public static final int					TERRAIN_SWAMP		= 205;

	protected int							type;
	protected int							baseTerrain;
	public int								chunkSize;
	protected HashMap<Coordinate, Chunk>	chunks				= new HashMap<Coordinate, Chunk>();

	private int								maxX				= Integer.MIN_VALUE;
	private int								minX				= Integer.MAX_VALUE;
	private int								maxY				= Integer.MIN_VALUE;
	private int								minY				= Integer.MAX_VALUE;

	public boolean existsAround(final Coordinate coord, final int radius) {
		// TODO: find some sort of boundary solution to avoid looping through
		// all chunks here
		return false;
	}

	private void updateBoudaries(final Coordinate coord) {
		maxX = Math.max(maxX, coord.x);
		minX = Math.min(minX, coord.x);
		maxY = Math.max(maxY, coord.y);
		minY = Math.min(minY, coord.y);
	}

	public void addChunk(final int terrain, final Coordinate coord, final int size) {
		chunks.put(coord, new Chunk(coord, terrain, size));
		updateBoudaries(coord);
	}

	public Chunk getChunk(final Coordinate coord) {
		return chunks.get(coord);
	}

	public void debugPrint() {
		System.out.println("boundaries: x: [" + minX + "|" + maxX + "] y: [" + minY + "|" + maxY + "]");
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				Coordinate coord = new Coordinate(x, y);
				if (chunks.containsKey(coord)) {
					Chunk chunk = chunks.get(coord);
					String s = Integer.toString(chunk.terrain);
					char c = s.charAt(s.length() - 1);
					System.out.print(c);
				} else {
					System.out.print('•');
				}
			}
			System.out.println();
		}
	}
}
