package net.tmt.entityComponents.other;

import net.tmt.entity.Entity2D;
import net.tmt.entity.bullets.TargetRocket;
import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;
import net.tmt.global.achievments.Achievments;

public class RocketLauncherComponent extends Component {
	public static final String	LAUNCH_TYPE_1	= "LAUNCH_TYPE_1";
	public static final String	LAUNCH_TYPE_2	= "LAUNCH_TYPE_2";
	public static final String	LAUNCH_TYPE_3	= "LAUNCH_TYPE_3";
	public static final String	LAUNCH_TYPE_4	= "LAUNCH_TYPE_4";

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {


		if (caller.isSet(LAUNCH_TYPE_1) && caller.isSet(TargetSearchComponent.TARGET)
				&& (boolean) caller.getValue(TargetSearchComponent.IS_LOCKED)) {
			Entity2D target = (Entity2D) caller.getValue(TargetSearchComponent.TARGET);

			double rotation = (double) caller.getValue(ROTATION_ANGLE_LOOK);
			caller.getEntityManager().addEntity(new TargetRocket(owner.getPos().copy(), rotation, target, owner));
			Achievments.achiev(Achievments.Space.FIRE_ROCKET);
		}
	}
}
