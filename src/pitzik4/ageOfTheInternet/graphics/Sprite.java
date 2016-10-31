package pitzik4.ageOfTheInternet.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

//Objective: This class is responsible to provide support for rendering the sprites in game.

public class Sprite implements Renderable {
	private int positionX = 0;
	private int positionY = 0;
	private BufferedImage image;
	public static final int SPRITE_WIDTH = 16;
	public static final int SPRITE_HEIGHT = 16;
	public static final int LETTER_WIDTH = 8;
	public static final int LETTER_HEIGHT = 12;

	public Sprite(int which, int x, int y, BufferedImage spritesheet, int width, int height) {
		assert(spritesheet != null): "Paramater BufferedImage is null";
		this.positionX = x;
		this.positionY = y;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();
		graphics.drawImage(spritesheet, 0 - ((which * width) % spritesheet.getWidth()),
				0 - (((which) / (spritesheet.getWidth() / width)) * height), null);
		graphics.dispose();
	}

	public Sprite(int which, int x, int y, boolean letter) {
		this(which, x, y, (letter ? Screen.font : Screen.spritesheet), letter ? LETTER_WIDTH : SPRITE_WIDTH,
				letter ? LETTER_HEIGHT : SPRITE_HEIGHT);
	}

	public Sprite(int which) {
		this(which, 0, 0, false);
	}

	@Override
	public BufferedImage draw() {
		assert(image != null): "Paramater BufferedImage is null";
		return image;
	}

	@Override
	public void drawOn(Graphics2D g, int scrollx, int scrolly) {
		assert(g != null): "Paramater Graphics2D is null";
		g.drawImage(image, positionX - scrollx, positionY - scrolly, null);
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
		this.positionX = x;
		this.positionY = y;
	}

	public void mergeSprite(Sprite spr) {
		assert(spr != null): "Paramater Sprite is null";
		Graphics2D graphics = image.createGraphics();
		graphics.drawImage(spr.draw(), 0, 0, null);
		graphics.dispose();
	}

}
