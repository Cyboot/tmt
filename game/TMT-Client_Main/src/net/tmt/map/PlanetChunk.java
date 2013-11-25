package net.tmt.map;

import net.tmt.entity.Entity2D;
import net.tmt.entity.economy.Building;

public class PlanetChunk extends Chunk {
	private boolean	hasBuilding	= false;


	public PlanetChunk(final Coordinate coord, final Terrain terrain, final int size) {
		super(coord, terrain, size);
	}

	@Override
	public boolean addStaticEntity(final Entity2D e) {
		// #27 maybe only one Building per chunk?
		if (e instanceof Building) {
			if (hasBuilding)
				return false;
			hasBuilding = true;
		}

		return super.addStaticEntity(e);
	}
}
