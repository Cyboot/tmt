package net.tmt.gui;

import net.tmt.gfx.Graphics;
import net.tmt.global.achievments.Achiev;
import net.tmt.gui.view.AchievmentView;
import net.tmt.util.CountdownTimer;

public class AchievmentOverlay extends Gui {
	private CountdownTimer	timer	= CountdownTimer.createManuelResetTimer(5);
	private boolean			show	= false;

	private AchievmentView	view	= new AchievmentView();

	@Override
	public void update(final double delta) {
		if (guiManager.isSet(ACHIEVMENT)) {
			Achiev achievment = (Achiev) guiManager.getValue(ACHIEVMENT);

			view.setAchievName(achievment.getName());
			view.setAchievDesc(achievment.getDescription());

			guiManager.remove(ACHIEVMENT);
			timer.reset();
			show = true;
		}

		if (timer.isTimeleft(delta)) {
			show = false;
		}
	}

	@Override
	public void render(final Graphics g) {
		if (!show)
			return;

		view.render(g);
	}
}
