package pitzik4.ageOfTheInternet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import pitzik4.ageOfTheInternet.graphics.Renderable;
import pitzik4.ageOfTheInternet.graphics.RenderableString;
import pitzik4.ageOfTheInternet.graphics.Sprite;

public class Menu implements Renderable, Tickable {
	public XButton exitButton;
	public Button[] buttons;
	private RenderableString renderableString;
	private int positionX = 0;
	private int positionY = 0;
	private int width = 0;
	private int height = 0;

	public Menu(Game owner, final int positionX, final int positionY, final int width, final String[] extraButtons, final String title) {
		this.positionX = positionX;
		this.positionY = positionY;
		
		renderableString = new RenderableString(title, positionX, positionY);
		this.height = extraButtons.length * (Sprite.SPRITE_HEIGHT + 2) - 2 + XButton.BU_SI + renderableString.height;
		
		if (height <= 0) {
			height = 1;
		} else {
			//nothing
		}
		
		this.width = renderableString.width < width ? width : renderableString.width;
		exitButton = new XButton(owner, positionX + width - XButton.BU_SI, positionY + renderableString.height);
		buttons = new Button[extraButtons.length];
		
		for (int i = 0; i < extraButtons.length; i++) {
			try {
				buttons[i] = new Button(owner, positionX, positionY + (i * (Sprite.SPRITE_HEIGHT + 2)) + XButton.BU_SI + renderableString.height, width,
						extraButtons[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private boolean exiting = false;
	public boolean exited = false;

	@Override
	public void tick() {
		
		for (Button button : buttons) {
			button.tick();
		}
		
		exitButton.tick();
		
		if (exiting && !exitButton.isClicked) {
			exited = true;
		}
		
		exiting = exitButton.isClicked;
	}

	@Override
	public BufferedImage draw() {
		BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = out.createGraphics();
		drawOn(g, positionX, positionY);
		g.dispose();
		
		return out;
	}

	@Override
	public void drawOn(Graphics2D g, final int scrollx, final int scrolly) {
		try{
			if (!exited) {
				for (Button b : buttons) {
					b.drawOn(g, scrollx, scrolly);
				}
				exitButton.drawOn(g, scrollx, scrolly);
				renderableString.drawOn(g, scrollx, scrolly);
			}
		}catch(NullPointerException graphicsNull){
			JOptionPane.showMessageDialog(null, "Have an error to load graphics2D ! Restart the game ", "Error Graphics2D", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public int getX() {
		return positionX;
	}

	@Override
	public int getY() {
		return positionY;
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
	public void goTo(final int x, final int y) {
		final int dx = x - this.positionX;
		final int dy = y - this.positionY;
		
		this.positionX = x;
		this.positionY = y;
		
		for (Button b : buttons) {
			b.goTo(b.getX() + dx, b.getY() + dy);
		}
		
		exitButton.goTo(exitButton.getX() + dx, exitButton.getY() + dy);
		renderableString.goTo(x, y);
	}

}
