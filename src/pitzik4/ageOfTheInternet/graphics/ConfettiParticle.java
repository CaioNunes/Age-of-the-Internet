package pitzik4.ageOfTheInternet.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import pitzik4.ageOfTheInternet.RenderableTickable;

public class ConfettiParticle implements RenderableTickable {
	private Color color = Color.WHITE;
	private boolean goingRight = false;
	private boolean goingUp = false;
	private boolean moving = false;
	private int positionX, positionY;
	private static final Random randomNumber = new Random();
	public static final Color[] colorfulColors = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.CYAN };
	public static final int INVERSE_DIR_SWITCH_PROB = 8;
	public static final int INVERSE_GO_UP_PROB = 8;

	public ConfettiParticle(Color color, int x, int y, boolean goingRight) {
		this.color = color;
		this.positionX = x;
		this.positionY = y;
		this.goingRight = goingRight;
	}

	public ConfettiParticle(Color color, int x, int y) {
		this(color, x, y, randomNumber.nextBoolean());
	}

	public ConfettiParticle(int x, int y, boolean goingRight) {
		this(colorfulColors[randomNumber.nextInt(colorfulColors.length - 1)], x, y, goingRight);
	}

	public ConfettiParticle(int x, int y) {
		this(x, y, randomNumber.nextBoolean());
	}

	@Override
	public BufferedImage draw() {
		BufferedImage bufferedImageOut = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = bufferedImageOut.createGraphics();
		graphics.setColor(color);
		graphics.fillRect(0, 0, 1, 1);
		graphics.dispose();
		return bufferedImageOut;
	}

	@Override
	public void drawOn(Graphics2D graphics, int scrollx, int scrolly) {
		graphics.setColor(color);
		graphics.fillRect(positionX - scrollx, positionY - scrolly, 1, 1);
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

	@Override
	public void tick() {
		if (moving) {
			if (goingRight) {
				goTo(positionX + 1, positionY);
			} else {
				goTo(positionX - 1, positionY);
			}
			if (goingUp) {
				goingUp = false;
				goTo(positionX, positionY - 1);
			} else {
				goTo(positionX, positionY + 1);
			}
			if (randomNumber.nextInt(INVERSE_DIR_SWITCH_PROB) == 0) {
				goingRight = !goingRight;
			} else {
				// nothing
			}
			if (randomNumber.nextInt(INVERSE_GO_UP_PROB) == 0) {
				goingUp = true;
			} else {
				// nothing
			}
		}
		moving = !moving;
	}

}
