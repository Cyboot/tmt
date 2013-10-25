package net.tmt.entity;

import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.entity.component.RenderComponent;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public abstract class Entity2D implements Renderable {
	private boolean				isAlive			= true;
	protected Vector2d			pos;
	protected int				radius;

	private ComponentDispatcher	compDispatcher	= new ComponentDispatcher(this);

	public Entity2D() {
		this(new Vector2d());
	}

	public Entity2D(final Vector2d pos) {
		this.pos = pos;
		compDispatcher.addComponent(new RenderComponent.Builder().pos(pos).sprite(null).build());
	}

	public void update(final double delta, EntityManager caller) {
		compDispatcher.update(delta);
	}

	protected void dispatchValue(final String name, final Object value) {
		compDispatcher.sendValueToComponents(name, value);
	}

	protected void addComponent(final Component component) {
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
	}

	public void setSprite(final Sprite sprite) {
		for (Component c : compDispatcher.getComponents()) {
			if (c instanceof RenderComponent)
				((RenderComponent) c).setSprite(sprite);
		}
	}
}
