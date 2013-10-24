package net.tmt.game.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.gamestate.AbstractGamestate;
import net.tmt.gamestate.DummyGamestate;
import net.tmt.gamestate.SpaceGamestate;
import net.tmt.gfx.Graphics;

/**
 * Manages the Gamestates. (pause/resume, active/inactive/background Gamestates)
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public class GameManager implements Updateable, Renderable {
	private static GameManager		instance;

	/** all inactive, but yet not deleted Gamestates */
	private List<AbstractGamestate>	inactivedGamestates		= new CopyOnWriteArrayList<>();
	/** all updated, but not visible Gamestates */
	private List<AbstractGamestate>	backgroundGamestates	= new CopyOnWriteArrayList<>();
	/** the active and visible Gamestate */
	private AbstractGamestate		activeGamestate;


	public static GameManager init() {
		instance = new GameManager();

		instance.start(SpaceGamestate.getInstance());
		instance.background(new DummyGamestate());
		return instance;
	}

	@Override
	public void render(final Graphics g) {
		activeGamestate.render(g);
	}

	@Override
	public void update(final double delta) {
		activeGamestate.update(delta);

		for (AbstractGamestate a : backgroundGamestates)
			a.update(delta);
	}

	/**
	 * Resumes the Gamestate for the given class.
	 * 
	 * @param clazz
	 *            of the Gamestate to continue
	 * @throws IllegalArgumentException
	 *             if no such Gamestate/class was paused before
	 */
	public void resume(final Class<AbstractGamestate> clazz) {
		for (AbstractGamestate a : inactivedGamestates) {
			if (a.getClass().equals(clazz)) {
				activeGamestate = a;
				a.setState(AbstractGamestate.ACTIVE);
				return;
			}
		}
		throw new IllegalArgumentException("Cannot resume '" + clazz + "' because it was not paused before!");
	}

	/**
	 * pauses the given Gamestate (mark it as inactive, so it can be resumed
	 * afterwards)
	 * 
	 * @param gamestate
	 */
	public void pause(final AbstractGamestate gamestate) {
		gamestate.setState(AbstractGamestate.PAUSED);
		backgroundGamestates.remove(gamestate);

		if (gamestate == activeGamestate) {
			activeGamestate = null;
		}

		// don't duplicate Gamestate in list
		if (!inactivedGamestates.contains(gamestate))
			inactivedGamestates.add(gamestate);
	}

	/**
	 * makes the given Gamestate to a background Gamestate
	 * 
	 * @param gamestate
	 */
	public void background(final AbstractGamestate gamestate) {
		gamestate.setState(AbstractGamestate.BACKGROUND);
		inactivedGamestates.remove(gamestate);

		if (gamestate == activeGamestate) {
			activeGamestate = null;
		}

		// don't duplicate Gamestate in list
		if (!backgroundGamestates.contains(gamestate))
			backgroundGamestates.add(gamestate);
	}

	/**
	 * stops and deletes the given Gamestate. It cannot resumed afterwards
	 * 
	 * @param gamestate
	 */
	public void delete(final AbstractGamestate gamestate) {
		inactivedGamestates.remove(gamestate);
		backgroundGamestates.remove(gamestate);

		if (activeGamestate == gamestate)
			activeGamestate = null;
	}

	/**
	 * starts the given Gamestate as the one active Gamestate
	 * 
	 * @param gamestate
	 */
	public void start(final AbstractGamestate gamestate) {
		gamestate.setState(AbstractGamestate.ACTIVE);
		inactivedGamestates.remove(gamestate);
		backgroundGamestates.remove(gamestate);

		activeGamestate = gamestate;
	}

	public static GameManager getInstance() {
		return instance;
	}
}
