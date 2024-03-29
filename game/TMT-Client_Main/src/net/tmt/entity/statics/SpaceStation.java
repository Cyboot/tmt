package net.tmt.entity.statics;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.move.RotateComponent;
import net.tmt.entityComponents.other.MissionOfferComponent;
import net.tmt.entityComponents.other.OnHoverComponent;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Sprite;
import net.tmt.global.mission.SpaceRaceMission;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

public class SpaceStation extends Entity2D {
	private static final int	SIZE	= 128;


	public SpaceStation(final Vector2d pos, final int type) {
		super(pos);

		addComponent(new MissionOfferComponent(SpaceRaceMission.class, SIZE * 3));
		addComponent(new OnHoverComponent());
		addComponent(new RotateComponent(0, 3));

		switch (type) {
		case 1:
			setSprite(new Sprite("spacestation_1"));
			break;
		case 2:
			setSprite(new Sprite("spacestation_2"));
			break;
		}
	}


	@Override
	public void update(final EntityManager caller, final World world, final double delta) {
		dispatchValue(RotateComponent.IS_ROTATE_LEFT, true);
		super.update(caller, world, delta);
	}
}
