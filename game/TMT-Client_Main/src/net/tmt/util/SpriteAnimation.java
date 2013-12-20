package net.tmt.util;

import java.util.ArrayList;
import java.util.List;

import net.tmt.gfx.Sprite;

public class SpriteAnimation {

	private List<Sprite>	frames		= new ArrayList<Sprite>();
	private CountdownTimer	timer;
	private boolean			looping;
	private boolean			paused		= false;
	private int				currFrame	= 0;

	public SpriteAnimation(final List<Sprite> frames, final double framerate, final boolean looping) {
		this.frames = frames;
		this.timer = new CountdownTimer(framerate);
		this.looping = looping;
	}

	public SpriteAnimation(final List<Sprite> frames, final double framerate) {
		this(frames, framerate, true);
	}

	public SpriteAnimation(final String[] frameNames, final double framerate, final boolean looping) {
		this(new ArrayList<Sprite>(), framerate, looping);
		for (String n : frameNames) {
			this.frames.add(new Sprite(n));
		}
	}

	public SpriteAnimation(final String[] frameNames, final double framerate) {
		this(frameNames, framerate, true);
	}

	public void pause() {
		this.paused = true;
	}

	public void resume() {
		this.paused = false;
	}

	public Sprite getFrame(final double delta) {
		if (paused)
			return frames.get(0);
		if (timer.isTimeUp(delta)) {
			int max = frames.size();
			if (looping)
				currFrame = (currFrame + 1) % max;
			else
				currFrame = (currFrame < max ? currFrame++ : max);
		}
		return frames.get(currFrame);
	}
}
