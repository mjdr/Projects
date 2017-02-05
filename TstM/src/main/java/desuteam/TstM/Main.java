package desuteam.TstM;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.FrameGrabber.Exception;

public class Main extends JPanel implements Runnable {


	BufferedImage image;
	BufferedImage map;
	JFrame frame;
	
	public static void main(String[] args) {
		new Main();
	}
	
	
	public Main() {
		frame = new JFrame("Video");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(this);
		frame.setVisible(true);
	}
	
	
	@Override
	public void addNotify() {
		super.addNotify();
		new Thread(this).start();
	}
	
	@Override
	public void paint(Graphics g) {
		if(image != null && map != null){
			g.drawImage(image, 0, 0,image.getWidth()/2,image.getHeight()/2, null);
			g.drawImage(map, image.getWidth()/2, 0,image.getWidth()/2,image.getHeight()/2, null);
		}
	}
	
	
	public void run() {
		
		FrameGrabber grabber = new FFmpegFrameGrabber("D://video.mp4");
		Java2DFrameConverter converter = new Java2DFrameConverter();
		
		Frame frame;
		
		
		
		try {
			
			grabber.start();
			FrameRecorder recorder = new FFmpegFrameRecorder("D://dst.mp4", grabber.getAudioChannels());
			
			recorder.setImageWidth(grabber.getImageWidth());
			recorder.setImageHeight(grabber.getImageHeight());
			
			recorder.start();
			
			setPreferredSize(new Dimension(2*grabber.getImageWidth()/2, grabber.getImageHeight()/2));
			this.frame.pack();
			
			while((frame = grabber.grabFrame()) != null){
				
				recorder.setTimestamp(grabber.getTimestamp());
				if(frame.image != null){
					
					
					BufferedImage image = converter.convert(frame);

					this.image = image;
					this.map = convert(image);
					repaint();
					recorder.record(converter.convert(map));
					
				}
				else
					recorder.record(frame);
				
				
			}
			recorder.stop();
			recorder.release();
			grabber.stop();
			grabber.release();
			
		} catch (Exception e) {
			e.printStackTrace();
		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	private BufferedImage convert(BufferedImage image){
		
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		int[] 
				dx = {-1,0,1,0},
				dy = {0,1,0,-1};
		
		
		for(int x = 0;x < image.getWidth();x++)
			for(int y = 0;y < image.getHeight();y++){
				int maxDBritness = 0;
				for(int k = 0;k < dx.length;k++){
					int xx = x + dx[k];
					int yy = y + dy[k];
					if(xx < 0 || xx >= image.getWidth())continue;
					if(yy < 0 || yy >= image.getHeight())continue;
					
					int dB = dBritness(image.getRGB(x, y), image.getRGB(xx, yy));
					
						maxDBritness += dB;
					}
				int color = maxDBritness/(dx.length*3);
				
				result.setRGB(x, y, add(image.getRGB(x, y), (color << 8)|color));
				
				
			}
		
		
		return result;
		
	}
	
	
	int dBritness(int rgb1, int rgb2){

		int r1 = (rgb1 & 0x00FF0000) >> 16;
		int g1 = (rgb1 & 0x0000FF00) >>  8;
		int b1 = (rgb1 & 0x000000FF) >>  0;

		int r2 = (rgb2 & 0x00FF0000) >> 16;
		int g2 = (rgb2 & 0x0000FF00) >>  8;
		int b2 = (rgb2 & 0x000000FF) >>  0;
		
		return Math.abs((r1+g1+b1) - (r2+g2+b2));
	}
	
	int add(int rgb1, int rgb2){

		int r1 = (rgb1 & 0x00FF0000) >> 16;
		int g1 = (rgb1 & 0x0000FF00) >>  8;
		int b1 = (rgb1 & 0x000000FF) >>  0;

		int r2 = (rgb2 & 0x00FF0000) >> 16;
		int g2 = (rgb2 & 0x0000FF00) >>  8;
		int b2 = (rgb2 & 0x000000FF) >>  0;

		int r = Math.min(r1 + r2,255);
		int g = Math.min(g1 + g2,255);
		int b = Math.min(b1 + b2,255);
		
		return (r << 16) | (g << 8) | b;
	}
	

}
