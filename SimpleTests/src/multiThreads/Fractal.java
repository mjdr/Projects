package multiThreads;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Fractal extends JComponent
{
	private static final long serialVersionUID = 1L;
	public static final int 
		WIDTH = 1600,
		HEIGHT = 900,
		ITERATIONS = 20;
	public static final double
		SX = -2,
		EX = 2,
		SY = -1.125,
		EY = 1.125,
		ALIASING = 1.2
		;
	Point2D.Double C = new Point2D.Double(
			//-0.835, -0.2321
			//-0.4,0.6,
			//-0.70176,-0.3842,
			0,0
			);
	
	
	public BufferedImage img;
	Thread t1;
	Thread t2;
	
	//sing stuff
	Timer updTimer = new Timer(500, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			repaint();
		}
	});
	
	
	public Fractal() throws IOException 
	{
		img = new BufferedImage((int)(WIDTH * ALIASING), (int)(HEIGHT  * ALIASING), BufferedImage.TYPE_INT_RGB);
		

		t1 = new Thread(new Worker(img, 0, 0, (int)(WIDTH * ALIASING), (int)(HEIGHT/2 * ALIASING)));
		t2 = new Thread(new Worker(img, 0,  (int)(HEIGHT/2 * ALIASING), (int)(WIDTH * ALIASING), (int)(HEIGHT/2 * ALIASING)));
		
		ImageIO.write(img, "PNG", new File("O://fract.png"));
	}
	
	@Override
	public void addNotify() 
	{
		super.addNotify();
		
		addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				System.exit(0);
			}
		});
		t1.start();
		t2.start();

		updTimer.start();
	}
	
	
	public double map(double sx1 , double ex1 , double sx2 , double ex2 , double x)
	{
		return sx2 + (ex2 - sx2) * (x-sx1)/(ex1 - sx1);
	}
	
	public int getColor(int iter , double dist)
	{
		int c  = (int) (255 - (dist * 4 + iter));
		
		//c = (int) (255*Math.pow(2, -(float)iter/Math.log(ITERATIONS)/2));
		if(c < 0)
			c = 0;
		return (c << 16) | (c << 8) | c;
	}
	
	@Override
	public void paint(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.drawImage(img, 0, 0, WIDTH , HEIGHT , null);
	}
	
	class Worker implements Runnable
	{
		
		BufferedImage img;
		int sx,sy,w,h;
		
		
		public Worker(BufferedImage img, int x, int y, int w, int h) 
		{
			this.img = img;
			this.sx = x;
			this.sy = y;
			this.w = w;
			this.h = h;
		}

		@Override
		public void run()
		{
			Point2D.Double curr = new Point2D.Double();
			Point2D.Double tmp = new Point2D.Double();
			Point2D.Double C = new Point2D.Double();
			int iter;
			
			for(int x = 0;x < w;x++)
			{
				for(int y = 0;y < h;y++)
				{
					curr.x = map(0,WIDTH * ALIASING , SX , EX , sx + x);
					curr.y = map(0,HEIGHT * ALIASING , SY , EY , sy + y);
					C.setLocation(curr);
					iter = 0;
					for(;iter < ITERATIONS;iter++)
					{
						tmp.x = curr.x *curr.x - curr.y * curr.y + C.x;
						tmp.y = 2 * curr.x * curr.y + C.y;
						curr.setLocation(tmp);
						if(curr.distanceSq(0, 0) > 4)
							break;
					}
					img.setRGB(sx + x,sy + y, getColor(iter,curr.distance(0, 0)));
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		JComponent c = new Fractal();
		
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setUndecorated(true);
		GraphicsEnvironment
        .getLocalGraphicsEnvironment().getScreenDevices()[0].setFullScreenWindow(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(c);
		frame.setVisible(true);
		
	}
}
