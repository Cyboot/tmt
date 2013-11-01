package net.tmt.entity.component;

import net.tmt.game.GameEngine;
import net.tmt.game.manager.GuiManager;
import net.tmt.gfx.Graphics;
import net.tmt.gui.Gui;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Color;

public class OnHoverComponent extends Component {

	private boolean	isHover	= false;

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		Vector2d mouse2d = Vector2d.tmp1.set(Mouse.getX(), GameEngine.HEIGHT - Mouse.getY());
		Vector2d shipAbsolut2d = Vector2d.tmp2.set(pos.x - World.getInstance().getOffset().x, pos.y
				- World.getInstance().getOffset().y);

		isHover = false;
		// bedinung genauer einstellen. radius abhänging von bildgröße
		if (mouse2d.distanceTo(shipAbsolut2d) < 32) {
			GuiManager.getInstance().dispatch(Gui.GUI_HOVER, owner.toString());
			isHover = true;
		}
	}

	@Override
	public void render(final ComponentDispatcher caller, final Graphics g) {
		if (!isHover)
			return;

		g.setColor(Color.CYAN);
		g.drawCircle(pos.x, pos.y, 32);
	}
}
