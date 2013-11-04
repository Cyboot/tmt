package net.tmt.gui.elements;

import java.util.ArrayList;
import java.util.List;

import net.tmt.gfx.Graphics;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class CompositeElement extends GuiElement {

	protected List<GuiElement>	gElementList	= new ArrayList<>();

	public CompositeElement(final Vector2d pos, final Color borderColor, final double width, final double height) {
		super(pos, borderColor, height, width);
	}

	@Override
	public void update(final Vector2d offset, final double delta) {
		for (GuiElement guiElement : gElementList) {
			guiElement.update(pos, delta);
		}
	}

	@Override
	public void render(final Graphics g) {
		for (GuiElement guiElement : gElementList) {
			guiElement.render(g);
		}
	}


	public void addGuiElement(final GuiElement guiElement) {
		gElementList.add(guiElement);
	}

}
