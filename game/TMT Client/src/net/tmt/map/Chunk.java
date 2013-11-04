package net.tmt.map;

import java.util.ArrayList;

import net.tmt.entity.Entity2D;
import net.tmt.game.interfaces.Renderable;
import net.tmt.gfx.Graphics;

import org.lwjgl.util.ReadableColor;


public class Chunk implements Renderable {

	public int					terrain;
	public int					size;
	public Coordinate			coord;
	// DEBUG border color
	private ReadableColor		color;
	private ArrayList<Entity2D>	staticEntityList	= new ArrayList<Entity2D>();

	public Chunk(final Coordinate coord, final int terrain, final int size) {
		this.coord = coord;
		this.terrain = terrain;
		this.size = size;
	}

	public Chunk(final Coordinate coord, final int terrain, final int size, final ArrayList<Entity2D> entities,
			final ReadableColor color) {
		this.coord = coord;
		this.terrain = terrain;
		this.size = size;
		this.staticEntityList = entities;
		this.color = color;
	}

	public void addStaticEntity(final Entity2D e) {
		staticEntityList.add(e);
	}

	public ArrayList<Entity2D> getStaticEntities() {
		return staticEntityList;
	}

	@Override
	public void render(final Graphics g) {
		g.setColor(color);
		g.drawRect(coord.center2pos(size).x - (size / 2), coord.center2pos(size).y - (size / 2), size - 5, size - 5);
		for (Entity2D e : staticEntityList) {
			e.render(g);
		}
	}

	public ReadableColor getColor() {
		return color;
	}

	public void setColor(final ReadableColor color) {
		this.color = color;
	}

}
