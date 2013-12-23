package net.tmt.gamestate;

import net.tmt.entity.Entity2D;
import net.tmt.entity.Hero;
import net.tmt.entity.ambient.Ball;
import net.tmt.entity.npc.SpaceBug;
import net.tmt.entity.pickups.BackPack;
import net.tmt.entity.pickups.GlowingStuff;
import net.tmt.entity.pickups.MoneyBundle;
import net.tmt.entity.statics.Planet;
import net.tmt.entity.statics.area.MissionAreaOffer;
import net.tmt.entity.vehicle.Boat;
import net.tmt.entity.vehicle.Helicopter;
import net.tmt.entity.vehicle.Jeep;
import net.tmt.entity.weapons.Weapon;
import net.tmt.game.GameEngine;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.global.mission.BugMission;
import net.tmt.global.mission.EnterSpaceMission;
import net.tmt.global.mission.Mission;
import net.tmt.global.mission.MissionManager;
import net.tmt.gui.PlanetGui;
import net.tmt.map.PlanetMap;
import net.tmt.map.generator.MapGeneratorPlanet;
import net.tmt.util.ColorUtil;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class PlanetGamestate extends AbstractGamestate {
	// centered x,y in the map
	public static final int	X	= MapGeneratorPlanet.MAX_X / 2 * PlanetMap.CHUNK_SIZE;
	public static final int	Y	= MapGeneratorPlanet.MAX_Y / 2 * PlanetMap.CHUNK_SIZE;

	private Planet			planet;
	private Hero			hero;

	public PlanetGamestate(final Planet planet) {
		super(new PlanetMap(planet));
		hero = new Hero(entityManager.getCollisionManager(), new Vector2d(X, Y));
		setPlayer(hero);
		entityManager.addEntity(hero, EntityManager.LAYER_3_FRONT);
		this.planet = planet;

		// DEBUG: debug items
		BackPack bp = new BackPack(new Vector2d(X - 40, Y - 40));
		entityManager.addEntity(bp);
		GlowingStuff gs = new GlowingStuff(new Vector2d(X - 140, Y - 140));
		Weapon weapon = new Weapon(new Vector2d(X - 120, Y - 120));
		entityManager.addEntity(gs);
		entityManager.addEntity(weapon);
		for (int i = 0; i < 20; i++) {
			int x = RandomUtil.intRange(-250, 250);
			int y = RandomUtil.intRange(-250, 250);
			SpaceBug sb = new SpaceBug(new Vector2d(X + x, Y + y), 100, hero);
			entityManager.addEntity(sb);
		}
		for (int i = 0; i < 50; i++)
			entityManager.addEntity(new Ball(new Vector2d(RandomUtil.doubleRange(-500 + X, 500 + Y), RandomUtil
					.doubleRange(-500 + X, 500 + Y)), entityManager.getCollisionManager()));

		entityManager.addEntity(new Jeep(new Vector2d(380 + X, 250 + Y), entityManager.getCollisionManager()),
				EntityManager.LAYER_3_FRONT);
		entityManager.addEntity(new Boat(new Vector2d(300 + X, -300 + Y), entityManager.getCollisionManager()),
				EntityManager.LAYER_3_FRONT);
		entityManager.addEntity(new Helicopter(new Vector2d(-300 + X, -300 + Y), entityManager.getCollisionManager()),
				EntityManager.LAYER_3_FRONT);

		// Runner rnr = new Runner(new Vector2d(X - 340,
		// Y - 340), 100, hero);
		// entityManager.addEntity(rnr);
		MoneyBundle mb = new MoneyBundle(new Vector2d(X - 340, Y - 340));
		entityManager.addEntity(mb);

		MissionManager missionManager = MissionManager.getInstance();

		Mission bugMission = new BugMission();
		MissionAreaOffer missionArea = new MissionAreaOffer(new Vector2d(200 + X, 200 + Y), bugMission, 128);

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

	/**
	 * set the landing Vehicle, it will be displayed in PlanetMap and will be
	 * the way back to space
	 * 
	 * @param landingVehicle
	 */
	public void setLandingVehicle(final Entity2D landingVehicle) {
		landingVehicle.getPos().set(X, Y);
		entityManager.addEntity(landingVehicle);

		MissionManager missionManager = MissionManager.getInstance();
		Mission spaceMission = new EnterSpaceMission();
		MissionAreaOffer missionArea = new MissionAreaOffer(landingVehicle.getPos().copy(), spaceMission, 128);

		missionManager.registerArea(missionArea);

		if (hero != null)
			hero.getPos().set(landingVehicle.getPos());
	}

	public Planet getPlanet() {
		return planet;
	}

	@Override
	public String toString() {
		return super.toString() + " [planet = " + planet + "]";
	}
}
