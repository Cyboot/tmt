package net.tmt.map;

import java.math.BigInteger;

public class Coordinate {
	public int	x, y;

	public Coordinate(final int x, final int y) {
		this.x = x;
		this.y = y;
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
		big.shiftLeft(32);
		big.add(bigY);
		return big.hashCode();
	}
}
