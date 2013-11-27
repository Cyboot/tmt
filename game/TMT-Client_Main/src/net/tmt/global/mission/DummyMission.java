package net.tmt.global.mission;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.Entity2D;
import net.tmt.entity.statics.area.Area;
import net.tmt.entity.statics.area.MissionAreaAction;
import net.tmt.game.manager.MissionManager;
import net.tmt.util.Vector2d;

public class DummyMission extends Mission {
	private long		timePassed	= 0;

	private List<Area>	areas		= new ArrayList<>();

	public DummyMission() {
		super("Dummy", "dummy Mission for debugging purpose, find the circle to complete the mission");

		setRewardMoney(1000, 500, 250);
		setRewardRP(1250, 1000, 750);

		areas.add(new MissionAreaAction(new Vector2d(-1000, -500), this));
	}

	@Override
	public void start() {
		super.start();

		for (Area a : areas)
			MissionManager.getInstance().registerArea(a);
	}

	@Override
	public void update(final double delta) {
		super.update(delta);

		if (getState() == State.ACTIVE) {
			timePassed += delta;
		}
	}

	@Override
	public void onAction(final Object caller) {
		if (caller instanceof MissionAreaAction) {
			Medal medal = Medal.BRONZE;

			if (timePassed < 10)
				medal = Medal.GOLD;
			else if (timePassed < 20)
				medal = Medal.SILVER;

			finish(medal);
		}
	}

	@Override
	protected void finish(final Medal medal) {
		super.finish(medal);

		for (Entity2D e : areas)
			e.kill();
	}
}
