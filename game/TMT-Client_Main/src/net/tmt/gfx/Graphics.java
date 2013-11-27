package net.tmt.gfx;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import net.tmt.util.Vector2d;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

public class Graphics {
	private static final int		DEFAULT_LINE_WIDTH	= 1;

	private float					lineWidth			= DEFAULT_LINE_WIDTH;
	private ReadableColor			color				= Color.PURPLE;
	private TrueTypeFont			font;
	private boolean					onGui;
	private org.newdawn.slick.Color	slickColor			= new org.newdawn.slick.Color(0);
	private Vector2d				offset;

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
		if (!onGui)
			glTranslated(-offset.x, -offset.y, 0);
		onGui = false;

		// reset lineWidth to default width
		// lineWidth = DEFAULT_LINE_WIDTH;
	}

	public void setColor(final ReadableColor color) {
		this.color = color;

		this.slickColor.r = color.getRed() / 255f;
		this.slickColor.g = color.getGreen() / 255f;
		this.slickColor.b = color.getBlue() / 255f;
		this.slickColor.a = color.getAlpha() / 255f;
	}

	public void setOffset(final Vector2d offset) {
		this.offset = offset;
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

	public void drawCircle(final double centerX, final double centerY, final double radius) {
		// TODO better performance for cicles
		applyColor();
		int sides = 32;

		glPushMatrix();
		{
			applyOffset();
			glTranslated(centerX, centerY, 0);
			glBegin(GL_LINE_LOOP);
			for (int a = 0; a < 360; a += 360 / sides) {
				double heading = Math.toRadians(a);

				glVertex2d(Math.cos(heading) * radius, Math.sin(heading) * radius);
			}
			glEnd();
		}
		glPopMatrix();
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

		sprite.setWidth(radius);
		sprite.setHeight(radius);
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
			font.drawString((float) x, (float) y, text, slickColor);
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


	private static Graphics	instance;

	public static Graphics init() {
		instance = new Graphics();
		Textures.rect = new Sprite("white").setCentered(false);
		Textures.whiteTexture = Textures.rect.getTexture();
		Textures.circle_fill_16 = new Sprite("circle_16").setCentered(false);
		Textures.circle_fill_256 = new Sprite("circle_256").setCentered(false);

		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("res" + File.separator + "gfx" + File.separator
					+ "CONTRA__.ttf"));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		Fonts.font_12_italic = new TrueTypeFont(font.deriveFont(Font.ITALIC, 12), true);
		Fonts.font_12_plain = new TrueTypeFont(font.deriveFont(Font.PLAIN, 12), true);
		Fonts.font_12_bold = new TrueTypeFont(font.deriveFont(Font.BOLD, 12), true);

		Fonts.font_14_italic = new TrueTypeFont(font.deriveFont(Font.ITALIC, 14), true);
		Fonts.font_14_plain = new TrueTypeFont(font.deriveFont(Font.PLAIN, 14), true);
		Fonts.font_14_bold = new TrueTypeFont(font.deriveFont(Font.BOLD, 14), true);

		Fonts.font_16_italic = new TrueTypeFont(font.deriveFont(Font.ITALIC, 16), true);
		Fonts.font_16_plain = new TrueTypeFont(font.deriveFont(Font.PLAIN, 16), true);
		Fonts.font_16_bold = new TrueTypeFont(font.deriveFont(Font.BOLD, 16), true);

		Fonts.font_18_italic = new TrueTypeFont(font.deriveFont(Font.ITALIC, 18), true);
		Fonts.font_18_plain = new TrueTypeFont(font.deriveFont(Font.PLAIN, 18), true);
		Fonts.font_18_bold = new TrueTypeFont(font.deriveFont(Font.BOLD, 18), true);

		Fonts.font_26_italic = new TrueTypeFont(font.deriveFont(Font.ITALIC, 26), true);
		Fonts.font_26_plain = new TrueTypeFont(font.deriveFont(Font.PLAIN, 26), true);
		Fonts.font_26_bold = new TrueTypeFont(font.deriveFont(Font.BOLD, 26), true);

		Fonts.font_default = Fonts.font_12_plain;
		instance.font = Fonts.font_default;

		return instance;
	}

	public static Graphics getInstance() {
		return instance;
	}

	public static class Fonts {
		public static TrueTypeFont	font_default;

		public static TrueTypeFont	font_12_plain;
		public static TrueTypeFont	font_12_bold;
		public static TrueTypeFont	font_12_italic;

		public static TrueTypeFont	font_14_plain;
		public static TrueTypeFont	font_14_bold;
		public static TrueTypeFont	font_14_italic;

		public static TrueTypeFont	font_16_plain;
		public static TrueTypeFont	font_16_bold;
		public static TrueTypeFont	font_16_italic;

		public static TrueTypeFont	font_18_plain;
		public static TrueTypeFont	font_18_bold;
		public static TrueTypeFont	font_18_italic;

		public static TrueTypeFont	font_26_plain;
		public static TrueTypeFont	font_26_bold;
		public static TrueTypeFont	font_26_italic;
	}

	private static class Textures {
		public static Texture	whiteTexture;
		public static Sprite	rect;
		public static Sprite	circle_fill_16;
		public static Sprite	circle_fill_256;
	}
}
