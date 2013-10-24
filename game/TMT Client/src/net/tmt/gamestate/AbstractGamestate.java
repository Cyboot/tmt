package net.tmt.gamestate;

import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.game.manager.GameManager;
import net.tmt.gfx.Graphics;

public abstract class AbstractGamestate implements Updateable, Renderable {
	public static final int	ACTIVE		= 0;
	public static final int	PAUSED		= 1;
	public static final int	BACKGROUND	= 2;

	private int				state;
	protected GameManager	gameManager	= GameManager.getInstance();


	@Override
	public abstract void update(final double delta);

	@Override
	public abstract void render(Graphics g);

	public int getState() {
		return state;
	}

	public void setState(final int state) {
		int old = this.state;
		this.state = state;
		onStateChanged(old, state);
	}

	/**
	 * called if the state is changed
	 * 
	 * @param oldState
	 * @param newState
	 */
	protected void onStateChanged(final int oldState, final int newState) {
	}
}
