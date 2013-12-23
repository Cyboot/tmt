package net.tmt.map;

import net.tmt.entity.statics.Planet;
import net.tmt.game.manager.EntityManager;
import net.tmt.map.generator.MapGenerator;
import net.tmt.util.Vector2d;


public class PlanetMap extends WorldMap {
	public static final int	CHUNK_SIZE	= 512;

	private int				uniqueId;
	private boolean			isInit		= false;

	public PlanetMap(final Terrain baseTerrain, final int uniqueId) {
		setChunkSize(CHUNK_SIZE);
		setBaseTerrain(baseTerrain);
		this.uniqueId = uniqueId;

	}

	public PlanetMap(final Planet planet) {
		this(planet.getBaseTerrain(), planet.getPos().hashCode());
	}

	@Override
	public void update(final Vector2d offset, final EntityManager entityManager, final double delta) {
		if (!isInit) {
			MapGenerator.generatePlanetMap(this, entityManager);
			isInit = true;
		}

		super.update(offset, entityManager, delta);
	}

	@Override
	public int hashCode() {
		return uniqueId;
	}
}
