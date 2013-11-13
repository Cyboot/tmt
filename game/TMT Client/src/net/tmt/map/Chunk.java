package net.tmt.map;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.Entity2D;
import net.tmt.entity.statics.Planet;
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
	private List<Entity2D>	staticEntityList	= new ArrayList<Entity2D>();

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

	public void update(final Vector2d offset) {
		for (Entity2D e : staticEntityList) {
			if (e instanceof Planet) {
				((Planet) e).landingCheck(offset);
			}
		}
	}

	@Override
	public void render(final Graphics g) {
		g.setColor(color);
		g.drawSprite(new Vector2d(getCoord().x * size, getCoord().y * size), terrain.getSprite());
		g.drawRect(getCoord().x * size + 4, getCoord().y * size + 4, size - 8, size - 8);
		for (Entity2D e : staticEntityList) {
			e.render(g);
		}
	}

	public void addStaticEntity(final Entity2D e) {
		staticEntityList.add(e);
	}

	public Coordinate getCoord() {
		return coord;
	}
}
