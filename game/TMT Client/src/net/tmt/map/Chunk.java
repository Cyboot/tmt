package net.tmt.map;

import java.util.ArrayList;

import net.tmt.entity.Entity2D;


public class Chunk {

	public int					terrain;
	public int					size;
	public Coordinate			coord;
	private ArrayList<Entity2D>	staticEntityList	= new ArrayList<Entity2D>();

	public Chunk(final Coordinate coord, final int terrain, final int size) {
		this.coord = coord;
		this.terrain = terrain;
		this.size = size;
	}

	public Chunk(final Coordinate coord, final int terrain, final int size, final ArrayList<Entity2D> entities) {
		this.coord = coord;
		this.terrain = terrain;
		this.size = size;
		this.staticEntityList = entities;
	}

	public void addStaticEntity(final Entity2D e) {
		staticEntityList.add(e);
	}

	public ArrayList<Entity2D> getStaticEntities() {
		return staticEntityList;
	}

}
