package net.tmt.global.mission;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.statics.area.MissionAreaAction;
import net.tmt.game.manager.ZoomManager;
import net.tmt.util.RandomUtil;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class SpaceRaceMission extends Mission {
	private static final double		TIME_FOR_GOLD	= 30;
	private static final double		TIME_FOR_SILVER	= 60;
	private static final double		TIME_FOR_BRONZE	= 90;

	private List<MissionAreaAction>	checkPoints		= new ArrayList<>();
	private int						numberCheckpoints;

	private double					timePassed		= 0;

	public SpaceRaceMission(final Vector2d startPoint, final int numberCheckpoints) {
		super("Spacerace - Checkpoint", "Reach the checkpoints as fast as you can.");

		setRewardMoney(250, 100, 50);
		setRewardRP(750, 500, 250);

		this.numberCheckpoints = numberCheckpoints;

		setUpCheckpoints(startPoint, numberCheckpoints);
	}

	@Override
	public void start() {
		super.start();

		MissionManager missionManager = MissionManager.getInstance();
		for (MissionAreaAction c : checkPoints)
			missionManager.registerArea(c);
		ZoomManager.setFixedZoom(ZoomManager.ZOOM_075);
	}

	private void setUpCheckpoints(final Vector2d startPoint, final int numberCheckpoints) {
		Vector2d lastPos = startPoint;

		final double MIN_DELTA = 128;
		final double MAX_DELTA = MIN_DELTA * 2;

		for (int i = 0; i < numberCheckpoints; i++) {
			Vector2d pos = new Vector2d(lastPos);

			double deltaX = MIN_DELTA * 2;
			double deltaY = RandomUtil.doubleRange(MIN_DELTA, MAX_DELTA);

			if (RandomUtil.randBoolean())
				deltaY = -deltaY;

			pos.add(deltaX, deltaY);

			MissionAreaAction ma = new MissionAreaAction(pos, i);
			if (i != 0)
				ma.setActive(false);
			if (i == 1)
				ma.setColor((Color) Color.GREEN);
			if (i >= 2)
				ma.setVisible(false);

			checkPoints.add(ma);
			lastPos = pos;
		}
	}

	@Override
	public void update(final double delta) {
		super.update(delta);

		// FIXME: other updateMethod where always state == active
		if (getState() == State.ACTIVE) {
			timePassed += delta;
		}
	}

	@Override
	protected void onAction(final Object object, final String message) {
		if (object instanceof MissionAreaAction) {
			int id = ((MissionAreaAction) object).getAreaId();

			if (id == numberCheckpoints - 1) {
				Medal medal = Medal.NONE;

				if (timePassed < TIME_FOR_GOLD)
					medal = Medal.GOLD;
				else if (timePassed < TIME_FOR_SILVER)
					medal = Medal.SILVER;
				else if (timePassed < TIME_FOR_BRONZE)
					medal = Medal.BRONZE;

				finish(medal);
				ZoomManager.resetZoom();
			} else {
				id++;
				checkPoints.get(id).setActive(true);
				checkPoints.get(id).setColor((Color) Color.PURPLE);

				if (id < numberCheckpoints - 1) {
					checkPoints.get(id + 1).setVisible(true);
					checkPoints.get(id + 1).setColor((Color) Color.GREEN);
				}
			}
			((MissionAreaAction) object).kill();
		}
	}
}
