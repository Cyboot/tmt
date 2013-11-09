package net.tmt.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.tmt.entity.Entity2D;
import net.tmt.game.interfaces.Renderable;
import net.tmt.gfx.Graphics;
import net.tmt.map.generator.MapGenerator;
import net.tmt.util.Vector2d;


public abstract class WorldMap implements Renderable {

	// PRELOAD_RADIUS in chunks
	private static final int			PRELOAD_RADIUS		= 3;
	private static final int			RENDER_RADIUS		= 1;
	public static final int				TYPE_SPACE			= 1;
	public static final int				TYPE_PLANET			= 2;
	/* spacy stuff */
	public static final int				TERRAIN_VOID		= 100;
	public static final int				TERRAIN_ASTEROIDS	= 101;
	public static final int				TERRAIN_DEBRIS		= 102;
	/* planety stuff */
	public static final int				TERRAIN_GRASS		= 200;
	public static final int				TERRAIN_WATER		= 201;
	public static final int				TERRAIN_DESERT		= 202;
	public static final int				TERRAIN_SNOW		= 203;
	public static final int				TERRAIN_FOREST		= 204;
	public static final int				TERRAIN_SWAMP		= 205;

	private int							type;
	private int							baseTerrain;
	private Vector2d					rederOffset;
	protected int						chunkSize;
	protected Map<Coordinate, Chunk>	chunkMap			= new HashMap<Coordinate, Chunk>();

	public int							maxX				= Integer.MIN_VALUE;
	public int							minX				= Integer.MAX_VALUE;
	public int							maxY				= Integer.MIN_VALUE;
	public int							minY				= Integer.MAX_VALUE;

	public boolean existsAround(final Coordinate coord, final int radius) {
		Coordinate tmpC = new Coordinate(0, 0);
		boolean exists = true;
		for (int x = coord.x - radius; x <= coord.x + radius; x++) {
			for (int y = coord.y - radius; y <= coord.y + radius; y++) {
				tmpC.set(x, y);
				if (chunkMap.get(tmpC) == null) {
					exists = false;
					break;
				}
			}
			if (!exists)
				break;
		}
		return exists;
	}

	private void updateBoudaries(final Coordinate coord) {
		maxX = Math.max(maxX, coord.x);
		minX = Math.min(minX, coord.x);
		maxY = Math.max(maxY, coord.y);
		minY = Math.min(minY, coord.y);
	}

	public void addChunk(final Coordinate coord, final Chunk c) {
		chunkMap.put(coord, c);
		updateBoudaries(coord);
	}

	public void addChunk(final int terrain, final Coordinate coord, final int size,
			final ArrayList<Entity2D> staticEntities) {
		chunkMap.put(coord, new Chunk(coord, terrain, size, staticEntities));
		updateBoudaries(coord);
	}

	public void putChunk(final Coordinate coord, final Chunk c) {
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

	public Map<Coordinate, Chunk> getChunks() {
		return chunkMap;
	}

	public SpaceMap subSpaceMap(final Vector2d pos, final int r) {
		return (SpaceMap) subMap(new Coordinate(pos, chunkSize), r, new SpaceMap());
	}

	public PlanetMap subPlanetMapMap(final Coordinate coord, final int r, final int planetId) {
		return (PlanetMap) subMap(coord, r, new PlanetMap(planetId));
	}

	private WorldMap subMap(final Coordinate coord, final int r, final WorldMap m) {
		for (int x = coord.x - r; x <= coord.x + r; x++) {
			for (int y = coord.y - r; y <= coord.y + r; y++) {
				// TODO: why can't I just use the old chunk and add it to the
				// new map?!
				Coordinate currCoord = new Coordinate(x, y);
				Chunk oldChunk = this.chunkMap.get(currCoord);
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
				if (chunkMap.containsKey(coord)) {
					Chunk chunk = chunkMap.get(coord);
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

	public void update(final Vector2d offset, final Vector2d pPos, final World world) {
		rederOffset = offset;
		MapGenerator.generateAround(new Coordinate(offset, chunkSize), this, PRELOAD_RADIUS);

		Coordinate check = Coordinate.tmp0;
		Coordinate.tmp0.set(pPos, chunkSize);
		if (chunkMap.get(check) != null) {
			chunkMap.get(check).update(pPos, world);
		}
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

}
