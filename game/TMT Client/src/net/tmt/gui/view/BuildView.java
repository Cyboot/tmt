package net.tmt.gui.view;

import net.tmt.entity.economy.Base;
import net.tmt.entity.economy.Mine;
import net.tmt.game.interfaces.ClickListener;
import net.tmt.gamestate.EconomyGamestate;
import net.tmt.gamestate.EconomyGamestate.InputState;
import net.tmt.gfx.Sprite;
import net.tmt.gui.elements.Button;
import net.tmt.gui.elements.ContainerElement;
import net.tmt.gui.elements.GuiElement;
import net.tmt.util.Vector2d;

public class BuildView extends ContainerElement implements ClickListener {


	public BuildView(final Vector2d pos, final double width, final double height) {
		super(pos, width, height);
		addButton(new Button(new Vector2d(0, 0), new Sprite("icon_base"), "icon_base"));
		addButton(new Button(new Vector2d(64 + DEFAULT_PADDING, 0), new Sprite("icon_mine"), "icon_mine"));
		addButton(new Button(new Vector2d(128 + DEFAULT_PADDING * 2, 0), new Sprite("icon_store"), "icon_store"));
	}


	private void addButton(final Button button) {
		button.addClickListener(this);
		addGuiElement(button);
	}


	@Override
	public void onClick(final GuiElement caller) {
		EconomyGamestate game = EconomyGamestate.getInstance();

		// Buttons work only if Inputstate is neutral
		if (game.getInputState() == InputState.NEUTRAL) {
			game.setInputState(InputState.CONSTRUCTING);
			switch (caller.getTitle()) {
			case "icon_base":
				game.setBuildingToConstruct(new Base(new Vector2d()));
				break;
			case "icon_mine":
				game.setBuildingToConstruct(new Mine(new Vector2d()));
				break;
			case "icon_store":
				// #27 Store Building here
				break;
			default:
				break;
			}
		}
	}
}
