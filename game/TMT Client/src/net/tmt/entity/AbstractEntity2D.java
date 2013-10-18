package net.tmt.entity;

import net.tmt.entity.component.Component;
import net.tmt.entity.component.ComponentDispatcher;
import net.tmt.entity.component.RenderComponent;
import net.tmt.gfx.Graphics;
import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

public abstract class AbstractEntity2D {
	private boolean				isAlive			= true;
	protected Vector2d			pos;
	protected int				radius;

	private ComponentDispatcher	compDispatcher	= new ComponentDispatcher();

	public AbstractEntity2D() {
		this(new Vector2d());
	}

	public AbstractEntity2D(final Vector2d pos) {
		this.pos = pos;
		compDispatcher.addComponent(new RenderComponent.Builder().pos(pos).sprite(null).build());
	}

	public void update(final double delta) {
		compDispatcher.update(delta);
	}

	protected void sendValueToComponent(final String name, final Object value) {
		compDispatcher.sendValueToComponents(name, value);
	}

	protected void addComponent(final Component component) {
		compDispatcher.addComponent(component);
	}

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

	public void setSprite(final Sprite sprite) {
		for (Component c : compDispatcher.getComponents()) {
			if (c instanceof RenderComponent)
				((RenderComponent) c).setSprite(sprite);
		}
	}
}
