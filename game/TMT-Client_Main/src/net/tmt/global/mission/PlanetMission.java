package net.tmt.global.mission;

import net.tmt.entity.statics.Planet;
import net.tmt.game.manager.GameManager;
import net.tmt.gamestate.AbstractGamestate;
import net.tmt.gamestate.PlanetGamestate;
import net.tmt.global.RPLevel;

public class PlanetMission extends Mission {
	private static final int	RP_FOR_NEW_PLANET	= 200;
	private static final int	RP_FOR_OLD_PLANET	= 50;
	private Planet				planet;

	public PlanetMission(final Planet planet) {
		super(planet.getName() + " (Planet #" + planet.getId() + ")",
				"Welcome to this wonderful planet. Here you can enjoy a lot of the lovley terrain type "
						+ planet.getBaseTerrain().name());
		this.planet = planet;
	}

	@Override
	public void update(final double delta) {
		super.update(delta);

		switch (getState()) {
		case ACTIVE:
			// immediatly finish mission because it changes to PlanetGamestate
			finish(Medal.NONE);
			break;
		default:
			break;
		}
	}

	@Override
	public void finish(final Medal medal) {
		super.finish(medal);
		GameManager gm = GameManager.getInstance();
		gm.pause(gm.getActiveGamestate());

		// check if PlanetGamestate for this planet already exists (if yes
		// resume it)
		PlanetGamestate pausedGamestate = null;
		for (AbstractGamestate a : gm.getInactivedGamestates()) {
			if (a instanceof PlanetGamestate) {
				if (((PlanetGamestate) a).getPlanet() == planet)
					pausedGamestate = (PlanetGamestate) a;
			}
		}
		if (pausedGamestate == null) {
			gm.startNew(new PlanetGamestate(planet));
			RPLevel.addRP(RP_FOR_NEW_PLANET);
		} else {
			gm.resume(pausedGamestate.getId());
			RPLevel.addRP(RP_FOR_OLD_PLANET);
		}
	}

}
