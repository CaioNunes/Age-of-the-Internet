package pitzik4.ageOfTheInternet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import pitzik4.ageOfTheInternet.graphics.BlueFrame;
import pitzik4.ageOfTheInternet.graphics.RenderableString;

public class LoseScreen implements RenderableTickable {
	private RenderableString youLose;
	private RenderableString loseReason;
	public Button retry;
	private BlueFrame blueFrame_bg;
	private int positionX = 0, positionY = 0;

	public LoseScreen(Game owner, final int x, final int y, final int width, final int height, final String reason) {
		this.positionX = x;
		this.positionY = y;

		blueFrame_bg = new BlueFrame(x, y, width, height);
		youLose = new RenderableString("Level Unsuccessful", 0, 0);
		youLose.goTo(x + ((width - youLose.width) / 2), y + (height / 3 - youLose.height / 2));
		loseReason = new RenderableString(reason, 0, 0);
		loseReason.goTo(x + ((width - loseReason.width) / 2), y + (height / 2 - loseReason.height / 2));
		try {
			retry = new Button(owner, x + width / 4, y + (height / 3) * 2, width / 2, "Retry");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public BufferedImage draw() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawOn(Graphics2D graphics, final int scrollx, final int scrolly) {
		try {
			blueFrame_bg.drawOn(graphics, scrollx, scrolly);
			youLose.drawOn(graphics, scrollx, scrolly);
			loseReason.drawOn(graphics, scrollx, scrolly);
			retry.drawOn(graphics, scrollx, scrolly);
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
	public void goTo(final int newPositionX, final int newPositionY) {
		final int dx = newPositionX - this.positionX;
		final int dy = newPositionY - this.positionY;
		this.positionX = newPositionX;
		this.positionY = newPositionY;
		youLose.goTo(youLose.getX() + dx, youLose.getY() + dy);
		loseReason.goTo(loseReason.getX() + dx, loseReason.getY() + dy);
		retry.goTo(retry.getX() + dx, retry.getY() + dy);
		blueFrame_bg.goTo(blueFrame_bg.getX() + dx, blueFrame_bg.getY() + dy);
	}

	@Override
	public void tick() {
		retry.tick();
	}

}
