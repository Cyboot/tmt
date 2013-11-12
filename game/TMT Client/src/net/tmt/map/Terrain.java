package net.tmt.map;

import net.tmt.gfx.Sprite;

public enum Terrain {
	SPACE_VOID("space_void"), //
	SPACE_NEBULA("space_void"), //
	SPACE_PLANET("space_void"), //
	SPACE_ASTEROIDS_FLOATING("space_void"), //
	SPACE_ASTEROIDS_BELT("space_void"), //
	SPACE_DEBRIS("space_void"), //

	PLANET_GRASS("planet_grass"), //
	PLANET_WATER("planet_grass"), //
	PLANET_DESERT("planet_grass"), //
	PLANET_SNOW("planet_grass"), //
	PLANET_FOREST("planet_grass"), //
	PLANET_SWAMP("planet_grass"), //
	PLANET_MOUNTAIN("planet_grass");

	private Sprite	sprite;
	private String	str;

	private Terrain(final String fileSprite) {
		str = fileSprite;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public static void init() {
		for (Terrain t : Terrain.values())
			t.sprite = new Sprite(t.str).setCentered(false);
	}
}
