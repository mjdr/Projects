import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Lab15 extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) 
	{
		JFrame f = new JFrame("Lab15");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		f.setResizable(false);
		f.setVisible(true);
		f.add(new Lab15());
	}
	
	Point2D.Float[] points;
	
	public Lab15() 
	{
		float sx = .1F;
		float ex = 3F;
		float x;
		
		points = new Float[5];
		for(int i = 0;i < points.length;i++)
		{
			//points[i] = new Point2D.Float((i + 1),func((i + 1)));
			x = sx + (ex - sx) * (float)Math.cos((1.0F*(points.length - 1 - i))/points.length * Math.PI/2);
			points[i] = new Float(x, func(x));
			System.out.println("X:"+x);
		}
	}
	
	float func(float x)
	{
		return (float)Math.log(x);
	}
	
	
	float L(float x , Point2D.Float[] ps)
	{
		float res = 0;
		float tmp;
		for(int i = 0;i < ps.length;i++)
		{
			tmp = 1;
			for(int j = 0;j < ps.length;j++)
			{
				if(i == j) continue;
				tmp *= (x - ps[j].x) / (ps[i].x - ps[j].x);
			}
			res += ps[i].y * tmp;
		}
		return res;
	}
	float err(float x , Point2D.Float[] ps)
	{
		return 100F * (func(x) - L(x , ps));
	}
	
	@Override
	public void paint(Graphics g) 
	{
		
		float sx = -1;
		float ex = 15;
		float sy = 5;
		float ey = -5;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		

		g.setColor(Color.red);
		if(Math.signum(ex) != Math.signum(sx))
		{
			g.drawLine(
					(int)((0 - sx) / (ex - sx) * 800), 0, 
					(int)((0 - sx) / (ex - sx) * 800), 600);
		}
		if(Math.signum(ey) != Math.signum(sy))
		{
			g.drawLine(
					0,(int)((0 - sy) / (ey - sy) * 600),
					800,(int)((0 - sy) / (ey - sy) * 600));
		}
		float oldY = 0;
		float dx = (ex - sx) / 800;
		for(float x = sx;x < ex;x+=dx)
		{
			float y = L(x , points);
			g.setColor(Color.GREEN);
			g.drawLine(
					(int)((x - sx) / (ex - sx) * 800), (int)((y - sy) / (ey - sy) * 600),
					(int)((x-dx - sx) / (ex - sx) * 800), (int)((oldY - sy) / (ey - sy) * 600));
			if(x > 0 && x != 1)
			{
			g.setColor(Color.GRAY);
			g.drawLine(
					(int)((x - sx) / (ex - sx) * 800), (int)((func(x) - sy) / (ey - sy) * 600),
					(int)((x-dx - sx) / (ex - sx) * 800), (int)((func(x - dx) - sy) / (ey - sy) * 600));
			g.setColor(Color.WHITE);
			g.drawLine(
					(int)((x - sx) / (ex - sx) * 800), (int)((err(x , points) - sy) / (ey - sy) * 600),
					(int)((x-dx - sx) / (ex - sx) * 800), (int)((err(x - dx,points) - sy) / (ey - sy) * 600));
			}
			oldY = y;
		}
		
		g.setColor(Color.WHITE);
		for(Point2D.Float p : points)
		{
			g.fillOval((int)((p.x - sx) / (ex - sx) * 800 - 3), (int)((p.y - sy) / (ey - sy) * 600 - 3), 6, 6);
			System.out.println((int)((p.y - sy) / (ex - sy) * 600 - 3));
		}
	}

}
