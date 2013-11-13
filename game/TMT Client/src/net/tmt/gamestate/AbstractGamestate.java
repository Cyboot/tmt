package net.tmt.gamestate;

import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.game.manager.GameManager;
import net.tmt.game.manager.GuiManager;
import net.tmt.gfx.Graphics;

public abstract class AbstractGamestate implements Updateable, Renderable {
	public static final int	ACTIVE		= 0;
	public static final int	PAUSED		= 1;
	public static final int	BACKGROUND	= 2;

	private int				state;
	protected GameManager	gameManager	= GameManager.getInstance();
	protected GuiManager	guiManager	= GuiManager.getInstance();

	@Override
	public abstract void update(final double delta);

	@Override
	public abstract void render(Graphics g);

	public abstract void requestMap();

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
}
