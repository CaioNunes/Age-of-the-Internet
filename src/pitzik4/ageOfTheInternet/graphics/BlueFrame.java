package pitzik4.ageOfTheInternet.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

//Objective: This class is responsible for rendering the blue screen when the player die or the background not is utilized.

public class BlueFrame implements Renderable {
	private static final int[][] BG_SPRITES = { { 192, 193, 194 }, { 224, 225, 226 }, { 256, 257, 258 } };
	private static final int BG_SPRITE_SIZE = 8;
	private Sprite[][] background;
	private int width = 0;
	private int height = 0;
	private int positionX = 0;
	private int positionY = 0;

	public BlueFrame(int x, int y, int width, int height) {
		setPositionX(x);
		setPositionY(y);
		setWidth(width);
		setHeight(height);
		Sprite[][] background = new Sprite[width / getBgSpriteSize()][height / getBgSpriteSize()]; 
		setBackground(background);
		
		assert(background != null): "background not instantiated";
		
		for (int i = 0; i < width; i += getBgSpriteSize()) {
			for (int j = 0; j < height; j += getBgSpriteSize()) {
				int whichx;
				int whichy;
				if (i == 0) {
					whichy = 0;
				} else if (i == width - getBgSpriteSize()) {
					whichy = 2;
				} else {
					whichy = 1;
				}
				if (j == 0) {
					whichx = 0;
				} else if (j == height - getBgSpriteSize()) {
					whichx = 2;
				} else {
					whichx = 1;
				}
		
				background[i / getBgSpriteSize()][j / getBgSpriteSize()] = new Sprite(getBgSprites()[whichx][whichy], i + x,
						j + x, Screen.spritesheet, getBgSpriteSize(), getBgSpriteSize());
			}
		}
	}

	public Sprite[][] getBackground() {
		return background;
	}

	public void setBackground(Sprite[][] background) {
		this.background = background;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public static int[][] getBgSprites() {
		return BG_SPRITES;
	}

	public static int getBgSpriteSize() {
		return BG_SPRITE_SIZE;
	}

	@Override
	public BufferedImage draw() {
		BufferedImage bufferedImageOut = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		assert(bufferedImageOut != null): "BufferedImageOut not instantiated";
		
		Graphics2D graphics = bufferedImageOut.createGraphics();
		assert(graphics != null): "graphics not created";
		
		drawOn(graphics, getPositionX(), getPositionY());
		graphics.dispose();
		
		assert(bufferedImageOut != null): "Return BufferedImageOut is null";
		return bufferedImageOut;
	}

	@Override
	public void drawOn(Graphics2D g, int scrollx, int scrolly) {
		assert(g != null): "Graphics2D-g parameter is null";
		assert(getBackground() != null): "Background var is null";
		for (Sprite[] ss : getBackground()) {
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

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
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
		int dx = x - getPositionX();
		int dy = y - getPositionY();
		setPositionX(x);
		setPositionY(y);
		assert(getBackground() != null): "Background var is null";
		for (Sprite[] ss : getBackground()) {
			assert(ss != null): "ss var is null";
			for (Sprite s : ss) {
				assert(s != null): "s var is null";
				s.goTo(s.getX() + dx, s.getY() + dy);
			}
		}
	}

}
