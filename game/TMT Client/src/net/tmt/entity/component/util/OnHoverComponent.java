package net.tmt.entity.component.util;

import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.gui.GuiManager;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

import org.lwjgl.input.Mouse;

public class OnHoverComponent extends Component {

	public OnHoverComponent(final Vector2d pos) {
		this.pos = pos;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		super.update(caller, delta);
		Vector2d mouse2d = new Vector2d(Mouse.getX(), Mouse.getY());
		Vector2d shipAbsolut2d = new Vector2d(pos.x - World.getInstance().getOffset().x, pos.y
				- World.getInstance().getOffset().y);
		// bedinung genauer einstellen. radius abhänging von bildgröße
		if (mouse2d.distanceTo(shipAbsolut2d) < 30.d) {
			GuiManager.getInstance().sendValueToGuiElements("onHover", caller.toString());
		}
	}
}
