package pitzik4.ageOfTheInternet.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import pitzik4.ageOfTheInternet.Game;
import pitzik4.ageOfTheInternet.Menu;
import pitzik4.ageOfTheInternet.Tickable;

public class Screen implements Tickable {
	private Game owner;
	private int width;
	private int height;
	private int scrollx = 0, scrolly = 0;
	private volatile List<Renderable> renderables = new ArrayList<Renderable>();
	private byte rumbleX = 0, rumbleY = 0;
	private byte rumbleForce = 0;
	private Random rumbleRand = new Random(System.currentTimeMillis());
	private int fade = 0;
	private int xVelocity = 0;
	private int yVelocity = 0;
	public static final int POOR_RES = 2;
	public static final BufferedImage spritesheet = spritesheet("grid");
	public static final BufferedImage font = spritesheet("font");

	public Screen(Game game) {
		assert(game != null): "Paramater Game is null";
		this.owner = game;
		width = owner.getWidth() / POOR_RES + 1;
		height = owner.getHeight() / POOR_RES + 1;
	}

	public static BufferedImage spritesheet(String name) {
		assert(name != null): "String name is null";
		BufferedImage bufferedImageOut = null;
		try {
			bufferedImageOut = ImageIO.read(Game.class.getResourceAsStream("/" + name + ".gif"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return bufferedImageOut;
	}

	public synchronized BufferedImage draw() {
		BufferedImage bufferedImageOut = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImageOut.createGraphics();
		graphics.setPaint(Color.MAGENTA);
		graphics.fillRect(0, 0, width - 1, height - 1);
		for (Renderable renderable : renderables) {
			renderable.drawOn(graphics, scrollx, scrolly);
		}
		// g.setPaint(Color.WHITE);
		// g.drawString("Test", 10, 10);
		graphics.setPaint(new Color(fade << 24, true));
		graphics.fillRect(0, 0, width - 1, height - 1);
		graphics.dispose();
		BufferedImage bufferedImageOut2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		graphics = bufferedImageOut2.createGraphics();
		graphics.setPaint(Color.BLACK);
		graphics.fillRect(0, 0, width - 1, height - 1);
		graphics.drawImage(bufferedImageOut, rumbleX, rumbleY, null);
		graphics.dispose();
		assert(bufferedImageOut2 != null): "Return bufferedImageOut2 is null";
		return bufferedImageOut2;
	}

	public synchronized void addRenderable(Renderable r) {
		assert(r != null): "Parameter Renderable is null";
		renderables.add(r);
	}

	public synchronized void addRenderable(int index, Renderable r) {
		assert(r != null): "Parameter Renderable is null";
		renderables.add(index, r);
	}

	public synchronized void removeRenderable(Renderable r) {
		assert(r != null): "Parameter Renderable is null";
		while (renderables.contains(r)) {
			renderables.remove(r);
		}
	}

	public synchronized void removeRenderable(int index) {
		renderables.remove(index);
	}

	public synchronized void sendToFront(Renderable r) {
		assert(r != null): "Parameter Renderable is null";
		renderables.remove(r);
		renderables.add(r);
	}

	public synchronized void sendToBack(Renderable r) {
		assert(r != null): "Parameter Renderable is null";
		renderables.remove(r);
		renderables.add(0, r);
	}

	public synchronized void sendTo(int index, Renderable r) {
		assert(r != null): "Parameter Renderable is null";
		renderables.remove(r);
		renderables.add(index, r);
	}

	// to fix magic numbers
	@Override
	public void tick() {
		if (rumbleForce > 0) {
			rumbleX = (byte) (rumbleRand.nextInt(rumbleForce) - (rumbleForce / 2));
			rumbleY = (byte) (rumbleRand.nextInt(rumbleForce) - (rumbleForce / 2));
		}
		if (owner.keysDown.contains(37)) {
			if (xVelocity > -6) {
				xVelocity -= 1;
			}
		}
		if (owner.keysDown.contains(39)) {
			if (xVelocity < 6) {
				xVelocity += 1;
			}
		}
		if (owner.keysDown.contains(38)) {
			if (yVelocity > -6) {
				yVelocity -= 1;
			}
		}
		if (owner.keysDown.contains(40)) {
			if (yVelocity < 6) {
				yVelocity += 1;
			}
		}
		if (!(owner.keysDown.contains(37) || owner.keysDown.contains(39))) {
			if (xVelocity < 0) {
				xVelocity += 1;
			} else if (xVelocity > 0) {
				xVelocity -= 1;
			}
		}
		if (!(owner.keysDown.contains(38) || owner.keysDown.contains(40))) {
			if (yVelocity < 0) {
				yVelocity += 1;
			} else if (yVelocity > 0) {
				yVelocity -= 1;
			}
		}
		if (owner.currentLevel.isScrollable()) {
			scrollx += xVelocity;
			scrolly += yVelocity;
		}
	}

	public void rumble(int i) {
		rumbleForce = (byte) i;
	}

	public int getFade() {
		return fade;
	}

	public void fadeTo(int fade) {
		this.fade = fade;
	}

	public void scrollTo(int scrollx, int scrolly) {
		this.scrollx = scrollx;
		this.scrolly = scrolly;
	}

	public void scrollToX(int scrollx) {
		this.scrollx = scrollx;
	}

	public void scrollToY(int scrolly) {
		this.scrolly = scrolly;
	}

	public Point getScroll() {
		return new Point(scrollx, scrolly);
	}

	public int getScrollX() {
		return scrollx;
	}

	public int getScrollY() {
		return scrolly;
	}

	public void closeAllMenus() {
		for (Iterator<Renderable> it = renderables.iterator(); it.hasNext();) {
			assert(it != null): "Renderable Iterator is null";
			Renderable render = it.next();
			if (render instanceof Menu) {
				it.remove();
			}
		}
	}

}
