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

	private Entity2D	itemOwner;
	private Vector2d	relativePos;
	private boolean		wearable;

	public PickUpComponent(final Vector2d relativePos, final boolean wearable) {
		this.relativePos = relativePos;
		this.wearable = wearable;
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (itemOwner == null) {
			checkPickUp(caller);
		} else {
			updatePosition(caller);
		}
	}

	private void updatePosition(final ComponentDispatcher caller) {
		// TODO: maybe a better solution for this? ... aaand the thing above
		double itemOwnerRotationAngleLook = Double.MIN_VALUE;
		itemOwnerRotationAngleLook = (double) itemOwner.getValue(MoveComponent.ROTATION_ANGLE_LOOK);
		if (itemOwnerRotationAngleLook != Double.MIN_VALUE) {
			caller.getOwner().dispatchValue(MoveComponent.ROTATION_ANGLE_LOOK, itemOwnerRotationAngleLook);
		}
		Vector2d.tmp1.set(itemOwner.getPos());
		Vector2d.tmp2.set(relativePos);
		Vector2d.tmp2.rotate(Math.toRadians(itemOwnerRotationAngleLook));
		Vector2d.tmp1.add(Vector2d.tmp2);
		caller.getOwner().getPos().set(Vector2d.tmp1);
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
							itemOwner = e;
							if (!wearable)
								((Hero) e).setHoldingSth(true);
						}
					}
				}
			}
		}
	}

	public Entity2D getOwner() {
		return itemOwner;
	}

	public boolean isWearable() {
		return wearable;
	}

}
