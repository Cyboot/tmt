package net.tmt.map.generator.space;

import net.tmt.entity.ambient.Prop;
import net.tmt.entity.ambient.Prop.Type;
import net.tmt.game.manager.EntityManager;
import net.tmt.map.Chunk;
import net.tmt.map.generator.ChunkFiller;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class AsteroidFiller implements ChunkFiller {
	@Override
	public void fill(final Chunk chunk, final EntityManager entityManager) {
		Vector2d pos = chunk.getCoord().center2pos(chunk.size);

		int seed = chunk.getCoord().hashCode();
		RandomUtil.setSeed(seed);
		for (int i = 0; i < 30; i++) {
			double deltaX = RandomUtil.doubleRange(-256, 256);
			double deltaY = RandomUtil.doubleRange(-256, 256);

			entityManager.addEntity(Prop.createProp(Type.SPACEROCK, pos.copy().add(deltaX, deltaY)),
					EntityManager.LAYER_2_MEDIUM);
		}
	}
}
