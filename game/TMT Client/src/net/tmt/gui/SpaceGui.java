package net.tmt.gui;

import net.tmt.game.Controls;
import net.tmt.game.GameEngine;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.gui.elements.ToolTip;
import net.tmt.util.StringFormatter;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class SpaceGui extends Gui {
	public static final String	GUI_SHIP_HEALTH	= "GUI_SHIP_HEALTH";
	public static final String	GUI_SHIP_SPEED	= "GUI_SHIP_SPEED";

	private int					width			= GameEngine.WIDTH;
	private int					height			= GameEngine.HEIGHT;

	private Sprite				shipSchema		= new Sprite("schema_ship_back_64", 128, 128).setCentered(false);


	@Override
	public void update(final double delta) {
		gameStateToolbar.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		g.setColor(Color.CYAN);
		renderMissions(g);
		renderMap(g);
		renderShipInfo(g);
		renderInfoWindow(g);
		gameStateToolbar.render(g);
	}

	private void renderMissions(final Graphics g) {
		double leftX = width * 0.8;
		double widthElement = width - leftX;
		double heightElement = height * 0.35;
		double padding = 4;

		g.onGui().drawText(leftX + padding, 0, "Mission");
		g.onGui().drawRect(leftX, 0, widthElement, heightElement);
	}

	private void renderMap(final Graphics g) {
		double topY = height * 0.71;
		double heightTab = 20;
		double widthTab = 50;
		double padding = 4;

		g.onGui().drawRect(0 * widthTab, topY, widthTab, heightTab);
		g.onGui().drawText(0 * widthTab + padding, topY + padding, "Map");

		g.onGui().drawRect(1 * widthTab, topY, widthTab, heightTab);
		g.onGui().drawText(1 * widthTab + padding, topY + padding, "Chat");
		g.onGui().drawRect(0, topY + heightTab, width * 0.25, height - topY - heightTab);

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
			String text = (String) guiManager.getValue(GUI_HOVER);
			g.onGui().drawText(width * 0.75 + 5, height * 0.75, text);
			ToolTip tmp = new ToolTip(Vector2d.tmp1.set(Controls.mouseX() + 10, Controls.mouseY()), text);
			tmp.render(g);
		} else {
			g.onGui().drawText(width * 0.75 + 5, height * 0.75, "InfoView");
		}
		guiManager.remove(GUI_HOVER);
	}
}
