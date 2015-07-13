package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.parseFloat;

public class OBJModel 
{
	public List<Vector3f> vertexs;
	public List<List<List<Integer>>> faces;
	
	
	private OBJModel() 
	{
		vertexs = new ArrayList<Vector3f>();
		faces = new ArrayList<List<List<Integer>>>();
	}
	
	
	public static OBJModel loadModel(String filename) throws IOException
	{
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		OBJModel model = new OBJModel();
		
		String line;
		
		while((line = reader.readLine()) != null)
		{
			if(line.trim().isEmpty())
				continue;
			String[] parts = line.split(" +");
			if(parts[0].equals("v"))
			{
				model.vertexs.add(
						new Vector3f(
								parseFloat(parts[1]), 
								parseFloat(parts[2]), 
								parseFloat(parts[3])));
			}
			else if(parts[0].equals("f"))
			{
				List<List<Integer>>face = new ArrayList<List<Integer>>();
				List<Integer>vtxs = new ArrayList<Integer>();
				List<Integer>texs = new ArrayList<Integer>();
				List<Integer>norms = new ArrayList<Integer>();
				
				for(int i = 1;i < 4;i++)
				{
					String[] vs = parts[i].split("/");
					vtxs.add(Integer.parseInt(vs[0]));
					if(!vs[1].isEmpty())
						texs.add(Integer.parseInt(vs[1]));

					if(!vs[1].isEmpty())
						norms.add(Integer.parseInt(vs[2]));
				}
				face.add(vtxs);
				face.add(texs);
				face.add(norms);
				model.faces.add(face);
			}
		}
		
		reader.close();
		return model;
		
	}
	
	public void nomalizeVertexs()
	{

		Vector3f max = new Vector3f(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
		Vector3f min = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
		
		for(Vector3f v : vertexs)
		{
			if(v.x > max.x) max.x = v.x;
			if(v.y > max.y) max.y = v.y;
			if(v.z > max.z) max.z = v.z;

			if(v.x < min.x) min.x = v.x;
			if(v.y < min.y) min.y = v.y;
			if(v.z < min.z) min.z = v.z;
		}
		for(Vector3f v : vertexs)
		{
			v.set((v.minus(min)).devideComponents(max.minus(min)));
		}
	}
}
