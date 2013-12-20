package net.tmt.gfx;

import java.util.ArrayList;
import java.util.List;

import net.tmt.util.Vector2d;

public class Shape {
	private List<Vector2d>	points	= new ArrayList<>();
	private boolean			isCentered;
	private double			rotation;
	private double			scale	= 1;

	public void addPoint(final Vector2d point) {
		points.add(point);
	}

	public List<Vector2d> getPoints() {
		return points;
	}

	public Vector2d getLastPoint() {
		return points.get(points.size() - 1);
	}

	public Vector2d getFirstPoint() {
		return points.get(0);
	}

	public Shape setScale(final double scale) {
		this.scale = scale;
		return this;
	}

	public Shape setRotation(final double rotation) {
		this.rotation = rotation;
		return this;
	}

	public Shape setCentered(final boolean center) {
		isCentered = center;
		return this;
	}

	public void scale(final double scale) {
		this.scale = scale;
	}

	protected Vector2d getOffset() {
		if (isCentered) {
			Vector2d center = new Vector2d();

			double minX = Double.MAX_VALUE;
			double maxX = Double.MIN_VALUE;
			double minY = Double.MAX_VALUE;
			double maxY = Double.MIN_VALUE;

			for (Vector2d p : points) {
				if (p.x < minX)
					minX = p.x;
				if (p.x > maxX)
					maxX = p.x;
				if (p.y < minY)
					minY = p.y;
				if (p.y > maxY)
					maxY = p.y;
			}

			center.x = (maxX + minX) / 2 * scale;
			center.y = (maxY + minY) / 2 * scale;

			return center;
		} else {
			return new Vector2d();
		}
	}

	public double getRotation() {
		return rotation;
	}

	public double getScale() {
		return scale;
	}
}
