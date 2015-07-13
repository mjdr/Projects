package main;

import java.awt.image.BufferedImage;

public class Painter 
{
	public static void drawLine(int x0 , int y0 , int x1,int y1 , BufferedImage buffer , int color)
	{
		
		  boolean steep = false;
		   if (Math.abs(x0-x1)<Math.abs(y0-y1)) 
		   {
			   int tmp = x0;
			   x0 = y0;
			   y0 = tmp;
			   
			   tmp = x1;
			   x1 = y1;
			   y1 = tmp;
		       steep = true;
		    }
		    if (x0>x1) 
		    {
		    	int tmp = x0;
				x0 = x1;
				x1 = tmp;
				   
				tmp = y0;
				y0 = y1;
				y1 = tmp;
		    }
		    int dx = x1-x0;
		    int dy = y1-y0;
		    float derror = Math.abs(dy/(float)dx);
		    float error = 0;
		    int y = y0;
		    for (int x=x0; x<=x1; x++) {
		        if (steep) {
		        	if(y > 0 && y < buffer.getWidth() &&
		        		x > 0 && x < buffer.getHeight())
		            buffer.setRGB(y, buffer.getHeight() - x, color);
		        } else {
		        	if(y > 0 && y < buffer.getHeight() &&
			        		x > 0 && x < buffer.getWidth())
		        	buffer.setRGB(x, buffer.getHeight() - y, color);
		        }
		        error += derror;

		        if (error>.5) {
		            y += (y1>y0?1:-1);
		            error -= 1.;
		        }
		    }
		
		
	}
	public static void drawLine(Vector2i p1 , Vector2i p2 , BufferedImage buffer , int color)
	{
		drawLine(p1.x, p1.y, p2.x, p2.y, buffer, color);
	}
	public static void drawTriangle(
			Vector3i t0 , 
			Vector3i t1 , 
			Vector3i t2, 
			int[] colorBuffer , 
			int[] zBuffer , 
			int w,int h,
			int color)
	{
		if (t0.y>t1.y) Vector3i.swap(t0, t1);
	    if (t0.y>t2.y) Vector3i.swap(t0, t2);
	    if (t1.y>t2.y) Vector3i.swap(t1, t2);

	    int total_height = t2.y-t0.y;
	    for (int i=0; i<total_height; i++) 
	    {
	        boolean second_half = i>t1.y-t0.y || t1.y==t0.y;
	        int segment_height = second_half ? t2.y-t1.y : t1.y-t0.y;
	        float alpha = (float)i/total_height;
	        float beta  = (float)(i-(second_half ? t1.y-t0.y : 0))/segment_height; // be careful: with above conditions no division by zero here
	        Vector3i A = Vector3f.linerInterpInt(t0, t2, alpha);
	        Vector3i B = second_half ? 
	        		Vector3f.linerInterpInt(t1, t2, beta) : 
	        		Vector3f.linerInterpInt(t0, t1, beta);
	        
	        	if (A.x>B.x) Vector3i.swap(A, B);
	        
	        for (int j=A.x; j<=B.x;j++) {
	            float phi = B.x==A.x ? 1f : (float)(j-A.x)/(B.x-A.x);
	            Vector3i P = Vector3f.linerInterpInt(A, B, phi);
	            int id = P.x *h + (h-P.y-1);
	            if (zBuffer[id]<P.z) {
	            	zBuffer[id] = P.z;
	            	colorBuffer[id] = color;
	            }
	        }
	    }
	}
}
