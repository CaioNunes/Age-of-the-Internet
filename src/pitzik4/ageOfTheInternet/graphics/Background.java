package pitzik4.ageOfTheInternet.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pitzik4.ageOfTheInternet.Tickable;

public class Background implements Renderable, Tickable {
	private int positionX = 0;
	private int positionY = 0;
	private int width = 0;
	private Sprite[][] tiles;
	public static final byte MOVE_COUNTDOWN = 2;
	private byte moveCountdown = MOVE_COUNTDOWN;
	public static final int BG_SPRITE = 16;

	public Background(int x, int y, int width, int height) {
		setPositionX(x);
		setPositionY(y);
		setWidth(width);
		
		if (width % Sprite.SPRITE_WIDTH != 0) {
			throw new RuntimeException("Illegal width for Background: " + width);
		}
		
		if (height % Sprite.SPRITE_HEIGHT != 0) {
			throw new RuntimeException("Illegal height for Background: " + height);
		}
		
		setTiles(new Sprite[height / Sprite.SPRITE_HEIGHT][width / Sprite.SPRITE_WIDTH + 1]);
		
		for (int i = 0; i < width / Sprite.SPRITE_WIDTH + 1; i++) {
			for (int j = 0; j < height / Sprite.SPRITE_HEIGHT; j++) {
				getTiles()[j][i] = new Sprite(BG_SPRITE, x + (i - 1) * Sprite.SPRITE_WIDTH, y + j * Sprite.SPRITE_HEIGHT,
						false);
			}
		}
	}

	
	public byte getMoveCountdown() {
		return moveCountdown;
	}


	public void setMoveCountdown(byte moveCountdown) {
		this.moveCountdown = moveCountdown;
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
	public BufferedImage draw() {
		// TODO Add code here
		return null;
	}

	@Override
	public void drawOn(Graphics2D graphics, int scrollx, int scrolly) {
		assert(graphics != null): "Graphics var is null";
		assert(getTiles() != null): "Tiles var is null";
		for (Sprite[] ss : getTiles()) {
			assert(ss != null): "Sprite[] var is null";
			for (Sprite s : ss) {
				assert(s != null): "Sprite var is null";
				s.drawOn(graphics, 0, 0);
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
		int dx = x - getPositionX();
		int dy = y - getPositionY();
		setPositionX(x);
		setPositionY(y);
		assert(getTiles() != null): "Tiles var is null";
		for (Sprite[] ss : getTiles()) {
			assert(ss != null): "Sprite[] var is null";
			for (Sprite s : ss) {
				assert(s != null): "Sprite var is null";
				s.goTo(s.getX() + dx, s.getY() + dy);
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Sprite[][] getTiles() {
		return tiles;
	}

	public void setTiles(Sprite[][] tiles) {
		this.tiles = tiles;
	}


	public static int getBgSprite() {
		return BG_SPRITE;
	}

	@Override
	public void tick() {
		moveCountdown--;
		if (getMoveCountdown() == 0) {
			setMoveCountdown(MOVE_COUNTDOWN);
		
			assert(getTiles() != null): "Tiles var is null";
		
			for (int i = 0; i < getTiles().length; i++) {
				for (Sprite tiless : tiles[i]) {
					if (i % 2 == 0) {
						tiless.goTo(tiless.getX() + 1, tiless.getY());
						if (tiless.getX() > getPositionX() + getWidth()) {
							tiless.goTo(getPositionX() - Sprite.SPRITE_WIDTH + 1, tiless.getY());
						}
					} else {
						tiless.goTo(tiless.getX() - 1, tiless.getY());
						if (tiless.getX() < getPositionX() - Sprite.SPRITE_WIDTH) {
							tiless.goTo(getPositionX() + getWidth() - 1, tiless.getY());
						}
					}
				}
			}
		}
	}

}
