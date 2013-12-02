package net.tmt.entity.component.other;

import java.util.List;

import net.tmt.entity.Entity2D;
import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.entity.npc.NPCSpaceShip;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Shape;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class TargetSearchComponent extends Component {
	public static final String	CHANGE_TARGET	= "CHANGE_TARGET";
	public static final String	TARGET			= "TARGET";
	public static final String	IS_LOCKED		= "IS_LOCKED";

	private Shape				shapeIn			= new Shape().setCentered(true);
	private Shape				shapeOut		= new Shape().setCentered(true);
	private Color				colorIn			= (Color) Color.YELLOW;
	private Color				colorOut		= (Color) Color.YELLOW;

	private Entity2D			target;
	private double				shapeScale		= 3;
	private double				diffAngle;
	private boolean				isLocked;

	public TargetSearchComponent() {
		shapeIn.addPoint(new Vector2d(0, 0));
		shapeIn.addPoint(new Vector2d(10, 10));
		shapeIn.addPoint(new Vector2d(0, 20));
		shapeIn.addPoint(new Vector2d(-10, 10));

		shapeOut.addPoint(new Vector2d(-12, 0));
		shapeOut.addPoint(new Vector2d(12, 0));
		shapeOut.addPoint(new Vector2d(12, 24));
		shapeOut.addPoint(new Vector2d(-12, 24));
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		isLocked = false;
		if (target == null || caller.isSet(CHANGE_TARGET)) {
			searchTarget(caller.getEntityManager().getCollidableEntities(pos));
		} else {
			calcFocus(caller);

			if (!target.isAlive()) {
				target = null;
			}
		}

		caller.dispatch(IS_LOCKED, isLocked);
		caller.dispatch(TARGET, target);
		shapeIn.setScale(shapeScale);
		shapeOut.setScale(shapeScale);
	}

	private void calcFocus(final ComponentDispatcher caller) {
		Vector2d diff = Vector2d.tmp1.set(pos).sub(target.getPos());
		double targetAngle = (Math.toDegrees(diff.getRotation()) + 180) % 360;
		double rotationAngle = (double) caller.getValue(ROTATION_ANGLE_LOOK);
		diffAngle = Math.abs(targetAngle - rotationAngle);
		if (diffAngle > 180)
			diffAngle = 360 - diffAngle;

		colorOut = (Color) Color.YELLOW;
		colorIn = (Color) Color.YELLOW;
		if (diffAngle < 45)
			colorOut = (Color) Color.RED;
		if (diffAngle < 30) {
			isLocked = true;
			colorIn = (Color) Color.RED;
		}
	}

	private void searchTarget(final List<List<Entity2D>> list) {
		Entity2D previousTarget = target;

		// find the nearest Entity and mark it as target
		double minDist = Double.MAX_VALUE;
		for (List<Entity2D> innerList : list) {
			for (Entity2D e : innerList) {
				if (e == owner || e == previousTarget || !(e instanceof NPCSpaceShip))
					continue;

				double dist = e.getPos().distanceTo(owner.getPos());
				if (dist < minDist) {
					minDist = dist;
					target = e;
				}
			}
		}
	}

	@Override
	public void render(final ComponentDispatcher caller, final Graphics g) {
		if (target != null) {
			g.setColor(colorIn);
			g.drawShape(target.getPos(), shapeIn);
			g.setColor(colorOut);
			g.drawShape(target.getPos(), shapeOut);
		}
	}
}
