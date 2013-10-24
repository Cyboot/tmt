package net.tmt.map;

import java.util.ArrayList;


public class Chunk {

	public int					terrain;
	public int					size;
	public Coordinate			position;
	private ArrayList<Object>	objects	= new ArrayList<Object>();

	public Chunk(final Coordinate position, final int terrain, final int size) {
		this.position = position;
		this.terrain = terrain;
		this.size = size;
	}

	public void addObject(final Object o) {
		objects.add(o);
	}

	public ArrayList<Object> getObjects() {
		return objects;
	}

}
