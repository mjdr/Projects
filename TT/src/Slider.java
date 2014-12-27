

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Slider 
{
	public enum SType {LINER, CIRCLE,BIEZIER,SPLINE};
	
	private static final int EMPTY_CELL = Float.floatToIntBits(Float.MAX_VALUE);
	
	private float radius;
	private Point[] points;
	private Point offset;
	private SType type;
	private BufferedImage buffer;

	private int borderR;
	private int borderG;
	private int borderB;
	
	private int colorR;
	private int colorG;
	private int colorB;
	
	public Slider(Point[] points ,float r , SType type) 
	{
		if(points == null || points.length < 2)
			throw new IllegalArgumentException("points is empty!");
		this.points = points;
		this.type = type;
		this.radius = r;
		
		borderR = 255;
		borderG = 255;
		borderB = 255;
		
		colorR = 200;
		colorG = 200;
		colorB = 200;
		
		
	}
	public void preRender()
	{
		float 
				minX = Float.MAX_VALUE,
				maxX = Float.MIN_VALUE,
				minY = Float.MAX_VALUE,
				maxY = Float.MIN_VALUE;
		
		for(Point p : points)
		{
			if(p.x < minX)
				minX = p.x;
			if(p.y < minY)
				minY = p.y;
			if(p.x > maxX)
				maxX = p.x;
			if(p.y > maxY)
				maxY = p.y;
		}
		
		offset = new Point(minX - radius, minY - radius);
		buffer = new BufferedImage(
				(int)(maxX - minX + 2 * radius + 2), 
				(int)(maxY - minY + 2 * radius + 2), 
				BufferedImage.TYPE_INT_ARGB);	
		
		for(int i = 0;i < buffer.getHeight()*buffer.getWidth();i++)
			buffer.setRGB(i % buffer.getWidth(), i / buffer.getWidth(),EMPTY_CELL);
		
		if(type == SType.LINER)
		{
			for(int i = 1;i < points.length;i++)
			{
				renderLine(
						points[i - 1].x - offset.x, 
						points[i - 1].y - offset.y, 
						points[i].x - offset.x, 
						points[i].y - offset.y, 
						radius);
			}
			for(Point p : points)
				renderCircle(p.x - offset.x, p.y - offset.y , radius);
			texturing();
			return;
		}
	}
	private void renderCircle(float rx , float ry , float r)
	{
		int sx = (int)(rx - r - 1);
		int ex = (int)(rx + r + 1);
		int sy = (int)(ry - r - 1);
		int ey = (int)(ry + r + 1);
		float tmp;
		float val;
		
		for(int x = sx; x < ex;x++)
			for(int y = sy;y < ey;y++)
			{
				tmp = (float)Math.sqrt((x - rx) * (x - rx) + (y - ry) * (y - ry));
				if(tmp < r)
				{
					val = Float.intBitsToFloat(buffer.getRGB(x, y));
					if(val > tmp)
						buffer.setRGB(x, y, Float.floatToIntBits(tmp));
				}
			}
	}
	private void renderLine(float x1,float y1,float x2,float y2,float r)
	{
		float a = y1 - y2;
		float b = x2 - x1;
		float c = x1*y2 - x2*y1;
		
		float a1 = -b;
		float b1 = a;
		float c1;
		
		float pX;
		float pY;
		
		float tmp;
		float absqrt = (float)Math.sqrt(a*a+b*b);
		
		for(int x = 0; x < buffer.getWidth();x++)
			for(int y = 0;y < buffer.getHeight();y++)
				if(inLine(a, b, c,absqrt, r, x, y))
				{
					c1 = -(a1 * x + b1 * y);
					pX = -(b1*c - b*c1)/(a*b1 - a1*b);
					if(pX <= Math.max(x1, x2) && pX >= Math.min(x1, x2))
					{
						pY = -(a*c1-a1*c)/(a*b1-a1*b);
						if(pY <= Math.max(y1, y2) && pY >= Math.min(y1, y2))
						{
							tmp = (float)Math.hypot(pX - x,pY - y);
							if(tmp < Float.intBitsToFloat(buffer.getRGB(x, y)))
								buffer.setRGB(x, y, Float.floatToIntBits(tmp));
						}
					}
			}
		
	}
	private boolean inLine(float a , float b , float c, float absqrt ,float r , float x , float y)
	{
		return Math.abs(a*x+b*y+c)/absqrt < r;
	}
	private void texturing()
	{
		float value;
		int x;
		int y;
		for(int i = 0;i < buffer.getHeight()*buffer.getWidth();i++)
		{
			x = i % buffer.getWidth();
			y = i / buffer.getWidth();
			
			value = Float.intBitsToFloat(buffer.getRGB(x , y));
			if(value <= radius && value != EMPTY_CELL)
				buffer.setRGB(x, y, color(value/radius));
			else
				buffer.setRGB(x, y, 0);	
		}
	}
	int color(float x)
	{
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 0;
		
		
		if(x < 0.7F)
		{
			r = colorR;
			g = colorG;
			b = colorB;
			a = (int)((0.7F + 0.25F * x / 0.75F) * 255);
		}
		else if(x < 0.74F)
		{
			r = range(colorR, borderR, (x - 0.7F) / 0.04F);
			g = range(colorG, borderG, (x - 0.7F) / 0.04F);
			b = range(colorB, borderB, (x - 0.7F) / 0.04F);
			a = 255;
		}
		else if(x < 0.86F)
		{
			r = borderR;
			g = borderG;
			b = borderB;
			a = 255;
		}
		else if(x < 0.9)
		{
			r = (int)(borderR * (1 - (x - 0.86F)/0.04F));
			g = (int)(borderG * (1 - (x - 0.86F)/0.04F));
			b = (int)(borderB * (1 - (x - 0.86F)/0.04F));
			a = range(255, 155, (x - 0.86F)/0.04F);
		}
		else
		{
			r = 0;
			g = 0;
			b = 0;
			a = (int)(155*(0.5F - 5*(x - 0.9F)));
		}
		return (a << 24) | (r << 16) | (g << 8) | b;
		
	}
	int range(float min , float max,float x)
	{
		return (int)(min + (max - min) * x);
	}
	public void render(Graphics g)
	{
		g.drawImage(buffer,(int)offset.x,(int) offset.y,null);
	}
	public int getSize()
	{
		int size = 0;

		size += points.length * 3 * 4;
		if(buffer != null)
			size += buffer.getWidth() * buffer.getHeight()*4;
		return size;
	}
	public int getBufferWidth()
	{
		return buffer.getWidth();
	}
	public int getBufferHeight()
	{
		return buffer.getHeight();
	}
	static class Point
	{
		public enum PType{ CONTROL , DIRECT};
		float x;
		float y;
		PType type;
		public Point(float x, float y,PType type) 
		{
			this.type = type;
			this.x = x;
			this.y = y;
		}
		public Point(float x , float y)
		{
			this(x,y,null);
		}
		
		
		
	}
}
