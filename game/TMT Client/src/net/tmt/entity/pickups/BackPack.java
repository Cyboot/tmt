package net.tmt.entity.pickups;

import java.util.ArrayList;
import java.util.List;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.collision.CollisionComponent;
import net.tmt.entity.component.other.PickUpComponent;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public class BackPack extends Entity2D {

	private static int		DEFAULT_CAPACITY	= 10;

	private List<Entity2D>	contents			= new ArrayList<Entity2D>();
	private int				capacity			= DEFAULT_CAPACITY;

	public BackPack(final Vector2d pos) {
		super(pos);
		Sprite s = new Sprite("backpack");
		setSprite(s);
		addComponent(new PickUpComponent(new Vector2d(0, 0), true));
		addComponent(new CollisionComponent(10));
	}

	public void packItem(final Entity2D e) {
		if (contents.size() <= capacity)
			contents.add(e);
	}

	public void unPackItem(final Entity2D e) {
		contents.remove(e);
	}

}
