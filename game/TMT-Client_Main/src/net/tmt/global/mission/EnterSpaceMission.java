package net.tmt.global.mission;

import net.tmt.game.manager.GameManager;
import net.tmt.gamestate.SpaceGamestate;

public class EnterSpaceMission extends Mission {

	public EnterSpaceMission() {
		super("Back to Space", "");
	}

	@Override
	public void update(final double delta) {
		super.update(delta);

		// immediatly finish mission because it changes to SpaceGamestate
		if (getState() == State.ACTIVE)
			finish(Medal.NONE);
	}

	@Override
	protected void finish(final Medal medal) {
		super.finish(medal);
		GameManager gm = GameManager.getInstance();
		gm.pauseActiveGame();
		gm.resume(SpaceGamestate.getInstance().getId());
	}
}
