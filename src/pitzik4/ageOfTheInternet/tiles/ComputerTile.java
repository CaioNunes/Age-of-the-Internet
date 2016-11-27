package pitzik4.ageOfTheInternet.tiles;

import java.awt.Graphics2D;

import pitzik4.ageOfTheInternet.Game;
import pitzik4.ageOfTheInternet.Level;
import pitzik4.ageOfTheInternet.Menu;
import pitzik4.ageOfTheInternet.graphics.Sprite;

public class ComputerTile extends Tile {
	private Game owner;
	private Menu rightClickMenu = null;
	private boolean yours = false;
	private boolean theirs = false;
	public static final int RIGHT_CLICK_MENU_WIDTH = 96;
	public String[] rightClickMenuOptions = { "Hack" };
	public String rightClickMenuTitle = "Computer";

	public ComputerTile(int x, int y) {
		this(x, y, Game.game);
	}

	public ComputerTile(int positionX, int positionY, Game owner) {
		setX(positionX);
		setY(positionY);
		this.owner = owner;
		setSprite(new Sprite(33, positionX, positionY, false));
	}

	@Override
	/*
	 * The tick method set the behavior of the many things. And this override 
	 * of the ComputerTile show if the computer can be hacked by the cost or unhacked.
	 */
	public void tick() {
		if (owner.mouseInsideOf(getX(), getY(), Sprite.SPRITE_WIDTH, Sprite.SPRITE_HEIGHT)) {
			if (owner.mouseDown) {
				if (rightClickMenu == null) {
					if ((((Level) owner.currentLevel).canBeHackedBy(this) != null
							&& ((Level) owner.currentLevel).getRAM() >= hackCost()) || yours) {
						rightClickMenu = new Menu(owner, getX() + Sprite.SPRITE_WIDTH, getY(), RIGHT_CLICK_MENU_WIDTH,
								rightClickMenuOptions, rightClickMenuTitle);
						owner.screen.addRenderable(rightClickMenu);
					}
				}
			}
		}
		if (rightClickMenu != null) {
			rightClickMenu.tick();
			if (rightClickMenu.buttons[0].nowClicked) {
				if (!yours) {
					hack();
				} else {
					unHack();
				}
				rightClickMenu.exited = true;
			}
			if (rightClickMenu.exited) {
				owner.screen.removeRenderable(rightClickMenu);
				rightClickMenu = null;
			}
		}
		object_ending(rightClickMenuOptions);
		object_ending(rightClickMenu);
	}

	@Override
	public void furtherDraw(Graphics2D graphics, int scrollx, int scrolly) {
		// if(rightClickMenu != null)
		// rightClickMenu.drawOn(g, scrollx, scrolly);
	}

	public void hack() {
		if (!yours) {
			yours = true;
			// sprite = new Sprite(32, x, y, false);
			rightClickMenuOptions[0] = "Unhack";
			((Level) owner.currentLevel).hack(this);
		}
	}

	@Override
	public void startEvil() {
		theirs = true;
	}

	@Override
	public void deEvil() {
		theirs = false;
		setSprite(new Sprite(33, getX(), getY(), false)); // 33?
	}

	@Override
	public void beOwned() {
		setSprite(new Sprite(32, getX(), getY(), false)); // 32?
	}

	@Override
	public void beEvil() {
		setSprite(new Sprite(22, getX(), getY(), false)); // 22
	}

	@Override
	public boolean canBeEvil() {
		return true;
	}

	public void unHack() {
		if (yours) {
			yours = false;
			setSprite(new Sprite(33, getX(), getY(), false));
			rightClickMenuOptions = new String[1];
			rightClickMenuOptions[0] = "Hack";
			((Level) owner.currentLevel).unhack(this);
		}
	}

	@Override
	public int hackCost() {
		int cost = 5;
		return cost;
	}

	@Override
	public boolean isEvil() {
		return theirs;
	}
	
	//make it easyer to the garbage collector get it
	private void object_ending(Object object){
		object = null;
	}
}
