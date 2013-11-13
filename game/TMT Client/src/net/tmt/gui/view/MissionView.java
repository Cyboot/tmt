package net.tmt.gui.view;

import net.tmt.game.GameEngine;
import net.tmt.game.interfaces.ClickListener;
import net.tmt.game.manager.MissionManager;
import net.tmt.gfx.Sprite;
import net.tmt.gui.elements.Button;
import net.tmt.gui.elements.ContainerElement;
import net.tmt.gui.elements.GuiElement;
import net.tmt.gui.elements.TextView;
import net.tmt.mission.Mission;
import net.tmt.util.Vector2d;

public class MissionView extends ContainerElement implements ClickListener {
	private Mission	mission;

	public MissionView(final Mission mission) {
		super(new Vector2d(GameEngine.WIDTH * 0.85, 0), GameEngine.WIDTH * 0.15, GameEngine.HEIGHT * 0.35);

		this.mission = mission;

		addButton(new Button(new Vector2d(DEFAULT_PADDING, 100), new Sprite("ship_blue"), "start"));
		addButton(new Button(new Vector2d(DEFAULT_PADDING + 64, 100), new Sprite("ship_red"), "cancel"));

		TextView textview_name = new TextView(new Vector2d(DEFAULT_PADDING, DEFAULT_PADDING), width - 2
				* DEFAULT_PADDING, 15);
		TextView textview_desc = new TextView(new Vector2d(DEFAULT_PADDING, 20 + DEFAULT_PADDING), width - 2
				* DEFAULT_PADDING, 30);

		textview_name.setText(mission.getTitle());
		textview_desc.setText(mission.getDesc());
		textview_name.noBackground();
		textview_desc.noBackground();
		addGuiElement(textview_name);
		addGuiElement(textview_desc);
	}

	private void addButton(final Button button) {
		button.noBackground();
		button.addClickListener(this);
		addGuiElement(button);
	}

	@Override
	public void onClick(final GuiElement caller) {
		MissionManager missionManager = MissionManager.getInstance();
		switch (caller.getTitle()) {
		case "start":
			missionManager.startMission(mission);
			break;
		case "cancel":
			missionManager.refuseMission(mission);
			System.out.println("cancel");
			break;
		}
	}
}
