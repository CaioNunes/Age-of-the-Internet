package pitzik4.ageOfTheInternet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import pitzik4.ageOfTheInternet.graphics.BlueFrame;
import pitzik4.ageOfTheInternet.graphics.Renderable;
import pitzik4.ageOfTheInternet.graphics.RenderableString;

public class PauseScreen implements Renderable {
	private int positionX = 0;
	private int positionY = 0;
	private int width = 0;
	private int height = 0;
	private RenderableString message;
	private BlueFrame frame;

	public PauseScreen(final int x, final int y, final int width, final int height) {
		this(x, y, width, height, Game.game);
	}

	public PauseScreen(final int x, final int y, final int width, final int height, Game owner) {
		this.positionX = x;
		this.positionY = y;
		this.width = width;
		this.height = height;
		this.frame = new BlueFrame(x, y, width, height);
		this.message = new RenderableString("Game Paused", 0, 0);
		this.message.centerBetweenX(x, x + width);
		this.message.centerBetweenY(y, y + height);
	}

	@Override
	public BufferedImage draw() {
		BufferedImage tmp = new BufferedImage(width + positionX, height + positionY, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = tmp.createGraphics();
		drawOn(g, 0, 0);
		g.dispose();

		BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = out.createGraphics();
		g.drawImage(tmp, -positionX, -positionY, null);
		g.dispose();

		return out;
	}

	@Override
	public void drawOn(Graphics2D g, int scrollx, int scrolly) {
		try {
			scrollx = scrolly = 0;
			frame.drawOn(g, scrollx, scrolly);
			message.drawOn(g, scrollx, scrolly);
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
	public void goTo(final int x, final int y) {
		final int dx = x - this.positionX;
		final int dy = y - this.positionY;
		this.positionX = x;
		this.positionY = y;

		frame.goTo(frame.getX() + dx, frame.getY() + dy);
		message.goTo(message.getX() + dx, message.getY() + dy);
	}

}
