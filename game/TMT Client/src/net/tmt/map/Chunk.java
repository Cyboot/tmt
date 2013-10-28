package net.tmt.map;

import java.util.ArrayList;

import net.tmt.entity.Entity2D;


public class Chunk {

	public int					terrain;
	public int					size;
	public Coordinate			position;
	// FIXME: "store" entities in the chunks they're located in
	private ArrayList<Entity2D>	entities	= new ArrayList<Entity2D>();

	public Chunk(final Coordinate position, final int terrain, final int size) {
		this.position = position;
		this.terrain = terrain;
		this.size = size;
	}

	public void addEntity(final Entity2D o) {
		entities.add(o);
	}

	public ArrayList<Entity2D> getEntities() {
		return entities;
	}

}
