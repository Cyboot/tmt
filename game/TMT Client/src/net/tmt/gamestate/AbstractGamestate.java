package net.tmt.gamestate;

import net.tmt.gfx.Graphics;

public abstract class AbstractGamestate {

	public abstract void update(final double delta);

	public abstract void render(Graphics g);

}
