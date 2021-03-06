package pitzik4.ageOfTheInternet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import pitzik4.ageOfTheInternet.graphics.Renderable;
import pitzik4.ageOfTheInternet.graphics.Screen;
import pitzik4.ageOfTheInternet.graphics.Sprite;

public class XButton implements Renderable, Tickable {
	private Game owner;
	private Sprite sprite;
	private int positionX = 0;
	private int positionY = 0;
	public boolean isClicked = false;
	public boolean isScrolled = false;
	public static final int BUTTON_SPRITE = 260;
	public static final int BU_SI = 8;

	public XButton(Game owner, final int x, final int y) {
		this.owner = owner;
		this.positionX = x;
		this.positionY = y;
		sprite = new Sprite(BUTTON_SPRITE, x, y, Screen.spritesheet, BU_SI, BU_SI);
	}

	@Override
	public BufferedImage draw() {
		BufferedImage bufferedImageOut = new BufferedImage(BU_SI, BU_SI, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImageOut.createGraphics();
		drawOn(graphics, positionX, positionY);
		graphics.dispose();
		return bufferedImageOut;
	}

	@Override
	public void drawOn(Graphics2D graphics, final int scrollx, final int scrolly) {
		try {
			sprite.drawOn(graphics, scrollx, scrolly);
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
		this.positionX = x;
		this.positionY = y;
		sprite.goTo(x, y);
	}

	@Override
	public void tick() {
		if (owner.mouseInsideOf(positionX, positionY, BU_SI, BU_SI)) {
			beScrolled(true);
			if (owner.mouseDown) {
				beClicked(true);
			} else {
				beClicked(false);
			}
		} else {
			beClicked(false);
			beScrolled(false);
		}
	}

	private void beScrolled(final boolean newScrolled) {
		if (isScrolled != newScrolled) {
			isScrolled = newScrolled;
			if (isScrolled) {
				sprite = new Sprite(BUTTON_SPRITE + 1, positionX, positionY, Screen.spritesheet, BU_SI, BU_SI);
			} else {
				sprite = new Sprite(BUTTON_SPRITE, positionX, positionY, Screen.spritesheet, BU_SI, BU_SI);
			}
		}
	}

	private void beClicked(final boolean newClicked) {
		if (isClicked != newClicked) {
			isClicked = newClicked;
			if (isClicked) {
				sprite = new Sprite(BUTTON_SPRITE + 2, positionX, positionY, Screen.spritesheet, BU_SI, BU_SI);
			} else {
				sprite = new Sprite(BUTTON_SPRITE + 1, positionX, positionY, Screen.spritesheet, BU_SI, BU_SI);
			}
		}
	}

}
