package net.tmt.global.achievments;

/**
 * Achievment class, every Achievment in the game is an object of this class
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public class Achiev {
	private String		key;
	private String		description;
	private String		name;

	private final int	COUNTER_TARGET;
	private int			counter;


	protected Achiev(final String key, final String name, final String desc, final int countTarget) {
		this.key = key;
		this.name = name;
		this.description = desc;
		this.COUNTER_TARGET = countTarget;
	}

	public Achiev(final String key, final String name, final String desc) {
		this(key, name, desc, 1);
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getKey() {
		return key;
	}

	public void addCount() {
		counter++;
	}

	public boolean achieved() {
		return counter >= COUNTER_TARGET;
	}

	@Override
	public String toString() {
		String count = "";
		if (COUNTER_TARGET > 1) {
			count = " [" + COUNTER_TARGET + "]";
		}

		return "Achievment \"" + name + "\"" + count + ": " + description;
	}
}
