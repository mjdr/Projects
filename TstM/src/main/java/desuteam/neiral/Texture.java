package desuteam.neiral;

import java.awt.image.BufferedImage;

public class Texture {
	private BufferedImage image;
	private int width;
	private int height;
	
	public Texture(BufferedImage image){
		this.image = image;
		width = image.getWidth();
		height = image.getHeight();
	}
	
	public Texture(int width, int height) {
		this.width = width;
		this.height = height;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
	
	public RGB getRGB(int x, int y){
		if(x < 0 || x >= width || y < 0 || y >= height)
			return new RGB(0);
		return new RGB(image.getRGB(x, y));
	}
	
	public void setRGB(int x, int y, RGB rgb){
		image.setRGB(x, y, rgb.rgb());
	}
	
	public BufferedImage getImage(){
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
