package pitzik4.ageOfTheInternet.tiles;

import java.util.Set;

import pitzik4.ageOfTheInternet.graphics.Sprite;

public class BrokenConnectionTile extends Tile {
	public static final Set<Integer> CONNECTABLE_TILES = ConnectionTile.connectableTiles();
	public static final int[] POSITIONAL_SPRITES = { 13, 2, 1, 3, 2, 2, 4, 17, 1, 6, 1, 18, 5, 19, 20, 21 };
	private byte neededComputers = 9;
	private byte ownedComputers = 0;
	private int position = 0;

	public BrokenConnectionTile(int x, int y, int[] neighbors, byte neededComputers) {
		setX(x);
		setY(y);
		
		for (int i = 0; i < neighbors.length; i++) {
			neighbors[i] = neighbors[i] & 0xFFFFFF;
		}
		
		boolean[] neighbools = new boolean[4];
		
		for (int i = 0; i < 4; i++) {
			neighbools[i] = (CONNECTABLE_TILES.contains(neighbors[i])) || ((neighbors[i] & 0xFFFF) == 0x8040);
		}
		
		if (neighbools[0]) {
			setPosition(getPosition() + 1);
		}
		
		if (neighbools[1]) {
			setPosition(getPosition() + 2);
		}
		
		if (neighbools[2]) {
			setPosition(getPosition() + 4);
		}
		
		if (neighbools[3]) {
			setPosition(getPosition() + 8);
		}
		
		setSprite(new Sprite(POSITIONAL_SPRITES[getPosition()] + 191, x, y, false));
		
		((Sprite) getSprite()).mergeSprite(new Sprite(207 - (neededComputers % 10)));
		setNeededComputers(neededComputers);
	}

	@Override
	public void tick() {

	}

	@Override
	public int hackCost() {
		return 0;
	}

	public boolean notifyOwnedChange(int newOwned) {
		if (newOwned != getOwnedComputers()) {
			setOwnedComputers((byte) (newOwned % 256));
			setSprite(new Sprite(POSITIONAL_SPRITES[getPosition()] + 191, getX(), getY(), false));
			((Sprite) getSprite()).mergeSprite(new Sprite(207 - (Math.abs(getNeededComputers() - getOwnedComputers()) % 10)));
		} else {
			// nothing
		}
		
		if (getOwnedComputers() >= getNeededComputers()) {
			setSprite(new Sprite(POSITIONAL_SPRITES[getPosition()],getX(), getY(), false));
			((Sprite) getSprite()).mergeSprite(new Sprite(207 - Math.abs(getNeededComputers() - getOwnedComputers()) % 10));
		} else {
			// nothing
		}
		
		return getOwnedComputers() >= getNeededComputers();
	}

	public boolean usable() {
		return getOwnedComputers() >= getNeededComputers();
	}
	
	public byte getNeededComputers(){
		return this.neededComputers;
	}

	public void setNeededComputers(byte neededComputer){
		this.neededComputers = neededComputer;
	}
	
	public byte getOwnedComputers(){
		return this.ownedComputers;
	}

	public void setOwnedComputers(byte ownedComputer){
		this.ownedComputers = ownedComputer;
	}
	
	public int getPosition(){
		return this.position;
	}

	public void setPosition(int position){
		this.position = position;
	}
}