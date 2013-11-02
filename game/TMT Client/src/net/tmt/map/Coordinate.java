package net.tmt.map;

import java.math.BigInteger;

import net.tmt.util.Vector2d;

public class Coordinate {
	public int	x, y;

	public Coordinate(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate(final Vector2d pos, final int chunkSize) {
		x = (int) Math.ceil((pos.x / chunkSize) - .5);
		y = (int) Math.ceil((pos.y / chunkSize) - .5);
	}

	public Vector2d center2pos(final int chunkSize) {
		double newX = x * chunkSize;
		double newY = y * chunkSize;
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
		BigInteger big = new BigInteger(Integer.toString(x));
		BigInteger bigY = new BigInteger(Integer.toString(y));
		big = big.shiftLeft(32);
		big = big.add(bigY);
		return big.hashCode();
	}

	public void set(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Coord= " + x + ":" + y;
	}
}
