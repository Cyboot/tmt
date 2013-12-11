package net.tmt.global.mission;

import net.tmt.entity.npc.SpaceBug;

public class BugMission extends Mission {
	private int	bugsLeftToKill	= 2;

	public BugMission() {
		super("BugMission", "kill 10 Spacebugs");

		setRewardMoney(500, 250, 100);
		setRewardRP(500, 100, 10);
	}

	@Override
	protected void onAction(final Object object, final String message) {
		if (object instanceof SpaceBug) {
			if (message.equals("killed")) {
				bugsLeftToKill--;
			}

			if (bugsLeftToKill <= 0) {
				finish(Medal.GOLD);
			}
		}
	}
}
