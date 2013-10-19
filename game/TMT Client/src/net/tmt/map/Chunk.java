package net.tmt.map;


public class Chunk {

	public static final int	SIZE	= 100;

	public int				terrain;
	public Coordinate		coord;

	public Chunk(final int terrain, final Coordinate coord) {
		this.terrain = terrain;
		this.coord = coord;
	}

}
