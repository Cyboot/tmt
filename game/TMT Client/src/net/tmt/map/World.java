package net.tmt.map;

import java.util.HashMap;
import java.util.Map;

import net.tmt.entity.Entity2D;
import net.tmt.game.GameEngine;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

public class World implements Updateable, Renderable {
	private static final double			RATIO			= 1.8;
	private double						MOVE_DIFF_WIDTH;
	private double						MOVE_DIFF_HEIGHT;
	private double						MOVE_MAX_WIDTH;
	private double						MOVE_MAX_HEIGTH;

	private static Map<Class<?>, World>	worldInstances	= new HashMap<Class<?>, World>();
	private static World				activeWorld;

	private Vector2d					tmp				= new Vector2d();
	private Vector2d					offset			= new Vector2d();

	private WorldMap					map;
	private Entity2D					player;

	private World() {
		// TODO: quick & dirty: Worldoffset
		MOVE_MAX_WIDTH = (GameEngine.WIDTH / 2) * 1 / 50.;
		MOVE_DIFF_WIDTH = (GameEngine.WIDTH / 2 - MOVE_MAX_WIDTH) * RATIO;

		MOVE_MAX_HEIGTH = (GameEngine.HEIGHT / 2) * 1 / 50.;
		MOVE_DIFF_HEIGHT = (GameEngine.HEIGHT / 2 - MOVE_MAX_HEIGTH) * RATIO;
	}

	@Override
	public void update(final double delta) {
		centerAroundPlayer(delta * 1000);
		map.update(getOffsetCentered(), player.getPos());
	}

	@Override
	public void render(final Graphics g) {
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

	private Vector2d getOffsetCentered() {
		tmp.x = offset.x + GameEngine.WIDTH / 2;
		tmp.y = offset.y + GameEngine.HEIGHT / 2;
		return tmp;
	}

	public static void init(final Class<?> clazz) {
		worldInstances.put(clazz, new World());
	}

	public void setPlayer(final Entity2D ship) {
		this.player = ship;
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

	public static World getActiveWorld() {
		return activeWorld;
	}

	public static void setActiveWorld(final Class<?> clazz) {
		activeWorld = worldInstances.get(clazz);
	}
}
