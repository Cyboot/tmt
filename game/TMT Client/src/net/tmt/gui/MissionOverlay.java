package net.tmt.gui;

import net.tmt.gfx.Graphics;
import net.tmt.gui.view.MissionView;
import net.tmt.mission.Mission;

public class MissionOverlay extends Gui {
	private MissionView	missionView;

	@Override
	public void update(final double delta) {
		Mission mission = (Mission) guiManager.getValue(MISSION);

		// FIXME always new MissionView
		if (mission != null)
			missionView = new MissionView(mission);

		if (missionView != null)
			missionView.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		if (missionView != null)
			missionView.render(g);
	}

}
