package net.tmt.map;

import java.util.HashMap;
import java.util.Map;

import net.tmt.entity.Entity2D;
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

	// FIXME: Entitymanager here?
	protected EntityManager				entityManager;

	public void update(final Vector2d offset, final double delta) {
		rederOffset = offset;

		// generate new Terrain if needed
		MapGenerator.generateAround(new Coordinate(offset, chunkSize), this, preloadRadius);

		for (Chunk c : chunkMap.values())
			c.update(entityManager, delta);
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
	public boolean addStaticEntity(final Entity2D e) {
		Coordinate coord = new Coordinate(e.getPos(), this.chunkSize);
		MapGenerator.generateAround(coord, this, preloadRadius);
		return chunkMap.get(coord).addStaticEntity(e);
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

	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
