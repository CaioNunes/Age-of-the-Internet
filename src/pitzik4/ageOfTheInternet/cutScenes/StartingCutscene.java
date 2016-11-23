// File:StartingCutscene.java
//Objective: first cut scene of the game

package pitzik4.ageOfTheInternet.cutScenes;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import pitzik4.ageOfTheInternet.Game;
import pitzik4.ageOfTheInternet.Stage;
import pitzik4.ageOfTheInternet.Tickable;
import pitzik4.ageOfTheInternet.graphics.Animation;
import pitzik4.ageOfTheInternet.graphics.InfoBox;
import pitzik4.ageOfTheInternet.graphics.Renderable;
import pitzik4.ageOfTheInternet.graphics.RenderableString;
import pitzik4.ageOfTheInternet.graphics.Screen;
import pitzik4.ageOfTheInternet.graphics.Sprite;

public class StartingCutscene implements Stage {
	private static final int[] coffeeBreakFrames = { 13, 103, 104, 105, 106, 107 };
	private Animation coffeeBreak = new Animation(coffeeBreakFrames, 1, 146, 212, false);
	private static final int[] tobyWalkFrames = { 160, 161, 160, 162 };
	private static final int[] drThompsonWalkFrames = { 97, 98, 99, 100 };
	private static final int[] digitizerFrames = { 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 16 };
	private static final int[] screenFrames = { 0, 1, 2, 3, 4 };
	private Animation digitizer = createDigitizer();
	private Animation tobyWalk = new Animation(tobyWalkFrames, 4, -16, 212, true);
	private Animation drThompsonWalk = new Animation(drThompsonWalkFrames, 4, 76, 212, true);
	private Renderable toby = tobyWalk;
	private Renderable drThompson = new Sprite(96, 76, 212, false);
	private InfoBox dialogue = null;
	public static final BufferedImage bg = Screen.spritesheet("startSceneBG");
	private int lifeTime = 0;
	private Game owner;
	private boolean done = false;
	private boolean closing = false;
	private Set<Integer> lastKeysPressed = new HashSet<Integer>();
	private Animation screen = createScreen();
	private RenderableString instructions = new RenderableString("Space to advance text\nEnter to skip cutscene", 102,
			214);
	public static final Renderable drThompsonHead = new Sprite(32, 0, 0, Screen.spritesheet, 32, 32);
	public static final Renderable drThompsonOhNoHead = new Sprite(33, 0, 0, Screen.spritesheet, 32, 32);
	public static final Renderable tobyHead = new Sprite(34, 0, 0, Screen.spritesheet, 32, 32);
	public static final BufferedImage bigSpriteSheet = Screen.spritesheet("startSceneSprites");

