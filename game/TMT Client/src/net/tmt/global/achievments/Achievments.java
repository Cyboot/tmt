package net.tmt.global.achievments;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.tmt.game.manager.GuiManager;
import net.tmt.gui.Gui;
import net.tmt.util.FileUtil;

/**
 * class to manage Achievments
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public class Achievments {
	private static final String				FILE		= "res/save/achiev.txt";

	private static final Map<Achiev, Long>	achieved	= new HashMap<Achiev, Long>();

	/**
	 * unlock a new Achievments for the player
	 * 
	 * @param achiev
	 */
	public static void achiev(final Achiev achiev) {
		achiev.addCount();
		if (achiev.achieved() && !achieved.containsKey(achiev)) {
			achieved.put(achiev, System.currentTimeMillis());

			GuiManager.getInstance().dispatch(Gui.ACHIEVMENT, achiev);
			FileUtil.appendToFile(FILE, achiev.getKey() + ":" + System.currentTimeMillis() + "\n");
			System.out.println("new " + achiev);
		}
	}

	public static void init() {
		String fileText;
		try {
			// try to read achievment File, ignore if not found
			fileText = FileUtil.readFromFile(FILE);
			if (fileText == "")
				return;
		} catch (Exception e) {
			return;
		}

		for (String str : fileText.split("\n")) {
			try {
				String achievStr = str.split(":")[0];
				Long time = Long.valueOf(str.split(":")[1]);
				Field field = Space.class.getDeclaredField(achievStr);
				Achiev achiev = (Achiev) field.get(Space.class);

				achieved.put(achiev, time);

			} catch (Exception e) {
				System.err.print("Error in achiev.txt. ignoring it... " + e);
			}
		}

	}

	public static class Space {
		public static final Achiev	SPEED_500		= new Achiev("SPEED_500", "Speed > 500",
															"fly faster than 500 units/s");
		public static final Achiev	FIRE_ROCKET		= new Achiev("FIRE_ROCKET", "Fire a rocket",
															"fire a rocket with your space ship");
		public static final Achiev	FIRE_10_LASER	= new Achiev("FIRE_10_LASER", "Manual aiming",
															"fire 10 shoots with your secondary (laser) weapon", 10);
	}
}
