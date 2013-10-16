package net.tmt.util;


import java.text.DecimalFormat;

public class Vector2d {
	public double	x, y;

	public Vector2d() {
		this(0, 0);
	}

	public Vector2d(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	public int x() {
		return (int) x;
	}

	public int y() {
		return (int) y;
	}

	public Vector2d add(final double x, final double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2d copy() {
		return new Vector2d(x, y);
	}

	public void set(final Vector2d position) {
		x = position.x;
		y = position.y;
	}

	public void set(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	public double distanceTo(final Vector2d other) {
		double dx = Math.abs(x - other.x);
		double dy = Math.abs(y - other.y);

		return Math.sqrt(dx * dx + dy * dy);
	}

	public double distanceTo(final double x, final double y) {
		double dx = Math.abs(this.x - x);
		double dy = Math.abs(this.y - y);

		return Math.sqrt(dx * dx + dy * dy);
	}

	public Vector2d normalize() {
		double length = distanceTo(0, 0);

		x /= length;
		y /= length;

		return this;
	}

	public Vector2d flipX() {
		x = -x;
		return this;
	}

	public Vector2d flipY() {
		y = -y;
		return this;
	}

	public static Vector2d randomNormalized() {
		double x = Math.random() * 2 - 1;
		double y = Math.random() * 2 - 1;

		return new Vector2d(x, y).normalize();
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#0.00");

		return df.format(x) + " : " + df.format(y);
	}
}
