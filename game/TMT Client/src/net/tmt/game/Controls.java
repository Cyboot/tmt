package net.tmt.game;

import static org.lwjgl.input.Keyboard.*;

import org.lwjgl.input.Keyboard;

public class Controls {
	public static final int	LEFT	= 0;
	public static final int	RIGHT	= 1;
	public static final int	UP		= 2;
	public static final int	DOWN	= 3;
	public static final int	FIRE	= 4;


	public static boolean pressed(final int key) {
		boolean pressed = false;

		switch (key) {
		case LEFT:
			pressed = Keyboard.isKeyDown(KEY_LEFT) || Keyboard.isKeyDown(KEY_A);
			break;
		case RIGHT:
			pressed = Keyboard.isKeyDown(KEY_RIGHT) || Keyboard.isKeyDown(KEY_D);
			break;
		case UP:
			pressed = Keyboard.isKeyDown(KEY_UP) || Keyboard.isKeyDown(KEY_W);
			break;
		case DOWN:
			pressed = Keyboard.isKeyDown(KEY_DOWN) || Keyboard.isKeyDown(KEY_S);
			break;
		case FIRE:
			pressed = Keyboard.isKeyDown(KEY_SPACE);
			break;
		}

		return pressed;
	}
}
