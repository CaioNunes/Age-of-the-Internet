package pitzik4.ageOfTheInternet;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import pitzik4.ageOfTheInternet.graphics.Sprite;
import pitzik4.ageOfTheInternet.tiles.HackerTile;

public class Player implements RenderableTickable {
	private Sprite sprite;
	private int positionX = 0;
	private int positionY = 0;
	public boolean going = false;
	private Point[] path;
	private int progress = 0;

	public static final int stepSize() {
		return 3 + HackerTile.hackersOwned;
	}

	public Player(int x, int y, Point[] path) {
		this.positionX = x;
		this.positionY = y;
		this.path = path;
		sprite = new Sprite(80, x, y, false);
	}

	@Override
	public BufferedImage draw() {
		return sprite.draw();
	}

	@Override
	public void drawOn(Graphics2D g, int scrollx, int scrolly) {
		try {
			sprite.drawOn(g, scrollx, scrolly);
		} catch (NullPointerException graphicsNull) {
			JOptionPane.showMessageDialog(null, "Have an error to load graphics2D ! Restart the game ",
					"Error Graphics2D", JOptionPane.ERROR_MESSAGE);
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
		this.positionX = x;
		this.positionY = y;
		sprite.goTo(x, y);
	}

	@Override
	public void tick() {
		if (going) {
			int nextX = path[progress].x;
			int nextY = path[progress].y;

			if (nextX > positionX) {
				goTo(positionX + stepSize(), positionY);
				if (nextX < positionX) {
					goTo(nextX, positionY);
				}
			} else if (nextX < positionX) {
				goTo(positionX - stepSize(), positionY);
				if (nextX > positionX) {
					goTo(nextX, positionY);
				}
			}
			if (nextY > positionY) {
				goTo(positionX, positionY + stepSize());
				if (nextY < positionY) {
					goTo(positionX, nextY);
				}
			} else if (nextY < positionY) {
				goTo(positionX, positionY - stepSize());
				if (nextY > positionY) {
					goTo(positionX, nextY);
				}
			}

			if (nextX == positionX && nextY == positionY) {
				progress++;
				if (progress >= path.length) {
					going = false;
				}
			}
		}
	}

	public void go() {
		going = true;
	}

	public void stop() {
		going = false;
	}

}
