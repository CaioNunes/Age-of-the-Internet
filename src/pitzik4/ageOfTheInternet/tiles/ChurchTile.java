package pitzik4.ageOfTheInternet.tiles;

import java.awt.Graphics2D;

import pitzik4.ageOfTheInternet.Game;
import pitzik4.ageOfTheInternet.Level;
import pitzik4.ageOfTheInternet.Menu;
import pitzik4.ageOfTheInternet.graphics.Sprite;

public class ChurchTile extends Tile {
	private Game owner;
	private Menu rightClickMenu = null;
	private boolean yours = false;
	public static final int RI_CLI_MENU_WIDTH = 96;
	public String[] rightClickMenuOptions = { "Hack" };
	public String rightClickMenuTitle = "Church";

	public ChurchTile(int positionX, int positionY) {
		this(positionX, positionY, Game.game);
	}

	public ChurchTile(int positionX, int positionY, Game owner) {
		setX(positionX);
		setY(positionY);
		this.owner = owner;
		setSprite(new Sprite(42, positionX, positionY, false));
	}

	@Override
	public void tick() {
		if (owner.mouseInsideOf(getX(), getY(), Sprite.SPRITE_WIDTH, Sprite.SPRITE_HEIGHT)) {
			if (owner.mouseDown) {
				if (rightClickMenu == null) {
					if ((((Level) owner.currentLevel).canBeHackedBy(this) != null
							&& ((Level) owner.currentLevel).getRAM() >= hackCost()) || yours) {
						rightClickMenu = new Menu(owner, getX() + Sprite.SPRITE_WIDTH, getY(), RI_CLI_MENU_WIDTH,
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
			CorporationTile.fastestUnhack += 2;
			CorporationTile.slowestUnhack += 2;
			CorporationTile.inverseUnhackProbability += 25;
		}
	}

	@Override
	public void beOwned() {
		setSprite(new Sprite(41, getX(), getY(), false));
	}

	public void unHack() {
		if (yours) {
			yours = false;
			setSprite(new Sprite(42, getX(), getY(), false));
			rightClickMenuOptions = new String[1];
			rightClickMenuOptions[0] = "Hack";
			((Level) owner.currentLevel).unhack(this);
			CorporationTile.fastestUnhack -= 2;
			CorporationTile.slowestUnhack -= 2;
			CorporationTile.inverseUnhackProbability -= 25;
		}
	}

	@Override
	public int hackCost() {
		int cost = 5;
		return cost;
	}

}
