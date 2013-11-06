package net.tmt.entity.component.other;

import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.util.Vector2d;

public class ExtraOffsetComponent extends Component {
	public static final String	TRANSLATE_POS	= "TRANSLATE_POS";

	private Vector2d			offsetPos;
	private Vector2d			offset;

	public ExtraOffsetComponent(final Vector2d offset) {
		this.offset = offset;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		Vector2d translate = Vector2d.tmp1.set(offset);
		double rotation = (double) caller.getValue(ROTATION_ANGLE);
		translate.rotate(Math.toRadians(rotation));

		offsetPos.x = pos.x + translate.x;
		offsetPos.y = pos.y + translate.y;

		caller.dispatch(TRANSLATE_POS, offsetPos);
	}

	@Override
	public void initialDispatch(final ComponentDispatcher caller) {
		super.initialDispatch(caller);

		offsetPos = new Vector2d(pos);
	}
}
