package net.tmt.game.interfaces;

public interface Dispatcher {
	public void dispatch(String key, Object value);

	public Object getValue(String key);

	public boolean isSet(String key);

	public void remove(String key);
}
