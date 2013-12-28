package net.tmt.entity;

import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;
import net.tmt.entityComponents.move.PhysicsComponent;
import net.tmt.entityComponents.other.RenderComponent;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

import org.jbox2d.collision.shapes.Shape;

public abstract class Entity2D implements Renderable {
	private static long			currentID		= 1;

	private long				id;
	private boolean				isAlive			= true;

	protected Vector2d			pos;
	private int					posZ			= 1;
	protected Entity2D			owner;

	private ComponentDispatcher	compDispatcher	= new ComponentDispatcher(this);
	private Sprite				sprite;
	private Entity2D			sensorEntity;

	private PhysicsComponent	physicsComponent;


	public Entity2D(final Vector2d pos) {
		id = currentID++;
		this.pos = pos;
		compDispatcher.addComponent(new RenderComponent.Builder().sprite(null).build());
	}

	public void update(final EntityManager caller, final World world, final double delta) {
		compDispatcher.update(caller, world, delta);
	}

	public void dispatchValue(final String name, final Object value) {
		compDispatcher.sendValueToComponents(name, value);
	}

	public void addComponent(final Component component) {
		if (component instanceof PhysicsComponent)
			physicsComponent = (PhysicsComponent) component;

		compDispatcher.addComponent(component);
	}

	public Object getValue(final String key) {
		return compDispatcher.getValue(key);
	}

	public boolean isSet(final String key) {
		return compDispatcher.isSet(key);
	}

	@Override
	public void render(final Graphics g) {
		compDispatcher.render(g);
	}

	public void kill() {
		isAlive = false;
		onKilled();
	}

	protected void onKilled() {
		compDispatcher.onKilled();
	}

	protected Entity2D getSensorEntity() {
		return sensorEntity;
	}

	public void triggerSensor(final Entity2D touchingEntity, final boolean beginContact) {
		if (beginContact) {
			// ignore entity if sensorentity is already != null
			if (sensorEntity == null)
				sensorEntity = touchingEntity;
		} else {
			// delete sensorEntity if it stops touching
			if (sensorEntity == touchingEntity)
				sensorEntity = null;
		}
	}

	public boolean isAlive() {
		return isAlive;
	}

	public Vector2d getPos() {
		return pos;
	}

	/**
	 * removes all Components by creating a new empty ComponentDispatcher
	 */
	protected void removeAllComponents() {
		compDispatcher = new ComponentDispatcher(this);
	}

	public void setSprite(final Sprite sprite) {
		this.sprite = sprite;
		for (Component c : compDispatcher.getComponents()) {
			if (c instanceof RenderComponent)
				((RenderComponent) c).setSprite(sprite);
		}
	}

	/**
	 * called if entities update()-Method was not called (because its to far
	 * away)
	 */
	public void disabledUpdate(final double delta) {
		if (physicsComponent != null) {
			physicsComponent.disable();
		}
	}

	public <T extends Component> T getComponent(final Class<T> clazz) {
		return compDispatcher.getComponent(clazz);
	}

	public long getId() {
		return id;
	}

	public Entity2D getOwner() {
		return owner;
	}

	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public String toString() {
		String str = getClass().getSimpleName() + " #" + id + " (" + pos.toString() + ")";
		return str;
	}

	protected Shape getCollisionShape() {
		return null;
	}

	public int getZ() {
		return posZ;
	}

	/**
	 * set the "Height", important for render order:<br>
	 * <b>0</b> - 0m (bottom)<br>
	 * <b>127</b> - 127m (max)
	 * 
	 * @param posZ
	 * @return
	 */
	public Entity2D setPosZ(final int posZ) {
		if (posZ < 0 || posZ >= EntityManager.LAYER_COUNT)
			throw new IllegalArgumentException("the given Height '" + posZ + "' is not between 0 and "
					+ EntityManager.LAYER_COUNT);

		this.posZ = posZ;
		return this;
	}
}
