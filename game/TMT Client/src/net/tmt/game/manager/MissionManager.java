package net.tmt.game.manager;

import java.util.ArrayList;
import java.util.List;

import net.tmt.game.interfaces.Updateable;
import net.tmt.gui.Gui;
import net.tmt.mission.Mission;

public class MissionManager implements Updateable {
	private static final MissionManager	instance	= new MissionManager();

	private Mission						activeMission;
	private List<Mission>				missionList	= new ArrayList<>();

	public void offerMission(final Mission mission) {
		// ignore if mission is already known (offered before)
		if (mission.equals(activeMission) || missionList.contains(mission))
			return;

		missionList.add(mission);

		GuiManager guiManager = GuiManager.getInstance();
		guiManager.dispatch(Gui.MISSION, mission);
	}

	public void startMission(final Mission mission) {
		activeMission = mission;
		mission.start();
	}

	@Override
	public void update(final double delta) {
		if (activeMission != null) {
			activeMission.update(delta);

			if (activeMission.isFinished())
				activeMission = null;
		}
	}

	public static MissionManager getInstance() {
		return instance;
	}

	public void refuseMission(final Mission mission) {
		missionList.remove(mission);
	}
}
