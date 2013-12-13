package net.tmt.map.generator;

import net.tmt.game.manager.EntityManager;
import net.tmt.map.Chunk;

public interface ChunkFiller {

	void fill(Chunk chunk, EntityManager entityManager);

}
