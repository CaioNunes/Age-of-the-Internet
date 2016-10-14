package pitzik4.ageOfTheInternet.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pitzik4.ageOfTheInternet.RenderableTickable;

public class Animation implements RenderableTickable {
	private Renderable[] frames;
	private int[] durations;
	private int currentFrame = 0;
	private int countDown = 0;
	private int x = 0, y = 0;
	private boolean looping = false;
	private boolean going = false;

	public Animation(Renderable[] frames, int[] durations, int x, int y, boolean looping) {
		assert(frames != null): "Parameter Renderable[]-Frames is null";
		assert(durations != null): "Parameter Int[]-Durations is null";
		
		this.frames = frames;
		this.durations = durations;
		countDown = durations[currentFrame];
		this.x = x;
		this.y = y;
		this.looping = looping;
	}

	public Animation(Renderable[] frames, int duration, int x, int y, boolean looping) {
		//Assert is not allowed in this line.
		this(frames, new int[frames.length], x, y, looping);
		int[] durs = new int[frames.length];
		for (int i = 0; i < durs.length; i++) {
			durs[i] = duration;
		}
		durations = durs;
	}

	public Animation(int[] frames, int[] durations, int x, int y, boolean looping) {
		//Assert is not allowed in this line.
		this(new Renderable[frames.length], durations, x, y, looping);
		for (int i = 0; i < this.frames.length; i++) {
			this.frames[i] = new Sprite(frames[i], x, y, false);
		}
	}

	public Animation(int[] frames, int duration, int x, int y, boolean looping) {
		//Assert is not allowed in this line.
		this(new Renderable[frames.length], duration, x, y, looping);
		for (int i = 0; i < this.frames.length; i++) {
			this.frames[i] = new Sprite(frames[i], x, y, false);
		}
	}

	@Override
	public BufferedImage draw() {
		assert(frames != null): "Frames var is null";
		assert(frames[currentFrame] != null): "Frames[currentFrame] is null";
		return frames[currentFrame].draw();
	}

	@Override
	public void drawOn(Graphics2D g, int scrollx, int scrolly) {
		assert(g != null): "Parameter Graphics2D-g is null";
		assert(frames != null): "Frames var is null";
		assert(frames[currentFrame] != null): "Frames[currentFrame] is null";
		frames[currentFrame].drawOn(g, scrollx, scrolly);
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getXOffset() {
		assert(frames != null): "Frames var is null";
		assert(frames[currentFrame] != null): "Frames[currentFrame] is null";
		return frames[currentFrame].getXOffset();
	}

	@Override
	public int getYOffset() {
		assert(frames != null): "Frames var is null";
		assert(frames[currentFrame] != null): "Frames[currentFrame] is null";
		return frames[currentFrame].getYOffset();
	}

	@Override
	public void goTo(int x, int y) {
		int dx = x - this.x;
		int dy = y - this.y;
		this.x = x;
		this.y = y;
		assert(frames != null): "Frames var is null";
		for (Renderable r : frames) {
			assert(r != null): "r is null";
			r.goTo(r.getX() + dx, r.getY() + dy);
		}
	}

	public void go() {
		going = true;
	}

	public void stop() {
		going = false;
	}

	@Override
	public void tick() {
		if (going) {
			countDown--;
			if (countDown <= 0) {
				currentFrame++;
				assert(frames != null): "Frames var is null";
				if (currentFrame >= frames.length) {
					if (looping) {
						currentFrame = 0;
					} else {
						going = false;
						currentFrame--;
						return;
					}
				}
				assert(durations != null): "Durations is null";
				countDown = durations[currentFrame];
			}
		}
	}
	
	public Renderable[] getFrames() {
		return frames;
	}

	public void setFrames(Renderable[] frames) {
		this.frames = frames;
	}

	public int[] getDurations() {
		return durations;
	}

	public void setDurations(int[] durations) {
		this.durations = durations;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getCountDown() {
		return countDown;
	}

	public void setCountDown(int countDown) {
		this.countDown = countDown;
	}

	public boolean isLooping() {
		return looping;
	}

	public void setLooping(boolean looping) {
		this.looping = looping;
	}

	public boolean isGoing() {
		return going;
	}

	public void setGoing(boolean going) {
		this.going = going;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}


}
