package net.tmt.global.mission;

import net.tmt.entity.Entity2D;
import net.tmt.entity.ambient.Prop;
import net.tmt.entity.statics.Planet;
import net.tmt.game.manager.GameManager;
import net.tmt.gamestate.AbstractGamestate;
import net.tmt.gamestate.PlanetGamestate;
import net.tmt.global.RPLevel;
import net.tmt.util.Vector2d;

public class EnterPlanetMission extends Mission {
	private static final int	RP_FOR_NEW_PLANET	= 200;
	private static final int	RP_FOR_OLD_PLANET	= 50;
	private Planet				planet;

	public EnterPlanetMission(final Planet planet) {
		super(planet.getName() + " (Planet #" + planet.getId() + ")",
				"Welcome to this wonderful planet. Here you can enjoy a lot of the lovley terrain type "
						+ planet.getBaseTerrain().name(), true);
		this.planet = planet;
	}

	@Override
	public void update(final double delta) {
		super.update(delta);

		// immediatly finish mission because it changes to PlanetGamestate
		if (getState() == State.ACTIVE)
			finish(Medal.NONE);
	}

	@Override
	public void finish(final Medal medal) {
		super.finish(medal);
		GameManager gm = GameManager.getInstance();

		// check if PlanetGamestate for this planet already exists (if yes
		// resume it)
		PlanetGamestate pausedGamestate = null;
		for (AbstractGamestate a : gm.getInactivedGamestates()) {
			if (a instanceof PlanetGamestate) {
				if (((PlanetGamestate) a).getPlanet() == planet)
					pausedGamestate = (PlanetGamestate) a;
			}
		}

		Entity2D player = gm.getActiveGamestate().getPlayer();
		Entity2D landingVehicle = Prop.createPropGeneric(new Vector2d(), player.getSprite());

		gm.pauseActiveGame();
		if (pausedGamestate == null) {
			PlanetGamestate newPlanetGame = new PlanetGamestate(planet);
			newPlanetGame.setLandingVehicle(landingVehicle);

			gm.startNew(newPlanetGame);
			RPLevel.addRP(RP_FOR_NEW_PLANET);
		} else {
			pausedGamestate.setLandingVehicle(landingVehicle);
			gm.resume(pausedGamestate.getId());
			RPLevel.addRP(RP_FOR_OLD_PLANET);
		}
	}

}
