package pitzik4.ageOfTheInternet.tiles;

import pitzik4.ageOfTheInternet.Game;
import pitzik4.ageOfTheInternet.Level;
import pitzik4.ageOfTheInternet.Menu;
import pitzik4.ageOfTheInternet.graphics.Sprite;

public class HomeTile extends Tile {
	public static final int ANIMATION_COUNTDOWN = 4;
	private int animationCountDown = ANIMATION_COUNTDOWN;
	private int currentAnimation = 34;
	private Menu rightClickMenu = null;
	private Game owner;
	public static final int RI_CLI_MENU_WIDTH = 96;
	public static final String[] RI_CLI_MENU_OPTIONS = { "Upgrade" };
	public static final String RI_CLI_MENU_TITLE = "Home";

	public HomeTile(int x, int y, Game owner) {
		setX(x);
		setY(y);
		this.owner = owner;
		setSprite(new Sprite(currentAnimation, getX(), getY(), false));
	}

	@Override
	public void tick() {
		animationCountDown--;
		if (animationCountDown <= 0) {
			animationCountDown = ANIMATION_COUNTDOWN;
			currentAnimation++;
			if (currentAnimation > 36) {
				currentAnimation = 34;
			}
			setSprite(new Sprite(currentAnimation, getX(), getY(), false));
		}
		if (owner.mouseInsideOf(getX(), getY(), Sprite.SPRITE_WIDTH, Sprite.SPRITE_HEIGHT)) {
			if (owner.mouseDown) {
				if (rightClickMenu == null && ((Level) owner.currentLevel).canUpgrade()) {
					rightClickMenu = new Menu(owner, getX() + Sprite.SPRITE_WIDTH, getY(), RI_CLI_MENU_WIDTH,
							RI_CLI_MENU_OPTIONS, RI_CLI_MENU_TITLE);
					owner.screen.addRenderable(rightClickMenu);
				}
			}
		}
		if (rightClickMenu != null) {
			rightClickMenu.tick();
			if (rightClickMenu.buttons[0].nowClicked) {
				((Level) owner.currentLevel).upgradeHome();
				rightClickMenu.exited = true;
			}
			if (rightClickMenu.exited) {
				owner.screen.removeRenderable(rightClickMenu);
				rightClickMenu = null;
			}
		}
		
		object_ending(RI_CLI_MENU_OPTIONS);
		object_ending(RI_CLI_MENU_TITLE);
	}

	@Override
	public int hackCost() {
		int cost = 0;
		return cost;
	}
	
	//make it easyer to the garbage collector get it
	private void object_ending(Object object){
        object = null;
	}

}
