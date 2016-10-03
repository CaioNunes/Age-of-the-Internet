package pitzik4.ageOfTheInternet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import pitzik4.ageOfTheInternet.cutScenes.EndingCutscene;
import pitzik4.ageOfTheInternet.cutScenes.HackerCutscene;
import pitzik4.ageOfTheInternet.cutScenes.MoneyCutscene;
import pitzik4.ageOfTheInternet.cutScenes.StartingCutscene;
import pitzik4.ageOfTheInternet.graphics.Background;
import pitzik4.ageOfTheInternet.graphics.Screen;
import pitzik4.ageOfTheInternet.tiles.CorporationTile;
import pitzik4.ageOfTheInternet.tiles.HackerTile;
import sun.rmi.runtime.Log;

public class Game extends Applet
		implements Runnable, WindowListener, KeyListener, MouseListener, MouseMotionListener, FocusListener {

	private static final long serialVersionUID = 7763462792733778046L;

	public Screen screen;
	private BufferedImage image;
	public long tickAmount = 0;
	public short ticks = 0;
	public int frames = 0;
	public boolean stopping = false;
	public List<Tickable> tickables = new ArrayList<Tickable>();
	public Set<Integer> keysDown = new HashSet<Integer>();
	public volatile int mousePositionX = 0, mousePositionY = 0;
	public volatile boolean mouseDown = false;
	private volatile boolean mouseActuallyDown = false;
	private volatile boolean mouseReleasedTooSoon = false;
	public volatile boolean rightButton = false;
	public int level = 0;
	public Stage currentLevel;
	public Stage[] levels = new Stage[12];
	public PauseScreen pauseScreen;
	public boolean paused = false;
	public boolean wasPausing = false;
	public boolean pausedByKeyboard = false;
	public static Game game = new Game();
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final String NAME = "Age of the Internet";
	public static final double TICKS_PER_MILLI = 20.0 / 1000.0;

	@Override
	public void init() {
		game = this;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		screen = new Screen(this);
		addTickable(screen);
		for (int i = 0; i < levels.length; i++) {
			levels[i] = remakeLevel(i);
		}
	}
	
	
	// return player level.

	public Stage remakeLevel(int index) {
		assert(index>=0 && index<=11) : "Error on index value !";
		
		try{
			switch(index){
				case 0:
					return new StartingCutscene(this);
				
				case 1:
					return new TitleScreen(this);
				
				case 2:
					return new Level(1, this);
				
				case 3:
					return new Level(2, this);				
				
				case 4:
					return new MoneyCutscene(this);					
				
				case 5:
					return new Level(3, this);					
				
				case 6:
					return new Level(4, this);	
					
				case 7:
					return new Level(5, this);
				
				case 8:
					return new HackerCutscene(this);					
				
				case 9:
					return new Level(6, this);
				
				case 10:
					return new Level(7, this);			
				
				case 11:
					return new EndingCutscene(this);					
				
				default:
					return null;
			}
		}catch(IOException inputError){
			System.out.println("Ocorreu um erro na execução do programa. Por favor, reinicie !");
		}
		
		return null;
	}

	@Override
	public void start() {
		Background backGround = new Background(0, 0, WIDTH / Screen.POOR_RES, HEIGHT / Screen.POOR_RES);
		screen.addRenderable(backGround);
		addTickable(backGround);
		beginGame();
		
		new Thread(this).start();

	}

	@Override
	public void stop() {

	}

	@Override
	public void paint(Graphics graphics) {
		assert(graphics!=null): "The paramater graphics is null";		
		
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth() - 1, image.getHeight() - 1, null);
		frames++;
	}

	@Override
	public void update(Graphics graphics) {
		assert(graphics!=null): "The parameter graphics is null ";
		
		paint(graphics);
	}

	public void updateScreen() {
		image = screen.draw();
		repaint();
	}

	@Override
	public void run() {
		long time;
		double catchup = 0.0;
		long lastTime = System.currentTimeMillis();
		long slowness;
		long lastSecond = lastTime;
		
		while (!stopping) {
			time = System.currentTimeMillis();
			slowness = time - lastTime;
			catchup += slowness * TICKS_PER_MILLI;
			lastTime = time;
		
			while (catchup >= 1.0) {
				tick();
				catchup--;
			}
			
			updateScreen();
			final int TIMECORRECTION = 1000;
			if (time >= lastSecond + TIMECORRECTION) {
				System.out.format("%d FPS, %d ticks%n", frames, ticks);
				frames = 0;
				ticks = 0;
				lastSecond += TIMECORRECTION;
			} else {
				// nothing
			}
			
			if (slowness != 0) {
				try {
					long MILISSECONDS = (int) Math.round(100.0 / (double) slowness);
					Thread.sleep(MILISSECONDS);
				} catch (InterruptedException e) {
			
				}
			} else {
				// nothing
			}
		}
	}

	public void tick() {
		final int ESC_BUTTON = 27;
		final int P_BUTTON = 80;
		
		if (!paused) {
			ticks++;
			tickAmount++;
			
			for (Tickable tickable : tickables) {
				tickable.tick();
			}
			
			if (currentLevel.isClosing()) {
				nextLevel();
			} else if (currentLevel.isResetting()) {
				resetLevel();
			}
		}
		
		if ((keysDown.contains(ESC_BUTTON) || keysDown.contains(P_BUTTON)) && !wasPausing) {
			pause(!paused);
			pausedByKeyboard = paused;
			wasPausing = true;
		}
		
		if (!(keysDown.contains(ESC_BUTTON) || keysDown.contains(P_BUTTON))) {
			wasPausing = false;
		}
		
		if (mouseReleasedTooSoon) {
			mouseReleasedTooSoon = false;
			mouseDown = mouseActuallyDown;
		}
	}

	public void addTickable(Tickable tickable) {
		
		if(tickable != null){
			tickables.add(tickable);			
		}else{
			//nothing to do.
		}
	}

	public void removeTickable(Object tick) {
		if(tick != null){
			tickables.remove(tick);			
		}else{
			//nothing to do.
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.init();

		JFrame frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game);
		frame.addWindowListener(game);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.start();
	}

	// Where games start's
	public void beginGame() {
		screen.closeAllMenus();
		
		if (level >= levels.length) {
			level = 1;
		}
		
		Stage lvl = levels[level];
		screen.addRenderable(lvl);
		addTickable(lvl);
		currentLevel = lvl;
		screen.scrollTo(0 - (WIDTH / 2 - lvl.getWidth()) / 2, 0 - (HEIGHT / 2 - lvl.getHeight()) / 2);
		CorporationTile.resetStats();
		HackerTile.resetStats();
		level++;
	}

	public void nextLevel() {
		screen.removeRenderable(currentLevel);
		removeTickable(currentLevel);
		levels[level - 1] = remakeLevel(level - 1);
		beginGame();
	}

	public void resetLevel() {
		screen.removeRenderable(currentLevel);
		removeTickable(currentLevel);
		levels[level - 1] = remakeLevel(level - 1);
		level--;
		beginGame();
	}

	// Window event handling
	@Override
	public void windowActivated(WindowEvent eventt) {
		if (!pausedByKeyboard) {
			pause(false);
		}
	}

	@Override
	public void windowClosed(WindowEvent eventt) {
		pause(true);
	}

	@Override
	public void windowClosing(WindowEvent eventt) {
		pause(true);
	}

	@Override
	public void windowDeactivated(WindowEvent eventt) {
		pause(true);
	}

	@Override
	public void windowDeiconified(WindowEvent eventt) {
		
	}

	@Override
	public void windowIconified(WindowEvent eventt) {
		pause(true);
	}

	@Override
	public void windowOpened(WindowEvent eventt) {
		
	}

	// Keyboard event
	@Override
	public void keyPressed(KeyEvent eventt) {
		keysDown.add(eventt.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent eventt) {
		keysDown.remove(eventt.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent eventt) {

	}

	// Mouse events
	@Override
	public void mouseDragged(MouseEvent event) {
		mousePositionX = event.getX() / Screen.POOR_RES;
		mousePositionY = event.getY() / Screen.POOR_RES;
	}

	@Override
	public void mouseMoved(MouseEvent eventt) {
		mousePositionX = eventt.getX() / Screen.POOR_RES;
		mousePositionY = eventt.getY() / Screen.POOR_RES;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent event) {
		mouseDown = true;
		mouseActuallyDown = true;
		mouseReleasedTooSoon = true;
		rightButton = event.getButton() == MouseEvent.BUTTON3;
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (!mouseReleasedTooSoon) {
			mouseDown = false;
		}
		
		mouseActuallyDown = false;
	}

	public boolean mouseInsideOf(int positionX, int positionY, int width, int height) {
		positionX -= screen.getScrollX();
		positionY -= screen.getScrollY();
		
		return mousePositionX >= positionX && mousePositionX < positionX + width && mousePositionY > positionY
				&& mousePositionY <= positionY + height;
	}

	public boolean mouseInsideOf(int positionX, int positionY, int width, int height, boolean mindScroll) {
		if (mindScroll) {
			positionX -= screen.getScrollX();
			positionY -= screen.getScrollY();
		}
		
		return mousePositionX >= positionX && mousePositionX < positionX + width && mousePositionY > positionY
				&& mousePositionY <= positionY + height;
	}

	@Override
	public boolean isFocusable() {
		return true;
	}

	// Game Pause
	public void pause(boolean paused) {
		this.paused = paused;
		if (paused) {
			if (pauseScreen == null) {
				pauseScreen = new PauseScreen(4, 4, 312, 232);
				screen.addRenderable(pauseScreen);
			}
		} else {
			if (pauseScreen != null) {
				screen.removeRenderable(pauseScreen);
				pauseScreen = null;
			}
		}
	}

	// Focus event handling
	@Override
	public void focusGained(FocusEvent g) {
		pause(false);
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		pause(true);
	}

}
