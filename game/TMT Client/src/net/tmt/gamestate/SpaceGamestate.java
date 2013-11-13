package net.tmt.gamestate;

import net.tmt.entity.PlayerSpaceShip;
import net.tmt.entity.npc.NPCCargoShip;
import net.tmt.entity.npc.NPCTransporterShip;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gui.SpaceGui;
import net.tmt.map.SpaceMap;
import net.tmt.map.World;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class SpaceGamestate extends AbstractGamestate {
	private static SpaceGamestate	instance;

	private EntityManager			entityManager	= new EntityManager();
	private PlayerSpaceShip			player			= new PlayerSpaceShip();
	private World					world;

	private SpaceGamestate() {
		requestMap();

		// add default Entities
		for (int i = 0; i < 20; i++) {
			double x = RandomUtil.doubleRange(-1000, 2000);
			double y = RandomUtil.doubleRange(-1000, 2000);
			entityManager.addEntity(new NPCCargoShip(new Vector2d(x, y)));
		}
		entityManager.addEntity(new NPCTransporterShip(new Vector2d(800, 200), NPCTransporterShip.TYPE_1));
		entityManager.addEntity(new NPCTransporterShip(new Vector2d(500, 700), NPCTransporterShip.TYPE_2));

		entityManager.addEntity(player, EntityManager.LAYER_3_FRONT);

		onResume(-1);
	}

	@Override
	public void requestMap() {
		// init World
		World.init(SpaceGamestate.class);
		World.setActiveWorld(SpaceGamestate.class);
		world = World.getActiveWorld();
		world.setMap(SpaceMap.getInstance());
		world.setPlayer(player);
	}

	@Override
	public void update(final double delta) {
		world.update(delta);
		entityManager.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		guiManager.setGui(SpaceGui.class);
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
