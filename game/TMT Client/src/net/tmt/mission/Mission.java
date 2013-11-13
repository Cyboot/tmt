package net.tmt.mission;

import net.tmt.game.interfaces.Updateable;

public abstract class Mission implements Updateable {
	private boolean	isFinished	= false;
	private String	desc;
	private String	title;

	public Mission(final String title, final String desc) {
		this.title = title;
		this.desc = desc;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public String getTitle() {
		return title;
	}

	public String getDesc() {
		return desc;
	}

	protected void finish() {
		isFinished = true;
	}

	public abstract void start();
}
