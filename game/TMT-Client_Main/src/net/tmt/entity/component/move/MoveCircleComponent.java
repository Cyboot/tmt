package net.tmt.entity.component.move;

import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.util.Vector2d;

public class MoveCircleComponent extends Component {
	private Vector2d	centerPos;
	private double		radius;
	private double		angle;
	private double		angleSpeed;

	public MoveCircleComponent(final double angle, final Vector2d centerPos, final double radius, final double speed) {
		this.angle = angle;
		this.centerPos = centerPos;
		this.radius = radius;

		double timeForCircle = speed / (2 * Math.PI * radius);
		angleSpeed = timeForCircle * 2 * Math.PI;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		pos.x = Math.sin(angle) * radius + centerPos.x;
		pos.y = -Math.cos(angle) * radius + centerPos.y;

		angle += angleSpeed * delta;
		if (angle > 2 * Math.PI)
			angle -= 2 * Math.PI;
	}
}
