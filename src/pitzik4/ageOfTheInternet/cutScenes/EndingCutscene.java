// File:EndingCutscene.java
//Objective: last cut scene in the game

package pitzik4.ageOfTheInternet.cutScenes;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import pitzik4.ageOfTheInternet.Game;
import pitzik4.ageOfTheInternet.Stage;
import pitzik4.ageOfTheInternet.Tickable;
import pitzik4.ageOfTheInternet.graphics.Animation;
import pitzik4.ageOfTheInternet.graphics.ConfettiParticle;
import pitzik4.ageOfTheInternet.graphics.InfoBox;
import pitzik4.ageOfTheInternet.graphics.Renderable;
import pitzik4.ageOfTheInternet.graphics.Screen;
import pitzik4.ageOfTheInternet.graphics.Sprite;

public class EndingCutscene implements Stage {
	private static final int[] tobyWalkFrames = { 160 + 16, 161 + 16, 160 + 16, 162 + 16 };
	private static final int[] drThompsonWalkFrames = { 97, 98, 99, 100 };
	private static final int[] dedigitizerFrames = { 16, 17, 18, 19, 20, 21, 22, 23, 23, 23, 23, 23, 23, 23, 22, 21, 20,
			19, 18, 17, 16 };
	private static final int[] screenFrames = { 0, 1, 2, 3, 4 };
	private static final int[] screen2Frames = { 300, 3, 2, 1, 0 };
	private Animation dedigitizer = createDedigitizer();
	private Animation tobyWalk = new Animation(tobyWalkFrames, 4, 286, 208, true);
	private Animation drThompsonWalk = new Animation(drThompsonWalkFrames, 4, -16, 212, true);
	private Renderable toby = new Sprite(176, tobyWalk.getX(), tobyWalk.getY(), false);
	private Renderable drThompson = drThompsonWalk;
	private Renderable drGreen = new Sprite(128, 76, 212, bigSpriteSheet, 16, 16);
	private Renderable insideDedigitizer = new Sprite(24, 270, 196, bigSpriteSheet, 32, 32);
	private boolean tobyBeforeDe = false;
	private InfoBox dialogue = null;
	public static final BufferedImage bg = Screen.spritesheet("startSceneBG");
	private int lifeTime = 0;
	private Game owner;
	private boolean done = false;
	private boolean closing = false;
	private Set<Integer> lastKeysPressed = new HashSet<Integer>();
	private Animation screen = createScreen();
	private Animation screen2 = createScreen2();
	private Set<ConfettiParticle> confetti = new HashSet<ConfettiParticle>();
	private static final Random rnd = new Random();
	public static final Renderable drThompsonHead = new Sprite(32, 0, 0, Screen.spritesheet, 32, 32);
	public static final Renderable drThompsonOhNoHead = new Sprite(33, 0, 0, Screen.spritesheet, 32, 32);
	public static final Renderable tobyHead = new Sprite(34, 0, 0, Screen.spritesheet, 32, 32);
	public static final BufferedImage bigSpriteSheet = Screen.spritesheet("startSceneSprites");
	public static final Renderable drGreenHead = new Sprite(25, 270, 196, bigSpriteSheet, 32, 32);

	private final int SCREEN_HEIGHT = 240;
	private final int SCREEN_WIDTH = 320;

	private static Animation createDedigitizer() {
		Sprite[] sprites = new Sprite[dedigitizerFrames.length];
		final int element_x_position = 270;
		final int element_y_position = 196;
		for (int i = 0; i < sprites.length; i++) {
			final int sprite_sheet_x_position = 32;
			final int sprite_sheet_y_position = 32;
			sprites[i] = new Sprite(dedigitizerFrames[i], element_x_position, element_y_position, bigSpriteSheet,
					sprite_sheet_x_position, sprite_sheet_y_position);
		}
		return new Animation(sprites, 2, element_x_position, element_y_position, false);
	}

