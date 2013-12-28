package net.tmt.gamestate;

import net.tmt.entity.PlayerSpaceShip;
import net.tmt.entity.statics.BackgroundBody;
import net.tmt.entity.statics.Planet;
import net.tmt.entity.statics.SpaceStation;
import net.tmt.gfx.Graphics;
import net.tmt.global.mission.EnterPlanetMission;
import net.tmt.global.mission.MissionManager;
import net.tmt.gui.SpaceGui;
import net.tmt.map.SpaceMap;
import net.tmt.map.Terrain;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class SpaceGamestate extends AbstractGamestate {
	private static SpaceGamestate	instance;

	private PlayerSpaceShip			player	= new PlayerSpaceShip(new Vector2d(62 * 1000, 2000));

	private SpaceGamestate() {
		super(SpaceMap.getInstance());
		setGui(new SpaceGui(world));
		setPlayer(player);

		entityManager.addEntity(player.setPosZ(10));
		entityManager.addEntity(new SpaceStation(new Vector2d(-500, -300), 1));
		entityManager.addEntity(new SpaceStation(new Vector2d(-500, -1200), 2));

		double MAX = 1024 * 100;
		double MIN = -MAX;
		RandomUtil.setSeed(123);
		for (int i = 0; i < 25; i++) {
			entityManager.addEntity(new BackgroundBody(new Vector2d(RandomUtil.doubleRange(MIN, MAX), RandomUtil
					.doubleRange(MIN, MAX)), 32).setPosZ(0));
		}

		// DEBUG: Planet on Player position
		Planet debugPlanet = new Planet(Terrain.PLANET_GRASS, 300, new Vector2d(62 * 1000, 0), 0, 0.1);
		entityManager.addEntity(debugPlanet);
		MissionManager.getInstance().offerMission(new EnterPlanetMission(debugPlanet));

		onResume(-1);
	}

	@Override
	public void update(final double delta) {
		world.update(delta);
		entityManager.update(world, delta);
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		guiManager.setGui(getGui());

		world.render(g);
		entityManager.render(g);
	}

	public PlayerSpaceShip getShip() {
		return player;
	}

	public static SpaceGamestate getInstance() {
		if (instance == null)
			instance = new SpaceGamestate();
		return instance;
	}
}
