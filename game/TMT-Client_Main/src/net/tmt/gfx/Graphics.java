package net.tmt.gfx;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.tmt.game.manager.ZoomManager;
import net.tmt.main.LoaderTimer;
import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

public class Graphics {
	private double					scale				= 1;
	private static final int		DEFAULT_LINE_WIDTH	= 1;

	private float					lineWidth			= DEFAULT_LINE_WIDTH;
	private ReadableColor			color				= Color.PURPLE;
	private TrueTypeFont			font;
	private boolean					onGui;
	private org.newdawn.slick.Color	slickColor			= new org.newdawn.slick.Color(0);
	private Vector2d				offset				= new Vector2d();

	public void drawSprite(final Vector2d pos, final Sprite sprite) {
		sprite.getTexture().bind();

		double widthHALF = sprite.getWidth() / 2;
		double heightHALF = sprite.getHeight() / 2;
		double x = pos.x;
		double y = pos.y;
		if (!sprite.isCentered()) {
			x += widthHALF;
			y += heightHALF;
		}

		Color c = sprite.getBlendColor();
		glColor4d(c.getRed() / 255., c.getGreen() / 255., c.getBlue() / 255., c.getAlpha() / 255.);

		glPushMatrix();
		{
			applyOffset();
			glTranslated(x, y, 0);
			glRotated(sprite.getRotation(), 0, 0, 1);
			{
				glBegin(GL_QUADS);
				{
					glTexCoord2d(0, 1);
					glVertex2d(-widthHALF, heightHALF);

					glTexCoord2d(0, 0);
					glVertex2d(-widthHALF, -heightHALF);

					glTexCoord2d(1, 0);
					glVertex2d(widthHALF, -heightHALF);

					glTexCoord2d(1, 1);
					glVertex2d(widthHALF, heightHALF);
				}
				glEnd();
			}
		}
		glPopMatrix();
	}

	public void drawShape(final Vector2d pos, final Shape shape) {
		applyColor();

		Vector2d shapeOffset = shape.getOffset();

		glLineWidth(lineWidth);
		glPushMatrix();
		{
			applyOffset();
			glTranslated(pos.x, pos.y, 0);
			glTranslated(-shapeOffset.x, -shapeOffset.y, 0);
			glScaled(shape.getScale(), shape.getScale(), 1);
			glRotated(shape.getRotation(), 0, 0, 1);
			{
				glBegin(GL_LINE_STRIP);
				{
					for (Vector2d v : shape.getPoints())
						glVertex2d(v.x, v.y);

					Vector2d last = shape.getFirstPoint();
					glVertex2d(last.x, last.y);
				}
				glEnd();
			}
		}
		glPopMatrix();
	}

	private void applyOffset() {
		// ignore offset if onGui == true
		if (!onGui) {
			glScaled(scale, scale, scale);
			glTranslated(-offset.x, -offset.y, 0);
		}
		onGui = false;
	}

	public void setColor(final ReadableColor color) {
		this.color = color;

		this.slickColor.r = color.getRed() / 255f;
		this.slickColor.g = color.getGreen() / 255f;
		this.slickColor.b = color.getBlue() / 255f;
		this.slickColor.a = color.getAlpha() / 255f;
	}

	public void setOffset(final Vector2d offset) {
		this.offset.set(offset).sub(ZoomManager.getWidthZoomed() / 2, ZoomManager.getHeightZoomed() / 2);
	}

	public void setLineWidth(final int width) {
		lineWidth = width;
	}

	public void drawRect(final double x, final double y, final double with, final double height) {
		applyColor();


		glLineWidth(lineWidth);
		glPushMatrix();
		{
			applyOffset();
			glTranslated(x, y, 0);
			{
				glBegin(GL_LINE_STRIP);
				{
					glVertex2d(0, height);
					glVertex2d(0, 0);
					glVertex2d(with, 0);
					glVertex2d(with, height);
					glVertex2d(0, height);
				}
				glEnd();
			}
		}
		glPopMatrix();
	}

	public void fillRect(final double x, final double y, final double width, final double height) {
		Sprite sprite = Textures.rect;

		sprite.setWidth(width);
		sprite.setHeight(height);
		sprite.setBlendColor((Color) color);

		drawSprite(Vector2d.tmp1.set(x, y), sprite);
	}

