package pitzik4.ageOfTheInternet;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class TitleScreen implements Stage {
	private Game owner;
	private BufferedImage title;
	private Button gameStartButton;
	private boolean gameStartButtonClicked = false;
	private boolean gameStarting = false;
	private boolean done = false;

	public TitleScreen(Game owner) {
		try {
			title = ImageIO.read(Game.class.getResourceAsStream("/title.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.owner = owner;
		gameStartButton = new Button(owner, 96, 160, 128, "Start Game");
	}

	@Override
	public void tick() {
		gameStartButton.tick();

		if (gameStartButton.isClicked)
			gameStartButtonClicked = true;

		if (gameStartButtonClicked && !gameStartButton.isClicked) {
			gameStarting = true;
		}

		if (gameStarting) {
			if (owner.screen.getFade() < 255) {
				if (owner.screen.getFade() > 245) {
					owner.screen.fadeTo(255);
				} else {
					owner.screen.fadeTo(owner.screen.getFade() + 10);
				}
			} else {
				done = true;
			}
		}

		if (owner.screen.getFade() > 0 && !gameStarting) {
			if (owner.screen.getFade() < 10) {
				owner.screen.fadeTo(0);
			} else {
				owner.screen.fadeTo(owner.screen.getFade() - 10);
			}
		}
	}

	@Override
	public BufferedImage draw() {
		BufferedImage bufferedImageOut = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImageOut.createGraphics();
		drawOn(graphics, 0, 0);
		graphics.dispose();

		return bufferedImageOut;
	}

	@Override
	public void drawOn(Graphics2D graphics, int scrollx, int scrolly) {
		try {
			scrollx = 0;
			scrolly = 0;
			graphics.drawImage(title, 111 - scrollx, 64 - scrolly, null);
			gameStartButton.drawOn(graphics, scrollx, scrolly);
		} catch (NullPointerException graphicsNull) {
			JOptionPane.showMessageDialog(null, "Have an error to load graphics2D ! Restart the game ",
					"Error Graphics2D", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
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

	}

	@Override
	public boolean isClosing() {
		return done;
	}

	@Override
	public int getWidth() {
		return 320;
	}

	@Override
	public int getHeight() {
		return 240;
	}

	@Override
	public Dimension getSize() {
		return new Dimension(320, 240);
	}

	@Override
	public boolean isScrollable() {
		return false;
	}

	@Override
	public boolean isResetting() {
		return false;
	}

}
