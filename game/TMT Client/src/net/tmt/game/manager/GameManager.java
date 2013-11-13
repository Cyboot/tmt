package net.tmt.game.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.tmt.game.interfaces.Renderable;
import net.tmt.game.interfaces.Updateable;
import net.tmt.gamestate.AbstractGamestate;
import net.tmt.gamestate.DummyGamestate;
import net.tmt.gamestate.EconomyGamestate;
import net.tmt.gamestate.SimulatorGamestate;
import net.tmt.gamestate.SpaceGamestate;
import net.tmt.gfx.Graphics;
import net.tmt.map.World;

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

	private GuiManager				guiManager;

	public static GameManager init() {
		instance = new GameManager();
		instance.guiManager = GuiManager.init();

		instance.pause(new SpaceGamestate());
		instance.pause(new SimulatorGamestate());
		instance.pause(EconomyGamestate.getInstance());
		instance.pause(new DummyGamestate());

		instance.resume(SpaceGamestate.class);
		return instance;
	}

	@Override
	public void render(final Graphics g) {
		activeGamestate.render(g);
		guiManager.render(g);
	}

	@Override
	public void update(final double delta) {
		activeGamestate.update(delta);

		for (AbstractGamestate a : backgroundGamestates)
			a.update(delta);

		guiManager.update(delta);
	}

	/**
	 * Resumes the Gamestate for the given class. the previous Gamestate will be
	 * send to background
	 * 
	 * @param clazz
	 *            of the Gamestate to continue
	 * @throws IllegalArgumentException
	 *             if no such Gamestate/class was paused before
	 */
	public void resume(final Class<? extends AbstractGamestate> clazz) {
		AbstractGamestate toResume = null;

		for (AbstractGamestate a : inactivedGamestates) {
			if (a.getClass().equals(clazz)) {
				toResume = a;
				break;
			}
		}
		for (AbstractGamestate a : backgroundGamestates) {
			if (a.getClass().equals(clazz)) {
				toResume = a;
				break;
			}
		}
		if (toResume != null) {
			if (activeGamestate != null)
				background(activeGamestate);

			activeGamestate = toResume;
			activeGamestate.setState(AbstractGamestate.ACTIVE);

			inactivedGamestates.remove(activeGamestate);
			backgroundGamestates.remove(activeGamestate);
		} else
			throw new IllegalArgumentException("Cannot resume '" + clazz + "' because it was not paused before!");

		prepareWorld();
	}

	public void start(final AbstractGamestate gamestate) {
		pause(gamestate);
		resume(gamestate.getClass());
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

	private void prepareWorld() {
		World.setActiveWorld(activeGamestate.getClass());
		// make sure update() is called before render() is called
		World.getActiveWorld().update(0);
	}


	public AbstractGamestate getActiveGamestate() {
		return activeGamestate;
	}

	public static GameManager getInstance() {
		return instance;
	}

	public List<AbstractGamestate> getBackgroundGamestates() {
		return backgroundGamestates;
	}
}
