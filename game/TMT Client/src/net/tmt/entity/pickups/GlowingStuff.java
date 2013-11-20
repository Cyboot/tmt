package net.tmt.entity.pickups;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.collision.CollisionComponent;
import net.tmt.entity.component.other.AnimatedRenderComponent;
import net.tmt.entity.component.other.PickUpComponent;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class GlowingStuff extends Entity2D {

	public GlowingStuff(final Vector2d pos) {
		super(pos);
		removeAllComponents();
		List<Sprite> aniSprites = new ArrayList<Sprite>();
		aniSprites.add(new Sprite("glowing_stuff_0"));
		aniSprites.add(new Sprite("glowing_stuff_1"));
		aniSprites.add(new Sprite("glowing_stuff_2"));
		aniSprites.add(new Sprite("glowing_stuff_3"));
		addComponent(new AnimatedRenderComponent(aniSprites, 0.1));
		addComponent(new CollisionComponent(10));
		addComponent(new PickUpComponent(new Vector2d(3, -15), false));
	}

}
