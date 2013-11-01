package net.tmt.entity.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tmt.entity.Entity2D;
import net.tmt.game.interfaces.Dispatcher;
import net.tmt.game.interfaces.Renderable;
import net.tmt.game.manager.EntityManager;
import net.tmt.gfx.Graphics;

public class ComponentDispatcher implements Renderable, Dispatcher {
	private Entity2D			owner;
	private EntityManager		entityManager;

	private List<Component>		components	= new ArrayList<>();

	private Map<String, Object>	valueMap	= new HashMap<>();
	private Map<String, Object>	dispatchMap	= new HashMap<>();


	public ComponentDispatcher(final Entity2D owner) {
		this.owner = owner;
	}

	public void update(final EntityManager caller, final double delta) {
		entityManager = caller;
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

	protected Entity2D getOwner() {
		return owner;
	}

	protected EntityManager getEntityManager() {
		return entityManager;
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
}