	// Default Values
	private static final int DEFAULT_WIDTH = 320;
	private static final int DEFAULT_HEIGHT = 240;

	
	// Creates the door animation
	private static Animation createDigitizer() {
		Sprite[] sprites = new Sprite[digitizerFrames.length];
		final int element_x_position = 286;
		final int element_y_position = 196;
		for (int i = 0; i < sprites.length; i++) {
			final int sprite_sheet_x_position = 16;
			final int sprite_sheet_y_position = 32;
			sprites[i] = new Sprite(digitizerFrames[i], element_x_position, element_y_position, bigSpriteSheet,
					sprite_sheet_x_position, sprite_sheet_y_position);
		}
		return new Animation(sprites, 2, element_x_position, element_y_position, false);
	}

	
	// Creates the screen animation
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

	
	public StartingCutscene(Game owner) {
		if (owner != null) {
			this.owner = owner;
		} else {
			// nothing to do.
		}
		tobyWalk.go();
		drThompsonWalk.go();
	}

	
	@Override
	public BufferedImage draw() {
		BufferedImage out = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = out.createGraphics();
		drawOn(g, 0, 0);
		g.dispose();
		return out;
	}

	
	@Override
	public void drawOn(Graphics2D graphics, int scrollX, int scrollY) {
		
		// Draw the cutscene background.
		scrollX = 0;
		scrollY = 0;
		graphics.drawImage(bg, 0, 0, null);
		
		// Draw the cutscene elements, like the characters, screen, door...
		toby.drawOn(graphics, 0, 0);
		drThompson.drawOn(graphics, 0, 0);
		if (dialogue != null) {
			dialogue.drawOn(graphics, 0, 0);
		} 
		screen.drawOn(graphics, 0, 0);
		digitizer.drawOn(graphics, 0, 0);
		coffeeBreak.drawOn(graphics, 0, 0);
		if (instructions != null) {
			instructions.drawOn(graphics, 0, 0);
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
		
		final int enter_key = 10;
		Set<Integer> keysPressed = new HashSet<Integer>(owner.keysDown);
		
		// Tick for Toby
		if (toby instanceof Tickable) {
			((Tickable) toby).tick();
		}
		
		// Tick for Thompson
		if (drThompson instanceof Tickable) {
			((Tickable) drThompson).tick();
		}
		
		// Tick for dialog if has not finished yet
		if (dialogue != null) {
			dialogue.tick();
		}
		digitizer.tick();
		screen.tick();
		coffeeBreak.tick();
		
		// If finished the dialog, go to the next "tick".
		if (dialogue == null) {
			lifeTime++;
		}
		
		// If user is pressing the enter key, jump Cutscene. That is, jump all scenes.
		if (owner.keysDown.contains(enter_key)) {
			done = true;
		}
		
		// Dialog box characteristics
		final int box_diagonal_position = 4;
		final int text_y_position = 4;
		final int box_width = 312; // >=200
		final int box_height = 64;
		
		// Scene number 1.
		if (lifeTime > 20 && lifeTime < 50) {
			final int units_walked_by_tick = 2;
			final int toby_x_position = toby.getX();
			final int toby_y_position = toby.getY();
			toby.goTo(toby_x_position + units_walked_by_tick, toby_y_position);
			
		// Scene number 2
		} else if (lifeTime == 50) {
			// Change Toby position
			final int toby_x_position = toby.getX();
			final int toby_y_position = drThompson.getY();
			final int element_rendered_toby = 160;
			toby = new Sprite(element_rendered_toby, toby_x_position, toby_y_position, false);
			
			// Change Thompson position
			final int thompson_x_position = drThompson.getX();
			final int thompson_y_position = 212;
			final int element_rendered_thompson = 112;
			drThompson = new Sprite(element_rendered_thompson, thompson_x_position, thompson_y_position, false);
			
		// Scene number 3
		} else if (lifeTime == 60) {
			final String dialog_text = "Ah! You must be Toby. I'm Dr Thompson.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 4
		} else if (lifeTime == 62) {
			final String dialog_text = "I'll be conducting today's tests.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		
		// Scene number 5
		} else if (lifeTime == 64) {
			final String dialog_text = "Hi, Dr Thompson! So you're going to put me in a computer?";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			instructions = null;
			
		// Scene number 6
		} else if (lifeTime == 66) {
			final String dialog_text = "I suppose -";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 7
		} else if (lifeTime == 68) {
			final String dialog_text = "Wow! That's the biggest computer I've ever seen!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 8
		} else if (lifeTime == 70) {
			toby = tobyWalk;
			
		// Scene number 9
		} else if (lifeTime > 70 && lifeTime < 110) {
			final int units_walked_by_tick = 2;
			final int toby_x_position = toby.getX();
			final int toby_y_position = toby.getY();
			toby.goTo(toby_x_position + units_walked_by_tick, toby_y_position);
		
		// Scene number 10
		} else if (lifeTime == 110) {
			// Change Toby position
			final int toby_x_position = toby.getX();
			final int toby_y_position = toby.getY();
			final int element_rendered_toby = 160;
			toby = new Sprite(element_rendered_toby, toby_x_position, toby_y_position, false);
			
			// Change Thompson position
			final int thompson_x_position = 76;
			final int thompson_y_position = 212;
			final int element_rendered_thompson = 96;
			drThompson = new Sprite(element_rendered_thompson, thompson_x_position, thompson_y_position, false);
			
		// Scene number 11
		} else if (lifeTime == 120) {
			final String dialog_text = "That's the computer where you will be, uh, put in.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		
		// Scene number 12
		} else if (lifeTime == 122) {
			final int toby_x_position = toby.getX();
			final int toby_y_position = toby.getY();
			final int element_rendered_toby = 176;
			toby = new Sprite(element_rendered_toby, toby_x_position, toby_y_position, false);
			final String dialog_text = "Wow! This whole computer is just for me?";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		
		// Scene number 13
		} else if (lifeTime == 124) {
			final String dialog_text = "No way! I mean, no, it's for the project.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
		
		// Scene number 14
		} else if (lifeTime == 126) {
			final int toby_x_position = toby.getX();
			final int toby_y_position = toby.getY();
			final int element_rendered_toby = 160;
			toby = new Sprite(element_rendered_toby, toby_x_position, toby_y_position, false);
			final String dialog_text = "Oh. It's still awesome, though.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 15
		} else if (lifeTime == 128) {
			final int toby_x_position = toby.getX();
			final int toby_y_position = toby.getY();
			final int element_rendered_toby = 176;
			toby = new Sprite(element_rendered_toby, toby_x_position, toby_y_position, false);
			final String dialog_text = "So, what do I do?";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 16
		} else if (lifeTime == 130) {
			final String dialog_text = "Just get into that machine over there.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 17
		} else if (lifeTime == 132) {
			final int toby_x_position = toby.getX();
			final int toby_y_position = toby.getY();
			final int element_rendered_toby = 160;
			toby = new Sprite(element_rendered_toby, toby_x_position, toby_y_position, false);
			final String dialog_text = "That's all? Ok, I'll get in.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 18
		} else if (lifeTime == 134) {
			toby = tobyWalk;
			
		// Scene number 19
		} else if (lifeTime > 134 && lifeTime < 210) {
			final int units_walked_by_tick = 2;
			final int toby_x_position = toby.getX();
			final int toby_y_position = toby.getY();
			toby.goTo(toby_x_position + units_walked_by_tick, toby_y_position);
			
		// Scene number 20
		} else if (lifeTime == 212) {
			final int toby_x_position = toby.getX();
			final int toby_y_position = toby.getY();
			final int element_rendered_toby = 160;
			toby = new Sprite(element_rendered_toby, toby_x_position, toby_y_position, false);

			digitizer.go();
			
		// Scene number 21
		} else if (lifeTime == 219) {
			toby = tobyWalk;
			final int units_walked_by_tick = 2;
			final int toby_x_position = toby.getX();
			final int toby_y_position = toby.getY();
			toby.goTo(toby_x_position + units_walked_by_tick, toby_y_position);
			
		// Scene number 22
		} else if (lifeTime > 219 && lifeTime < 226) {
			final int units_walked_by_tick = 2;
			final int toby_x_position = toby.getX();
			final int toby_y_position = toby.getY();
			toby.goTo(toby_x_position + units_walked_by_tick, toby_y_position);
		}
		
		// Scene number 23
		if (lifeTime == 222) {
			drThompson = drThompsonWalk;
			
		// Scene number 24
		} else if (lifeTime == 226) {
			// Change Toby position
			final int toby_x_position = 0;
			final int toby_y_position = 0;
			final int element_rendered_toby = 13;
			toby = new Sprite(element_rendered_toby, toby_x_position, toby_y_position, false);

			// Thompson walks
			final int units_walked_by_tick = 2;
			final int thompson_x_position = drThompson.getX();
			final int thompson_y_position = drThompson.getY();
			drThompson.goTo(thompson_x_position + units_walked_by_tick, thompson_y_position);
			
		// Scene number 25
		} else if (lifeTime > 222 && lifeTime < 250) {
			final int units_walked_by_tick = 2;
			final int thompson_x_position = drThompson.getX();
			final int thompson_y_position = drThompson.getY();
			drThompson.goTo(thompson_x_position + units_walked_by_tick, thompson_y_position);
			
		// Scene number 26
		} else if (lifeTime == 250) {
			final int thompson_x_position = drThompson.getX();
			final int thompson_y_position = drThompson.getY();
			final int element_rendered_thompson = 96;
			drThompson = new Sprite(element_rendered_thompson, thompson_x_position, thompson_y_position, false);
			
		// Scene number 27
		} else if (lifeTime == 260) {
			screen.go();
			
		// Scene number 28
		} else if (lifeTime == 270) {
			final String dialog_text = "Wow! This is neat!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 29
		} else if (lifeTime == 272) {
			final String dialog_text = "It worked? Yes! It worked! I can't believe it!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 30
		} else if (lifeTime == 274) {
			final String dialog_text = "I can't wait to tell the others!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 31
		} else if (lifeTime == 276) {
			final String dialog_text = "Oh, but, of course... Better get you out of there.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 32
		} else if (lifeTime == 278) {
			final int thompson_x_position = drThompson.getX();
			final int thompson_y_position = drThompson.getY();
			final int element_rendered_thompson = 112;
			drThompson = new Sprite(element_rendered_thompson, thompson_x_position, thompson_y_position, false);
			
		// Scene number 34
		} else if (lifeTime == 284) {
			final int thompson_x_position = drThompson.getX();
			final int thompson_y_position = drThompson.getY();
			final int element_rendered_thompson = 96;
			drThompson = new Sprite(element_rendered_thompson, thompson_x_position, thompson_y_position, false);
			
		// Scene number 35
		} else if (lifeTime == 290) {
			final int thompson_x_position = drThompson.getX();
			final int thompson_y_position = drThompson.getY();
			final int element_rendered_thompson = 112;
			drThompson = new Sprite(element_rendered_thompson, thompson_x_position, thompson_y_position, false);
			
		// Scene number 36
		} else if (lifeTime == 296) {
			final int thompson_x_position = drThompson.getX();
			final int thompson_y_position = drThompson.getY();
			final int element_rendered_thompson = 96;
			drThompson = new Sprite(element_rendered_thompson, thompson_x_position, thompson_y_position, false);
		
		// Scene number 37
		} else if (lifeTime == 310) {
			final int thompson_x_position = drThompson.getX();
			final int thompson_y_position = drThompson.getY();
			final int element_rendered_thompson = 102;
			drThompson = new Sprite(element_rendered_thompson, thompson_x_position, thompson_y_position, false);

			coffeeBreak.go();
		
		// Scene number 38
		} else if (lifeTime == 330) {
			final String dialog_text = "Oh, no!! The machine to bring you back is missing!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonOhNoHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 39
		} else if (lifeTime == 332) {
			final String dialog_text = "We can't get you out now!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonOhNoHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 40
		} else if (lifeTime == 334) {
			final String dialog_text = "This is the coolest thing that's ever happened to me!!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 41
		} else if (lifeTime == 336) {
			final String dialog_text = "You can't stay in there forever!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonOhNoHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 42
		} else if (lifeTime == 338) {
			final String dialog_text = "Yeah, I guess you're right... How can I get out?";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 43
		} else if (lifeTime == 340) {
			final String dialog_text = "You'll have to go to the Internet and hack computers!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonOhNoHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 44
		} else if (lifeTime == 342) {
			final String dialog_text = "Click a computer to open a menu on it, and click \"Hack\" to hack it.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonOhNoHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 45
		} else if (lifeTime == 344) {
			final String dialog_text = "Hack one of our research facilities and bring yourself back.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonOhNoHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 46
		} else if (lifeTime == 346) {
			final String dialog_text = "Remember, it takes RAM to hack computers. Only hack what you need to.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, drThompsonOhNoHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 47
		} else if (lifeTime == 348) {
			final String dialog_text = "Got it!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, tobyHead,
					dialog_text);
			dialogue.go();
			lifeTime++;
			
		// Scene number 48
		} else if (lifeTime > 350) {
			done = true;
		}
		
		// If space key is pressed, go to the next dialog.
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
			} else {
				// DO NOTHING
			}
		} else {
			// DO NOTHING
		}
		
		// If the Cutscene ended, make the screen black.
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
		} else {
			// DO NOTHING
		}
	}

	
	@Override
	public boolean isClosing() {
		return closing;
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
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
