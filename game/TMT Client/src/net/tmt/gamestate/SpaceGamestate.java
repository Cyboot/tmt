package net.tmt.gamestate;

import net.tmt.entity.ControlledSpaceShip;
import net.tmt.entity.npc.NPCCargoShip;
import net.tmt.entity.npc.NPCTransporterShip;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gui.SpaceGui;
import net.tmt.map.World;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

public class SpaceGamestate extends AbstractGamestate {
	private static SpaceGamestate	instance		= new SpaceGamestate();

	private EntityManager			entityManager	= new EntityManager();
	private ControlledSpaceShip		player			= new ControlledSpaceShip();
	private World					world;

	private SpaceGamestate() {
		// init World
		World.init(entityManager);
		world = World.getInstance();
		world.setEntityManager(entityManager);
		world.setPlayer(player);

		// add default Entities
		entityManager.addEntity(new NPCCargoShip(new Vector2d()));
		entityManager.addEntity(new NPCCargoShip(new Vector2d(1000, 500)));
		entityManager.addEntity(new NPCCargoShip(new Vector2d(500, 500)));

		for (int i = 0; i < 10; i++) {
			double x = RandomUtil.doubleRange(-1000, 1000);
			double y = RandomUtil.doubleRange(-1000, 1000);
			entityManager.addEntity(new NPCCargoShip(new Vector2d(x, y)));
		}
		entityManager.addEntity(new NPCTransporterShip(new Vector2d(800, 200), NPCTransporterShip.TYPE_1));
		entityManager.addEntity(new NPCTransporterShip(new Vector2d(500, 500), NPCTransporterShip.TYPE_2));

		entityManager.addEntity(player, EntityManager.LAYER_3_FRONT);

		onResume(-1);
	}

	@Override
	public void update(final double delta) {
		world.update(delta);
		entityManager.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		guiManager.setGui(SpaceGui.getInstance());
		entityManager.render(g);
	}

	public ControlledSpaceShip getShip() {
		return player;
	}

	public static SpaceGamestate getInstance() {
		return instance;
	}
}