	public void drawCircle(final double x, final double y, final double radius) {
		Sprite sprite;
		if (radius > 16)
			sprite = Textures.circle_256;
		else
			sprite = Textures.circle_16;

		sprite.setCentered(true);
		sprite.setWidth(radius * 2);
		sprite.setHeight(radius * 2);
		sprite.setBlendColor((Color) color);

		drawSprite(Vector2d.tmp1.set(x, y), sprite);
	}

	public void drawLine(final double x1, final double y1, final double x2, final double y2) {
		applyColor();

		glLineWidth(lineWidth);
		glPushMatrix();
		{
			applyOffset();
			{
				glBegin(GL_LINES);
				{
					glVertex2d(x1, y1);
					glVertex2d(x2, y2);
				}
				glEnd();
			}
		}
		glPopMatrix();
	}

	public void fillCircle(final double x, final double y, final float radius) {
		Sprite sprite;
		if (radius > 16)
			sprite = Textures.circle_fill_256;
		else
			sprite = Textures.circle_fill_16;

		sprite.setCentered(true);
		sprite.setWidth(radius * 2);
		sprite.setHeight(radius * 2);
		sprite.setBlendColor((Color) color);

		drawSprite(Vector2d.tmp1.set(x, y), sprite);
	}

	public void drawPoint(final double centerX, final double centerY, final float size) {
		applyColor();

		glPointSize(size);

		glPushMatrix();
		{
			applyOffset();
			glBegin(GL_POINTS);
			{
				glVertex2d(centerX, centerY);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public void drawText(final double x, final double y, final String text) {
		glPushMatrix();
		{
			applyOffset();
			font.drawString((int) x, (int) y, text, slickColor);
		}
		glPopMatrix();
	}

	public void setFont(final TrueTypeFont font) {
		this.font = font;
	}

	private void applyColor() {
		Textures.whiteTexture.bind();
		glColor4d(color.getRed() / 255., color.getGreen() / 255., color.getBlue() / 255., color.getAlpha() / 255.);
	}

	/**
	 * Render absolut to screen dimension, not relative to World (ignore
	 * World-offset) An Example would be the Gui.
	 * 
	 * @return
	 */
	public Graphics onGui() {
		onGui = true;
		return this;
	}


	public void setScale(final double scale) {
		this.scale = scale;
	}

	private static Graphics	instance;

	public static Graphics getInstance() {
		return instance;
	}

	public static Graphics init() {
		LoaderTimer.start("Graphics");
		instance = new Graphics();
		Textures.rect = new Sprite("white").setCentered(false);
		Textures.whiteTexture = Textures.rect.getTexture();
		Textures.circle_16 = new Sprite("circle_16").setCentered(false);
		Textures.circle_256 = new Sprite("circle_256").setCentered(false);
		Textures.circle_fill_16 = new Sprite("circle_fill_16").setCentered(false);
		Textures.circle_fill_256 = new Sprite("circle_fill_256").setCentered(false);

		try {
			Fonts.awtFont = Font.createFont(Font.TRUETYPE_FONT, new File("res" + File.separator + "gfx"
					+ File.separator + "NeogreyRegular.otf"));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		Fonts.defaultFont = Fonts.get(12);
		instance.font = Fonts.defaultFont;

		LoaderTimer.stop("Graphics");
		return instance;
	}

	public static class Fonts {
		private static Font							awtFont;
		private static Map<Integer, TrueTypeFont>	fontMap	= new HashMap<>();


		public static TrueTypeFont					defaultFont;

		public enum Style {
			PLAIN(Font.PLAIN), BOLD(Font.BOLD), ITALIC(Font.ITALIC);
			private int	value;

			private Style(final int value) {
				this.value = value;
			}
		}

		public static TrueTypeFont get(final int size) {
			return get(size, Style.PLAIN);
		}

		public static TrueTypeFont get(final int size, final Style style) {
			int key = (style.value << 16) | size;
			TrueTypeFont font = fontMap.get(key);

			if (font == null) {
				font = new TrueTypeFont(awtFont.deriveFont(style.value, size), true);
				fontMap.put(key, font);
			}

			return font;
		}
	}

	private static class Textures {
		public static Texture	whiteTexture;
		public static Sprite	rect;
		public static Sprite	circle_fill_16;
		public static Sprite	circle_fill_256;
		public static Sprite	circle_16;
		public static Sprite	circle_256;
	}
}
