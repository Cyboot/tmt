package net.tmt.gui;

import net.tmt.gfx.Graphics;
import net.tmt.gui.elements.TextView;
import net.tmt.util.Vector2d;

public class DummyGui extends Gui {
	TextView	textview	= new TextView(new Vector2d(300, 300), 300, 100);

	public DummyGui() {
	}

	@Override
	public void update(final double delta) {
		textview.setText("Lorem ipsum \n dolor sit amet, consectetur adipiscing elit. Sed sodales porttitor lorem. "
				+ "In tincidunt pretium sem, id accumsan erat tempor id. Phasellus imperdiet consequat pharetra. "
				+ "Donec aliquet ei massa et commodo. Nunc placerat lacinia scelerisque. Donec quis malesuada nulla. "
				+ "Aliquam id sagittis leo. Nam congue rhoncus est, et vestibulum mi. "
				+ "Suspendisse fermentum, mauris ut auctor gravida, nulla nulla eleifend neque, vitae pellentesque massa neque in urna. "
				+ "Phasellus volutpat lectus eu eros ultricies, at dapibus libero lacinia. "
				+ "Proin libero ante, pellentesque at est eu, consequat adipiscing metus. Etiam blandit ipsum in elit rhoncus fermentum. "
				+ "Cras massa diam, cursus in enim nec, venenatis pellentesque lorem. Fusce magna augue, tristique at elit at, rutrum egestas justo. "
				+ "Nulla facilisi. Vestibulum vel turpis sit amet sapien porta vestibulum. Vivamus rutrum vehicula faucibus. "
				+ "Duis ut facilisis purus, vel tincidunt enim. Integer eget lacus molestie lectus iaculis ultrices vitae id mauris. "
				+ "Ut congue magna et metus tristique, at pretium ante sodales. ");
		gameStateToolbar.update(delta);
	}

	@Override
	public void render(final Graphics g) {
		gameStateToolbar.render(g);
		// textview.render(g);
	}
}
