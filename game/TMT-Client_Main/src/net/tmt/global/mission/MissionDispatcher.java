package net.tmt.global.mission;

import java.util.HashSet;
import java.util.Set;

public class MissionDispatcher {
	private static final Set<Entry>	entries	= new HashSet<>();

	public static void dispatch(final Object object) {
		dispatch(object, "");
	}

	public static void dispatch(final Object object, final String message) {
		entries.add(new Entry(object, message));
	}

	protected static void clearValues() {
		entries.clear();
	}

	protected static Set<Entry> getEntries() {
		return entries;
	}

	protected static class Entry {
		private Object	object;
		private String	message;

		public Entry(final Object object, final String message) {
			this.object = object;
			this.message = message;
		}

		public Object getObject() {
			return object;
		}

		public String getMessage() {
			return message;
		}
	}
}
