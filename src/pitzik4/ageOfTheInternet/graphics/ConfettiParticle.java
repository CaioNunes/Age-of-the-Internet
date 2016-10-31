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
	private int positionX;
	private int positionY;
	private static final Random randomNumber = new Random();
	public static final Color[] colorfulColors = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.CYAN };
	public static final int INVERSE_DIR_SWITCH_PROB = 8;
	public static final int INVERSE_GO_UP_PROB = 8;

	public ConfettiParticle(Color color, int x, int y, boolean goingRight) {
		assert(color != null): "Color-color parameter is null";
		setColor(color);
		setPositionX(x);
		setPositionY(y);
		setGoingRight(goingRight);
	}

	public ConfettiParticle(Color color, int x, int y) {
		this(color, x, y, getRandomnumber().nextBoolean());
	}

	public ConfettiParticle(int x, int y, boolean goingRight) {
		this(getColorfulcolors()[getRandomnumber().nextInt(getColorfulcolors().length - 1)], x, y, goingRight);
	}

	public ConfettiParticle(int x, int y) {
		this(x, y, getRandomnumber().nextBoolean());
	}

	@Override
	public BufferedImage draw() {
		BufferedImage bufferedImageOut = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		assert(bufferedImageOut != null): "BufferedImageOut is null";
		Graphics2D graphics = bufferedImageOut.createGraphics();
		assert(graphics != null): "graphics var is null";
		assert(this.getColor() != null): "color is null";
		graphics.setColor(this.getColor());
		graphics.fillRect(0, 0, 1, 1);
		graphics.dispose();
		return bufferedImageOut;
	}

	@Override
	public void drawOn(Graphics2D graphics, int scrollx, int scrolly) {
		assert(graphics != null): "Graphics2D-graphics parameter is null";
		assert(this.getColor() != null): "color var is null";
		graphics.setColor(this.getColor());
		graphics.fillRect(this.getPositionX() - scrollx, this.getPositionY() - scrolly, 1, 1);
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
			if (this.isGoingRight()) {
				goTo(this.getPositionX() + 1, this.getPositionY());
			} else {
				goTo(this.getPositionX() - 1, this.getPositionY());
			}
			if (this.isGoingUp()) {
				setGoingUp(false);
				goTo(this.getPositionX(), this.getPositionY() - 1);
			} else {
				goTo(this.getPositionX(), this.getPositionY() + 1);
			}
			if (getRandomnumber().nextInt(getInverseDirSwitchProb()) == 0) {
				setGoingRight(!isGoingRight());
			} else {
				// nothing
			}
			if (getRandomnumber().nextInt(getInverseGoUpProb()) == 0) {
				setGoingUp(true);
			} else {
				// nothing
			}
		}
		setMoving(isMoving());
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isGoingRight() {
		return goingRight;
	}

	public void setGoingRight(boolean goingRight) {
		this.goingRight = goingRight;
	}

	public boolean isGoingUp() {
		return goingUp;
	}

	public void setGoingUp(boolean goingUp) {
		this.goingUp = goingUp;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
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

	public static Random getRandomnumber() {
		return randomNumber;
	}

	public static Color[] getColorfulcolors() {
		return colorfulColors;
	}

	public static int getInverseDirSwitchProb() {
		return INVERSE_DIR_SWITCH_PROB;
	}

	public static int getInverseGoUpProb() {
		return INVERSE_GO_UP_PROB;
	}

}
