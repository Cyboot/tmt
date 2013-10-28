package net.tmt.entity;

import net.tmt.entity.component.CollisionComponent;
import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.entity.component.RenderComponent;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public abstract class Entity2D implements Renderable {
	private static long			currentID		= 1;
	private long				id;
	private boolean				isAlive			= true;
	private boolean				isCollisable	= false;
	protected Vector2d			pos;
	protected Entity2D			owner;

	private ComponentDispatcher	compDispatcher	= new ComponentDispatcher(this);

	public Entity2D() {
		this(new Vector2d());
	}

	public Entity2D(final Vector2d pos) {
		id = currentID++;
		this.pos = pos;
		compDispatcher.addComponent(new RenderComponent.Builder().pos(pos).sprite(null).build());
	}

	public void update(final EntityManager caller, final double delta) {
		compDispatcher.update(caller, delta);
	}

	protected void dispatchValue(final String name, final Object value) {
		compDispatcher.sendValueToComponents(name, value);
	}

	public void addComponent(final Component component) {
		if (component instanceof CollisionComponent)
			isCollisable = true;

		compDispatcher.addComponent(component);
	}

	protected Object getValue(final String name) {
		return compDispatcher.getValue(name);
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
		isCollisable = false;
	}

	public void setSprite(final Sprite sprite) {
		for (Component c : compDispatcher.getComponents()) {
			if (c instanceof RenderComponent)
				((RenderComponent) c).setSprite(sprite);
		}
	}

	public boolean isCollisable() {
		return isCollisable;
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

	@Override
	public String toString() {
		String str = getClass().getSimpleName() + " #" + id + " (" + pos.toString() + ")";
		return str;
	}
}
