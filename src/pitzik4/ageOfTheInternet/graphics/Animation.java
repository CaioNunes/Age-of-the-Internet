package pitzik4.ageOfTheInternet.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pitzik4.ageOfTheInternet.RenderableTickable;

//Objective: This class is responsible for rendering game animations.

public class Animation implements RenderableTickable {
	private Renderable[] frames;
	private int[] durations;
	private int currentFrame = 0;
	private int countDown = 0;
	private int x = 0;
	private int y = 0;
	private boolean looping = false;
	private boolean going = false;

	public Animation(Renderable[] frames, int[] durations, int x, int y, boolean looping) {
		assert (frames != null) : "Parameter Renderable[]-Frames is null";
		assert (durations != null) : "Parameter Int[]-Durations is null";

		this.setFrames(frames);
		this.setDurations(durations);
		this.setCountDown(durations[getCurrentFrame()]);
		this.setX(x);
		this.setY(y);
		this.setLooping(looping);
	}

	public Animation(Renderable[] frames, int duration, int x, int y, boolean looping) {
		// Assert is not allowed in this line.
		this(frames, new int[frames.length], x, y, looping);
		int[] durs = new int[frames.length];

		for (int i = 0; i < durs.length; i++) {
			durs[i] = duration;
		}

		this.setDurations(durs);
	}

	public Animation(int[] frames, int[] durations, int x, int y, boolean looping) {
		// Assert is not allowed in this line.
		this(new Renderable[frames.length], durations, x, y, looping);
		for (int i = 0; i < this.getFrames().length; i++) {
			this.getFrames()[i] = new Sprite(frames[i], x, y, false);
		}
	}

	public Animation(int[] frames, int duration, int x, int y, boolean looping) {
		// Assert is not allowed in this line.
		this(new Renderable[frames.length], duration, x, y, looping);
		for (int i = 0; i < this.getFrames().length; i++) {
			this.getFrames()[i] = new Sprite(frames[i], x, y, false);
		}
	}

	@Override
	public BufferedImage draw() {
		assert (this.getFrames() != null) : "Frames var is null";
		assert (this.getFrames()[getCurrentFrame()] != null) : "Frames[currentFrame] is null";
		return this.getFrames()[this.getCurrentFrame()].draw();
	}

	@Override
	public void drawOn(Graphics2D g, int scrollx, int scrolly) {
		assert (g != null) : "Parameter Graphics2D-g is null";
		assert (this.getFrames() != null) : "Frames var is null";
		assert (this.getFrames()[this.getCurrentFrame()] != null) : "Frames[currentFrame] is null";
		this.getFrames()[this.getCurrentFrame()].drawOn(g, scrollx, scrolly);
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
		assert (this.getFrames() != null) : "Frames var is null";
		assert (this.getFrames()[this.getCurrentFrame()] != null) : "Frames[currentFrame] is null";
		return this.getFrames()[this.getCurrentFrame()].getXOffset();
	}

	@Override
	public int getYOffset() {
		assert (this.getFrames() != null) : "Frames var is null";
		assert (this.getFrames()[this.getCurrentFrame()] != null) : "Frames[currentFrame] is null";
		return this.getFrames()[this.getCurrentFrame()].getYOffset();
	}

	@Override
	public void goTo(int x, int y) {
		int dx = x - getX();
		int dy = y - getY();
		setX(x);
		setY(y);
		assert (this.getFrames() != null) : "Frames var is null";
		for (Renderable r : this.getFrames()) {
			assert (r != null) : "r is null";
			r.goTo(r.getX() + dx, r.getY() + dy);
		}
	}

	public void go() {
		this.setGoing(true);
	}

	public void stop() {
		this.setGoing(false);
	}

	@Override
	public void tick() {
		if (this.isGoing()) {
			this.setCountDown(this.getCountDown() - 1);
			if (this.getCountDown() <= 0) {
				this.setCurrentFrame(this.getCurrentFrame() + 1);
				assert (this.getFrames() != null) : "Frames var is null";
				if (this.getCurrentFrame() >= this.getFrames().length) {
					if (this.isLooping()) {
						this.setCurrentFrame(0);
					} else {
						this.setGoing(false);
						this.setCurrentFrame(this.getCurrentFrame() - 1);
						return;
					}
				}
				assert (this.getDurations() != null) : "Durations is null";
				this.setCountDown(this.getDurations()[this.getCurrentFrame()]);
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
