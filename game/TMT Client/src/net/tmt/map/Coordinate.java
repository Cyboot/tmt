package net.tmt.map;

import net.tmt.util.Vector2d;

public class Coordinate {
	public static Coordinate	tmp0	= new Coordinate(0, 0);
	public static Coordinate	tmp1	= new Coordinate(0, 0);

	public int					x, y;

	public Coordinate(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate(final Vector2d pos, final int chunkSize) {
		x = posScalar2coordScalar(pos.x, chunkSize);
		y = posScalar2coordScalar(pos.y, chunkSize);
	}

	private int posScalar2coordScalar(final double s, final int chunkSize) {
		int factor = (s < 0 ? -1 : 1);
		return (int) Math.rint(Math.abs(s) / chunkSize) * factor;
	}

	public Vector2d center2pos(final int chunkSize) {
		double newX = x * chunkSize + chunkSize / 2;
		double newY = y * chunkSize + chunkSize / 2;
		return new Vector2d(newX, newY);
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Coordinate))
			return false;
		Coordinate oc = (Coordinate) other;
		return (x == oc.x && y == oc.y);
	}

	@Override
	public int hashCode() {
		return x << 16 | (y & 0x0000FFFF);
	}

	public void set(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public void set(final Vector2d pos, final int chunkSize) {
		x = posScalar2coordScalar(pos.x, chunkSize);
		y = posScalar2coordScalar(pos.y, chunkSize);
	}

	@Override
	public String toString() {
		return "Coord= " + x + ":" + y;
	}
}
