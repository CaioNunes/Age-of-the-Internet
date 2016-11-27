package pitzik4.ageOfTheInternet.tiles;

import pitzik4.ageOfTheInternet.Game;
import pitzik4.ageOfTheInternet.Level;
import pitzik4.ageOfTheInternet.Menu;
import pitzik4.ageOfTheInternet.graphics.Sprite;

public class EndTile extends Tile {
	private Game owner;
	private Menu rightClickMenu = null;
	public static final int RI_CLI_MENU_WIDTH = 96;
	public String[] riCliMenuOptions = { "Hack" };
	public String riCliMenuTitle = "Goal";

	public EndTile(int x, int y, Game owner) {
		setX(x);
		setY(y);
		this.owner = owner;
		setSprite(new Sprite(37, getX(), getY(), false));
	}

	@Override
	public void tick() {
		if (owner.mouseInsideOf(getX(), getY(), Sprite.SPRITE_WIDTH, Sprite.SPRITE_HEIGHT)) {
			if (owner.mouseDown) {
				if (rightClickMenu == null) {
					if (((Level) owner.currentLevel).canBeHackedBy(this) != null
							&& ((Level) owner.currentLevel).getRAM() >= hackCost()) {
						rightClickMenu = new Menu(owner, getX() + Sprite.SPRITE_WIDTH, getY(), RI_CLI_MENU_WIDTH,
								riCliMenuOptions, riCliMenuTitle);
						owner.screen.addRenderable(rightClickMenu);
					}
				}
			}
		}
		if (rightClickMenu != null) {
			rightClickMenu.tick();
			if (rightClickMenu.buttons[0].nowClicked) {
				hack();
				rightClickMenu.exited = true;
			}
			if (rightClickMenu.exited) {
				owner.screen.removeRenderable(rightClickMenu);
				rightClickMenu = null;
			}
		}
		object_ending(riCliMenuTitle);
		object_ending(riCliMenuOptions);
	}

	public void hack() {
		((Level) owner.currentLevel).hack(this);
	}

	@Override
	public void beOwned() {
		((Level) owner.currentLevel).win();
	}

	@Override
	public int hackCost() {
		int cost = 10;
		return cost;
	}
	//make it easyer to the garbage collector get it
	private void object_ending(Object object){
		object = null;
	}

}
