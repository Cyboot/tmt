package net.tmt.entity;

import net.tmt.entityComponents.Component;
import net.tmt.entityComponents.ComponentDispatcher;
import net.tmt.entityComponents.animation.KillAnimationComponent;
import net.tmt.entityComponents.collision.CollisionComponent;
import net.tmt.entityComponents.other.RenderComponent;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.map.World;
import net.tmt.util.Vector2d;

import org.jbox2d.collision.shapes.Shape;

public abstract class Entity2D implements Renderable {
	private static long			currentID			= 1;
	private long				id;
	private boolean				isAlive				= true;
	private CollisionComponent	collisionComponent;
	private boolean				hasOnKillComponent	= false;
	protected Vector2d			pos;
	protected Entity2D			owner;

	private ComponentDispatcher	compDispatcher		= new ComponentDispatcher(this);
	private Sprite				sprite;

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
		if (component instanceof CollisionComponent)
			collisionComponent = (CollisionComponent) component;
		if (component instanceof KillAnimationComponent)
			hasOnKillComponent = true;

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
		if (hasOnKillComponent) {
			compDispatcher.onKilled();
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
		collisionComponent = null;
		hasOnKillComponent = false;
	}

	public void setSprite(final Sprite sprite) {
		this.sprite = sprite;
		for (Component c : compDispatcher.getComponents()) {
			if (c instanceof RenderComponent)
				((RenderComponent) c).setSprite(sprite);
		}
	}

	public boolean isCollisable() {
		return collisionComponent != null;
	}

	public CollisionComponent getCollisionComponent() {
		return collisionComponent;
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
}
