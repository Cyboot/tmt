package net.tmt.map;

import net.tmt.entity.Entity2D;
import net.tmt.game.GameEngine;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.game.manager.EntityManager;
import net.tmt.game.manager.ZoomManager;
import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.input.Keyboard;

public class World implements Updateable, Renderable {
	private Vector2d		offset	= new Vector2d(GameEngine.WIDTH / 2, GameEngine.HEIGHT / 2);

	private WorldMap		map;
	private Entity2D		player;
	private EntityManager	entityManager;

	public World(final EntityManager entityManager, final WorldMap map) {
		this.entityManager = entityManager;
		this.map = map;
	}

	@Override
	public void update(final double delta) {
		if (player != null)
			centerAroundPlayer(delta * 1000);

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			offset.x -= delta * 500;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			offset.x += delta * 500;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			offset.y += delta * 500;
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			offset.y -= delta * 500;

		if (map != null)
			map.update(offset, entityManager, delta);
	}

	@Override
	public void render(final Graphics g) {
		if (map != null)
			map.render(g);
	}


	private void centerAroundPlayer(final double delta) {
		double dx = offset.x - player.getPos().x;
		double dy = offset.y - player.getPos().y;

		// 1/6 from center --> 1/12 for both sides
		double factor = 1 / 12.;
		double BORDER_WIDTH = ZoomManager.getWidthZoomed() * factor;
		double BORDER_HEIGHT = ZoomManager.getHeightZoomed() * factor;


		if (Math.abs(dx) > BORDER_WIDTH) {
			double movefactor = (Math.abs(dx) - BORDER_WIDTH) / BORDER_WIDTH;
			movefactor = Math.pow(movefactor, 3);

			if (dx > 0)
				offset.x -= movefactor * delta;
			else
				offset.x += movefactor * delta;
		}

		if (Math.abs(dy) > BORDER_HEIGHT) {
			double movefactor = (Math.abs(dy) - BORDER_HEIGHT) / BORDER_HEIGHT;
			movefactor = Math.pow(movefactor, 2);

			if (movefactor > 2)
				System.out.println(movefactor);

			if (dy > 0)
				offset.y -= movefactor * delta;
			else
				offset.y += movefactor * delta;
		}
	}

	public Vector2d getOffset() {
		return offset;
	}

	public void setPlayer(final Entity2D player) {
		this.player = player;
	}

	public Entity2D getPlayer() {
		return player;
	}

	public WorldMap getMap() {
		return map;
	}

	/**
	 * calculates the corresponding OnScreen-Vector to the given World-Vector
	 * 
	 * @param posOnWorld
	 * @return the Vector on Screen (in pixel)
	 */
	public Vector2d getVectorOnScreen(final Vector2d posOnWorld) {
		Vector2d result = posOnWorld.copy().sub(offset);
		result.multiply(ZoomManager.getZoom());
		result.add(GameEngine.WIDTH / 2, GameEngine.HEIGHT / 2);

		return result;
	}

	/**
	 * calculates the corresponding OnScreen-Vector to the given World-Vector
	 * 
	 * @param posOnWorld
	 * @return the Vector on Screen (in pixel)
	 */
	public Vector2d getVectorOnWorld(final Vector2d posOnScreen) {
		Vector2d result = new Vector2d(-GameEngine.WIDTH / 2, -GameEngine.HEIGHT / 2);
		result.add(posOnScreen);
		result.multiply(ZoomManager.getZoomInverse());
		result.add(offset);

		return result;
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
}
