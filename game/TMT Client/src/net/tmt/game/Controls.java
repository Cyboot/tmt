package net.tmt.game;

import static org.lwjgl.input.Keyboard.*;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Controls {
	private static Map<Integer, Boolean>	keyMap					= new HashMap<Integer, Boolean>();

	private static final int				MOUSE_KEYVALUE_OFFSET	= -1000;

	public static final int					MOUSE_LEFT				= 0 + MOUSE_KEYVALUE_OFFSET;
	public static final int					MOUSE_RIGHT				= 1 + MOUSE_KEYVALUE_OFFSET;
	public static final int					MOUSE_MIDDLE			= 2 + MOUSE_KEYVALUE_OFFSET;

	public static final int					SHIP_MAIN_ENGINE		= KEY_LSHIFT;
	public static final int					SHIP_ROTATE_LEFT		= KEY_A;
	public static final int					SHIP_ROTATE_RIGHT		= KEY_D;
	public static final int					SHIP_BACK_SLOW_ACCL		= KEY_W;
	public static final int					SHIP_BACK_SLOW_DEACCL	= KEY_S;

	public static final int					SHIP_FIRE				= KEY_SPACE;
	public static final int					SHIP_SHIELD				= KEY_Q;
	public static final int					CHANGE_TARGET			= KEY_LEFT;
	public static final int					WEAPON_PRIMARY			= KEY_RIGHT;

	public static final int					DEBUG_COLLISION			= KEY_F9;


	/**
	 * Indicates a Key is pressed. It will keep returning true <b>until the Key
	 * is released</b>
	 * 
	 * @param key
	 * @return
	 */
	public static boolean pressed(final int key) {
		if (key < 0)
			return Mouse.isButtonDown(key - MOUSE_KEYVALUE_OFFSET);
		else
			return Keyboard.isKeyDown(key);
	}

	/**
	 * Indicates that a Key was typed. It will return true <b>only one time</b>
	 * until this Key is typed again
	 * 
	 * @param key
	 * @return
	 */
	public static boolean wasTyped(final int key) {
		Boolean result = keyMap.get(key);
		return result == null ? false : result;
	}

	/**
	 * Indicates that a Key was released. It will return true <b>only one
	 * time</b> until this Key is released again
	 * 
	 * @param key
	 * @return
	 */
	public static boolean wasReleased(final int key) {
		Boolean result = keyMap.get(key);
		return result == null ? false : !result;
	}

	public static int mouseX() {
		return Mouse.getX();
	}

	public static int mouseY() {
		return GameEngine.HEIGHT - Mouse.getY();
	}


	public static void update() {
		keyMap.clear();
		while (Mouse.next()) {
			int key = Mouse.getEventButton() + MOUSE_KEYVALUE_OFFSET;
			boolean value = Mouse.getEventButtonState();
			keyMap.put(key, value);
		}
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			boolean value = Keyboard.getEventKeyState();
			keyMap.put(key, value);
		}
	}
}
