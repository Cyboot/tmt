package net.tmt.gui.view;

import net.tmt.game.interfaces.ClickListener;
import net.tmt.gamestate.EconomyGamestate;
import net.tmt.gfx.Sprite;
import net.tmt.gui.elements.Button;
import net.tmt.gui.elements.ContainerElement;
import net.tmt.gui.elements.GuiElement;
import net.tmt.util.Vector2d;

public class BuildView extends ContainerElement implements ClickListener {


	public BuildView(final Vector2d pos, final double width, final double height) {
		super(pos, width, height);
		addButton(new Button(new Vector2d(0, 0), new Sprite("icon_base"), "icon_base"));
		addButton(new Button(new Vector2d(64, 0), new Sprite("icon_mine"), "icon_mine"));
		addButton(new Button(new Vector2d(128, 0), new Sprite("icon_store"), "icon_store"));
	}


	private void addButton(final Button button) {
		button.addClickListener(this);
		addGuiElement(button);
	}


	@Override
	public void onClick(final GuiElement caller) {

		switch (caller.getTitle()) {
		case "icon_base":
			EconomyGamestate.getInstance().setInputState(EconomyGamestate.CONSTRUCTING);
			break;

		case "icon_mine":

			break;

		case "icon_store":

			break;

		default:
			break;
		}

	}
}
