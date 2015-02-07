package p3d;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Test 
{
	public static void main(String[] args) throws IOException {
		Renderer r = new Renderer(400, 400);
		r.setColor(Color.WHITE);
		float[] vertex = {
				-0.5F,-0.5F,0,
				-0.5F,0.5F,0,
				-0.5F,0.5F,0,
				 0.5F,0.5F,0,
				 0.5F,0.5F,0,
				 0.5F,-0.5F,0,
				 0.5F,-0.5F,0,
				-0.5F,-0.5F,0,
				-0.5F,-0.5F,1,
				-0.5F,0.5F,1,
				-0.5F,0.5F,1,
				 0.5F,0.5F,1,
				 0.5F,0.5F,1,
				 0.5F,-0.5F,1,
				 0.5F,-0.5F,1,
				-0.5F,-0.5F,1,
				-0.5F,-0.5F,0,
				-0.5F,-0.5F,1,
				-0.5F,0.5F,0,
				-0.5F,0.5F,1,
				 0.5F,0.5F,0,
				 0.5F,0.5F,1,
				 0.5F,-0.5F,0,
				 0.5F,-0.5F,1
				
		};
		float[] v2 = 
			{

				-0.5F , -0.5F,1,
				0.5F , -0.5F,1,
				0.5F , 0.5F,1,
				0.5F , 0.5F,1,
				-0.5F , -0.5F,1,
				-0.5F,0.5F,1,
				-0.5F , -0.5F,0,
				0.5F , -0.5F,0,
				0.5F , 0.5F,0,
				0.5F , 0.5F,0,
				-0.5F , -0.5F,0,
				-0.5F,0.5F,0
			};
		for(int i = 0;i < vertex.length;i+=3)
			vertex[i] += 0.35F;
		//r.render(ShapeType.TRIANGLE_LINES, v2);
		//r.setColor(Color.RED);
		long t = System.currentTimeMillis();
		for(int i = 0;i < 1000;i++)
		r.render(ShapeType.LINE, vertex);
		System.out.println(System.currentTimeMillis() - t);
		ImageIO.write(r.getBuffer(), "PNG", new File("F://3d.png"));
	}
}
