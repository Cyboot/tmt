package net.tmt.gui;

import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.gui.elements.ToolTip;
import net.tmt.util.StringFormatter;
import net.tmt.util.Vector2d;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Color;

public class SpaceGui extends Gui {
	public static final String	GUI_SHIP_HEALTH	= "GUI_SHIP_HEALTH";
	public static final String	GUI_SHIP_SPEED	= "GUI_SHIP_SPEED";

	private int					width			= GameEngine.WIDTH;
	private int					height			= GameEngine.HEIGHT;

	private Sprite				shipSchema		= new Sprite("schema_ship_back_64", 128, 128).setCentered(false);


	@Override
	public void update(final double delta) {
		updateRessource(delta);
		updateMap(delta);
		updateSideBar(delta);
		updateMissions(delta);
		updateLog(delta);
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
		renderShipInfo(g);
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

	private void updateInfoWindow(final double delta) {
	}


	private void renderRessource(final Graphics g) {
		g.setLineWidth(2);
		g.onGui().drawRect(0, 0, width * 0.3, height * 0.05);
		g.setColor(Color.WHITE);
		g.onGui().drawText(0, 0, "test");
	}

	private void renderMap(final Graphics g) {
		g.onGui().drawRect(width * 0.85, 0, width * 0.15, height * 0.25);

	}

	private void renderSideBar(final Graphics g) {
		g.onGui().drawRect(0, height * 0.15, width * 0.04, height * 0.5);

	}

	private void renderMissions(final Graphics g) {
		g.onGui().drawRect(width * 0.85, height * 0.3, width * 0.15, height * 0.4);
	}

	private void renderLog(final Graphics g) {
		g.onGui().drawRect(0, height * 0.71, width * 0.05, height * 0.04);
		g.onGui().drawRect(0, height * 0.75, width * 0.25, height * 0.25);

	}

	private void renderShipInfo(final Graphics g) {
		double elemWidth = width * 0.3;
		double leftX = width / 2 - elemWidth / 2;
		double topY = height * 0.75;

		// border
		g.onGui().drawRect(leftX, topY, elemWidth, height * 0.25);

		// ship
		g.onGui().drawSprite(Vector2d.tmp1.set(leftX + 5, topY + 10), shipSchema);

		double textX = leftX + 128 + 32;
		String health = "";
		String speed = "";

		if (guiManager.isSet(GUI_SHIP_HEALTH))
			health = StringFormatter.format((double) guiManager.getValue(GUI_SHIP_HEALTH));

		if (guiManager.isSet(GUI_SHIP_SPEED))
			speed = StringFormatter.format((double) guiManager.getValue(GUI_SHIP_SPEED));

		g.onGui().drawText(textX, topY + 16, "Health: " + health);
		g.onGui().drawText(textX, topY + 16 + 16, "Speed:  " + speed);

	}

	private void renderInfoWindow(final Graphics g) {
		g.onGui().drawRect(width * 0.75, height * 0.75, width * 0.25, height * 0.25);

		if (guiManager.isSet(GUI_HOVER)) {
			g.onGui().drawText(width * 0.75 + 5, height * 0.75, (String) guiManager.getValue(GUI_HOVER));
			new ToolTip(Vector2d.tmp1.set(Mouse.getX(), GameEngine.HEIGHT - Mouse.getY()), (Color) Color.CYAN, 15f,
					100f).render(g);
		} else {
			g.onGui().drawText(width * 0.75 + 5, height * 0.75, "overwrite");
		}
		guiManager.remove(GUI_HOVER);
	}
}
