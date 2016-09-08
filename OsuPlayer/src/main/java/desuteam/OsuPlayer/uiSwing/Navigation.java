package desuteam.OsuPlayer.uiSwing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

import desuteam.OsuPlayer.uiSwing.Animation.AnimationListener;

public class Navigation {
	
	private MainPanel parent;
	
	private float yOffest;
	private Dimension size;
	private GradientPaint backgroundGrad;
	private float height = 150;
	private float gradHeight = 5;
	
	private Animation openAnimation,closeAnimation;
	
	private boolean isOpen = false;
	
	public Navigation(MainPanel parent) {
		this.parent = parent;
		size = MainPanel.size;
		yOffest = size.height + height + gradHeight;
		
		
		initAnimations();
	}
	
	
	
	private void initAnimations() {
		openAnimation = new Animation(
				new AnimationListener() {
					
					@Override
					public void doAnimation(float k) {
						k = (float)(1-Math.cos(Math.PI/2*k));
						yOffest = size.height + (1 - k) * (height + gradHeight);
					}
					
					@Override
					public void before() {
						yOffest = size.height + height + gradHeight;
						isOpen = false;
					}
					
					@Override
					public void after() {
						yOffest = size.height;
						isOpen = true;
					}
				}
		, 0.5f);
		
		closeAnimation = new Animation(
				new AnimationListener() {
					
					@Override
					public void doAnimation(float k) {
						k = (float)(1-Math.cos(Math.PI/2*k));
						yOffest = size.height + k * (height + gradHeight);
					}
					
					@Override
					public void before() {
						yOffest = size.height;
						isOpen = true;
					}
					
					@Override
					public void after() {
						yOffest = size.height + height + gradHeight;
						isOpen = false;
					}
				}
		, 0.5f);
		
		openAnimation.start();
	}



	public void draw(Graphics2D g){
		
		backgroundGrad = new GradientPaint(0, yOffest - height, new Color(0, 0, 0, 200), 0, yOffest - height - gradHeight, new Color(0, 0, 0, 0));
		
		g.setPaint(backgroundGrad);
		g.fillRect(0, (int)(yOffest - height - gradHeight), size.width, (int)gradHeight);
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, (int)(yOffest - height), size.width, (int)height);
		
		
		//draw navigation
		
		g.setColor(Color.WHITE);
		int x = 40 + 70 * 3;
		g.setFont(new Font("Consolas",Font.PLAIN,40));
		g.drawString(
				String.format(
						"[%d/%d] %s", 
						parent.getController().getCurrent(),
						parent.getController().getAll(),
						parent.getController().getCurrentTitle()
						), 
				x, (int) (yOffest - height/2 - 20) + 35);
		
		
	}
	
	public void click(int x, int y){
		
		if(openAnimation.isAct() || closeAnimation.isAct()) return;
		
		if(isOpen)
			closeAnimation.start();
		else
			openAnimation.start();
	}



	public void update(float dt) {
		openAnimation.update(dt);
		closeAnimation.update(dt);
		
	}
	
}
