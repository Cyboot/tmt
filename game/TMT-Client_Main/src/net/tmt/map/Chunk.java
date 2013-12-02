package net.tmt.map;

import net.tmt.game.interfaces.Renderable;
import net.tmt.gfx.Graphics;
import net.tmt.util.ColorUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;


public class Chunk implements Renderable {
	public Terrain			terrain;
	public int				size;
	private Coordinate		coord;

	private ReadableColor	color;
	private boolean			empty;

	public Chunk(final Coordinate coord, final Terrain terrain, final int size) {
		this.coord = new Coordinate(coord.x, coord.y);
		this.terrain = terrain;

		switch (terrain) {
		case SPACE_VOID:
			color = Color.WHITE;
			break;
		case SPACE_NEBULA:
			color = Color.BLUE;
			break;
		case SPACE_ASTEROIDS_BELT:
			color = Color.RED;
			break;
		case SPACE_ASTEROIDS_FLOATING:
			color = Color.ORANGE;
			break;
		case SPACE_PLANET:
			color = Color.GREEN;
			break;
		case SPACE_DEBRIS:
			color = Color.PURPLE;
			break;
		default:
			color = ColorUtil.TRANSPARENT;
		}

		this.size = size;
	}


	@Override
	public void render(final Graphics g) {
		g.setColor(color);
		g.drawSprite(Vector2d.tmp1.set(getCoord().x * size, getCoord().y * size), terrain.getSprite());
	}

	public Coordinate getCoord() {
		return coord;
	}


	public boolean freeToBuild() {
		return empty;
	}

	public void setEmpty(final boolean empty) {
		this.empty = empty;
	}
}
