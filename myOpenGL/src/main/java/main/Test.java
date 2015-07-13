package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Test 
{
	
	public static void main(String[] args) throws IOException 
	{
		
		
		OBJModel model = OBJModel.loadModel("model.obj");

		BufferedImage colorBufferImg = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		BufferedImage zBufferImg = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		int[] zBuffer = new int[800*600];
		int[] colorBuffer = new int[800*600];
		int W = 800;
		int H = 600;
		
		Vector3f lightDir = new Vector3f(0, 0, 1);
		lightDir = lightDir.normalize();
		
		//model.nomalizeVertexs();
		
		long time = System.currentTimeMillis();
		

		Vector3i tmp1 = new Vector3i(0, 0, 0);
		Vector3i tmp2 = new Vector3i(0, 0, 0);
		Vector3i tmp3 = new Vector3i(0, 0, 0);
		
		for(List<List<Integer>>face : model.faces)
		{

			Vector3f vert0 = model.vertexs.get(face.get(0).get(0) - 1);
			Vector3f vert1 = model.vertexs.get(face.get(0).get(1) - 1);
			Vector3f vert2 = model.vertexs.get(face.get(0).get(2) - 1);
			
			Vector3f normal = ((vert1.minus(vert0)).multiplay(vert2.minus(vert0))).normalizeThis();
			
			int factor = (int)(lightDir.dotProduct(normal) * 254);
			
			if(factor > 0)
			{
				tmp1.x = (int)((vert0.x + 1) * 299);
				tmp1.y = (int)((vert0.y + 1) * 299);
				tmp1.z = (int)((vert0.z + 1) * 299);

				tmp2.x = (int)((vert1.x + 1) * 299);
				tmp2.y = (int)((vert1.y + 1) * 299);
				tmp2.z = (int)((vert1.z + 1) * 299);

				tmp3.x = (int)((vert2.x + 1) * 299);
				tmp3.y = (int)((vert2.y + 1) * 299);
				tmp3.z = (int)((vert2.z + 1) * 299);
				
				Painter.drawTriangle(
						tmp1,
						tmp2,
						tmp3,
						colorBuffer,zBuffer,W,H,
						factor | (factor << 8) | (factor << 16));
			}
		}
		
		

		
		System.out.println(1000f/(System.currentTimeMillis() - time));

		for(int x = 0;x < 800 * 600;x++)
			colorBufferImg.setRGB(x/H, x%H, colorBuffer[x]);
		int maxZ = Integer.MIN_VALUE;
		int minZ = Integer.MAX_VALUE;
		for(int i = 0;i < 800 * 600;i++)
		{
				if(zBuffer[i] > maxZ) maxZ = zBuffer[i];
				if(zBuffer[i] > 0 && zBuffer[i] < minZ) minZ = zBuffer[i];
		}
		
		for(int x = 0;x < 800 * 600;x++)
			{
				if(zBuffer[x] > 0)
				{
					int factor = (int)(0xFF*(zBuffer[x]-minZ)/(float)(maxZ - minZ));
					zBufferImg.setRGB(x/H, x%H, factor | (factor << 8) | (factor << 16));
				}
			}
		for(int x = 0;x < 800 * 600;x++)
			colorBufferImg.setRGB(x/H, x%H, colorBuffer[x]);
		
		
		ImageIO.write(zBufferImg, "PNG", new File("zGL.png"));
		ImageIO.write(colorBufferImg, "PNG", new File("colorGL.png"));
		
				
		
		
	}
	
	
}
