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

public class HackerCutscene implements Stage {
	private int lifeTime = 0;
	private Renderable drThompson = new Sprite(96, 131, 212, false);
	private Renderable screen = new Sprite(4, 220, 180, StartingCutscene.bigSpriteSheet, 36, 26);
	private Renderable digitizer = new Sprite(16, 286, 196, StartingCutscene.bigSpriteSheet, 16, 32);
	private InfoBox dialogue = null;
	private boolean done = false;
	private boolean closing = false;
	private Game owner;
	private Set<Integer> lastKeysPressed = new HashSet<Integer>();
	
	private static final int DEFAULT_WIDTH = 320;
	private static final int DEFAULT_HEIGHT = 240;

	public HackerCutscene(Game owner) {
		if (owner != null) {
			this.owner = owner;
		} else {
			// nothing to do.
		}
	}

	@Override
	public BufferedImage draw() {
		BufferedImage out = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = out.createGraphics();
		drawOn(g, 0, 0);
		g.dispose();
		return null;
	}

	@Override
	public void drawOn(Graphics2D graphics, int scrollx, int scrolly) {
		scrollx = 0;
		scrolly = 0;
		graphics.drawImage(StartingCutscene.bg, 0, 0, null);
		drThompson.drawOn(graphics, 0, 0);
		screen.drawOn(graphics, 0, 0);
		digitizer.drawOn(graphics, 0, 0);
		if (dialogue != null) {
			dialogue.drawOn(graphics, 0, 0);
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
		final int SPACEBAR_BUTTON = 32;
		final int ENTER_BUTTON = 10;
		Set<Integer> keysPressed = new HashSet<Integer>(owner.keysDown);
		if (dialogue == null) {
			lifeTime++;
		} else {
			dialogue.tick();
			if (keysPressed.contains(SPACEBAR_BUTTON ) && !lastKeysPressed.contains(SPACEBAR_BUTTON )) {
				if (dialogue.isGoing()) {
					dialogue.finish();
				} else {
					dialogue = null;
				}
			}
		}
		if (lifeTime == 10) {
			final String dialog_text = "Toby, I have some rather bad news.";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 12) {
			final String dialog_text = "That's bad enough news for me.";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.tobyHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 14) {
			final String dialog_text = "It seems that you're not the only hacker around.";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 16) {
			final String dialog_text = "Yeah! There's you!";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.tobyHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 18) {
			final String dialog_text = "What are you talking about?";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 20) {
			final String dialog_text = "There's more than one meaning to the word \"hacker\".";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.tobyHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 22) {
			final String dialog_text = "Toby, would you be serious for just a moment?";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 24) {
			final String dialog_text = "Fine, what is it?";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.tobyHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 26) {
			final String dialog_text = "There's a group of hackers out there called Some Stupid Hacker.";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 28) {
			final String dialog_text = "This is likely to lead to unwanted competition.";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 30) {
			final String dialog_text = "Hackers are mean!";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.tobyHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 32) {
			final String dialog_text = "Also, I'm guessing their computers are hard to hack.";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 34) {
			final String dialog_text = "Rats!";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.tobyHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 36) {
			final String dialog_text = "We've also created a program that will let you into secured networks.";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 38) {
			final String dialog_text = "Really? Awesome!";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.tobyHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 40) {
			final String dialog_text = "It takes more than one computer to find the key, though.";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 42) {
			final String dialog_text ="Some networks have harder-to-crack security than others." ;
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 44) {
			final String dialog_text = "Ok, great.";
			dialogue = new InfoBox(4, 4, 312, 64, StartingCutscene.tobyHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if (lifeTime == 46) {
			done = true;
		}
		if (owner.keysDown.contains(ENTER_BUTTON)) {
			done = true;
		}
		lastKeysPressed = keysPressed;
		if (done) {
			final int black_color = 255;
			if (owner.screen.getFade() < black_color) {
				if (owner.screen.getFade() > black_color- 10) {
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

	// MAGIC NUMBRS
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

}
