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
import net.tmt.global.mission.MissionManager;

/**
 * Manages the Gamestates. (pause/resume, active/inactive/background Gamestates)
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public class GameManager implements Updateable, Renderable {
	private static GameManager		instance;

	private boolean					dirtyGamestateswitch	= false;
	/** all inactive, but yet not deleted Gamestates */
	private List<AbstractGamestate>	inactivedGamestates		= new CopyOnWriteArrayList<>();
	/** all updated, but not visible Gamestates */
	private List<AbstractGamestate>	backgroundGamestates	= new CopyOnWriteArrayList<>();
	/** the active and visible Gamestate */
	private AbstractGamestate		activeGamestate;


	private GuiManager				guiManager;
	private MissionManager			missionManager;

	public static GameManager init() {
		instance = new GameManager();
		instance.guiManager = GuiManager.init();
		instance.missionManager = MissionManager.getInstance();

		instance.pause(SpaceGamestate.getInstance());
		instance.pause(SimulatorGamestate.getInstance());
		instance.pause(EconomyGamestate.getInstance());
		instance.pause(DummyGamestate.getInstance());

		instance.resume(SpaceGamestate.getInstance().getId());
		return instance;
	}

	@Override
	public void render(final Graphics g) {
		// don't render if Gamestate was switched before (to make sure update()
		// is always called before render() )
		if (dirtyGamestateswitch)
			return;

		activeGamestate.render(g);
		guiManager.render(g);
	}

	@Override
	public void update(final double delta) {
		dirtyGamestateswitch = false;
		activeGamestate.update(delta);

		for (AbstractGamestate a : backgroundGamestates)
			a.update(delta);

		missionManager.update(activeGamestate.getEntityManager(), activeGamestate.getWorld(), delta);
		guiManager.update(delta);
	}

	/**
	 * Resumes the Gamestate for the given id. The previous Gamestate will be
	 * send to background
	 * 
	 * @param id
	 *            of the Gamestate to continue
	 * @throws IllegalArgumentException
	 *             if no such Gamestate/class was paused before
	 */
	public void resume(final int id) {
		AbstractGamestate toResume = null;

		for (AbstractGamestate a : inactivedGamestates) {
			if (a.getId() == id) {
				toResume = a;
				break;
			}
		}
		for (AbstractGamestate a : backgroundGamestates) {
			if (a.getId() == id) {
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
			throw new IllegalArgumentException("Cannot resume Gamestate #" + id + " because it was not paused before!");

		dirtyGamestateswitch = true;
	}

	/**
	 * adds and starts a new Gamestate
	 * 
	 * @param gamestate
	 */
	public void startNew(final AbstractGamestate gamestate) {
		pause(gamestate);
		resume(gamestate.getId());
	}

	/**
	 * pauses the actual Gamestate
	 */
	public void pauseActiveGame() {
		pause(activeGamestate);
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
	 * makes the given Gamestate to a background Gamestate
	 * 
	 * @param gamestate
	 */
	private void background(final AbstractGamestate gamestate) {
		gamestate.setState(AbstractGamestate.BACKGROUND);
		inactivedGamestates.remove(gamestate);

		if (gamestate == activeGamestate) {
			activeGamestate = null;
		}

		// don't duplicate Gamestate in list
		if (!backgroundGamestates.contains(gamestate))
			backgroundGamestates.add(gamestate);
	}

	public AbstractGamestate getActiveGamestate() {
		return activeGamestate;
	}

	public List<AbstractGamestate> getBackgroundGamestates() {
		return backgroundGamestates;
	}

	public List<AbstractGamestate> getInactivedGamestates() {
		return inactivedGamestates;
	}

	public static GameManager getInstance() {
		return instance;
	}
}