	private static Animation createScreen() {
		Sprite[] sprites = new Sprite[screenFrames.length];
		final int element_x_position = 220;
		final int element_y_position = 180;
		for (int i = 0; i < sprites.length; i++) {
			final int sprite_sheet_x_position = 36;
			final int sprite_sheet_y_position = 26;
			sprites[i] = new Sprite(screenFrames[i], element_x_position, element_y_position, bigSpriteSheet,
					sprite_sheet_x_position, sprite_sheet_y_position);
		}
		return new Animation(sprites, 1, element_x_position, element_y_position, false);
	}

	private static Animation createScreen2() {
		Sprite[] sprites = new Sprite[screen2Frames.length];
		final int element_x_position = 220;
		final int element_y_position = 180;
		for (int i = 0; i < sprites.length; i++) {
			final int sprite_sheet_x_position = 36;
			final int sprite_sheet_y_position = 26;
			sprites[i] = new Sprite(screen2Frames[i], element_x_position, element_y_position, bigSpriteSheet,
					sprite_sheet_x_position, sprite_sheet_y_position);
		}
		return new Animation(sprites, 1, element_x_position, element_y_position, false);
	}

	public EndingCutscene(Game owner) {
		if (owner != null) {
			this.owner = owner;
		} else {
			// nothing to do.
		}
		tobyWalk.go();
		drThompsonWalk.go();
		/*
		 * for(int i = 0; i < 100; i++) { confetti.add(new
		 * ConfettiParticle((int) (Math.round(Math.random() * 260) + 32), 77));
		 * }
		 */
	}

	@Override
	public BufferedImage draw() {
		BufferedImage outImage = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = outImage.createGraphics();
		drawOn(graphics, 0, 0);
		graphics.dispose();
		return outImage;
	}

