package shape;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Canvas extends Component implements ActionListener , Runnable
{
	BufferedImage original;
	Vector<Rect>shares = new Vector<Rect>();
	Timer timer = new Timer(100, this);
	int index = 0;
	
	public Canvas(BufferedImage image) 
	{
		original = image;
		setPreferredSize(new Dimension(original.getWidth() , original.getHeight()));
		shares.add(
				new Rect(
						0, 0, 
						original.getWidth(), 
						original.getHeight(), 
						original));
		timer.start();
		new Thread(this).start();
		new Thread(this).start();
		
		//System.out.println(new Rect(0, 0, 1, 1, original).color);
		
	}
	@Override
	public void addNotify() 
	{
		super.addNotify();
		setPreferredSize(new Dimension(original.getWidth() , original.getHeight()));
		addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mousePressed(MouseEvent e) 
			{
				BufferedImage screen = new BufferedImage(
						original.getWidth(), 
						original.getHeight(), 
						BufferedImage.TYPE_INT_RGB);
				Graphics g = screen.getGraphics();
				paint(g);
				try 
				{
					ImageIO.write(screen, "PNG", new File("F://save"+index+".png"));
				} catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				index++;
			}
		});
	}
	
	@Override
	public void paint(Graphics g) 
	{
		synchronized(shares)
		{
			for(Rect s : shares)
			{
				g.setColor(s.color);
				g.fillRect(s.x1, s.y1, s.x2 - s.x1, s.y2 - s.y1);
				g.setColor(Color.RED);
				g.drawRect(s.x1, s.y1, s.x2 - s.x1, s.y2 - s.y1);
			}
		}
	}
	
	public void step()
	{
		Rect r;
		synchronized(shares)
		{
			Collections.sort(shares);
			r = shares.lastElement();
			shares.remove(shares.size() - 1);
		 
		//   @*
		//   **
		shares.add(new Rect(r.x1, r.y1, (r.x2+r.x1)/2, (r.y2+r.y1)/2, original));
		//   *@
		//   **
		shares.add(new Rect((r.x2+r.x1)/2, r.y1, r.x2, (r.y2+r.y1)/2, original));
		//   **
		//   @*
		shares.add(new Rect(r.x1, (r.y2+r.y1)/2, (r.x2+r.x1)/2, r.y2, original));
		//   **
		//   *@
		shares.add(new Rect((r.x2+r.x1)/2, (r.y2+r.y1)/2, r.x2, r.y2, original));
		}
	}
	
	public static void main(String[] args) throws IOException 
	{
		BufferedImage image = ImageIO.read(new File("F://osu.png"));
		JFrame f = new JFrame();	
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(image.getWidth(), image.getHeight());
		f.getContentPane().add(new Canvas(image));
		f.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		repaint();
	}
	@Override
	public void run() 
	{
		while(true)
		{
			step();
			try
			{
				Thread.sleep(10); 
			}
			catch(InterruptedException e){e.printStackTrace();}
		}
	}
	
	
}
