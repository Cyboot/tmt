package net.tmt.gamestate;

import net.tmt.entity.ControlledSpaceShip;
import net.tmt.entity.npc.NPCCargoShip;
import net.tmt.entity.npc.NPCTransporterShip;
import net.tmt.game.GameEngine;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.map.World;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

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
	}

	@Override
	public void update(final double delta) {
		world.update(delta);

		entityManager.update(delta);

		updateGUI(delta);
	}


	@Override
	public void render(final Graphics g) {
		entityManager.render(g);

		renderGUI(g);
	}

	private void updateGUI(final double delta) {
		// TODO Auto-generated method stub

	}

	private void renderGUI(final Graphics g) {

		int width = GameEngine.WIDTH;
		int height = GameEngine.HEIGHT;

		g.setColor(Color.CYAN);

		// ressource
		g.setLineWidth(2);
		g.gui().drawRect(0, 0, width * 0.3f, height * 0.05f);

		// minimap
		g.gui().drawRect(width * 0.85f, 0, width * 0.15f, height * 0.25f);

		// sidebar
		g.gui().drawRect(0, height * 0.15f, width * 0.04f, height * 0.5f);

		// missions
		g.gui().drawRect(width * 0.85f, height * 0.3f, width * 0.15f, height * 0.4f);

		// log/chat
		g.gui().drawRect(0, height * 0.71, width * 0.05f, height * 0.04f);
		g.gui().drawRect(0, height * 0.75, width * 0.25f, height * 0.25f);

		// weapons
		g.gui().drawRect(width * 0.3f, height * 0.75f, width * 0.07f, height * 0.1f);
		g.gui().drawRect(width * 0.3f, height * 0.90f, width * 0.07f, height * 0.1f);

		// states of ship
		g.gui().drawRect(width * 0.4f, height * 0.75f, width * 0.2f, height * 0.03f);
		g.gui().drawRect(width * 0.4f, height * 0.8f, width * 0.2f, height * 0.03f);
		g.gui().drawRect(width * 0.4f, height * 0.85f, width * 0.2f, height * 0.03f);
		g.gui().drawRect(width * 0.4f, height * 0.90f, width * 0.2f, height * 0.03f);
		g.gui().drawRect(width * 0.4f, height * 0.95f, width * 0.2f, height * 0.03f);

		// information window
		g.gui().drawRect(width * 0.75f, height * 0.75f, width * 0.25f, height * 0.25f);

	}

	public ControlledSpaceShip getShip() {
		return player;
	}

	public static SpaceGamestate getInstance() {
		return instance;
	}
}
