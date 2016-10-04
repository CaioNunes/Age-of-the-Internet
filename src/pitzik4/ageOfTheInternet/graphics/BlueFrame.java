package pitzik4.ageOfTheInternet.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BlueFrame implements Renderable {
	private static final int[][] BG_SPRITES = { { 192, 193, 194 }, { 224, 225, 226 }, { 256, 257, 258 } };
	private static final int BG_SPRITE_SIZE = 8;
	private Sprite[][] background;
	private int width, height;
	private int positionX, positionY;

	public BlueFrame(int x, int y, int width, int height) {
		this.positionX = x;
		this.positionY = y;
		this.width = width;
		this.height = height;
		background = new Sprite[width / BG_SPRITE_SIZE][height / BG_SPRITE_SIZE];
		assert(background != null): "background not instantiated";
		for (int i = 0; i < width; i += BG_SPRITE_SIZE) {
			for (int j = 0; j < height; j += BG_SPRITE_SIZE) {
				int whichx;
				int whichy;
				if (i == 0) {
					whichy = 0;
				} else if (i == width - BG_SPRITE_SIZE) {
					whichy = 2;
				} else {
					whichy = 1;
				}
				if (j == 0) {
					whichx = 0;
				} else if (j == height - BG_SPRITE_SIZE) {
					whichx = 2;
				} else {
					whichx = 1;
				}
				background[i / BG_SPRITE_SIZE][j / BG_SPRITE_SIZE] = new Sprite(BG_SPRITES[whichx][whichy], i + x,
						j + x, Screen.spritesheet, BG_SPRITE_SIZE, BG_SPRITE_SIZE);
			}
		}
	}

	@Override
	public BufferedImage draw() {
		BufferedImage bufferedImageOut = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		assert(bufferedImageOut != null): "BufferedImageOut not instantiated";
		Graphics2D graphics = bufferedImageOut.createGraphics();
		assert(graphics != null): "graphics not created";
		drawOn(graphics, positionX, positionY);
		graphics.dispose();
		assert(bufferedImageOut != null): "Return BufferedImageOut is null";
		return bufferedImageOut;
	}

	@Override
	public void drawOn(Graphics2D g, int scrollx, int scrolly) {
		assert(g != null): "Graphics2D-g parameter is null";
		assert(background != null): "Background var is null";
		for (Sprite[] ss : background) {
			assert(ss != null): "ss var is null";
			for (Sprite s : ss) {
				assert(s != null): "s var is null";
				s.drawOn(g, scrollx, scrolly);
			}
		}
	}

	@Override
	public int getX() {
		return positionX;
	}

	@Override
	public int getY() {
		return positionY;
	}

	@Override
	public int getXOffset() {
		return 0;
	}

	@Override
	public int getYOffset() {
		return 0;
	}

	@Override
	public void goTo(int x, int y) {
		int dx = x - this.positionX;
		int dy = y - this.positionY;
		this.positionX = x;
		this.positionY = y;
		assert(background != null): "Background var is null";
		for (Sprite[] ss : background) {
			assert(ss != null): "ss var is null";
			for (Sprite s : ss) {
				assert(s != null): "s var is null";
				s.goTo(s.getX() + dx, s.getY() + dy);
			}
		}
	}

}
