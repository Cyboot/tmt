package net.tmt.util;


import java.text.DecimalFormat;

import net.tmt.game.manager.CollisionManager;

import org.jbox2d.common.Vec2;

public class Vector2d {
	/** Use this static Vector for temporary usage */
	public static Vector2d	tmp1	= new Vector2d();
	/** Use this static Vector for temporary usage */
	public static Vector2d	tmp2	= new Vector2d();

	public double			x, y;

	public Vector2d() {
		this(0, 0);
	}

	public Vector2d(final Vector2d other) {
		this(other.x, other.y);
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

	public Vector2d add(final Vector2d other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}

	public Vector2d sub(final Vector2d other) {
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}

	public Vector2d copy() {
		return new Vector2d(x, y);
	}

	public Vector2d set(final Vector2d position) {
		x = position.x;
		y = position.y;
		return this;
	}

	public Vector2d set(final double x, final double y) {
		this.x = x;
		this.y = y;
		return this;
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

		if (length == 0)
			return this;

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

	public double length() {
		return distanceTo(0, 0);
	}

	public Vector2d multiply(final double factor) {
		x *= factor;
		y *= factor;
		return this;
	}

	/**
	 * Rotation to up-vector (0 - 2*Pi)
	 * 
	 * @return angle of vector in radians
	 */
	public double getRotation() {
		double skalar = -y;
		double nenner = length();

		if (x > 0)
			return Math.acos(skalar / nenner);
		else
			return Math.PI * 2 - Math.acos(skalar / nenner);
	}

	/**
	 * rotate the vector for the given value
	 * 
	 * @param rotationRad
	 */
	public Vector2d rotate(final double rotationRad) {
		double tempX = x;
		x = Math.cos(rotationRad) * x - Math.sin(rotationRad) * y;
		y = Math.cos(rotationRad) * y + Math.sin(rotationRad) * tempX;
		return this;
	}

	/**
	 * Angle of the two vectors
	 * 
	 * @param other
	 * @return
	 */
	public double angleTo(final Vector2d other) {
		double angle1 = this.getRotation();
		double angle2 = other.getRotation();

		return angle1 - angle2;
	}

	/**
	 * normalised Vector generated from given angle
	 * 
	 * @param roationRad
	 *            in radian
	 * @return
	 */
	public static Vector2d fromAngle(final double roationRad) {
		Vector2d result = new Vector2d();

		result.x = Math.sin(roationRad);
		result.y = -Math.cos(roationRad);

		return result.normalize();
	}

	public static Vector2d fromVec2(final Vec2 vec2) {
		return new Vector2d(vec2.x * CollisionManager.PIXEL_PER_METER, vec2.y * CollisionManager.PIXEL_PER_METER);
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

	public Vector2d sub(final double x, final double y) {
		return add(-x, -y);
	}

	public Vec2 toVec2() {
		return new Vec2((float) (x / CollisionManager.PIXEL_PER_METER),
				(float) (y / CollisionManager.PIXEL_PER_METER));
	}


	@Override
	public int hashCode() {
		return (int) x << 16 | ((int) y & 0x0000FFFF);
	}

	public void setFromVec2(final Vec2 vec2) {
		x = vec2.x * CollisionManager.PIXEL_PER_METER;
		y = vec2.y * CollisionManager.PIXEL_PER_METER;
	}
}
