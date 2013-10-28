package net.tmt.map;

import java.util.ArrayList;

import net.tmt.entity.Entity2D;


public class Chunk {

	public int					terrain;
	public int					size;
	public Coordinate			position;
	// FIXME: "store" entities in the chunks they're located in
	private ArrayList<Entity2D>	entities	= new ArrayList<Entity2D>();
	private ArrayList<Object>	mapObjects	= new ArrayList<Object>();

	public Chunk(final Coordinate position, final int terrain, final int size) {
		this.position = position;
		this.terrain = terrain;
		this.size = size;
	}

	public Chunk(final Coordinate position, final int terrain, final int size, final ArrayList<Entity2D> entities,
			final ArrayList<Object> mapObjects) {
		this.position = position;
		this.terrain = terrain;
		this.size = size;
		this.entities = entities;
		this.mapObjects = mapObjects;
	}

	public void addEntity(final Entity2D e) {
		entities.add(e);
	}

	public void addMapObject(final Object o) {
		mapObjects.add(o);
	}

	public ArrayList<Entity2D> getEntities() {
		return entities;
	}

	public ArrayList<Object> getMapObjects() {
		return mapObjects;
	}

}
