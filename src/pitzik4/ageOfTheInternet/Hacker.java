package pitzik4.ageOfTheInternet;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import pitzik4.ageOfTheInternet.graphics.Sprite;

public class Hacker implements RenderableTickable {
	private Sprite sprite;
	private int positionX = 0;
	private int positionY = 0;
	public boolean going = false;
	private Point[] path;
	private int progress = 0;
	public static final int STEP_SIZE = 3;

	public Hacker(int positionX, int positionY, Point[] path) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.path = path;
		sprite = new Sprite(81, positionX, positionY, false);
	}

	@Override
	public BufferedImage draw() {
		return sprite.draw();
	}

	@Override
	public void drawOn(Graphics2D graphics, int scrollx, int scrolly) {
		assert (graphics != null) : "The parameter graphics is null !";

		sprite.drawOn(graphics, scrollx, scrolly);

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
		this.positionX = positionX;
		this.positionY = positionY;
		sprite.goTo(positionX, positionY);
	}

	@Override
	public void tick() {
		if (going) {
			int nextPositionX = path[progress].x;
			int nextpositionY = path[progress].y;

			if (nextPositionX > positionX) {
				goTo(positionX + STEP_SIZE, positionY);
				if (nextPositionX < positionX) {
					goTo(nextPositionX, positionY);
				} else {
					// nothing to do
				}
			} else if (nextPositionX < positionX) {
				goTo(positionX - STEP_SIZE, positionY);
				if (nextPositionX > positionX) {
					goTo(nextPositionX, positionY);
				} else {
					// nothing to do
				}
			}

			if (nextpositionY > positionY) {
				goTo(positionX, positionY + STEP_SIZE);
				if (nextpositionY < positionY) {
					goTo(positionX, nextpositionY);
				} else {
					// nothing to do
				}
			} else if (nextpositionY < positionY) {
				goTo(positionX, positionY - STEP_SIZE);
				if (nextpositionY > positionY) {
					goTo(positionX, nextpositionY);
				} else {
					// nothing to do
				}
			}

			if (nextPositionX == positionX && nextpositionY == positionY) {
				progress++;
				if (progress >= path.length) {
					going = false;
				} else {
					// nothing to do
				}
			} else {
				// nothing to do
			}
		}
	}

	// Gamer playing or not
	public void go() {
		going = true;
	}

	public void stop() {
		going = false;
	}

}
