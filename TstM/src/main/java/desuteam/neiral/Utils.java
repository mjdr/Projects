package desuteam.neiral;

public class Utils {
	public static Texture conv(Texture tex, Texture pattern){
		
		Texture result = new Texture(tex.getWidth(), tex.getHeight());
		RGB patternAvg = getAVG(pattern, 0, 0, pattern.getWidth(), pattern.getHeight());
		
		boolean update = true;
		RGB allPat = new RGB(0);
		
		for(int x = 0;x < tex.getWidth();x++){
			System.out.println(x);
			for(int y = 0;y < tex.getHeight();y++){
				
				RGB regionAvg = getAVG(
							tex, 
							x - pattern.getWidth()/2, 
							y - pattern.getHeight()/2, 
							pattern.getWidth(), 
							pattern.getHeight()
						);
				RGB rgb = new RGB(0);
				RGB allTex = new RGB(0);
				
				for(int xp = 0;xp < pattern.getWidth();xp++)
					for(int yp = 0;yp < pattern.getHeight();yp++){

						int xx = x + xp - pattern.getWidth()/2;
						int yy = y + yp - pattern.getHeight()/2;
						
						RGB texColor = tex.getRGB(xx, yy);
						

						RGB dTex = texColor.substruct(regionAvg);
						
						RGB patColor = pattern.getRGB(xp, yp);
						RGB dPat = patColor.substruct(patternAvg);
						RGB mul = dTex.multiplay(dPat);
						if(update)
							allPat = allPat.add(dPat.multiplay(dPat));
						
						rgb = rgb.add(mul);
						allTex = allTex.add(dTex.multiplay(dTex));
						
						
					}
				
				update = false;
				
				if(allTex.r * allPat.r == 0) rgb.r = 0;
				else rgb.r /= (float)Math.sqrt(allTex.r * allPat.r);

				if(allTex.g * allPat.g == 0) rgb.g = 0;
				else rgb.g /= (float)Math.sqrt(allTex.g * allPat.g);

				if(allTex.g * allPat.g == 0) rgb.b = 0;
				else rgb.b /= (float)Math.sqrt(allTex.b * allPat.b);

				
				
				rgb.r = Math.abs(rgb.r);
				rgb.g = Math.abs(rgb.g);
				rgb.b = Math.abs(rgb.b);
				
				float c = Math.min(rgb.r, Math.min(rgb.g, rgb.b));
				
				if(c > 0.7) result.setRGB(x, y, new RGB(0, 1, 0));
				else result.setRGB(x, y, tex.getRGB(x, y));
			}
		}
		return result;
		
	}
	
	public static RGB getAVG(Texture tex, int px, int py, int width, int height){
		
		RGB all = new RGB(0);
		
		for(int x = 0;x < width;x++)
			for(int y = 0;y < height;y++)
				all = all.add(tex.getRGB(x + px, y + py));

		all.r /= (width * height);
		all.g /= (width * height);
		all.b /= (width * height);
		return all;
	}
	
	
}
