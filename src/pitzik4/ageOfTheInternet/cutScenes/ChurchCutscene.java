// File: ChurchCutscene.java
//Objective: cut scene when player get in a church and need to hack it

package pitzik4.ageOfTheInternet.cutScenes;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import pitzik4.ageOfTheInternet.Game;
import pitzik4.ageOfTheInternet.Stage;
import pitzik4.ageOfTheInternet.graphics.InfoBox;
import pitzik4.ageOfTheInternet.graphics.Renderable;
import pitzik4.ageOfTheInternet.graphics.Sprite;

public class ChurchCutscene implements Stage {
	private int lifeTime = 0;
	private Renderable drThompson = new Sprite(96, 131, 212, false);
	private Renderable screen = new Sprite(4, 220, 180, StartingCutscene.bigSpriteSheet, 36, 26);
	private Renderable digitizer = new Sprite(16, 286, 196, StartingCutscene.bigSpriteSheet, 16, 32);
	private InfoBox dialogue = null;
	private boolean done = false;
	private boolean closing = false;
	private Game owner;
	private Set<Integer> lastKeysPressed = new HashSet<Integer>();

	// Default Values
	private static final int DEFAULT_WIDTH = 320;
	private static final int DEFAULT_HEIGHT = 240;

	public ChurchCutscene(Game owner) {
		if (owner != null) {
			this.owner = owner;
		} else {
			// nothing to do.
		}
	}

	@Override
	public BufferedImage draw() {
		BufferedImage out = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = out.createGraphics();
		drawOn(graphics, 0, 0);
		graphics.dispose();
		return null;
	}

	@Override
	public void drawOn(Graphics2D graphics, int scrollx, int scrolly) {
		scrollx = 0;
		scrolly = 0;
		if (graphics != null) {
			graphics.drawImage(StartingCutscene.bg, 0, 0, null);
		} else {
			// do nothing
		}
		drThompson.drawOn(graphics, 0, 0);
		screen.drawOn(graphics, 0, 0);
		digitizer.drawOn(graphics, 0, 0);
		if (dialogue != null) {
			dialogue.drawOn(graphics, 0, 0);
		} else {
			// do nothing
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
	public void goTo(int walkPositionX, int walkPositionY) {

	}

	@Override
	public void tick() {
		Set<Integer> keysPressed = new HashSet<Integer>(owner.keysDown);
		if (dialogue == null) {
			lifeTime++;
		} else {
			dialogue.tick();
			final int space_key = 32;
			final boolean space_key_pressed = keysPressed.contains(space_key) && !lastKeysPressed.contains(space_key);
			if (space_key_pressed) {
				if (dialogue.isGoing()) {
					dialogue.finish();
				} else {
					dialogue = null;
				}
			}
		}
		final int box_diagonal_position = 4;
		final int text_y_position = 4;
		final int box_width = 312; // >=200
		final int box_height = 64;
		switch (lifeTime) {
		case 10:
			final String dialog_text_10 = "What's new?";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height,
					StartingCutscene.tobyHead, dialog_text_10);
			dialogue.go();
			lifeTime++;
			break;
		case 12:
			final String dialog_text_12 = "Well, I heard the Church of Pitzik4 has weak Internet security.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height,
					StartingCutscene.drThompsonHead, dialog_text_12);
			dialogue.go();
			lifeTime++;
			break;
		case 14:
			final String dialog_text_14 = "I don't believe in Pitzik4. I'll hack them.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height,
					StartingCutscene.tobyHead, dialog_text_14);
			dialogue.go();
			lifeTime++;
			break;
		case 16:
			final String dialog_text_16 = "That's not very nice.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height,
					StartingCutscene.drThompsonHead, dialog_text_16);
			dialogue.go();
			lifeTime++;
			break;

		case 18:
			final String dialog_text_18 = "Don't worry, I have an idea on what to do with them.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height,
					StartingCutscene.tobyHead, dialog_text_18);
			dialogue.go();
			lifeTime++;
			break;

		case 20:
			final String dialog_text_20 = "I'll schedule a whole bunch of events so that corporations'";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height,
					StartingCutscene.tobyHead, dialog_text_20);
			dialogue.go();
			lifeTime++;
			break;

		case 22:
			final String dialog_text_22 = "counter-hackers are occupied.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height,
					StartingCutscene.tobyHead, dialog_text_22);
			dialogue.go();
			lifeTime++;
			break;
		case 24:
			final String dialog_text_24 = "That's actually quite clever...";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height,
					StartingCutscene.drThompsonHead, dialog_text_24);
			dialogue.go();
			lifeTime++;
			break;
		case 26:
			final String dialog_text_26 = "You sound surprised.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height,
					StartingCutscene.tobyHead, dialog_text_26);
			dialogue.go();
			lifeTime++;
			break;
		case 28:
			done = true;
			break;
		}

		final int enter_key = 10;
		if (owner.keysDown.contains(enter_key)) {
			done = true;
		}
		lastKeysPressed = keysPressed;
		if (done) {
			final int black_color = 255;
			if (owner.screen.getFade() < black_color) {
				if (owner.screen.getFade() > black_color - 10) {
					owner.screen.fadeTo(black_color);
				} else {
					final int color_value_faded_by_tick = 10;
					owner.screen.fadeTo(owner.screen.getFade() + color_value_faded_by_tick);
				}
			} else {
				closing = true;
			}
		}
		final int clear_color = 0;
		if (owner.screen.getFade() > clear_color && !done) {
			if (owner.screen.getFade() < clear_color + 10) {
				owner.screen.fadeTo(clear_color);
			} else {
				final int color_value_faded_by_tick = 10;
				owner.screen.fadeTo(owner.screen.getFade() - color_value_faded_by_tick);
			}
		}
	}

	@Override
	public boolean isClosing() {
		return closing;
	}

	@Override
	public boolean isResetting() {
		return false;
	}

	@Override
	public int getWidth() {
		return DEFAULT_WIDTH;
	}

	@Override
	public int getHeight() {
		return DEFAULT_HEIGHT;
	}

	@Override
	public Dimension getSize() {
		final Dimension dimension = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		return dimension;
	}

	@Override
	public boolean isScrollable() {
		return false;
	}

}
