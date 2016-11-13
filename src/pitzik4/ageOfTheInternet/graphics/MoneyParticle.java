package pitzik4.ageOfTheInternet.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pitzik4.ageOfTheInternet.RenderableTickable;

public class MoneyParticle implements RenderableTickable {
	private int lifetime = 0;
	private int positionX = 0;
	private int positionY = 0;
	private Sprite sprite;
	public boolean dead = false;
	public static final int LIFE_SPAN = 20;

	public MoneyParticle(int x, int y) {
		this.positionX = x;
		this.positionY = y;
		sprite = new Sprite(82, x, y, false);
	}

	@Override
	public BufferedImage draw() {
		return sprite.draw();
	}

	@Override
	public void drawOn(Graphics2D g, int scrollx, int scrolly) {
		assert (g != null) : "Parameter Graphics2D is null";
		sprite.drawOn(g, scrollx, scrolly);
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
	public void goTo(int newPositionX, int newPositionY) {
		this.positionX = newPositionX;
		this.positionY = newPositionY;
		sprite.goTo(newPositionX, newPositionY);
	}

	@Override
	public void tick() {
		lifetime++;
		if (lifetime > LIFE_SPAN) {
			dead = true;
		} else {
			// nothing
		}
		goTo(positionX, positionY - 1);
	}

}
