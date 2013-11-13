package net.tmt.gui.view;

import net.tmt.gfx.Graphics;
import net.tmt.gui.elements.ContainerElement;
import net.tmt.gui.elements.TextView;
import net.tmt.util.Vector2d;

public class AchievmentView extends ContainerElement {
	private TextView	textview_name;
	private TextView	textview_desc;

	public AchievmentView() {
		super(new Vector2d(), 150, 50);

		textview_name = new TextView(new Vector2d(DEFAULT_PADDING, DEFAULT_PADDING), 150 - 2 * DEFAULT_PADDING, 15);
		textview_desc = new TextView(new Vector2d(DEFAULT_PADDING, 20 + DEFAULT_PADDING), 150 - 2 * DEFAULT_PADDING, 30);

		textview_name.noBackground();
		textview_desc.noBackground();
		addGuiElement(textview_name);
		addGuiElement(textview_desc);
	}

	public void setAchievName(final String name) {
		textview_name.setText(name);
	}

	public void setAchievDesc(final String desc) {
		textview_desc.setText(desc);
	}

	@Override
	public void render(final Graphics g) {
		width = Math.max(textview_name.getWidth(), textview_desc.getWidth()) + 4 * DEFAULT_PADDING;

		super.render(g);
	}
}
