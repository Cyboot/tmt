package net.tmt.map;

import java.util.ArrayList;

import net.tmt.entity.Entity2D;
import net.tmt.entity.statics.Planet;
import net.tmt.game.interfaces.Renderable;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;


public class Chunk implements Renderable {

	public int					terrain;
	public int					size;
	public Coordinate			coord;
	// DEBUG border color
	private ReadableColor		color;
	private ArrayList<Entity2D>	staticEntityList	= new ArrayList<Entity2D>();

	public Chunk(final Coordinate coord, final int terrain, final int size) {
		this.coord = new Coordinate(coord.x, coord.y);
		this.terrain = terrain;
		setColorAccordingToTerrain(terrain);
		this.size = size;
	}

	public Chunk(final int x, final int y, final int terrain, final int size) {
		this.coord = new Coordinate(x, y);
		this.terrain = terrain;
		setColorAccordingToTerrain(terrain);
		this.size = size;
	}

	public Chunk(final Coordinate coord, final int terrain, final int size, final ArrayList<Entity2D> entities) {
		this.coord = new Coordinate(coord.x, coord.y);
		this.terrain = terrain;
		setColorAccordingToTerrain(terrain);
		this.size = size;
		this.staticEntityList = entities;
	}

	private void setColorAccordingToTerrain(final int terrain) {
		switch (terrain) {
		case WorldMap.TERRAIN_VOID:
			color = Color.RED;
			break;
		case WorldMap.TERRAIN_ASTEROIDS:
			color = Color.GREEN;
			break;
		case WorldMap.TERRAIN_DEBRIS:
			color = Color.BLUE;
			break;
		case WorldMap.TERRAIN_GRASS:
			color = Color.GREEN;
			break;
		case WorldMap.TERRAIN_WATER:
			color = Color.BLUE;
			break;
		case WorldMap.TERRAIN_DESERT:
			color = Color.YELLOW;
			break;
		case WorldMap.TERRAIN_SNOW:
			color = Color.WHITE;
			break;
		case WorldMap.TERRAIN_FOREST:
			color = Color.DKGREY;
			break;
		case WorldMap.TERRAIN_SWAMP:
			color = Color.PURPLE;
			break;
		}
	}

	public void addStaticEntity(final Entity2D e) {
		staticEntityList.add(e);
	}

	public ArrayList<Entity2D> getStaticEntities() {
		return staticEntityList;
	}

	public void update(final Vector2d playerPos, final World world) {
		for (Entity2D e : staticEntityList) {
			if (e instanceof Planet) {
				((Planet) e).landingCheck(world, playerPos);
			}
		}
	}

	@Override
	public void render(final Graphics g) {
		g.setColor(color);
		g.drawRect(coord.center2pos(size).x - (size / 2), coord.center2pos(size).y - (size / 2), size - 10, size - 10);
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
