package net.tmt.gamestate;

import net.tmt.entity.Entity2D;
import net.tmt.game.interfaces.EntityWorldGetter;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.game.manager.EntityManager;
import net.tmt.game.manager.GameManager;
import net.tmt.game.manager.GuiManager;
import net.tmt.gfx.Graphics;
import net.tmt.gui.Gui;
import net.tmt.map.World;
import net.tmt.map.WorldMap;

public abstract class AbstractGamestate implements Updateable, Renderable, EntityWorldGetter {
	public static final int	ACTIVE		= 0;
	public static final int	PAUSED		= 1;
	public static final int	BACKGROUND	= 2;

	private static int		currentID	= 0;
	private int				id			= currentID++;
	private int				state;

	private Entity2D		player;
	protected EntityManager	entityManager;
	protected GameManager	gameManager	= GameManager.getInstance();
	protected GuiManager	guiManager	= GuiManager.getInstance();
	protected World			world;
	private Gui				gui;

	protected AbstractGamestate(final WorldMap worldmap) {
		entityManager = new EntityManager();
		world = new World(entityManager, worldmap);
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

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Entity2D getPlayer() {
		return player;
	}

	public void setPlayer(final Entity2D player) {
		this.player = player;
		if (world != null)
			world.setPlayer(player);
	}

	public Gui getGui() {
		return gui;
	}

	public void setGui(Gui gui) {
		this.gui = gui;
	}
}
