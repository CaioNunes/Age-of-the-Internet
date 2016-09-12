package pitzik4.ageOfTheInternet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pitzik4.ageOfTheInternet.graphics.Renderable;
import pitzik4.ageOfTheInternet.graphics.RenderableString;
import pitzik4.ageOfTheInternet.graphics.Sprite;

public class Button implements Renderable, Tickable {
	private Game owner;
	private Sprite[] sprites;
	private RenderableString renderableString;
	private int positionX = 0, positionY = 0;
	private int width;
	public boolean isClicked = false;
	public boolean isScrolled = false;
	public boolean nowClicked = false;
	public static final int BUTTON_SPRITE = 50;

	public Button(Game owner, int positionX, int positionY, int width, String textRenderable) {
		this.owner = owner;
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		sprites = new Sprite[width / Sprite.SPRITE_WIDTH];
		for (int i = 0; i < width / Sprite.SPRITE_WIDTH; i++) {
			if (i == 0) {
				sprites[i] = new Sprite(BUTTON_SPRITE, positionX, positionY, false);
			} else if (i == width / Sprite.SPRITE_WIDTH - 1) {
				sprites[i] = new Sprite(BUTTON_SPRITE + 2, positionX + i * Sprite.SPRITE_WIDTH, positionY, false);
			} else {
				sprites[i] = new Sprite(BUTTON_SPRITE + 1, positionX + i * Sprite.SPRITE_WIDTH, positionY, false);
			}
		}
		renderableString = new RenderableString(textRenderable, 0, 0);
		int stringWidth = renderableString.width;
		renderableString.goTo(positionX + (width - stringWidth) / 2, positionY + 2);
	}

	@Override
	public BufferedImage draw() {
		BufferedImage bufferedImageOut = new BufferedImage(width, Sprite.SPRITE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImageOut.createGraphics();
		drawOn(graphics, positionX, positionY);
		graphics.dispose();
		return bufferedImageOut;
	}

	@Override
	public void drawOn(Graphics2D graphics, int scrollx, int scrolly) {
		for (Sprite sprite : sprites) {
			sprite.drawOn(graphics, scrollx, scrolly);
		}
		renderableString.drawOn(graphics, scrollx, scrolly);
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
	public void goTo(int positionX, int positionY) {
		int dx = positionX - this.positionX;
		int dy = positionY - this.positionY;
		this.positionX = positionX;
		this.positionY = positionY;
		for (Sprite sprite : sprites) {
			sprite.goTo(sprite.getX() + dx, sprite.getY() + dy);
		}
		renderableString.goTo(renderableString.getX() + dx, renderableString.getY() + dy);
	}

	@Override
	public void tick() {
		if (owner.mouseInsideOf(positionX, positionY, width, Sprite.SPRITE_HEIGHT)) {
			beScrolled(true);
			boolean wasClicked = isClicked;
			if (owner.mouseDown) {
				beClicked(true);
			} else {
				beClicked(false);
			}
			if (wasClicked && !isClicked) {
				nowClicked = true;
			} else {
				nowClicked = false;
			}
		} else {
			beClicked(false);
			beScrolled(false);
		}
	}

	public void beScrolled(boolean newScrolled) {
		if (isScrolled != newScrolled) {
			isScrolled = newScrolled;
			if (isScrolled) {
				for (int i = 0; i < sprites.length; i++) {
					if (i == 0) {
						sprites[i] = new Sprite(BUTTON_SPRITE + 3, positionX, positionY, false);
					} else if (i == width / Sprite.SPRITE_WIDTH - 1) {
						sprites[i] = new Sprite(BUTTON_SPRITE + 5, positionX + i * Sprite.SPRITE_WIDTH, positionY,
								false);
					} else {
						sprites[i] = new Sprite(BUTTON_SPRITE + 4, positionX + i * Sprite.SPRITE_WIDTH, positionY,
								false);
					}
				}
			} else {
				for (int i = 0; i < sprites.length; i++) {
					if (i == 0) {
						sprites[i] = new Sprite(BUTTON_SPRITE, positionX, positionY, false);
					} else if (i == width / Sprite.SPRITE_WIDTH - 1) {
						sprites[i] = new Sprite(BUTTON_SPRITE + 2, positionX + i * Sprite.SPRITE_WIDTH, positionY,
								false);
					} else {
						sprites[i] = new Sprite(BUTTON_SPRITE + 1, positionX + i * Sprite.SPRITE_WIDTH, positionY,
								false);
					}
				}
			}
		}
	}

	public void beClicked(boolean newClicked) {
		if (isClicked != newClicked) {
			isClicked = newClicked;
			if (isClicked) {
				for (int i = 0; i < sprites.length; i++) {
					if (i == 0) {
						sprites[i] = new Sprite(BUTTON_SPRITE + 6, positionX, positionY, false);
					} else if (i == width / Sprite.SPRITE_WIDTH - 1) {
						sprites[i] = new Sprite(BUTTON_SPRITE + 8, positionX + i * Sprite.SPRITE_WIDTH, positionY,
								false);
					} else {
						sprites[i] = new Sprite(BUTTON_SPRITE + 7, positionX + i * Sprite.SPRITE_WIDTH, positionY,
								false);
					}
				}
			} else {
				for (int i = 0; i < sprites.length; i++) {
					if (i == 0) {
						sprites[i] = new Sprite(BUTTON_SPRITE + 3, positionX, positionY, false);
					} else if (i == width / Sprite.SPRITE_WIDTH - 1) {
						sprites[i] = new Sprite(BUTTON_SPRITE + 5, positionX + i * Sprite.SPRITE_WIDTH, positionY,
								false);
					} else {
						sprites[i] = new Sprite(BUTTON_SPRITE + 4, positionX + i * Sprite.SPRITE_WIDTH, positionY,
								false);
					}
				}
			}
		}
	}

}
