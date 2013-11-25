package net.tmt.game.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.tmt.game.interfaces.Updateable;
import net.tmt.global.mission.Mission;
import net.tmt.global.mission.Mission.State;
import net.tmt.gui.Gui;

public class MissionManager implements Updateable {
	private static final MissionManager	instance			= new MissionManager();

	private Mission						activeMission;
	private Mission						offeredMission;

	private List<Mission>				offeredMissionList	= new CopyOnWriteArrayList<>();

	/**
	 * offer a Mission to the player. The mission will automaticaly expire after
	 * a certain period of time, unless it is started via
	 * {@link MissionManager#startMission(Mission)}
	 * 
	 * @param mission
	 */
	public void offerMission(final Mission mission) {
		// only accept mission if there is currently no Mission
		if (activeMission != null)
			return;

		// only offer Mission if no other is currently offered and given mission
		// is not in Burnout (--> contained in offeredMissionList)
		if (offeredMission == null && !offeredMissionList.contains(mission)) {
			offeredMission = mission;
			mission.offer();
			offeredMissionList.add(mission);
		}
	}

	/**
	 * starts the given mission (if there is no other active mission)<br>
	 * it will also reject every other offered mission
	 * 
	 * @param mission
	 */
	public void startMission(final Mission mission) {
		if (activeMission != null)
			return;

		offeredMission = null;
		offeredMissionList.remove(mission);
		activeMission = mission;
		mission.start();
	}

	public void refuseMission(final Mission mission) {
		mission.refuse();
	}

	@Override
	public void update(final double delta) {
		// update the offered Mission
		if (offeredMission != null) {
			if (offeredMission.getState() == State.REFUSED || offeredMission.getState() == State.BURNOUT)
				offeredMission = null;
			else {
				GuiManager guiManager = GuiManager.getInstance();
				guiManager.dispatch(Gui.MISSION_OFFER, offeredMission);
			}
		}

		// update the active Mission
		if (activeMission != null) {
			activeMission.update(delta);

			if (activeMission.getState() != State.ACTIVE)
				activeMission = null;
		}

		for (Mission m : offeredMissionList) {
			m.update(delta);
			if (m.getState() == State.REFUSED)
				offeredMissionList.remove(m);
		}
	}

	public static MissionManager getInstance() {
		return instance;
	}
}
