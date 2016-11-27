package pitzik4.ageOfTheInternet.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pitzik4.ageOfTheInternet.Tickable;

public class InfoBox implements Renderable, Tickable {
	private RenderableString renderableString;
	private Renderable sideGraphic;
	private int sGrWidth, sGrHeight;
	private int width, height;
	private int positionX, positionY;
	private String displayWhat, currentDisp = "";
	private static final int[][] BG_SPRITES = { { 192, 193, 194 }, { 224, 225, 226 }, { 256, 257, 258 } };
	private static final int BG_SPRITE_SIZE = 8;
	private static final int WAIT_BETWEEN_CHARS = 2;
	private Sprite[][] background;
	private byte nextLetterCountdown = WAIT_BETWEEN_CHARS;
	private boolean going = false;

	public InfoBox(int x, int y, int width, int height, Renderable sideGraphic, String displayWhat) {
		assert (sideGraphic != null) : "Renderable-sideGraphic parameter is null";
		assert (displayWhat != null) : "displayWhat parameter is null";
		this.positionX = x;
		this.positionY = y;
		this.width = width;
		this.height = height;
		this.sideGraphic = sideGraphic;
		this.displayWhat = displayWhat;
		background = new Sprite[width / BG_SPRITE_SIZE][height / BG_SPRITE_SIZE];
		assert (background != null) : "background var is null";
		for (int i = 0; i < width; i += BG_SPRITE_SIZE) {
			for (int j = 0; j < height; j += BG_SPRITE_SIZE) {
				int whichx;
				int whichy;
				if (i == 0) {
					whichy = 0;
				} else if (i == width - BG_SPRITE_SIZE) {
					whichy = 2;
				} else {
					whichy = 1;
				}
				if (j == 0) {
					whichx = 0;
				} else if (j == height - BG_SPRITE_SIZE) {
					whichx = 2;
				} else {
					whichx = 1;
				}
				// try {
				background[i / BG_SPRITE_SIZE][j / BG_SPRITE_SIZE] = new Sprite(BG_SPRITES[whichx][whichy], i + x,
						j + x, Screen.spritesheet, BG_SPRITE_SIZE, BG_SPRITE_SIZE);
				// } catch(ArrayIndexOutOfBoundsException e) {}
			}
		}
		if (sideGraphic != null) {
			sGrWidth = sideGraphic.draw().getWidth();
			sGrHeight = sideGraphic.draw().getHeight();
			this.renderableString = new RenderableString("", x + BG_SPRITE_SIZE * 2 + sGrWidth + 2, y + BG_SPRITE_SIZE);
		} else {
			this.renderableString = new RenderableString("", x + BG_SPRITE_SIZE, y + BG_SPRITE_SIZE);
		}
	}

	@Override
	public BufferedImage draw() {
		BufferedImage bufferedImageOut = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		assert (bufferedImageOut != null) : "BufferedImageOut is null";
		Graphics2D graphics = bufferedImageOut.createGraphics();
		assert (graphics != null) : "Graphics2D-graphics is null";
		drawOn(graphics, 0, 0);
		graphics.dispose();
		assert (bufferedImageOut != null) : "Return bufferedImageOut is null";
		return bufferedImageOut;
	}

	@Override
	public void drawOn(Graphics2D graphics, int scrollx, int scrolly) {
		assert (graphics != null) : "Graphics2D-graphics parameter is null";
		scrollx = scrolly = 0;
		assert (background != null) : "background attribute is null";
		for (Sprite[] ss : background) {
			assert (ss != null) : "ss var is null";
			for (Sprite s : ss) {
				assert (s != null) : "s var is null";
				s.drawOn(graphics, scrollx, scrolly);
			}
		}
		assert (renderableString != null) : "renderableString attribute is null";
		renderableString.drawOn(graphics, scrollx, scrolly);
		if (sideGraphic != null) {
			// sideGraphic.drawOn(g,
			// sideGraphic.getX()+scrollx-BG_SPRITE_SIZE-x,
			// sideGraphic.getY()+scrolly-BG_SPRITE_SIZE-y);
			graphics.drawImage(sideGraphic.draw(), positionX - scrollx + BG_SPRITE_SIZE,
					(positionY + positionY + height - sGrHeight) / 2, null);
		} else {
			// DO NOTHING
		}
	}

	@Override
	public int getX() {
		return positionX;
	}

	@Override
	public int getY() {
		return positionX;
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
		int dx = x - this.positionX;
		int dy = y - this.positionY;
		this.positionX = x;
		this.positionY = y;
		assert (background != null) : "background attribute is null";
		for (Sprite[] ss : background) {
			assert (ss != null) : "ss var is null";
			for (Sprite s : ss) {
				assert (s != null) : "s var is null";
				s.goTo(s.getX() + dx, s.getY() + dy);
			}
		}
		assert (renderableString != null) : "renderableString attribute is null";
		renderableString.goTo(renderableString.getX() + dx, renderableString.getY() + dy);
	}

	@Override
	public void tick() {
		if (going) {
			nextLetterCountdown--;
			if (nextLetterCountdown <= 0) {
				nextLetterCountdown = WAIT_BETWEEN_CHARS;
				nextLetter();
			} else {
				// DO NOTHING
			}
		} else {
			// DO NOTHING
		}
	}

	public void nextLetter() {
		assert (currentDisp != null) : "currentDisp attribute is null";
		assert (displayWhat != null) : "displayWhat attribute is null";
		if (currentDisp.length() < displayWhat.length()) {
			String string = renderableString.represents;
			char nextChar = displayWhat.charAt(currentDisp.length());
			string += nextChar;
			currentDisp += nextChar;
			// System.out.format("Next character: %c%n", nextChar);
			RenderableString tmpStr = new RenderableString(string, renderableString.getX(), renderableString.getY());
			assert (tmpStr != null) : "tmpStr var is null";
			if (tmpStr.width > width - tmpStr.getX() + positionX - BG_SPRITE_SIZE) {
				// System.out.println("Making new line");
				int whereNewLine = tmpStr.represents.lastIndexOf(" ") + 1;
				StringBuilder sb = new StringBuilder(tmpStr.represents);
				assert (sb != null) : "sb var is null";
				if (whereNewLine == sb.toString().length()) {
					sb.replace(sb.toString().length() - 1, sb.toString().length(), "\n");
				} else {
					sb.insert(whereNewLine, '\n');
				}
				// string = str.represents;
				// string += "\n" + nextChar;
				string = sb.toString();
				tmpStr = new RenderableString(string, renderableString.getX(), renderableString.getY());
				if (tmpStr.height > height - tmpStr.getY() + positionY - BG_SPRITE_SIZE) {
					going = false;
					tmpStr = renderableString;
					// System.out.println("Too tall, stopping");
				} else {
					// DO NOTHING
				}
			} else {
				// DO NOTHING
			}
			renderableString = tmpStr;
			if (Character.isWhitespace(nextChar)) {
				nextLetter();
			} else {
				// DO NOTHING
			}
		} else {
			going = false;
		}
	}

	public InfoBox go() {
		going = true;
		return this;
	}

	public InfoBox stop() {
		going = false;
		return this;
	}

	public InfoBox finish() {
		go();
		while (going) {
			nextLetter();
		}
		return this;
	}

	public boolean isGoing() {
		return going;
	}

}
