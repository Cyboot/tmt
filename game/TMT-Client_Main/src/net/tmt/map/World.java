package net.tmt.map;

import net.tmt.entity.Entity2D;
import net.tmt.game.GameEngine;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

public class World implements Updateable, Renderable {
	private static final double	RATIO	= 1.8;
	private double				MOVE_DIFF_WIDTH;
	private double				MOVE_DIFF_HEIGHT;
	private double				MOVE_MAX_WIDTH;
	private double				MOVE_MAX_HEIGTH;

	private Vector2d			tmp		= new Vector2d();
	private Vector2d			offset	= new Vector2d();

	private WorldMap			map;
	private Entity2D			player;
	private EntityManager		entityManager;

	public World() {
		// TODO: quick & dirty: Worldoffset
		MOVE_MAX_WIDTH = (GameEngine.WIDTH / 2) * 1 / 50.;
		MOVE_DIFF_WIDTH = (GameEngine.WIDTH / 2 - MOVE_MAX_WIDTH) * RATIO;

		MOVE_MAX_HEIGTH = (GameEngine.HEIGHT / 2) * 1 / 50.;
		MOVE_DIFF_HEIGHT = (GameEngine.HEIGHT / 2 - MOVE_MAX_HEIGTH) * RATIO;
	}

	@Override
	public void update(final double delta) {
		if (player != null)
			centerAroundPlayer(delta * 1000);

		if (map != null)
			map.update(getOffsetCentered(), entityManager, delta);
	}

	@Override
	public void render(final Graphics g) {
		if (map != null)
			map.render(g);
	}

	private void centerAroundPlayer(final double delta) {
		double dx = getOffsetCentered().x - player.getPos().x;
		double dy = getOffsetCentered().y - player.getPos().y;

		if (dx > MOVE_MAX_WIDTH || dx < -MOVE_MAX_WIDTH) {
			double dxInner = Math.abs(dx) - MOVE_MAX_WIDTH;
			double factor = dxInner / MOVE_DIFF_WIDTH;
			factor = Math.pow(factor, 3);

			if (dx > 0)
				offset.x -= factor * dxInner * delta;
			else
				offset.x += factor * dxInner * delta;
		}

		if (dy > MOVE_MAX_HEIGTH || dy < -MOVE_MAX_HEIGTH) {
			double dyInner = Math.abs(dy) - MOVE_MAX_HEIGTH;
			double factor = dyInner / MOVE_DIFF_HEIGHT;
			factor = Math.pow(factor, 3);

			if (dy > 0)
				offset.y -= factor * dyInner * delta;
			else
				offset.y += factor * dyInner * delta;
		}
	}

	public Vector2d getOffset() {
		return offset;
	}

	public Vector2d getOffsetCentered() {
		tmp.x = offset.x + GameEngine.WIDTH / 2;
		tmp.y = offset.y + GameEngine.HEIGHT / 2;
		return tmp;
	}

	public void setPlayer(final Entity2D player) {
		this.player = player;
	}

	public Entity2D getPlayer() {
		return player;
	}

	public void setMap(final WorldMap m) {
		map = m;
	}

	public WorldMap getMap() {
		return map;
	}

	/**
	 * adds an Entity to the map
	 * 
	 * @param e
	 *            Entity to add
	 * @return if Entity was added successfull
	 */
	public boolean addStaticEntity(final Entity2D e) {
		boolean freeToBuild = map.chunkFreeToBuild(e.getPos(), entityManager);

		if (freeToBuild) {
			entityManager.addEntity(e);
			if (map instanceof PlanetMap)
				map.setChunkNotEmpty(e.getPos());
		}

		return freeToBuild;
	}

	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
