package net.tmt.entity.component.other;

import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.game.Controls;
import net.tmt.game.manager.GuiManager;
import net.tmt.gfx.Graphics;
import net.tmt.gui.Gui;
import net.tmt.gui.elements.Label;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class OnHoverComponent extends Component {

	private boolean	isHover	= false;

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		Vector2d mouse2d = Vector2d.tmp1.set(Controls.mouseX(), Controls.mouseY());

		World world = caller.getWorld();

		Vector2d shipAbsolut2d = Vector2d.tmp2.set(pos.x - world.getOffset().x, pos.y - world.getOffset().y);

		isHover = false;
		// bedinung genauer einstellen. radius abhänging von bildgröße
		if (mouse2d.distanceTo(shipAbsolut2d) < 32) {
			String info = owner.toString();
			GuiManager guiManager = GuiManager.getInstance();

			guiManager.dispatch(Gui.GUI_HOVER, info);
			guiManager.setTooltip(Label.createTooltip(new Vector2d(Controls.mouseX() + 15, Controls.mouseY()), info));
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
