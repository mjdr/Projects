package p3d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class Renderer 
{
	private BufferedImage buffer;
	private Graphics2D g;
	private Color color;
	private int width;
	private int height;
	
	private float perspective = 1F;
	
	public Renderer(int width , int height) 
	{
		this.width = width;
		this.height = height;
		
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = buffer.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	public BufferedImage getBuffer() {
		return buffer;
	}

	public void setBuffer(BufferedImage buffer) {
		this.buffer = buffer;
	}
	
	
	
	public void render(ShapeType type , float[] vertex)
	{
		if(type == ShapeType.POINT)
			drawPoints(vertex);
		else if(type == ShapeType.LINE)
			drawLines(vertex);
		else if(type == ShapeType.TRIANGLE)
			drawTriangles(vertex);
		else if(type == ShapeType.TRIANGLE_LINES)
			drawTrianglesLines(vertex);
	}

	private void drawTriangles(float[] vertex) 
	{

		float x1;
		float y1;
		float z1;
		float x2;
		float y2;
		float z2;
		float x3;
		float y3;
		float z3;
		Vector2 pos1;
		Vector2 pos2;
		Vector2 pos3;
		for(int j = 0;j < vertex.length;j+=9)
		{
			x1 = vertex[j];
			y1 = vertex[j + 1];
			z1 = vertex[j + 2];
			x2 = vertex[j + 3];
			y2 = vertex[j + 4];
			z2 = vertex[j + 5];
			x3 = vertex[j + 6];
			y3 = vertex[j + 7];
			z3 = vertex[j + 8];

			pos1 = get2DCoord(x1, y1, z1);
			pos2 = get2DCoord(x2, y2, z2);
			pos3 = get2DCoord(x3, y3, z3);
			g.setColor(color);
			g.fillPolygon(
					new int[]{(int)pos1.x,(int)pos2.x,(int)pos3.x}, 
					new int[]{(int)pos1.y,(int)pos2.y,(int)pos3.y}, 
					3);
			
		}
	}

	
	private void drawTrianglesLines(float[] vertex) 
	{

		float x1;
		float y1;
		float z1;
		float x2;
		float y2;
		float z2;
		float x3;
		float y3;
		float z3;
		Vector2 pos1;
		Vector2 pos2;
		Vector2 pos3;
		for(int j = 0;j < vertex.length;j+=9)
		{
			x1 = vertex[j];
			y1 = vertex[j + 1];
			z1 = vertex[j + 2];
			x2 = vertex[j + 3];
			y2 = vertex[j + 4];
			z2 = vertex[j + 5];
			x3 = vertex[j + 6];
			y3 = vertex[j + 7];
			z3 = vertex[j + 8];

			pos1 = get2DCoord(x1, y1, z1);
			pos2 = get2DCoord(x2, y2, z2);
			pos3 = get2DCoord(x3, y3, z3);
			g.setColor(color);
			g.drawLine((int)pos1.x, (int)pos1.y, (int)pos2.x, (int)pos2.y);
			g.drawLine((int)pos2.x, (int)pos2.y, (int)pos3.x, (int)pos3.y);
			g.drawLine((int)pos1.x, (int)pos1.y, (int)pos3.x, (int)pos3.y);
			
		}
	}
	private void drawLines(float[] vertex) 
	{
		float x1;
		float y1;
		float z1;
		float x2;
		float y2;
		float z2;
		Vector2 pos1;
		Vector2 pos2;
		for(int j = 0;j < vertex.length;j+=6)
		{
			x1 = vertex[j];
			y1 = vertex[j + 1];
			z1 = vertex[j + 2];
			x2 = vertex[j + 3];
			y2 = vertex[j + 4];
			z2 = vertex[j + 5];

			pos1 = get2DCoord(x1, y1, z1);
			pos2 = get2DCoord(x2, y2, z2);
			g.setColor(color);
			g.drawLine((int)pos1.x, (int)pos1.y,(int)pos2.x, (int)pos2.y);
			
		}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void drawPoints(float[] vertex) 
	{
		float x;
		float y;
		float z;
		Vector2 pos;
		for(int j = 0;j < vertex.length;j+=3)
		{
			x = vertex[j];
			y = vertex[j + 1];
			z = vertex[j + 2];
			
			pos = get2DCoord(x, y, z);
			g.setColor(color);
			g.drawLine((int)pos.x, (int)pos.y,(int)pos.x, (int)pos.y);
			
		}
	}
	private Vector2 get2DCoord(float x , float y , float z)
	{

		float scale = perspective / (z + perspective);
		return converToScreenCoords(new Vector2(x * scale, y * scale));
	}
	private Vector2 converToScreenCoords(Vector2 v)
	{
		return new Vector2((v.x + 1)/2 * width , (v.y + 1)/2 * height);
	}
	
}
