package net.tmt.map;

public abstract class Map {

	public static final int	TYPE_SPACE			= 0;
	public static final int	TYPE_PLANET			= 1;
	/* planety stuff */
	public static final int	TERRAIN_GRASS		= 0;
	public static final int	TERRAIN_WATER		= 1;
	public static final int	TERRAIN_DESERT		= 2;
	public static final int	TERRAIN_SNOW		= 3;
	public static final int	TERRAIN_FOREST		= 4;
	public static final int	TERRAIN_SWAMP		= 5;
	/* spacy stuff */
	public static final int	TERRAIN_VOID		= 6;
	public static final int	TERRAIN_ASTEROIDS	= 7;
	public static final int	TERRAIN_DEBRIS		= 8;

	protected int			type;
	protected int			baseTerrain;

}
