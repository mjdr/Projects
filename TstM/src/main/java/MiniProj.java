
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException; 

public class MiniProj {
	public static void main(String[] args) {
		
		new MiniProj();
		
	}
	
	
	public MiniProj() {
		try {
			convert(new File("C://BP-a-2-4.JPG"), new File("C://dst.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void convert(File src, File dst) throws IOException{
		
		BufferedImage image = ImageIO.read(src);
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for(int x = 0;x < image.getWidth();x++)
			for(int y = 0;y < image.getHeight();y++){
				int rgb = (image.getRGB(x, y)&0x00FFFFFF);
				int a = 255 - (rgb & 0xff);
				result.setRGB(x, y, rgb|(a<<24));
			}
		
		ImageIO.write(result, "PNG", dst);
	}
	
	
}
