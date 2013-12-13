package net.tmt.entityComponents.other;

import net.tmt.entity.statics.area.MissionAreaOffer;
import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;
import net.tmt.global.mission.DummyMission;
import net.tmt.global.mission.Mission;
import net.tmt.global.mission.Mission.State;
import net.tmt.global.mission.MissionManager;
import net.tmt.global.mission.SpaceRaceMission;
import net.tmt.util.CountdownTimer;

public class MissionOfferComponent extends Component {
	private static final double			DEFAULT_BURNOUT	= 5;

	private CountdownTimer				timerBurnout	= CountdownTimer.createManualResetTimer(DEFAULT_BURNOUT);
	private double						radius;

	private Mission						mission;
	private Class<? extends Mission>	missionClass;

	public MissionOfferComponent(final Class<? extends Mission> missionClass, final double radius) {
		this.radius = radius;
		this.missionClass = missionClass;
	}

	private void createMission() {
		if (missionClass.equals(SpaceRaceMission.class)) {
			mission = new SpaceRaceMission(owner.getPos().copy(), 10);
		}
		if (missionClass.equals(DummyMission.class)) {

		}
		MissionManager.getInstance().registerArea(new MissionAreaOffer(pos.copy(), mission, radius));
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if ((mission == null || mission.getState() == State.FINISHED) && timerBurnout.isTimeUp(delta)) {
			timerBurnout.reset();
			createMission();
		}
	}
}