	@Override
	public void drawOn(Graphics2D graphics, int scrollx, int scrolly) {
		scrollx = 0;
		scrolly = 0;
		graphics.drawImage(bg, 0, 0, null);
		insideDedigitizer.drawOn(graphics, 0, 0);
		toby.drawOn(graphics, 0, 0);
		drThompson.drawOn(graphics, 0, 0);
		if (dialogue != null) {
			dialogue.drawOn(graphics, 0, 0);
		}
		screen.drawOn(graphics, 0, 0);
		screen2.drawOn(graphics, 0, 0);
		drGreen.drawOn(graphics, 0, 0);
		dedigitizer.drawOn(graphics, 0, 0);
		if (tobyBeforeDe) {
			toby.drawOn(graphics, 0, 0);
		}
		for (ConfettiParticle cp : confetti) {
			cp.drawOn(graphics, scrollx, scrolly);
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
	public void goTo(int positionX, int positionY) {

	}

	@Override
	public void tick() {
		Set<Integer> keysPressed = new HashSet<Integer>(owner.keysDown);
		if (toby instanceof Tickable) {
			((Tickable) toby).tick();
		}
		if (drThompson instanceof Tickable) {
			((Tickable) drThompson).tick();
		}
		if (dialogue != null) {
			dialogue.tick();
		}
		for (ConfettiParticle cp : confetti) {
			if (cp.getY() < 227) {
				cp.tick();
			}
		}
		dedigitizer.tick();
		screen.tick();
		screen2.tick();
		if (dialogue == null) {
			lifeTime++;
		}
		final int box_diagonal_position = 4;
		final int text_y_position = 4;
		final int box_width = 312; // >=200
		final int box_height = 64;
		if (owner.keysDown.contains(10)) {
			done = true;
		}
		if (lifeTime > 20 && lifeTime < 50) {
			drThompson.goTo(drThompson.getX() + 2, drThompson.getY());
		} else if (lifeTime == 50) {
			drThompson = new Sprite(96, drThompson.getX(), drThompson.getY(), false);
			drGreen = new Sprite(129, 76, 212, bigSpriteSheet, 16, 16);
		} else if (lifeTime == 60) {
			final String dialog_text = "Who are you?";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drGreenHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 62) {
			final String dialog_text = "I am Dr Thompson. And who are you?";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 64) {
			final String dialog_text = "Dr Green. What are you doing here?";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drGreenHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 66) {
			final String dialog_text = "I'm here to collect Toby. Is he here yet?";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 68) {
			final String dialog_text = "I don't know who that is. Sorry.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drGreenHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 70) {
			screen.go();

			final int drGreen_x_position = 76;
			final int drGreen_y_position = 212;
			final int element_rendered_drGreen = 128;
			drGreen = new Sprite(element_rendered_drGreen, drGreen_x_position, drGreen_y_position, bigSpriteSheet, 16,
					16);
			// drGreen = new Sprite(128, 76, 212, bigSpriteSheet, 16, 16);
		} else if (lifeTime == 80) {
			final String dialog_text = "I am Toby! I have taken control of this facility! I -";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 82) {
			final String dialog_text = "Toby! What are you doing?";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 84) {
			final String dialog_text = "Oh! Dr Thompson! Uh... Heheh...";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 86) {
			final String dialog_text = "I told you to activate the Dedigitizer as soon as you arrived!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 88) {
			final String dialog_text = "Do I have to?";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 90) {
			final String dialog_text = "Of course you do, Toby.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 92) {
			final String dialog_text = "Fine.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 94) {
			dedigitizer.go();
			screen2.go();
		} else if (lifeTime == 110) {
			toby = tobyWalk;
			tobyBeforeDe = true;
		} else if (lifeTime > 110 && lifeTime < 200) {
			final int units_walked_by_tick = -2; // "-" = Going left
			final int toby_x_position = toby.getX();
			final int toby_y_position = toby.getY();
			toby.goTo(toby_x_position + units_walked_by_tick, toby_y_position);

			if (lifeTime == 117) {
				final int units_walked_by_tick_117 = 4;
				final int toby_x_position_tick_117 = toby.getX();
				final int toby_y_position_tick_117 = toby.getY();
				toby.goTo(toby_x_position_tick_117 + units_walked_by_tick_117, toby_y_position_tick_117);

			}
		} else if (lifeTime == 200) {
			toby = new Sprite(176, toby.getX(), toby.getY(), false);
		} else if (lifeTime == 202) {
			final String dialog_text = "I suppose you're Toby?";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drGreenHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 204) {
			final String dialog_text = "No! I am a wizard!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 206) {
			final String dialog_text = "Excellent. Let's be going, then.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 208) {
			drThompson = new Sprite(130, drThompson.getX(), drThompson.getY(), bigSpriteSheet, 16, 16);
			drGreen = new Sprite(130, drGreen.getX(), drGreen.getY(), bigSpriteSheet, 16, 16);
			for (int i = 0; i < 15; i++) {
				confetti.add(new ConfettiParticle(drThompson.getX() + rnd.nextInt(Sprite.SPRITE_WIDTH),
						drThompson.getY() + rnd.nextInt(Sprite.SPRITE_HEIGHT)));
			}
			for (int i = 0; i < 15; i++) {
				confetti.add(new ConfettiParticle(drGreen.getX() + rnd.nextInt(Sprite.SPRITE_WIDTH),
						drGreen.getY() + rnd.nextInt(Sprite.SPRITE_HEIGHT)));
			}
		} else if (lifeTime == 210) {
			drThompson.goTo(-16, 0);
			drGreen.goTo(-16, 0);
			final String dialog_text = "I win!!!!!!!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime > 212) {
			done = true;
		}
		final int SPACEBAR_BUTTON = 32;
		final boolean space_bar_pressed = keysPressed.contains(SPACEBAR_BUTTON)
				&& !lastKeysPressed.contains(SPACEBAR_BUTTON);
		if (space_bar_pressed) {
			if (dialogue != null) {
				if (dialogue.isGoing()) {
					dialogue.finish();
				} else {
					dialogue = null;
				}
			}
		}
		lastKeysPressed = keysPressed;
		if (done) {
			final int black_color = 255;
			final boolean screenNotBlack = owner.screen.getFade() < black_color;
			if (screenNotBlack) {
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
			if (owner.screen.getFade() < 10) {
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
	public int getWidth() {

		return SCREEN_WIDTH;
	}

	@Override
	public int getHeight() {

		return SCREEN_HEIGHT;
	}

	@Override
	public Dimension getSize() {
		return new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
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
