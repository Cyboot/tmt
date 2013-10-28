package net.tmt.gfx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Sprite {
	private static final Map<String, Texture>	textureMap	= new HashMap<String, Texture>();

	private Texture								texture;
	private double								rotation;
	private double								width;
	private double								height;
	private boolean								centered	= true;
	private Color								blendColor	= new Color(255, 255, 255, 255);


	public Sprite(final String imgname) {
		texture = textureMap.get(imgname + ".png");
		this.width = texture.getImageWidth();
		this.height = texture.getImageHeight();
	}

	public Sprite(final String imgname, final int width, final int height) {
		this(imgname);

		this.width = width;
		this.height = height;
	}

	public boolean isCentered() {
		return centered;
	}

	public Sprite setCentered(final boolean centered) {
		this.centered = centered;
		return this;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	protected Texture getTexture() {
		return texture;
	}

	public void rotate(final double rot) {
		rotation += rot;
		rotation %= 360;
	}

	public Sprite setRotation(final double rotation) {
		this.rotation = rotation;
		return this;
	}


	/**
	 * load all Images from Imagesfolder
	 * 
	 * @throws IOException
	 */
	public static void init() throws IOException {
		File folder = new File("res" + File.separator + "img");

		for (final File file : folder.listFiles()) {
			if (!file.isDirectory() && file.getName().contains(".png")) {
				FileInputStream fileIN = new FileInputStream(file);
				textureMap.put(file.getName(), TextureLoader.getTexture("PNG", fileIN));
			}
		}
	}

	public double getRotation() {
		return rotation;
	}

	public Color getBlendColor() {
		return blendColor;
	}

	public Sprite setBlendColor(final Color blendColor) {
		this.blendColor = blendColor;
		return this;
	}
}
