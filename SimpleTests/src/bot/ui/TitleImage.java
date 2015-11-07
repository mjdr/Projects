package bot.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import bot.Loader.Local;
import bot.Post;

public class TitleImage extends JLabel
{
	private static final long serialVersionUID = 1L;
	BufferedImage img;
	
	public TitleImage()
	{
		setPreferredSize(new Dimension(500,600));
	}
	public void setImage(Post p)
	{
		if(p == null)
		{
			img = null;
			repaint();
			return;
		}
		try 
		{
			img = ImageIO.read(new File(Local.torrentDir  + p.dir+"/poster.jpg"));
			setPreferredSize(new Dimension(400,600));
			repaint();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			setSize(new Dimension(0,600));
			img = null;
			repaint();
		}
			
		
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		if(img == null)
			return;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		float scale = Math.min(
				(float)getSize().width/img.getWidth(), 
				(float)getSize().height/img.getHeight());
		g2d.drawImage(
				img,
				(int)((getSize().width-img.getWidth()*scale)/2),
				(int)((getSize().height-img.getHeight()*scale)/2),
				(int)(img.getWidth()*scale),
				(int)(img.getHeight()*scale),
				null);
		
	}
	
}
