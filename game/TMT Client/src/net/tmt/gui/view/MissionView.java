package net.tmt.gui.view;

import net.tmt.game.GameEngine;
import net.tmt.game.interfaces.ClickListener;
import net.tmt.game.manager.MissionManager;
import net.tmt.gui.elements.Button;
import net.tmt.gui.elements.ContainerElement;
import net.tmt.gui.elements.GuiElement;
import net.tmt.gui.elements.TextView;
import net.tmt.mission.Mission;
import net.tmt.util.StringFormatter;
import net.tmt.util.Vector2d;

public class MissionView extends ContainerElement implements ClickListener {
	private Mission		mission;
	private TextView	textview_timeleft;

	public MissionView(final Mission mission) {
		super(new Vector2d(GameEngine.WIDTH * 0.75, 0), GameEngine.WIDTH * 0.25, GameEngine.HEIGHT * 0.35);

		this.mission = mission;

		addButton(new Button(new Vector2d(width / 2 - 64, height - 50), "Accept"));
		addButton(new Button(new Vector2d(width / 2 + 32, height - 50), "Refuse"));

		TextView textview_name = new TextView(new Vector2d(DEFAULT_PADDING, DEFAULT_PADDING), width - 2
				* DEFAULT_PADDING, 15);
		TextView textview_desc = new TextView(new Vector2d(DEFAULT_PADDING, 20 + DEFAULT_PADDING), width - 2
				* DEFAULT_PADDING, 30);

		textview_timeleft = new TextView(new Vector2d(DEFAULT_PADDING, height - 100), width - 2 * DEFAULT_PADDING, 30);

		textview_name.setText(mission.getTitle());
		textview_desc.setText(mission.getDesc());
		textview_name.noBackground();
		textview_desc.noBackground();
		textview_timeleft.noBackground();
		addGuiElement(textview_name);
		addGuiElement(textview_desc);
		addGuiElement(textview_timeleft);
	}

	@Override
	public void update(final double delta) {
		String timeleft = StringFormatter.format(mission.getOfferTimeleft(), 2, 0);
		textview_timeleft.setText("Mission expire in " + timeleft + " s");

		super.update(delta);
	}

	private void addButton(final Button button) {
		button.addClickListener(this);
		addGuiElement(button);
	}

	@Override
	public void onClick(final GuiElement caller) {
		MissionManager missionManager = MissionManager.getInstance();
		switch (caller.getTitle()) {
		case "Accept":
			missionManager.startMission(mission);
			break;
		case "Refuse":
			missionManager.refuseMission(mission);
			break;
		}
	}
}
