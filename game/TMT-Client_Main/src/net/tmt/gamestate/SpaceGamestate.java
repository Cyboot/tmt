package net.tmt.gamestate;

import net.tmt.entity.PlayerSpaceShip;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gui.SpaceGui;
import net.tmt.map.SpaceMap;

public class SpaceGamestate extends AbstractGamestate {
	private static SpaceGamestate	instance;

	private PlayerSpaceShip			player	= new PlayerSpaceShip();

	private SpaceGamestate() {
		super(SpaceMap.getInstance());
		world.setPlayer(player);

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
		super.render(g);
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
