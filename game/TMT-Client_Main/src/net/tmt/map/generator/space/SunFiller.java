package net.tmt.map.generator.space;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tmt.entity.ambient.Prop;
import net.tmt.entity.ambient.Prop.Type;
import net.tmt.entity.statics.Planet;
import net.tmt.game.manager.EntityManager;
import net.tmt.map.Chunk;
import net.tmt.map.Terrain;
import net.tmt.map.generator.ChunkFiller;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class SunFiller implements ChunkFiller {
	private EntityManager	entityManager;

	@Override
	public void fill(final Chunk chunk, final EntityManager entityManager) {
		Vector2d center = chunk.getCoord().center2pos(chunk.size);

		RandomUtil.setSeed(chunk.hashCode());
		this.entityManager = entityManager;

		addSun(center);
		RandomUtil.setSeed(chunk.hashCode());
		addPlanet(center, chunk.size, RandomUtil.intRange(1, 7));
	}

	private void addSun(final Vector2d center) {
		entityManager.addEntity(Prop.createProp(Type.SUN, center.copy()), EntityManager.LAYER_1_BACK);
	}

	private void addPlanet(final Vector2d star, final double radiusFactor, final int count) {
		List<Double> radii = new ArrayList<>();
		radii.add(1.);
		radii.add(1.5);
		radii.add(1.9);
		radii.add(2.5);
		radii.add(3.);
		radii.add(4.);
		radii.add(5.5);
		radii.add(6.5);
		radii.add(8.);
		radii.add(9.);
		Collections.shuffle(radii);

		for (int i = 0; i < count; i++) {
			double radius = radii.get(i) * radiusFactor;
			double angle = RandomUtil.doubleRange(0, Math.PI * 2);

			Planet planet = new Planet(Terrain.PLANET_GRASS, 300, star, angle, radius);
			entityManager.addEntity(planet);
		}
	}
}
