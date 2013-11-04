package net.tmt.util;

public class Rectangle {
	private double	x;
	private double	y;
	private double	width;
	private double	height;

	public Rectangle(final double x, final double y, final double width, final double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * returns true if Rect contains the given point
	 * 
	 * @param point
	 * @return
	 */
	public boolean contains(final Vector2d point) {
		return contains(point.x, point.y);
	}

	/**
	 * returns true if Rect contains the given point
	 * 
	 * @param point
	 * @return
	 */
	public boolean contains(final double x, final double y) {
		if (x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height)
			return true;
		else
			return false;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public void setHeight(final double height) {
		this.height = height;
	}

	public void setWidth(final double width) {
		this.width = width;
	}
}
