package net.tmt.map;

import net.tmt.gfx.Sprite;

public enum Terrain {
	SPACE_VOID("space_void"), //
	SPACE_SUN("space_void"), //
	SPACE_ASTEROID("space_void"), //
	SPACE_DEBRIS("space_void"), //
	SPACE_MINEFIELD("space_void"), //
	SPACE_NEBULA("space_void"), //
	SPACE_BLACKHOLE("space_void"), //

	PLANET_MOUNTAIN("planet_mountain", PlanetMap.CHUNK_SIZE), //
	PLANET_ASPHALT("asphalt", PlanetMap.CHUNK_SIZE), //
	PLANET_GRASS("planet_grass", PlanetMap.CHUNK_SIZE), //
	PLANET_WATER("planet_water", PlanetMap.CHUNK_SIZE), //
	PLANET_DESERT("planet_desert", PlanetMap.CHUNK_SIZE), //
	PLANET_SNOW("planet_snow", PlanetMap.CHUNK_SIZE), //
	PLANET_FOREST("planet_forest", PlanetMap.CHUNK_SIZE), //
	PLANET_SWAMP("planet_swamp", PlanetMap.CHUNK_SIZE); //

	private Sprite	sprite;
	private String	str;
	private int		size;

	private Terrain(final String fileSprite) {
		str = fileSprite;
	}

	private Terrain(final String fileSprite, final int size) {
		str = fileSprite;
		this.size = size;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public static void init() {
		for (Terrain t : Terrain.values()) {
			t.sprite = new Sprite(t.str).setCentered(false);
			if (t.size != 0) {
				t.sprite.setHeight(t.size);
				t.sprite.setWidth(t.size);
			}

		}
	}
}
