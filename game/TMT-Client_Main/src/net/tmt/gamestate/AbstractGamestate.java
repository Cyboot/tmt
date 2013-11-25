package net.tmt.gamestate;

import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.game.manager.EntityManager;
import net.tmt.game.manager.GameManager;
import net.tmt.game.manager.GuiManager;
import net.tmt.gfx.Graphics;
import net.tmt.map.World;
import net.tmt.map.WorldMap;

public abstract class AbstractGamestate implements Updateable, Renderable {
	public static final int	ACTIVE		= 0;
	public static final int	PAUSED		= 1;
	public static final int	BACKGROUND	= 2;

	private static int		currentID	= 0;
	private int				id			= currentID++;
	private int				state;

	protected EntityManager	entityManager;
	protected GameManager	gameManager	= GameManager.getInstance();
	protected GuiManager	guiManager	= GuiManager.getInstance();
	protected World			world;

	protected AbstractGamestate(final WorldMap worldmap) {
		world = new World();
		world.setMap(worldmap);
		entityManager = new EntityManager(world);

		if (worldmap != null)
			worldmap.setEntityManager(entityManager);
	}

	@Override
	public abstract void update(final double delta);

	@Override
	public void render(final Graphics g) {
		g.setOffset(world.getOffset());
	}

	public int getState() {
		return state;
	}

	public void setState(final int state) {
		// ignore if state was not changed
		if (state == this.state)
			return;

		int oldState = this.state;
		this.state = state;

		if (state == ACTIVE)
			onResume(oldState);
		if (state == PAUSED)
			onPause(oldState);
		if (state == BACKGROUND)
			onBackground(oldState);
	}

	/**
	 * called if Gamestate was put in background
	 * 
	 * @param oldState
	 *            state before
	 */
	protected void onBackground(final int oldState) {

	}

	/**
	 * called if Gamestate was paused
	 * 
	 * @param oldState
	 *            state before
	 */
	protected void onPause(final int oldState) {

	}

	/**
	 * called if Gamestate is becoming active again
	 * 
	 * @param oldState
	 *            state before
	 */
	protected void onResume(final int oldState) {

	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " #" + id;
	}

	public World getWorld() {
		return world;
	}
}
