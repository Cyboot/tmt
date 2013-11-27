package net.tmt.gui;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.StringFormatter;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class SpaceGui extends Gui {
	public static final String	GUI_SHIP_HEALTH	= "GUI_SHIP_HEALTH";
	public static final String	GUI_SHIP_SPEED	= "GUI_SHIP_SPEED";

	private Sprite				shipSchema		= new Sprite("schema_ship_back_64", 128, 128).setCentered(false);

	@Override
	public void update(final double delta) {
		gameStateToolbar.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		g.setColor(new Color(0, 150, 200));
		renderMap(g);
		renderShipInfo(g);
		g.setColor(new Color(0, 150, 200));
		renderInfoWindow(g);
		gameStateToolbar.render(g);
	}

	private void renderMap(final Graphics g) {
		final double topY = height * 0.71;
		final double heightTab = 25;
		final double widthTab = 50;
		final double padding = 4;

		g.onGui().drawRect(0 * widthTab, topY, widthTab, heightTab);
		g.onGui().drawText(0 * widthTab + padding, topY + padding, "Map");

		g.onGui().drawRect(1 * widthTab, topY, widthTab, heightTab);
		g.onGui().drawText(1 * widthTab + padding, topY + padding, "Chat");
		g.onGui().drawRect(0, topY + heightTab, width * 0.25, height - topY - heightTab);

	}

	private void renderShipInfo(final Graphics g) {

		final double elemWidth = width * 0.3;
		final double leftX = width / 2 - elemWidth / 2;
		final double topY = height * 0.75;

		// border
		g.onGui().drawRect(leftX, topY, elemWidth, height * 0.25);

		// ship
		g.onGui().drawSprite(Vector2d.tmp1.set(leftX + 5, topY + 10), shipSchema);

		final double textX = leftX + 128 + 32;
		String health = "";
		String speed = "";

		g.setColor(new Color(255, 150, 0));
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
		} else {
			g.onGui().drawText(width * 0.75 + 5, height * 0.75, "InfoView");
		}
		guiManager.remove(GUI_HOVER);
	}
}
