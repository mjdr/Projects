package desuteam.neiral;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	public static void main(String[] args) {
		try {
			new Main();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public Main() throws IOException {

		Texture texture = new Texture(ImageIO.read(new File("C://screen.png")));
		Texture pattern = new Texture(ImageIO.read(new File("C://pattern.png")));
		
		System.out.println("Start");
		
		Texture res = Utils.conv(texture, pattern);
		
		System.out.println("Done!");
		
		
		ImageIO.write(res.getImage(), "PNG", new File("C://result.png"));
		
		
	}
	
}
