package desuteam.OsuPlayer.uiSwing;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	
	private BufferedImage image;
	private Dimension size;
	
	public Background(Dimension size) {
		this.size = size;
	}
	
	public void changeBackground(File imageFile){
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void draw(Graphics2D g){
		if(image == null) return;
		int mode = 1;
		

		float scale = 0;
		
		int realW = 0;
		int realH = 0;

		int realX = 0;
		int realY = 0;
		
		
		if(mode == 0){
			scale = Math.min(size.width/(float)image.getWidth(), size.height/(float)image.getHeight());
			
			realW = (int)(image.getWidth() * scale);
			realH = (int)(image.getHeight() * scale);
	
			realX = (size.width - realW)/2;
			realY = (size.height - realH)/2;
		}
		else if(mode == 1){
			scale = Math.max(size.width/(float)image.getWidth(), size.height/(float)image.getHeight());
			
			realW = (int)(image.getWidth() * scale);
			realH = (int)(image.getHeight() * scale);
	
			realX = (size.width - realW)/2;
			realY = (size.height - realH)/2;
		}
		g.drawImage(image, realX, realY, realW, realH, null);
		
	}
	
	
}
