package net.tmt.gui;

import net.tmt.gfx.Graphics;
import net.tmt.gfx.Graphics.Fonts;
import net.tmt.gfx.Sprite;
import net.tmt.gui.view.MiniMapView;
import net.tmt.map.World;
import net.tmt.util.ColorUtil;
import net.tmt.util.MathUtil;
import net.tmt.util.StringFormatter;
import net.tmt.util.Vector2d;

public class SpaceGui extends Gui {
	public static final String	GUI_SHIP_HEALTH	= "GUI_SHIP_HEALTH";
	public static final String	GUI_SHIP_SPEED	= "GUI_SHIP_SPEED";

	private Sprite				shipSchema		= new Sprite("schema_ship_back_64", 128, 128).setCentered(false);
	private MiniMapView			miniMapView;

	public SpaceGui(final World world) {
		miniMapView = new MiniMapView(world, 50);
	}

	@Override
	public void update(final double delta) {
		miniMapView.update(delta);
		gameStateToolbar.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		g.setColor(ColorUtil.GUI_CYAN);
		g.setFont(Fonts.get(14));
		miniMapView.render(g);
		renderShipInfo(g);
		g.setColor(ColorUtil.GUI_CYAN);
		renderDebugWindow(g);
		gameStateToolbar.render(g);
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

		g.setColor(ColorUtil.GUI_ORANGE);
		if (guiManager.isSet(GUI_SHIP_HEALTH))
			health = StringFormatter.format((double) guiManager.getValue(GUI_SHIP_HEALTH));

		if (guiManager.isSet(GUI_SHIP_SPEED)) {
			int speedInt = (int) (double) guiManager.getValue(GUI_SHIP_SPEED);
			speedInt = MathUtil.roundTo(speedInt, 10);

			speed = StringFormatter.format(speedInt, 0, 0);
		}

		g.onGui().drawText(textX, topY + 16, "Health: " + health);
		g.onGui().drawText(textX, topY + 16 + 16, "Speed:  " + speed);

	}

	private void renderDebugWindow(final Graphics g) {
		final double topY = height * 0.75;
		final double leftX = width * 0.75;
		final double padding = 4;
		final double heightText = 20;

		g.onGui().drawRect(leftX, topY, width * 0.25, height * 0.25);


		if (guiManager.isSet(GUI_HOVER)) {
			String text = (String) guiManager.getValue(GUI_HOVER);
			g.onGui().drawText(leftX + padding, topY, text);
		} else {
			g.onGui().drawText(leftX + padding, topY, "DebugView");
		}

		if (guiManager.isSet(DEBUG_ENTITY_COUNT)) {
			g.onGui().drawText(leftX + padding, topY + heightText * 1,
					"Entities: " + guiManager.getValue(DEBUG_ENTITY_COUNT));
		}
		if (guiManager.isSet(DEBUG_INFO_1)) {
			g.onGui().drawText(leftX + padding, topY + (heightText * 2), guiManager.getValue(DEBUG_INFO_1) + "");
		}
		if (guiManager.isSet(DEBUG_INFO_2)) {
			g.onGui().drawText(leftX + padding, topY + (heightText * 3), guiManager.getValue(DEBUG_INFO_2) + "");
		}
		if (guiManager.isSet(DEBUG_INFO_3)) {
			g.onGui().drawText(leftX + padding, topY + (heightText * 4), guiManager.getValue(DEBUG_INFO_3) + "");
		}
		if (guiManager.isSet(DEBUG_INFO_4)) {
			g.onGui().drawText(leftX + padding, topY + (heightText * 5), guiManager.getValue(DEBUG_INFO_4) + "");
		}

		guiManager.remove(GUI_HOVER);
		guiManager.remove(DEBUG_INFO_1);
		guiManager.remove(DEBUG_INFO_2);
		guiManager.remove(DEBUG_INFO_3);
		guiManager.remove(DEBUG_INFO_4);
	}
}
