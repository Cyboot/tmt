package net.tmt.map;

import java.util.ArrayList;

public abstract class Map {

	public static final int		TYPE_SPACE			= 1;
	public static final int		TYPE_PLANET			= 2;
	/* spacy stuff */
	public static final int		TERRAIN_VOID		= 100;
	public static final int		TERRAIN_ASTEROIDS	= 101;
	public static final int		TERRAIN_DEBRIS		= 102;
	/* planety stuff */
	public static final int		TERRAIN_GRASS		= 200;
	public static final int		TERRAIN_WATER		= 201;
	public static final int		TERRAIN_DESERT		= 202;
	public static final int		TERRAIN_SNOW		= 203;
	public static final int		TERRAIN_FOREST		= 204;
	public static final int		TERRAIN_SWAMP		= 205;

	protected int				type;
	protected int				baseTerrain;
	protected ArrayList<Chunk>	chunks;

	public boolean existsAround(final Coordinate coord, final int radius) {
		// TODO: find some sort of barrier solution to avoid looping through all
		// chunks here
		return false;
	}

	public void addChunk(final int terrain, final Coordinate coord) {
		chunks.add(new Chunk(terrain, coord));
	}
}
