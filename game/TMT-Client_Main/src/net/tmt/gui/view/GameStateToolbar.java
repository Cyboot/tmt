package net.tmt.gui.view;

import net.tmt.game.interfaces.ClickListener;
import net.tmt.game.manager.GameManager;
import net.tmt.gamestate.DummyGamestate;
import net.tmt.gamestate.EconomyGamestate;
import net.tmt.gamestate.SimulatorGamestate;
import net.tmt.gamestate.SpaceGamestate;
import net.tmt.gfx.Sprite;
import net.tmt.gui.elements.Button;
import net.tmt.gui.elements.ContainerElement;
import net.tmt.gui.elements.GuiElement;
import net.tmt.util.Vector2d;

public class GameStateToolbar extends ContainerElement implements ClickListener {
	private static GameStateToolbar	instance;

	private GameStateToolbar() {
		super(new Vector2d(0, 200), 32 + 8, 5 * 32 + 5 * 8);
		// setBorderColor((Color) ColorUtil.GUI_CYAN_DARK_2);
		// setBackgroundColor((Color) ColorUtil.GUI_CYAN_DARK_3);

		addButton(new Button(new Vector2d(0, 0 * 32 + 0 * 8), new Sprite("icon_space"), "space"));
		addButton(new Button(new Vector2d(0, 1 * 32 + 1 * 8), new Sprite("icon_simulator"), "simulator"));
		addButton(new Button(new Vector2d(0, 2 * 32 + 2 * 8), new Sprite("icon_planet"), "planet"));
		addButton(new Button(new Vector2d(0, 3 * 32 + 3 * 8), new Sprite("icon_economy"), "economy"));
		addButton(new Button(new Vector2d(0, 4 * 32 + 4 * 8), new Sprite("icon_dummy"), "dummy"));
	}

	private void addButton(final Button button) {
		button.noBackground();
		button.addClickListener(this);
		addGuiElement(button);
	}

	public static GameStateToolbar getInstance() {
		if (instance == null)
			instance = new GameStateToolbar();

		return instance;
	}


	@Override
	public void onClick(final GuiElement caller) {
		GameManager gameManager = GameManager.getInstance();

		if (caller.getTitle() != "planet")
			gameManager.pause(gameManager.getActiveGamestate());

		switch (caller.getTitle()) {
		case "space":
			gameManager.resume(SpaceGamestate.getInstance().getId());
			break;
		case "simulator":
			gameManager.resume(SimulatorGamestate.getInstance().getId());
			break;
		case "planet":
			System.out.println("not working yet");
			break;
		case "economy":
			gameManager.resume(EconomyGamestate.getInstance().getId());
			break;
		case "dummy":
			gameManager.resume(DummyGamestate.getInstance().getId());
			break;
		}
	}
}
