package p3d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Test extends JComponent implements ActionListener
{
	
	Camera cam = new Camera();
	Renderer r = new Renderer(400, 400);
	Timer timer = new Timer(50, this);
	
	float[] vertex = {
			-0.5F,-0.5F,0,
			-0.5F,0.5F,0,
			-0.5F,0.5F,0,
			 0.5F,0.5F,0,
			 0.5F,0.5F,0,
			 0.5F,-0.5F,0,
			 0.5F,-0.5F,0,
			-0.5F,-0.5F,0,
			-0.5F,-0.5F,1,
			-0.5F,0.5F,1,
			-0.5F,0.5F,1,
			 0.5F,0.5F,1,
			 0.5F,0.5F,1,
			 0.5F,-0.5F,1,
			 0.5F,-0.5F,1,
			-0.5F,-0.5F,1,
			-0.5F,-0.5F,0,
			-0.5F,-0.5F,1,
			-0.5F,0.5F,0,
			-0.5F,0.5F,1,
			 0.5F,0.5F,0,
			 0.5F,0.5F,1,
			 0.5F,-0.5F,0,
			 0.5F,-0.5F,1
			
	};
	
	public Test() 
	{
		timer.start();
	}
	
	
	public static void main(String[] args) throws IOException 
	{
			JFrame f = new JFrame("3D");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setSize(400, 400);
			f.add(new Test());
			f.setVisible(true);
	}
	
	 @Override
	public void addNotify()
	{
		super.addNotify();
		addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if(code == KeyEvent.VK_W)
				{
					cam.position.z -= 0.05F;
					cam.update();
				}
				else if(code == KeyEvent.VK_S)
				{
					cam.position.z += 0.05F;
					cam.update();
				}
				else if(code == KeyEvent.VK_A)
				{
					cam.position.x += 0.05F;
					cam.update();
				}
				else if(code == KeyEvent.VK_D)
				{
					cam.position.x -= 0.05F;
					cam.update();
				}
				else if(code == KeyEvent.VK_SPACE)
				{
					cam.position.y += 0.05F;
					cam.update();
				}
				else if(code == KeyEvent.VK_CONTROL)
				{
					cam.position.y -= 0.05F;
					cam.update();
				}
				else if(code == KeyEvent.VK_0)
				{
					cam.scale.y -= 0.05F;
					cam.update();
				}
				else if(code == KeyEvent.VK_9)
				{
					cam.scale.y += 0.05F;
					cam.update();
				}
				
				
			}
		});
		setFocusable(true);
		requestFocus();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		r.clear();
		r.setColor(Color.WHITE);
		r.render(ShapeType.LINE, vertex , new BasicVertexShader() , cam);
		repaint();
	}
	
	@Override
	public void paint(Graphics g) 
	{
		g.drawImage(r.getBuffer(), 0, 0, null);
	}
}
