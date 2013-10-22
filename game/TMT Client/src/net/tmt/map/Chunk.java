package net.tmt.map;


public class Chunk {

	public static final int	SIZE	= 100;

	public int				terrain;
	public int				planetId;

	public Chunk(final int terrain, final int planetId) {
		this.terrain = terrain;
		this.planetId = planetId;
	}

}
