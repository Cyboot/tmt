package net.tmt.gui;

import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Color;

public class SpaceGui extends Gui {
	private int	width	= GameEngine.WIDTH;
	private int	height	= GameEngine.HEIGHT;


	@Override
	public void update(final double delta) {
		updateRessource(delta);
		updateMap(delta);
		updateSideBar(delta);
		updateMissions(delta);
		updateLog(delta);
		updateWeapons(delta);
		updateLife(delta);
		updateInfoWindow(delta);
	}

	@Override
	public void render(final Graphics g) {
		g.setColor(Color.CYAN);
		renderRessource(g);
		g.setColor(Color.CYAN);
		renderMap(g);
		renderSideBar(g);
		renderMissions(g);
		renderLog(g);
		renderWeapons(g);
		renderLife(g);
		renderInfoWindow(g);
	}

	private void updateRessource(final double delta) {


	}

	private void updateMap(final double delta) {

	}

	private void updateSideBar(final double delta) {

	}

	private void updateMissions(final double delta) {

	}

	private void updateLog(final double delta) {

	}

	private void updateWeapons(final double delta) {

	}

	private void updateLife(final double delta) {


	}

	private void updateInfoWindow(final double delta) {

	}


	private void renderRessource(final Graphics g) {

		g.setLineWidth(2);
		g.onGui().drawRect(0, 0, width * 0.3f, height * 0.05f);
		g.setColor(Color.WHITE);
		g.onGui().drawString(
				0,
				0,
				"(" + Mouse.getX() + "/" + Mouse.getY() + ")   "
						+ (String) GuiManager.getInstance().getGuiValue().get("coordShip"));


	}

	private void renderMap(final Graphics g) {
		g.onGui().drawRect(width * 0.85f, 0, width * 0.15f, height * 0.25f);

	}

	private void renderSideBar(final Graphics g) {
		g.onGui().drawRect(0, height * 0.15f, width * 0.04f, height * 0.5f);

	}

	private void renderMissions(final Graphics g) {
		g.onGui().drawRect(width * 0.85f, height * 0.3f, width * 0.15f, height * 0.4f);
	}

	private void renderLog(final Graphics g) {
		g.onGui().drawRect(0, height * 0.71, width * 0.05f, height * 0.04f);
		g.onGui().drawRect(0, height * 0.75, width * 0.25f, height * 0.25f);

	}

	private void renderWeapons(final Graphics g) {
		g.onGui().drawRect(width * 0.3f, height * 0.75f, width * 0.07f, height * 0.1f);
		g.onGui().drawRect(width * 0.3f, height * 0.90f, width * 0.07f, height * 0.1f);
	}

	private void renderLife(final Graphics g) {
		g.onGui().drawRect(width * 0.4f, height * 0.75f, width * 0.2f, height * 0.03f); // life
		g.onGui().drawRect(width * 0.4f, height * 0.8f, width * 0.2f, height * 0.03f);
		g.onGui().drawRect(width * 0.4f, height * 0.85f, width * 0.2f, height * 0.03f);
		g.onGui().drawRect(width * 0.4f, height * 0.90f, width * 0.2f, height * 0.03f);
		g.onGui().drawRect(width * 0.4f, height * 0.95f, width * 0.2f, height * 0.03f);
	}

	private void renderInfoWindow(final Graphics g) {
		g.onGui().drawRect(width * 0.75f, height * 0.75f, width * 0.25f, height * 0.25f);

		if (GuiManager.getInstance().getGuiValue().containsKey("onHover")) {
			g.onGui().drawString(width * 0.75f, height * 0.75f,
					(String) GuiManager.getInstance().getGuiValue().get("onHover"));
		} else {
			g.onGui().drawString(width * 0.75f, height * 0.75f, "overwrite");
		}
		GuiManager.getInstance().getGuiValue().remove("onHover");


	}


}
