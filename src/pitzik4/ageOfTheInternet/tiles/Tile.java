package pitzik4.ageOfTheInternet.tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pitzik4.ageOfTheInternet.Game;
import pitzik4.ageOfTheInternet.Tickable;
import pitzik4.ageOfTheInternet.graphics.Renderable;

public abstract class Tile implements Renderable, Tickable {
	private Renderable sprite;
	private int x, y;

	public static Tile getNewTile(int id, int x, int y, Tile[] neighbors, Game owner) {
		int[] ints = new int[neighbors.length];
		for (int i = 0; i < neighbors.length; i++) {
			ints[i] = intFromTile(neighbors[i]);
		}
		return getNewTile(id, x, y, ints, owner);
	}

	public static int intFromTile(Tile t) {

		if (t instanceof ComputerTile) {
			return 0x0000FF;
		} else {
			// Do nothing
		}

		if (t instanceof HomeTile) {
			return 0x00FFFF;
		} else {
			// Do nothing
		}

		if (t instanceof ConnectionTile) {
			return 0x00FF00;
		} else {
			// Do nothing
		}

		if (t instanceof EndTile) {
			return 0xFFFF00;
		} else {
			// Do nothing
		}

		if (t instanceof CorporationTile) {
			return 0xFF00FF;
		} else {
			// Do nothing
		}

		if (t instanceof ChurchTile) {
			return 0x808080;
		} else {
			// Do nothing
		}

		if (t instanceof HackerTile) {
			return 0x008000;
		} else {
			// Do nothing
		}

		if (t instanceof BrokenConnectionTile) {
			return 0x008040;
		} else {
			// Do nothing
		}
		return 0;
	}

	public static Tile getNewTile(int id, int x, int y, int[] neighbors, Game owner) {
		// using a Mask with id. The last byte is ignored.
		id = id & 0xFFFFFF;
		switch (id) {
		case 0x0000FF:
			return new ComputerTile(x, y, owner);

		case 0x00FFFF:
			return new HomeTile(x, y, owner);

		case 0x00FF00:
			return new ConnectionTile(x, y, neighbors);

		case 0xFFFF00:
			return new EndTile(x, y, owner);

		case 0xFF00FF:
			return new CorporationTile(x, y, owner);

		case 0x808080:
			return new ChurchTile(x, y, owner);

		case 0x008000:
			return new HackerTile(x, y, owner);

		default:
			// Do nothing
		}

		if ((id & 0x00FFFF) == 0x008040) {
			return new BrokenConnectionTile(x, y, neighbors, (byte) (id >> 16));
		}

		return null;
	}

	@Override
	public BufferedImage draw() {
		return getSprite().draw();
	}

	@Override
	public void drawOn(Graphics2D g, int scrollx, int scrolly) {
		getSprite().drawOn(g, scrollx, scrolly);
		furtherDraw(g, scrollx, scrolly);
	}

	public void furtherDraw(Graphics2D g, int scrollx, int scrolly) {

	}

	public Renderable getSprite() {
		return this.sprite;
	}

	public void setSprite(Renderable sprite) {
		this.sprite = sprite;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
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
		setX(x);
		setY(y);
		getSprite().goTo(x, y);
	}

	public void setTilePositions(int x, int y) {
		setX(x);
		setY(y);
	}

	public void beOwned() {

	}

	public void beEvil() {

	}

	public void startEvil() {

	}

	public void deEvil() {

	}

	public boolean isEvil() {
		return false;
	}

	public boolean canBeEvil() {
		return false;
	}

	public void unHack() {

	}

	public abstract int hackCost();

}
