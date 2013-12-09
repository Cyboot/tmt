package net.tmt.map;

import net.tmt.game.manager.EntityManager;
import net.tmt.map.generator.MapGenerator;
import net.tmt.util.Vector2d;

public class SpaceMap extends WorldMap {
	public static final int	CHUNK_SIZE	= 2048;
	private static SpaceMap	instance	= null;

	private boolean			isInit		= false;

	@Override
	public void update(final Vector2d offset, final EntityManager entityManager, final double delta) {
		if (!isInit) {
			MapGenerator.generateSpaceMap(this, entityManager);
			isInit = true;
		}

		super.update(offset, entityManager, delta);
	}

	public static SpaceMap getInstance() {
		if (instance == null)
			instance = new SpaceMap();
		return instance;
	}

	public SpaceMap() {
		setChunkSize(CHUNK_SIZE);

	}
}
