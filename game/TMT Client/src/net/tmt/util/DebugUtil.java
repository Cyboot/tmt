package net.tmt.util;

import org.lwjgl.input.Keyboard;

public class DebugUtil {
	public static final int	USER_MILOS		= 0;
	public static final int	USER_TAREK		= 1;
	public static final int	USER_TIM		= 2;

	private static int		user			= -1;

	public static boolean	renderCollision	= false;

	public static void setUser(final String user) {
		switch (user) {
		case "Milos":
			DebugUtil.user = USER_MILOS;
			break;
		case "Tim":
			DebugUtil.user = USER_TIM;
			break;
		case "Tarek":
			DebugUtil.user = USER_TAREK;
			break;
		}
	}

	public static void update() {
		while (Keyboard.next()) {
			boolean pressed = Keyboard.getEventKeyState();

			if (Keyboard.getEventKey() == Keyboard.KEY_F9 && pressed) {
				renderCollision = !renderCollision;
			}
		}
	}

	public static boolean isTim() {
		return user == USER_TIM;
	}

	public static boolean isTarek() {
		return user == USER_TAREK;
	}

	public static boolean isMilos() {
		return user == USER_MILOS;
	}
}
