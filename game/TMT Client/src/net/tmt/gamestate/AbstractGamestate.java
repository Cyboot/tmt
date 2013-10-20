package net.tmt.gamestate;

import net.tmt.game.Renderable;
import net.tmt.game.Updateable;
import net.tmt.gfx.Graphics;

public abstract class AbstractGamestate implements Updateable, Renderable {

	@Override
	public abstract void update(final double delta);

	@Override
	public abstract void render(Graphics g);

}
