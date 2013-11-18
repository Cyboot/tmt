package net.tmt.entity.component.other;

import java.util.List;

import net.tmt.entity.Entity2D;
import net.tmt.entity.Hero;
import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.entity.component.collision.CollisionComponent;
import net.tmt.entity.component.move.MoveComponent;
import net.tmt.util.Vector2d;

public class PickUpComponent extends Component {

	private Entity2D	owner;
	private Vector2d	relativePos;

	public PickUpComponent(final Vector2d relativePos) {
		this.relativePos = relativePos;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (owner == null) {
			checkPickUp(caller);
		} else {
			updatePosition(caller);
		}
	}

	private void updatePosition(final ComponentDispatcher caller) {
		MoveComponent mc = owner.getComponent(MoveComponent.class);
		if (mc != null) {
			Vector2d ownerPos = (Vector2d) owner.getValue(MoveComponent.MOVE_DIR);
			if (ownerPos != null) {
				Vector2d newPos = ownerPos.add(relativePos);
				owner.dispatchValue(MoveComponent.MOVE_DIR, newPos);
			}
			// TODO: maybe a better solution for this?
			double ownerRotationAngleLook = Double.MIN_VALUE;
			ownerRotationAngleLook = (double) owner.getValue(MoveComponent.ROTATION_ANGLE_LOOK);
			if (ownerRotationAngleLook != Double.MIN_VALUE) {
				owner.dispatchValue(MoveComponent.ROTATION_ANGLE_LOOK, ownerRotationAngleLook);
			}
		}
	}

	private void checkPickUp(final ComponentDispatcher caller) {
		CollisionComponent cc = caller.getComponent(CollisionComponent.class);
		if (cc != null) {
			Object isCollision = caller.getValue(CollisionComponent.IS_COLLISION);
			if (isCollision != CollisionComponent.IS_COLLISION && isCollision != null) {
				Object ceo = caller.getValue(CollisionComponent.COLLISION_ENTITIES);
				if (ceo != CollisionComponent.COLLISION_ENTITIES && ceo != null) {
					List<Entity2D> ce = (List<Entity2D>) ceo;
					for (Entity2D e : ce) {
						// TODO: maybe implement an AbleToPickUp interface
						if (e instanceof Hero) {
							owner = e;
						}
					}
				}
			}
		}
	}

	public Entity2D getOwner() {
		return owner;
	}

}
