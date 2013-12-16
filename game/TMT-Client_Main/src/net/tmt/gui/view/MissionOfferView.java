package net.tmt.gui.view;

import net.tmt.game.GameEngine;
import net.tmt.game.interfaces.ClickListener;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Graphics.Fonts;
import net.tmt.gfx.Graphics.Fonts.Style;
import net.tmt.gfx.Sprite;
import net.tmt.global.mission.Mission;
import net.tmt.global.mission.Mission.Medal;
import net.tmt.global.mission.MissionManager;
import net.tmt.gui.elements.Button;
import net.tmt.gui.elements.ContainerElement;
import net.tmt.gui.elements.GuiElement;
import net.tmt.gui.elements.ImageView;
import net.tmt.gui.elements.Label;
import net.tmt.gui.elements.TextView;
import net.tmt.util.ColorUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class MissionOfferView extends ContainerElement implements ClickListener {
	private final static int	WIDTH		= 300;
	private final static int	HEIGHT		= 400;

	private final static int	topYRewards	= HEIGHT - 140;

	private Mission				mission;
	private Label				label_timeleft;

	public MissionOfferView(final Mission mission) {
		super(new Vector2d(GameEngine.WIDTH - WIDTH, 0), WIDTH, HEIGHT);

		this.mission = mission;

		addButton(new Button(new Vector2d(width / 2 - 64, height - 50), "Accept"));
		addButton(new Button(new Vector2d(width / 2 + 32, height - 50), "Refuse"));


		addText();
		if (mission.hasReward())
			addReward();


		setBackgroundColor((Color) ColorUtil.GUI_CYAN_DARK_30);
	}

	private void addText() {
		final double topYDesc = 25;

		// title, desc and timeleft
		Label label_name = new Label(new Vector2d(DEFAULT_PADDING, DEFAULT_PADDING), mission.getTitle(), Fonts.get(18,
				Style.BOLD));
		TextView textview_desc = new TextView(new Vector2d(0, topYDesc), width - 2 * DEFAULT_PADDING, topYRewards
				- topYDesc);
		textview_desc.setFont(Graphics.Fonts.get(14));
		label_name.setForegroundColor((Color) ColorUtil.GUI_ORANGE);
		label_timeleft = new Label(new Vector2d(width / 4 + 8, height - 25), "");

		textview_desc.setText(mission.getDesc());
		textview_desc.noBackground();
		addGuiElement(label_name);
		addGuiElement(textview_desc);
		addGuiElement(label_timeleft);
	}

	private void addReward() {
		int deltaY = 20;
		double offsetX = 64 + DEFAULT_PADDING;
		double offsetImgX = offsetX + 24;

		Label label_reward = new Label(new Vector2d(DEFAULT_PADDING, topYRewards), "Rewards:");
		label_reward.setForegroundColor((Color) ColorUtil.GUI_ORANGE);

		ImageView img_gold = new ImageView(new Vector2d(offsetX, topYRewards + 1 * deltaY), new Sprite("medal_gold"));
		ImageView img_silver = new ImageView(new Vector2d(offsetX, topYRewards + 2 * deltaY),
				new Sprite("medal_silver"));
		ImageView img_bronze = new ImageView(new Vector2d(offsetX, topYRewards + 3 * deltaY),
				new Sprite("medal_bronze"));

		Label label_gold = new Label(new Vector2d(offsetImgX, topYRewards + 1 * deltaY),
				mission.getRewardRP(Medal.GOLD) + " RP" + "   -   " + "$ " + mission.getRewardMoney(Medal.GOLD));
		Label label_silver = new Label(new Vector2d(offsetImgX, topYRewards + 2 * deltaY),
				mission.getRewardRP(Medal.SILVER) + " RP" + "   -   " + "$ " + mission.getRewardMoney(Medal.SILVER));
		Label label_bronze = new Label(new Vector2d(offsetImgX, topYRewards + 3 * deltaY),
				mission.getRewardRP(Medal.BRONZE) + " RP" + "   -   " + "$ " + mission.getRewardMoney(Medal.BRONZE));

		addGuiElement(label_gold);
		addGuiElement(label_silver);
		addGuiElement(label_bronze);

		addGuiElement(label_reward);
		addGuiElement(img_gold);
		addGuiElement(img_silver);
		addGuiElement(img_bronze);
	}

	@Override
	public void update(final double delta) {
		String timeleft = (int) mission.getOfferTimeleft() + "";
		label_timeleft.setText("Mission expires in " + timeleft + " s");

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
