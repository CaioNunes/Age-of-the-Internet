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

public class MoneyCutscene implements Stage {
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

	
	public MoneyCutscene(Game owner) {
		if (owner != null) {
			this.owner = owner;
		} else {
			// nothing to do.
		};
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
	public void drawOn(Graphics2D g, int scrollX, int scrollY) {
		scrollX = 0;
		scrollY = 0;
		g.drawImage(StartingCutscene.bg, 0, 0, null);
		drThompson.drawOn(g, 0, 0);
		screen.drawOn(g, 0, 0);
		digitizer.drawOn(g, 0, 0);
		if(dialogue != null) {
			dialogue.drawOn(g, 0, 0);
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
		if(dialogue == null) {
			lifeTime++;
		} else {
			dialogue.tick();
			final boolean spacebar_pressed = keysPressed.contains(SPACEBAR_BUTTON) && !lastKeysPressed.contains(SPACEBAR_BUTTON);
			if(spacebar_pressed) {
				if(dialogue.isGoing()) {
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
		if(lifeTime == 10) {
			final String dialog_text = "Alright, the institution has decided to start funding you." ;
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if(lifeTime == 12) {
			final String dialog_text = "Yay! I get money!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, StartingCutscene.tobyHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if(lifeTime == 14) {
			final String dialog_text = "...Yes. Yes, you get money. Use it to upgrade the computer.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if(lifeTime == 16) {
			final String dialog_text = "You can also steal money from corporations by hacking them.";
				dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, StartingCutscene.drThompsonHead, dialog_text);
				dialogue.go();
				lifeTime++;
		} else if(lifeTime == 18) {
			final String dialog_text = "First hacking... Now stealing money! This just gets better and better!";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, StartingCutscene.tobyHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if(lifeTime == 20) {
			final String dialog_text = "*sigh* Look out, though, corporations will un-hack their computers.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if(lifeTime == 22) {
			final String dialog_text = "And if you get disconnected from any of your instances, very bad.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if(lifeTime == 24) {
			final String dialog_text = "So, I lose if I get disconnected.";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, StartingCutscene.tobyHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if(lifeTime == 26) {
			final String dialog_text = "What do you think this is, a game?";
			dialogue = new InfoBox(box_diagonal_position, text_y_position, box_width, box_height, StartingCutscene.drThompsonHead, dialog_text);
			dialogue.go();
			lifeTime++;
		} else if(lifeTime == 28) {
			done = true;
		}
		if(owner.keysDown.contains(ENTER_BUTTON)) {
			done = true;
		}
		lastKeysPressed = keysPressed;
		if(done) {
			final int black_color = 255;
			if(owner.screen.getFade() < black_color) {
				if(owner.screen.getFade() > black_color - 10) {
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
		if(owner.screen.getFade() > clear_color && !done) {
			if(owner.screen.getFade() < clear_color + 10) {
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
