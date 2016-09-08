package desuteam.OsuPlayer.uiSwing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import desuteam.OsuPlayer.Controller;
import desuteam.OsuPlayer.UIView;

public class MainPanel extends JPanel implements UIView {
	
	private static final long serialVersionUID = 1L;


	private Timer timer = new Timer(10, (l)->{update();repaint();});
	
	
	private Controller controller;
	private Background background;
	private Navigation navigation;
	
	private long lastUpdate = System.currentTimeMillis(); 

	public static final Dimension size = new Dimension(1600, 860);
	
	public MainPanel(String[] args) {
		super(true);

		size.width = Integer.parseInt(args[1]);
		size.height = Integer.parseInt(args[2]);
		
		
		background = new Background(size);
		navigation = new Navigation(this);
		
		
		
		
		setFocusable(true);
		requestFocus();
		addKeyListener(new KeyAdapter() {
			
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					controller.prevTrack();
					break;
				case KeyEvent.VK_RIGHT:
					controller.nextTrack();
					break;
				case KeyEvent.VK_SPACE:
					controller.pauseOrPlay();
					break;

				}
			}
		});
		
		
	}
	
	public void init(){
		controller.initLibrary();
		updateTrackInfo();
		controller.playCurrent();
	}
	
	
	private void update() {
		long updateTime = System.currentTimeMillis();
		float dt = (updateTime - lastUpdate)/1000f;
		controller.update(dt);
		navigation.update(dt);
		
		
		
		lastUpdate = updateTime;
	}


	@Override
	public void addNotify() {
		super.addNotify();
		
		
		setPreferredSize(size);
		timer.start();
		
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				navigation.click(e.getX(), e.getY());
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		background.draw(g2d);
		navigation.draw(g2d);
	}

	public void updateTrackInfo() {
		background.changeBackground(controller.getCurrentBackground());
	}
	
	public Controller getController() {
		return controller;
	}


	@Override
	public void setController(Controller controller) {
		this.controller = controller;
		
	}
}
