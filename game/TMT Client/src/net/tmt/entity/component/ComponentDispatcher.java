package net.tmt.entity.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tmt.entity.Entity2D;
import net.tmt.game.Renderable;
import net.tmt.game.Updateable;
import net.tmt.gfx.Graphics;

public class ComponentDispatcher implements Renderable, Updateable {
	private Entity2D			owner;
	private List<Component>		components	= new ArrayList<>();
	private Map<String, Object>	valueMap	= new HashMap<>();
	private Map<String, Object>	dispatchMap	= new HashMap<>();

	public ComponentDispatcher(final Entity2D owner) {
		this.owner = owner;
	}

	@Override
	public void update(final double delta) {
		for (Component c : components)
			c.update(this, delta);
	}

	@Override
	public void render(final Graphics g) {
		for (Component c : components)
			c.render(this, g);
		valueMap.clear();
	}

	public void addComponent(final Component component) {
		components.add(component);
		component.dispatch(this);
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

	public void dispatch(final String name, final Object value) {
		dispatchMap.put(name, value);
	}

	public Object getValue(final String name) {
		Object result = valueMap.get(name);

		return result == null ? dispatchMap.get(name) : result;
	}

	protected boolean isSet(final String name) {
		return valueMap.containsKey(name) || dispatchMap.containsKey(name);
	}

	protected Entity2D getOwner() {
		return owner;
	}
}
