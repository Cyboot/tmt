package net.tmt.gui.elements;

import net.tmt.gfx.Sprite;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;

public class MoveCompositeElement extends CompositeElement implements ClickListener {
	private Button	moveButton;

	public MoveCompositeElement(final Vector2d pos, final double width, final double height) {
		super(pos, width, height);

		moveButton = new Button(new Vector2d(width - DEFAULT_PADDING * 2 - 32, 0), new Sprite("arrow_left"));
		moveButton.setBorderColor(new Color(0, 0, 0, 0));
		moveButton.setBackgroundColor(new Color(0, 0, 0, 0));
		moveButton.addListener(this);

		addGuiElement(moveButton);
	}

	@Override
	public void onClick(final GuiElement caller) {
		System.out.println("comp click");
		this.setPos(new Vector2d(pos).add(-50, 0));
	}

}