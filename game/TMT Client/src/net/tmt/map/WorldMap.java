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
	private static final int			PRELOAD_RADIUS	= 3;
	private static final int			RENDER_RADIUS	= 1;

	private Terrain						baseTerrain;
	private Vector2d					rederOffset;
	private int							chunkSize;
	protected Map<Coordinate, Chunk>	chunkMap		= new HashMap<Coordinate, Chunk>();

	public void update(final Vector2d offset, final Vector2d pPos) {
		rederOffset = offset;

		// generate new Terrain if needed
		MapGenerator.generateAround(new Coordinate(offset, chunkSize), this, PRELOAD_RADIUS);

		// update all chunks (for now all of them)
		for (Chunk c : chunkMap.values())
			c.update(offset);
	}

	@Override
	public void render(final Graphics g) {
		Coordinate coord = Coordinate.tmp0;
		Coordinate.tmp0.set(rederOffset, chunkSize);

		for (int x = coord.x - RENDER_RADIUS; x <= coord.x + RENDER_RADIUS; x++) {
			for (int y = coord.y - RENDER_RADIUS; y <= coord.y + RENDER_RADIUS; y++) {
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
		MapGenerator.generateAround(coord, this, PRELOAD_RADIUS);
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
	}

	protected void setBaseTerrain(final Terrain terrain) {
		baseTerrain = terrain;
	}
}
