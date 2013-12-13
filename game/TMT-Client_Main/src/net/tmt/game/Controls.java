package net.tmt.game;

import static org.lwjgl.input.Keyboard.*;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tmt.util.ConfigUtil;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Controls {
	private static Map<Integer, Boolean>	keyMap						= new HashMap<Integer, Boolean>();

	// offsets
	private static final int				MOUSE_KEYVALUE_OFFSET		= -1000;
	private static final int				CONTROLLER_KEYVALUE_OFFSET	= 2000;

	// mouse
	public static final List<Integer>		MOUSE_LEFT					= new ArrayList<>();
	public static final List<Integer>		MOUSE_RIGHT					= new ArrayList<>();
	public static final List<Integer>		MOUSE_MIDDLE				= new ArrayList<>();


	// ship
	public static final List<Integer>		SHIP_MAIN_ENGINE			= new ArrayList<>();
	public static final List<Integer>		SHIP_ROTATE_LEFT			= new ArrayList<>();
	public static final List<Integer>		SHIP_ROTATE_RIGHT			= new ArrayList<>();
	public static final List<Integer>		SHIP_BACK_SLOW_ACCL			= new ArrayList<>();
	public static final List<Integer>		SHIP_BACK_SLOW_DEACCL		= new ArrayList<>();
	public static final List<Integer>		SHIP_FAST_ROTATE			= new ArrayList<>();
	public static final List<Integer>		SHIP_FIRE					= new ArrayList<>();
	public static final List<Integer>		SHIP_SHIELD					= new ArrayList<>();

	public static final List<Integer>		CHANGE_TARGET				= new ArrayList<>();
	public static final List<Integer>		WEAPON_PRIMARY				= new ArrayList<>();

	// on planet
	public static final List<Integer>		HERO_UP						= new ArrayList<>();
	public static final List<Integer>		HERO_DOWN					= new ArrayList<>();
	public static final List<Integer>		HERO_LEFT					= new ArrayList<>();
	public static final List<Integer>		HERO_RIGHT					= new ArrayList<>();
	public static final List<Integer>		HERO_SPRINT					= new ArrayList<>();
	public static final List<Integer>		HERO_PACK					= new ArrayList<>();
	public static final List<Integer>		HERO_UNPACK					= new ArrayList<>();

	// Debug Keys
	public static final List<Integer>		DEBUG_COLLISION				= new ArrayList<>();
	public static final List<Integer>		DEBUG_KEY_1					= new ArrayList<>();
	public static final List<Integer>		DEBUG_KEY_2					= new ArrayList<>();
	public static final List<Integer>		DEBUG_KEY_3					= new ArrayList<>();
	public static final List<Integer>		DEBUG_KEY_4					= new ArrayList<>();
	public static final List<Integer>		DEBUG_KEY_5					= new ArrayList<>();

	public static final List<Integer>		HERO_USE					= new ArrayList<>();


	/** gamepad */
	private static Controller				controller;

	/**
	 * Indicates a Key is pressed. It will keep returning true <b>until the Key
	 * is released</b>
	 * 
	 * @param key
	 * @return
	 */
	public static boolean pressed(final List<Integer> key) {
		boolean pressed = false;
		for (int i : key) {
			pressed |= pressed(i);
		}
		return pressed;
	}

	/**
	 * Indicates that a Key was released. It will return true <b>only one
	 * time</b> until this Key is released again
	 * 
	 * @param key
	 * @return
	 */
	public static boolean wasReleased(final List<Integer> key) {
		boolean released = false;
		for (int i : key)
			released |= wasReleased(i);
		return released;
	}

	/**
	 * Indicates that a Key was typed. It will return true <b>only one time</b>
	 * until this Key is typed again
	 * 
	 * @param key
	 * @return
	 */
	public static boolean wasTyped(final List<Integer> key) {
		boolean typed = false;
		for (int i : key)
			typed |= wasTyped(i);
		return typed;
	}

	private static boolean pressed(final int key) {
		if (key < 0)
			return Mouse.isButtonDown(key - MOUSE_KEYVALUE_OFFSET);
		else if (key >= GamepadConstants.POV_X_LEFT) {
			// TODO: controller == null, what to do?
			if (controller == null)
				return false;

			// POV
			if (key == GamepadConstants.POV_X_LEFT)
				return controller.getPovX() == 1f;
			if (key == GamepadConstants.POV_X_RIGHT)
				return controller.getPovX() == -1f;
			if (key == GamepadConstants.POV_Y_UP)
				return controller.getPovY() == -1f;
			if (key == GamepadConstants.POV_Y_DOWN)
				return controller.getPovY() == 1f;

			// Analog Sticks
			if (key == GamepadConstants.X_AXIS_RIGHT)
				return controller.getXAxisValue() > GamepadConstants.CONTROLLER_SENSIBILITY;
			if (key == GamepadConstants.X_AXIS_LEFT)
				return controller.getXAxisValue() < -GamepadConstants.CONTROLLER_SENSIBILITY;
			if (key == GamepadConstants.Y_AXIS_UP)
				return controller.getYAxisValue() < -GamepadConstants.CONTROLLER_SENSIBILITY;
			if (key == GamepadConstants.Y_AXIS_DOWN)
				return controller.getYAxisValue() > GamepadConstants.CONTROLLER_SENSIBILITY;
			if (key == GamepadConstants.Z_AXIS_RIGHT)
				return controller.getZAxisValue() > GamepadConstants.CONTROLLER_SENSIBILITY;
			if (key == GamepadConstants.Z_AXIS_LEFT)
				return controller.getZAxisValue() < -GamepadConstants.CONTROLLER_SENSIBILITY;
			if (key == GamepadConstants.Z_ROT_UP)
				return controller.getRZAxisValue() < -GamepadConstants.CONTROLLER_SENSIBILITY;
			if (key == GamepadConstants.Z_ROT_DOWN)
				return controller.getRZAxisValue() > GamepadConstants.CONTROLLER_SENSIBILITY;

			throw new IllegalArgumentException("No such Gamepad Button defined");
		} else if (key >= CONTROLLER_KEYVALUE_OFFSET)
			return controller == null ? false : controller.isButtonPressed(key - CONTROLLER_KEYVALUE_OFFSET);
		else
			return Keyboard.isKeyDown(key);
	}

	private static boolean wasTyped(final int key) {
		Boolean result = keyMap.get(key);
		return result == null ? false : result;
	}

	private static boolean wasReleased(final int key) {
		Boolean result = keyMap.get(key);
		return result == null ? false : !result;
	}

	public static int mouseX() {
		return Mouse.getX();
	}

	public static int mouseY() {
		return GameEngine.HEIGHT - Mouse.getY();
	}

	/**
	 * inits the Keybinding according to 'res/controls.cfg'
	 * 
	 * @param file
	 *            cfg-File with Keybindings
	 * @throws FileNotFoundException
	 *             if given file is not found
	 */
	public static void init(final String file) throws FileNotFoundException {
		ConfigUtil.init(file);

		SHIP_MAIN_ENGINE.add(getKeycode("SHIP_MAIN_ENGINE"));
		SHIP_ROTATE_LEFT.add(getKeycode("SHIP_ROTATE_LEFT"));
		SHIP_ROTATE_RIGHT.add(getKeycode("SHIP_ROTATE_RIGHT"));
		SHIP_BACK_SLOW_ACCL.add(getKeycode("SHIP_BACK_SLOW_ACCL"));
		SHIP_BACK_SLOW_DEACCL.add(getKeycode("SHIP_BACK_SLOW_DEACCL"));
		SHIP_FAST_ROTATE.add(getKeycode("SHIP_FAST_ROTATE"));
		SHIP_FIRE.add(getKeycode("SHIP_FIRE"));
		SHIP_SHIELD.add(getKeycode("SHIP_SHIELD"));
		CHANGE_TARGET.add(getKeycode("CHANGE_TARGET"));
		WEAPON_PRIMARY.add(getKeycode("WEAPON_PRIMARY"));

		HERO_UP.add(getKeycode("HERO_UP"));
		HERO_DOWN.add(getKeycode("HERO_DOWN"));
		HERO_LEFT.add(getKeycode("HERO_LEFT"));
		HERO_RIGHT.add(getKeycode("HERO_RIGHT"));
		HERO_SPRINT.add(getKeycode("HERO_SPRINT"));
		HERO_PACK.add(getKeycode("HERO_PACK"));
		HERO_UNPACK.add(getKeycode("HERO_UNPACK"));
		HERO_USE.add(getKeycode("HERO_USE"));

		MOUSE_LEFT.add(0 + MOUSE_KEYVALUE_OFFSET);
		MOUSE_RIGHT.add(1 + MOUSE_KEYVALUE_OFFSET);
		MOUSE_MIDDLE.add(2 + MOUSE_KEYVALUE_OFFSET);

		DEBUG_COLLISION.add(KEY_F9);
		DEBUG_KEY_1.add(KEY_F1);
		DEBUG_KEY_2.add(KEY_F2);
		DEBUG_KEY_3.add(KEY_F3);
		DEBUG_KEY_4.add(KEY_F4);
		DEBUG_KEY_5.add(KEY_F5);
	}


	private static int getKeycode(final String key) {
		String valueCFG = ConfigUtil.getString("controls." + key);
		int result = Keyboard.getKeyIndex(valueCFG);

		if (result == KEY_NONE) {
			try {
				Field field = GamepadConstants.class.getDeclaredField(valueCFG);
				result = field.getInt(GamepadConstants.class);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				throw new IllegalArgumentException("no such field");
			}
		}

		return result;
	}

	/**
	 * polls info from all input devices (Mouse, Keyboard, Gamepad/Controller)
	 */
	public static void update() {
		keyMap.clear();

		// polls Mouse info
		while (Mouse.next()) {
			int key = Mouse.getEventButton() + MOUSE_KEYVALUE_OFFSET;
			boolean value = Mouse.getEventButtonState();
			keyMap.put(key, value);
		}

		// polls Keyboard info
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			boolean value = Keyboard.getEventKeyState();
			keyMap.put(key, value);
		}

		// polls Controller info
		while (Controllers.next()) {
			controller = Controllers.getEventSource();
			int eventIndex = Controllers.getEventControlIndex();

			if (Controllers.isEventButton()) {
				int key = eventIndex + CONTROLLER_KEYVALUE_OFFSET;
				boolean value = controller.isButtonPressed(eventIndex);
				keyMap.put(key, value);
			}
			if (Controllers.isEventPovX() || Controllers.isEventPovY()) {
				if (controller.getPovX() == 1f)
					keyMap.put(GamepadConstants.POV_X_RIGHT, true);
				if (controller.getPovX() == -1f)
					keyMap.put(GamepadConstants.POV_X_LEFT, true);
				if (controller.getPovY() == -1f)
					keyMap.put(GamepadConstants.POV_Y_UP, true);
				if (controller.getPovY() == 1f)
					keyMap.put(GamepadConstants.POV_Y_DOWN, true);
			}
		}
	}

	@SuppressWarnings("unused")
	private static class GamepadConstants {
		static final float	CONTROLLER_SENSIBILITY	= 0.5f;

		static final int	POV_X_RIGHT				= 3001;
		static final int	POV_X_LEFT				= 3002;
		static final int	POV_Y_DOWN				= 3003;
		static final int	POV_Y_UP				= 3004;

		static final int	X_AXIS_RIGHT			= 3005;
		static final int	X_AXIS_LEFT				= 3006;
		static final int	Y_AXIS_UP				= 3007;
		static final int	Y_AXIS_DOWN				= 3008;
		static final int	Z_AXIS_LEFT				= 3009;
		static final int	Z_AXIS_RIGHT			= 3010;
		static final int	Z_ROT_UP				= 3011;
		static final int	Z_ROT_DOWN				= 3012;

		static final int	BUTTON_0				= CONTROLLER_KEYVALUE_OFFSET + 0;
		static final int	BUTTON_1				= CONTROLLER_KEYVALUE_OFFSET + 1;
		static final int	BUTTON_2				= CONTROLLER_KEYVALUE_OFFSET + 2;
		static final int	BUTTON_3				= CONTROLLER_KEYVALUE_OFFSET + 3;
		static final int	BUTTON_4				= CONTROLLER_KEYVALUE_OFFSET + 4;
		static final int	BUTTON_5				= CONTROLLER_KEYVALUE_OFFSET + 5;
		static final int	BUTTON_6				= CONTROLLER_KEYVALUE_OFFSET + 6;
		static final int	BUTTON_7				= CONTROLLER_KEYVALUE_OFFSET + 7;
		static final int	BUTTON_8				= CONTROLLER_KEYVALUE_OFFSET + 8;
		static final int	BUTTON_9				= CONTROLLER_KEYVALUE_OFFSET + 9;
		static final int	BUTTON_10				= CONTROLLER_KEYVALUE_OFFSET + 10;
		static final int	BUTTON_11				= CONTROLLER_KEYVALUE_OFFSET + 11;
		static final int	BUTTON_12				= CONTROLLER_KEYVALUE_OFFSET + 12;
	}
}
