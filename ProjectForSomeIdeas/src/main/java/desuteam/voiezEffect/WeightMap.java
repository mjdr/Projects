package desuteam.voiezEffect;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class WeightMap {
	 public double[][] terrain;
	  private double roughness, min, max;
	  private int divisions;
	  private Random rng;
	  
	  public WeightMap (int lod, double roughness) {
	    this.roughness = roughness;
	    this.divisions = 1 << lod;
	    terrain = new double[divisions + 1][divisions + 1];
	    rng = new Random();
	    terrain[0][0] = rnd ();
	    terrain[0][divisions] = rnd ();
	    terrain[divisions][divisions] = rnd ();
	    terrain[divisions][0] = rnd ();
	    double rough = roughness;
	    for (int i = 0; i < lod; ++ i) {
	      int q = 1 << i, r = 1 << (lod - i), s = r >> 1;
	      for (int j = 0; j < divisions; j += r)
	        for (int k = 0; k < divisions; k += r)
	          diamond (j, k, r, rough);
	      if (s > 0)
	        for (int j = 0; j <= divisions; j += s)
	          for (int k = (j + s) % r; k <= divisions; k += r)
	            square (j - s, k - s, r, rough);
	      rough *= roughness;
	    }
	    min = max = terrain[0][0];
	    for (int i = 0; i <= divisions; ++ i)
	      for (int j = 0; j <= divisions; ++ j)
	        if (terrain[i][j] < min) min = terrain[i][j];
	        else if (terrain[i][j] > max) max = terrain[i][j];
	  }
	  private void diamond (int x, int y, int side, double scale) {
	    if (side > 1) {
	      int half = side / 2;
	      double avg = (terrain[x][y] + terrain[x + side][y] +
	        terrain[x + side][y + side] + terrain[x][y + side]) * 0.25;
	      terrain[x + half][y + half] = avg + rnd () * scale;
	    }
	  }
	  private void square (int x, int y, int side, double scale) {
	    int half = side / 2;
	    double avg = 0.0, sum = 0.0;
	    if (x >= 0)
	    { avg += terrain[x][y + half]; sum += 1.0; }
	    if (y >= 0)
	    { avg += terrain[x + half][y]; sum += 1.0; }
	    if (x + side <= divisions)
	    { avg += terrain[x + side][y + half]; sum += 1.0; }
	    if (y + side <= divisions)
	    { avg += terrain[x + half][y + side]; sum += 1.0; }
	    terrain[x + half][y + half] = avg / sum + rnd () * scale;
	  }
	  private double rnd () {
	    return 2. * rng.nextDouble () - 1.0;
	  }
	  public double getAltitude (double i, double j) {
		    double alt = terrain[(int) (i * divisions)][(int) (j * divisions)];
		    return (alt - min) / (max - min);
	}
	  public double getAltitude (int x, int y) {
		    double alt = terrain[x][y];
		    return (alt - min) / (max - min);
	 }
	 
	
	public void print(){
		
		BufferedImage image = new BufferedImage(divisions, divisions, BufferedImage.TYPE_INT_RGB);
		
		for(int y = 0;y < divisions;y++){
			for(int x = 0;x < divisions;x++){
				int c = (int)(255 * getAltitude(x, y));
				image.setRGB(x, y, (c << 16)|(c << 8)|c);
			}
		}
		
		try {
			ImageIO.write(image, "PNG", new File("C://image.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
