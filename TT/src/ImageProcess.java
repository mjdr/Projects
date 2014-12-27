
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;



public class ImageProcess extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3418389910373808156L;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) 
	{
		JFrame f = new JFrame();
		f.setPreferredSize(new Dimension(800, 600));
		f.setSize(new Dimension(800, 600));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.getContentPane().add(new ImageProcess());
		f.resize(800, 601);
	}
	
	BufferedImage input;
	BufferedImage output;

	Slider s;
	Slider.Point[] ps = new Slider.Point[6];
	
	Timer update = new Timer(300, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			repaint();
		}
	});
	
	public ImageProcess() 
	{
		super();
		output = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		try 
		{
			init();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void addNotify() 
	{
		super.addNotify();
		update.start();
		
	}
	void init() throws Exception
	{
		
		int j = 1;
		for(int i = 0;i < 6;i++)
		{j+=2;
			ps[i] = new Slider.Point(
					300 + 250 * (float)Math.cos(1.6*Math.PI / 2 + 2 * 3.141592/ps.length * (j % (ps.length - 1))),
					300 + 250 * (float)Math.sin(1.6*Math.PI / 2 + 2 * 3.141592/ps.length * (j % (ps.length - 1))));
			
		}
		
		//ps[1] = new Slider.Point(300, 150);
		//ps[0] = new Slider.Point(500, 150);
		
		input = ImageIO.read(new File("C:/Users/Бог/Desktop/шьпы/nozaki-kun.jpeg"));
		
		long t = System.currentTimeMillis();
		s = new Slider(ps,50, Slider.SType.LINER);
		output.getGraphics().drawImage(input,0,0,null);
		//System.out.println(s.getSize() / 1024F);
		
		s.preRender();
		System.out.println(s.getSize() / 1024F);
		System.out.println((System.currentTimeMillis() - t));
		System.out.println("W: " + s.getBufferWidth());
		System.out.println("H: " + s.getBufferWidth());
		s.render(output.getGraphics());
	}
	@Override
	public void paint(Graphics g) 
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 800, 600);
		g.drawImage(output,0,0,800,600,null);
	}

}
