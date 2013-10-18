package net.tmt.entity.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tmt.gfx.Graphics;

public class ComponentDispatcher {
	protected List<Component>	components	= new ArrayList<>();
	protected Map<String, Object>		valueMap	= new HashMap<>();
	protected Map<String, Object>		dispatchMap	= new HashMap<>();

	public void update(final double delta) {
		for (Component c : components)
			c.update(this, delta);
	}

	public void render(final Graphics g) {
		for (Component c : components)
			c.render(this, g);
		valueMap.clear();
	}

	public void addComponent(final Component component) {
		components.add(component);
	}

	public void sendValueToComponents(final String name, final Object value) {
		valueMap.put(name, value);
	}

	public List<Component> getComponents() {
		return components;
	}


	public void dispatch(final String name, final double value) {
		dispatchMap.put(name, value);
	}

	protected Object getValue(final String name) {
		Object result = valueMap.get(name);

		return result == null ? dispatchMap.get(name) : result;
	}

	protected boolean isSet(final String name) {
		return valueMap.containsKey(name) || dispatchMap.containsKey(name);
	}
}
