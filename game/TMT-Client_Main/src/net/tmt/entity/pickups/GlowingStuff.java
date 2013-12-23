package net.tmt.entity.pickups;

import java.util.HashMap;
import java.util.Map;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.other.AnimatedRenderComponent;
import net.tmt.entityComponents.other.PickUpComponent;
import net.tmt.util.SpriteAnimation;
import net.tmt.util.Vector2d;

public class GlowingStuff extends Entity2D {

	public GlowingStuff(final Vector2d pos) {
		super(pos);
		removeAllComponents();
		Map<String, SpriteAnimation> aniMap = new HashMap<String, SpriteAnimation>();
		SpriteAnimation glow = new SpriteAnimation(new String[] { "glowing_stuff_0", "glowing_stuff_1",
				"glowing_stuff_2", "glowing_stuff_3" }, 0.1);
		aniMap.put("glow", glow);
		addComponent(new AnimatedRenderComponent(aniMap, "glow"));
		addComponent(new PickUpComponent(new Vector2d(3, -15), false));
	}
}
