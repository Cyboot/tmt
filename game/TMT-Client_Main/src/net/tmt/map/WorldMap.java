package net.tmt.map;

import java.util.HashMap;
import java.util.Map;

import net.tmt.game.interfaces.Renderable;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.map.generator.MapGenerator;
import net.tmt.util.Vector2d;


public abstract class WorldMap implements Renderable {
	// PRELOAD_RADIUS in chunks
	private int							preloadRadius;
	private int							renderRadius;

	private Vector2d					rederOffset;
	private int							chunkSize;
	protected Map<Coordinate, Chunk>	chunkMap	= new HashMap<Coordinate, Chunk>();
	private Terrain						baseTerrain;

	public void update(final Vector2d offset, final EntityManager entityManager, final double delta) {
		rederOffset = offset;

		// generate new Terrain if needed
		MapGenerator.generateAround(new Coordinate(offset, chunkSize), this, entityManager, preloadRadius);
	}

	@Override
	public void render(final Graphics g) {
		Coordinate coord = Coordinate.tmp0.set(rederOffset, chunkSize);
		Coordinate check = Coordinate.tmp1;

		for (int x = coord.x - renderRadius; x <= coord.x + renderRadius; x++) {
			for (int y = coord.y - renderRadius; y <= coord.y + renderRadius; y++) {
				check.set(x, y);
				if (chunkMap.get(check) != null) {
					chunkMap.get(check).render(g);
				}
			}
		}
	}

	public void addChunk(final Coordinate coord, final Chunk c) {
		chunkMap.put(coord, c);
	}

	/**
	 * adds an Entity to the map
	 * 
	 * @param e
	 *            Entity to add
	 * @return if Entity was added successfull
	 */
	public boolean chunkFreeToBuild(final Vector2d pos, final EntityManager entityManager) {
		Coordinate coord = new Coordinate(pos, this.chunkSize);
		MapGenerator.generateAround(coord, this, entityManager, 0);

		return true;
		// return chunkMap.get(coord).freeToBuild();
	}

	public Chunk getChunk(final Coordinate coord) {
		return chunkMap.get(coord);
	}

	public Chunk getChunk(final Vector2d pos) {
		return chunkMap.get(Coordinate.tmp0.set(pos, getChunkSize()));
	}

	public int getChunkSize() {
		return chunkSize;
	}

	protected void setChunkSize(final int chunkSize) {
		this.chunkSize = chunkSize;

		if (chunkSize == SpaceMap.CHUNK_SIZE) {
			preloadRadius = 3;
			renderRadius = 1;
		} else if (chunkSize == PlanetMap.CHUNK_SIZE) {
			preloadRadius = 15;
			renderRadius = 10;
		}
	}

	protected void setBaseTerrain(final Terrain baseTerrain) {
		this.baseTerrain = baseTerrain;
	}

	public Terrain getBaseTerrain() {
		return baseTerrain;
	}

	public void setChunkNotEmpty(final Vector2d pos) {
		Coordinate coord = new Coordinate(pos, this.chunkSize);
		chunkMap.get(coord).setEmpty(false);
	}
}
