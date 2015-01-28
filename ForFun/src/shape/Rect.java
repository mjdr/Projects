package shape;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Rect implements Comparable<Rect>
{
	int x1;
	int y1;
	int x2;
	int y2;
	Color color;
	BufferedImage original;
	double maxDiff;
	
	

	public Rect(int x1, int y1, int x2, int y2, BufferedImage original) 
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.original = original;
		__init__();
	}

	private void __init__() 
	{
		long sumR = 0;
		long sumG = 0;
		long sumB = 0;
		
		for(int i = x1;i < x2;i++)
			for(int j = y1;j < y2;j++)
			{
				sumR += (original.getRGB(i, j) & 0x00FF0000) >> 16;
				sumG += (original.getRGB(i, j) & 0x0000FF00) >> 8;
				sumB += (original.getRGB(i, j) & 0x000000FF);
				
			}

		if(sumR/(int)((x2 - x1)*(y2 - y1)) > 255)
			sumR = (int)((x2 - x1)*(y2 - y1)) * 255;
		if(sumB/(int)((x2 - x1)*(y2 - y1)) > 255)
			sumB = (int)((x2 - x1)*(y2 - y1)) * 255;
		if(sumG/(int)((x2 - x1)*(y2 - y1)) > 255)
			sumG = (int)((x2 - x1)*(y2 - y1)) * 255;
		
		color = new Color(
				(int)sumR/(int)((x2 - x1)*(y2 - y1)), 
				(int)sumG/(int)((x2 - x1)*(y2 - y1)), 
				(int)sumB/(int)((x2 - x1)*(y2 - y1)));
		

		int diffR;
		int diffG;
		int diffB;
		
		long diff = 0;

		for(int i = x1;i < x2;i++)
			for(int j = y1;j < y2;j++)
			{
				diffR = ((original.getRGB(i, j) & 0x00FF0000) >> 16) - color.getRed();
				diffG = ((original.getRGB(i, j) & 0x0000FF00) >> 8) - color.getGreen();
				diffB = (original.getRGB(i, j) & 0x000000FF) - color.getBlue();
				
				
				diff += Math.abs(diffR);
				diff += Math.abs(diffG);
				diff += Math.abs(diffB);
			}
		this.maxDiff = diff*(double)((x2 - x1)*(y2 - y1));
	}

	@Override
	public int compareTo(Rect o) 
	{
		return (int) (maxDiff - o.maxDiff);
	}
	
}
