package net.tmt.entity.component.other;

import java.util.List;

import net.tmt.entity.Entity2D;
import net.tmt.entity.Hero;
import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.entity.component.collision.CollisionComponent;
import net.tmt.entity.component.move.MoveComponent;
import net.tmt.util.CountdownTimer;
import net.tmt.util.Vector2d;

public class PickUpComponent extends Component {

	public static final String	ITEM_HOLDER	= "ITEM_HOLDER";

	private Entity2D			itemHolder;
	private Vector2d			relativePos;
	private boolean				wearable;
	private CountdownTimer		thrownTimer	= CountdownTimer.createManualResetTimer(1.5);

	public PickUpComponent(final Vector2d relativePos, final boolean wearable) {
		this.relativePos = relativePos;
		this.wearable = wearable;
		thrownTimer.setTimer(0);
	}

	@Override
	public void update(final ComponentDispatcher caller, final double delta) {
		if (itemHolder == null) {
			if (thrownTimer.isTimeUp(delta))
				checkPickUp(caller);
		} else {
			updatePosition(caller);
		}

		caller.dispatch(ITEM_HOLDER, itemHolder);
	}

	private RenderComponent getRenderer() {
		RenderComponent rc, arc;
		rc = owner.getComponent(RenderComponent.class);
		arc = owner.getComponent(AnimatedRenderComponent.class);
		return (rc != null ? rc : arc);
	}

	public void getPacked() {
		RenderComponent r = getRenderer();
		if (r == null)
			return;
		r.hide();
	}

	public void getUnPacked() {
		RenderComponent r = getRenderer();
		if (r == null)
			return;
		r.unhide();
	}

	public void getThrown() {
		itemHolder = null;
		thrownTimer.reset();
	}

	private void updatePosition(final ComponentDispatcher caller) {
		// TODO: maybe a better solution for this? ... aaand the thing above
		double itemOwnerRotationAngleLook = Double.MIN_VALUE;
		itemOwnerRotationAngleLook = (double) itemHolder.getValue(MoveComponent.ROTATION_ANGLE_LOOK);
		if (itemOwnerRotationAngleLook != Double.MIN_VALUE) {
			caller.getOwner().dispatchValue(MoveComponent.ROTATION_ANGLE_LOOK, itemOwnerRotationAngleLook);
		}
		Vector2d.tmp1.set(itemHolder.getPos());
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
							Hero h = (Hero) e;
							if (!h.holdsSomething()) {
								itemHolder = e;
								if (wearable)
									((Hero) e).wear(caller.getOwner());
								else
									((Hero) e).hold(caller.getOwner());
							}
						}
					}
				}
			}
		}
	}

	public Entity2D getHolder() {
		return itemHolder;
	}

	public boolean isWearable() {
		return wearable;
	}

}
