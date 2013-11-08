package net.tmt.global.achievments;

import java.util.ArrayList;
import java.util.List;

import net.tmt.game.manager.GuiManager;
import net.tmt.gui.Gui;

/**
 * class to manage Achievments
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public class Achievments {
	private static final List<Achiev>	achievedList	= new ArrayList<>();

	/**
	 * unlock a new Achievments for the player
	 * 
	 * @param achiev
	 */
	public static void achiev(final Achiev achiev) {
		achiev.addCount();
		if (achiev.achieved() && !achievedList.contains(achiev)) {
			achievedList.add(achiev);

			GuiManager.getInstance().dispatch(Gui.ACHIEVMENT, achiev);
			System.out.println("new " + achiev);
		}
	}

	public static class Space {
		public static final Achiev	SPEED_500		= new Achiev("Speed > 500", "fly faster than 500 units/s");
		public static final Achiev	FIRE_ROCKET		= new Achiev("Fire a rocket", "fire a rocket with your space ship");
		public static final Achiev	FIRE_10_LASER	= new Achiev("Manual aiming",
															"fire 10 shoots with your secondary (laser) weapon", 10);
	}
}
