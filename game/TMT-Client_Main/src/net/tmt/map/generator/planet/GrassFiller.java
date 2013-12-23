package net.tmt.map.generator.planet;

import net.tmt.entity.ambient.Prop;
import net.tmt.entity.ambient.Prop.Type;
import net.tmt.game.manager.EntityManager;
import net.tmt.map.Chunk;
import net.tmt.map.PlanetMap;
import net.tmt.map.generator.ChunkFiller;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class GrassFiller implements ChunkFiller {

	@Override
	public void fill(final Chunk chunk, final EntityManager entityManager) {
		addTrees(chunk, entityManager, PlanetMap.CHUNK_SIZE);
		addDecals(chunk, entityManager, PlanetMap.CHUNK_SIZE);
	}

	private static void addDecals(final Chunk chunk, final EntityManager entityManager, final int chunkSize) {
		Vector2d centerPos = chunk.getCoord().center2pos(chunkSize);
		for (int i = 0; i < RandomUtil.intRange(0, 2); i++) {

			Vector2d pos = centerPos.copy();
			pos.x += RandomUtil.doubleRange(-chunkSize / 2, chunkSize / 2);
			pos.y += RandomUtil.doubleRange(-chunkSize / 2, chunkSize / 2);

			Prop decal = Prop.createProp(Type.DECAL, pos, entityManager.getCollisionManager());
			entityManager.addEntity(decal, EntityManager.LAYER_1_BACK);
		}
	}

	private static void addTrees(final Chunk chunk, final EntityManager entityManager, final int chunkSize) {
		Vector2d centerPos = chunk.getCoord().center2pos(chunkSize);
		for (int i = 0; i < RandomUtil.intRange(0, 2); i++) {

			Vector2d pos = centerPos.copy();
			pos.x += RandomUtil.doubleRange(-chunkSize / 2, chunkSize / 2);
			pos.y += RandomUtil.doubleRange(-chunkSize / 2, chunkSize / 2);

			Prop tree = Prop.createProp(Type.TREE, pos, entityManager.getCollisionManager());
			entityManager.addEntity(tree);
		}
	}

}
