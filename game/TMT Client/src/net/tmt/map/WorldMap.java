package net.tmt.map;

import java.util.HashMap;
import java.util.Map;

import net.tmt.entity.Entity2D;
import net.tmt.game.interfaces.Renderable;
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

	public void update(final Vector2d offset, final Vector2d pPos) {
		rederOffset = offset;

		// generate new Terrain if needed
		MapGenerator.generateAround(new Coordinate(offset, chunkSize), this, preloadRadius);

		// update all chunks (for now all of them)
		for (Chunk c : chunkMap.values())
			c.update(offset);
	}

	@Override
	public void render(final Graphics g) {
		Coordinate coord = Coordinate.tmp0;
		Coordinate.tmp0.set(rederOffset, chunkSize);

		for (int x = coord.x - renderRadius; x <= coord.x + renderRadius; x++) {
			for (int y = coord.y - renderRadius; y <= coord.y + renderRadius; y++) {
				Coordinate check = Coordinate.tmp1;
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

	public void addStaticEntity(final Entity2D e) {
		Coordinate coord = new Coordinate(e.getPos(), this.chunkSize);
		MapGenerator.generateAround(coord, this, preloadRadius);
		chunkMap.get(coord).addStaticEntity(e);
	}

	public Chunk getChunk(final Coordinate coord) {
		return chunkMap.get(coord);
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
}
