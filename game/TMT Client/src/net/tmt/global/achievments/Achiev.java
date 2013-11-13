package net.tmt.global.achievments;

/**
 * Achievment class, every Achievment in the game is an object of this class
 * 
 * @author Tim Schmiedl (Cyboot)
 */
public class Achiev {
	private String		description;
	private String		name;

	private final int	COUNTER_TARGET;
	private int			counter;


	protected Achiev(final String name, final String desc, final int countTarget) {
		this.name = name;
		this.description = desc;
		this.COUNTER_TARGET = countTarget;
	}

	public Achiev(final String name, final String desc) {
		this(name, desc, 1);
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
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
