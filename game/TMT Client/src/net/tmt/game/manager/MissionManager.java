package net.tmt.game.manager;

import java.util.ArrayList;
import java.util.List;

import net.tmt.mission.Mission;

public class MissionManager {
	private static final MissionManager	instance	= new MissionManager();


	private Mission						activeMission;
	private List<Mission>				missionList	= new ArrayList<>();

	public void offerMission(final Mission mission) {
		missionList.add(mission);
	}

	public void startMission(final Mission mission) {
		activeMission = mission;
	}

	public static MissionManager getInstance() {
		return instance;
	}
}
