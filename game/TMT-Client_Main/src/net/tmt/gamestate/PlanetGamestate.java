package net.tmt.gamestate;

import net.tmt.entity.Hero;
import net.tmt.entity.npc.Runner;
import net.tmt.entity.npc.SpaceBug;
import net.tmt.entity.pickups.BackPack;
import net.tmt.entity.pickups.GlowingStuff;
import net.tmt.entity.statics.Planet;
import net.tmt.entity.statics.area.MissionAreaOffer;
import net.tmt.entity.weapons.Weapon;
import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;
import net.tmt.global.mission.BugMission;
import net.tmt.global.mission.Mission;
import net.tmt.global.mission.MissionManager;
import net.tmt.gui.PlanetGui;
import net.tmt.map.PlanetMap;
import net.tmt.util.ColorUtil;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class PlanetGamestate extends AbstractGamestate {
	private Planet	planet;

	private Hero	hero;

	public PlanetGamestate(final Planet planet) {
		super(new PlanetMap(planet));
		this.planet = planet;

		hero = new Hero(new Vector2d(GameEngine.WIDTH / 2, GameEngine.HEIGHT / 2));
		world.setPlayer(hero);
		entityManager.addEntity(hero);

		// DEBUG: debug items
		BackPack bp = new BackPack(new Vector2d(GameEngine.WIDTH / 2 - 40, GameEngine.HEIGHT / 2 - 40));
		entityManager.addEntity(bp);
		GlowingStuff gs = new GlowingStuff(new Vector2d(GameEngine.WIDTH / 2 - 140, GameEngine.HEIGHT / 2 - 140));
		Weapon weapon = new Weapon(new Vector2d(GameEngine.WIDTH / 2 - 120, GameEngine.HEIGHT / 2 - 120));
		entityManager.addEntity(gs);
		entityManager.addEntity(weapon);
		for (int i = 0; i < 20; i++) {
			int x = RandomUtil.intRange(-250, 250);
			int y = RandomUtil.intRange(-250, 250);
			SpaceBug sb = new SpaceBug(new Vector2d(GameEngine.WIDTH / 2 + x, GameEngine.HEIGHT / 2 + y), 100, hero);
			entityManager.addEntity(sb);
		}
		Runner rnr = new Runner(new Vector2d(GameEngine.WIDTH / 2 - 340, GameEngine.HEIGHT / 2 - 340), 100, hero);
		entityManager.addEntity(rnr);

		MissionManager missionManager = MissionManager.getInstance();

		Mission bugMission = new BugMission();
		MissionAreaOffer missionArea = new MissionAreaOffer(new Vector2d(), bugMission, 128);

		missionManager.registerArea(missionArea);
	}

	@Override
	public void update(final double delta) {
		world.update(delta);

		entityManager.update(world, delta);
	}

	@Override
	public void render(final Graphics g) {
		super.render(g);
		guiManager.setGui(PlanetGui.class);
		world.render(g);

		// Blend color, so hero and other entities are more noticable
		g.setColor(ColorUtil.BLACK_ALPHA_50);
		g.onGui().fillRect(0, 0, GameEngine.WIDTH, GameEngine.HEIGHT);

		entityManager.render(g);
	}

	public Planet getPlanet() {
		return planet;
	}

	@Override
	public String toString() {
		return super.toString() + " [planet = " + planet + "]";
	}
}
