package pitzik4.ageOfTheInternet.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pitzik4.ageOfTheInternet.RenderableTickable;

public class ExplosionParticle implements RenderableTickable {
	private int lifetime = 0;
	private double direction;
	private double positionX = 0;
	private double positionY = 0;
	public boolean dead = false;
	public static final int LIFE_SPAN = 20;

	public ExplosionParticle(double x, double y, double direction) {
		this.positionX = x;
		this.positionY = y;
		this.direction = direction;
	}

	@Override
	public BufferedImage draw() {
		BufferedImage bufferedImageOut = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
		assert (bufferedImageOut != null) : "BufferedImageOut is null";
		Graphics2D graphics = bufferedImageOut.createGraphics();
		assert (graphics != null) : "graphics is null";
		drawOn(graphics, (int) Math.round(positionX), (int) Math.round(positionY));
		graphics.dispose();
		assert (bufferedImageOut != null) : "Return BufferedImageOut is null";
		return bufferedImageOut;
	}

	@Override
	public void drawOn(Graphics2D graphics, int scrollx, int scrolly) {
		assert (graphics != null) : "Graphics2D-graphics parameter is null";
		graphics.setPaint(new Color(0x60BFBF));
		graphics.fillRect((int) Math.round(positionX - scrollx), (int) Math.round(positionY - scrolly), 2, 2);
	}

	@Override
	public int getX() {
		return (int) Math.round(positionX);
	}

	@Override
	public int getY() {
		return (int) Math.round(positionY);
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

	@Override
	public void tick() {
		lifetime++;
		if (lifetime > LIFE_SPAN) {
			dead = true;
		}
		positionX += Math.cos(direction);
		positionY += Math.sin(direction);
	}

}
