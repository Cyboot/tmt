package net.tmt.entity.statics;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.move.RotateComponent;
import net.tmt.entity.component.other.OnHoverComponent;
import net.tmt.entity.statics.area.MissionAreaOffer;
import net.tmt.game.manager.EntityManager;
import net.tmt.game.manager.MissionManager;
import net.tmt.gfx.Sprite;
import net.tmt.global.mission.DummyMission;
import net.tmt.util.Vector2d;

public class SpaceStation extends Entity2D {

	private static final int	SIZE	= 128;


	public SpaceStation(final Vector2d pos) {
		super(pos);

		MissionManager.getInstance().registerArea(new MissionAreaOffer(pos, new DummyMission(), SIZE));
		addComponent(new OnHoverComponent());
		addComponent(new RotateComponent(0, 3));

		setSprite(new Sprite("spacestation_1"));
	}


	@Override
	public void update(final EntityManager caller, final double delta) {
		dispatchValue(RotateComponent.IS_ROTATE_LEFT, true);
		super.update(caller, delta);
	}
}
