package net.tmt.mission;

import net.tmt.entity.statics.Planet;
import net.tmt.game.manager.GameManager;
import net.tmt.gamestate.PlanetGamestate;

public class PlanetMission extends Mission {
	private Planet	planet;

	public PlanetMission(final Planet planet) {
		super("Planet #" + planet.getId(), "Welcome on this wonderfull planet. Enjoy lot of the lovley terrain "
				+ planet.getBaseTerrain().name());
		this.planet = planet;
	}

	@Override
	public void update(final double delta) {
		// immediatly finish mission because it changes to PlanetGamestate
		finish();
	}

	@Override
	public void start() {
		GameManager gm = GameManager.getInstance();
		gm.pause(gm.getActiveGamestate());
		gm.start(new PlanetGamestate(planet));
	}

}
