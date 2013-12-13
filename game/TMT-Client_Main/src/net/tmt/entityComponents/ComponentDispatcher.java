package net.tmt.entityComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tmt.entity.Entity2D;
import net.tmt.entityComponents.animation.KillAnimationComponent;
import net.tmt.entityComponents.other.RenderComponent;
import net.tmt.game.interfaces.Dispatcher;
import net.tmt.game.interfaces.EntityWorldGetter;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;
import net.tmt.map.World;

public class ComponentDispatcher implements Renderable, Dispatcher, EntityWorldGetter {
	private Entity2D			owner;
	private EntityManager		entityManager;
	private World				world;

	private List<Component>		components	= new ArrayList<>();

	private Map<String, Object>	valueMap	= new HashMap<>();
	private Map<String, Object>	dispatchMap	= new HashMap<>();


	public ComponentDispatcher(final Entity2D owner) {
		this.owner = owner;
	}

	public void update(final EntityManager caller, final World world, final double delta) {
		entityManager = caller;
		this.world = world;
		for (Component c : components)
			c.update(this, delta);
	}

	@Override
	public void render(final Graphics g) {
		RenderComponent renderComp = null;
		for (Component c : components) {
			if (c instanceof RenderComponent)
				renderComp = (RenderComponent) c;
			else
				c.render(this, g);
		}
		// render RenderComponent last
		if (renderComp != null)
			renderComp.render(this, g);

		valueMap.clear();
	}

	public void addComponent(final Component component) {
		components.add(component);
		component.initialDispatch(this);
	}

	public void sendValueToComponents(final String name, final Object value) {
		valueMap.put(name, value);
	}

	public List<Component> getComponents() {
		return components;
	}

	public void clearValue(final String name) {
		dispatchMap.remove(name);
	}

	public Entity2D getOwner() {
		return owner;
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> T getComponent(final Class<T> clazz) {
		for (Component c : components) {
			if (c.getClass().equals(clazz))
				return (T) c;
		}
		return null;
	}

	@Override
	public void dispatch(final String name, final Object value) {
		dispatchMap.put(name, value);
	}

	@Override
	public Object getValue(final String name) {
		Object result = valueMap.get(name);

		return result == null ? dispatchMap.get(name) : result;
	}

	@Override
	public boolean isSet(final String name) {
		return valueMap.containsKey(name) || dispatchMap.containsKey(name);
	}

	@Override
	public void remove(final String key) {
		valueMap.remove(key);
		dispatchMap.remove(key);
	}

	/**
	 * calls {@link KillAnimationComponent#onKilled(ComponentDispatcher)}
	 */
	public void onKilled() {
		for (Component c : components) {
			if (c instanceof KillAnimationComponent)
				((KillAnimationComponent) c).onKilled(this);
		}
	}
}
