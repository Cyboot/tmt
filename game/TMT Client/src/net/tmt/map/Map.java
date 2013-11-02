package net.tmt.map;

import java.util.ArrayList;
import java.util.HashMap;

import net.tmt.entity.Entity2D;
import net.tmt.map.generator.MapGenerator;
import net.tmt.util.Vector2d;


public abstract class Map {

	// PRELOAD_RADIUS in chunks
	private static final int				PRELOAD_RADIUS		= 3;
	public static final int					TYPE_SPACE			= 1;
	public static final int					TYPE_PLANET			= 2;
	/* spacy stuff */
	public static final int					TERRAIN_VOID		= 100;
	public static final int					TERRAIN_ASTEROIDS	= 101;
	public static final int					TERRAIN_DEBRIS		= 102;
	/* planety stuff */
	public static final int					TERRAIN_GRASS		= 200;
	public static final int					TERRAIN_WATER		= 201;
	public static final int					TERRAIN_DESERT		= 202;
	public static final int					TERRAIN_SNOW		= 203;
	public static final int					TERRAIN_FOREST		= 204;
	public static final int					TERRAIN_SWAMP		= 205;

	private int								type;
	private int								baseTerrain;
	protected int							chunkSize;
	protected HashMap<Coordinate, Chunk>	chunks				= new HashMap<Coordinate, Chunk>();

	public int								maxX				= Integer.MIN_VALUE;
	public int								minX				= Integer.MAX_VALUE;
	public int								maxY				= Integer.MIN_VALUE;
	public int								minY				= Integer.MAX_VALUE;

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

	public void addChunk(final int terrain, final Coordinate coord, final int size,
			final ArrayList<Entity2D> staticEntities) {
		chunks.put(coord, new Chunk(coord, terrain, size, staticEntities));
		updateBoudaries(coord);
	}

	public void putChunk(final Coordinate coord, final Chunk c) {
		chunks.put(coord, c);
	}

	public void addStaticEntity(final Entity2D e) {
		Coordinate coord = new Coordinate(e.getPos(), this.chunkSize);
		MapGenerator.generateAround(coord, this, PRELOAD_RADIUS);
		chunks.get(coord).addStaticEntity(e);
	}

	public Chunk getChunk(final Coordinate coord) {
		return chunks.get(coord);
	}

	public HashMap<Coordinate, Chunk> getChunks() {
		return chunks;
	}

	public SpaceMap subSpaceMap(final Vector2d pos, final int r) {
		return (SpaceMap) subMap(new Coordinate(pos, chunkSize), r, new SpaceMap());
	}

	public PlanetMap subPlanetMapMap(final Coordinate coord, final int r, final int planetId) {
		return (PlanetMap) subMap(coord, r, new PlanetMap(planetId));
	}

	private Map subMap(final Coordinate coord, final int r, final Map m) {
		MapGenerator.generateAround(coord, this, PRELOAD_RADIUS);
		for (int x = coord.x - r; x <= coord.x + r; x++) {
			for (int y = coord.y - r; y <= coord.y + r; y++) {
				// TODO: why can't I just use the old chunk and add it to the
				// new map?!
				Coordinate currCoord = new Coordinate(x, y);
				Chunk oldChunk = this.chunks.get(currCoord);
				m.addChunk(oldChunk.terrain, currCoord, this.chunkSize, oldChunk.getStaticEntities());
			}
		}
		return m;
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

	public int getType() {
		return type;
	}

	public void setType(final int type) {
		this.type = type;
	}

	public int getBaseTerrain() {
		return baseTerrain;
	}

	public void setBaseTerrain(final int baseTerrain) {
		this.baseTerrain = baseTerrain;
	}

	public int getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(final int chunkSize) {
		this.chunkSize = chunkSize;
	}
}
