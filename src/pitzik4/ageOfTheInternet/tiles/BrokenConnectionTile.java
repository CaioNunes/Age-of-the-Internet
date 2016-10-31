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
		this.setX(x);
		this.setY(y);
		
		for (int i = 0; i < neighbors.length; i++) {
			neighbors[i] = neighbors[i] & 0xFFFFFF;
		}
		
		boolean[] neighbools = new boolean[4];
		
		for (int i = 0; i < 4; i++) {
			neighbools[i] = (CONNECTABLE_TILES.contains(neighbors[i])) || ((neighbors[i] & 0xFFFF) == 0x8040);
		}
		
		if (neighbools[0]) {
			this.setPosition(this.getPosition() + 1);
		}
		
		if (neighbools[1]) {
			this.setPosition(this.getPosition() + 2);
		}
		
		if (neighbools[2]) {
			this.setPosition(this.getPosition() + 4);
		}
		
		if (neighbools[3]) {
			this.setPosition(this.getPosition() + 8);
		}
		
		this.setSprite(new Sprite(POSITIONAL_SPRITES[this.getPosition()] + 191, x, y, false));
		
		((Sprite) this.getSprite()).mergeSprite(new Sprite(207 - (neededComputers % 10)));
		this.setNeededComputers(neededComputers);
	}

	@Override
	public void tick() {

	}

	@Override
	public int hackCost() {
		int cost =0;
		return cost;
	}

	public boolean notifyOwnedChange(int newOwned) {
		if (newOwned != this.getOwnedComputers()) {
			this.setOwnedComputers((byte) (newOwned % 256));
			this.setSprite(new Sprite(POSITIONAL_SPRITES[this.getPosition()] + 191, this.getX(), this.getY(), false));
			((Sprite) this.getSprite()).mergeSprite(new Sprite(207 - (Math.abs(this.getNeededComputers() - this.getOwnedComputers()) % 10)));
		} else {
			// nothing
		}
		
		if (this.getOwnedComputers() >= this.getNeededComputers()) {
			this.setSprite(new Sprite(POSITIONAL_SPRITES[this.getPosition()],this.getX(), this.getY(), false));
			((Sprite) this.getSprite()).mergeSprite(new Sprite(207 - Math.abs(this.getNeededComputers() - this.getOwnedComputers()) % 10));
		} else {
			// nothing
		}
		
		return this.getOwnedComputers() >= this.getNeededComputers();
	}

	public boolean usable() {
		return this.getOwnedComputers() >= this.getNeededComputers();
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