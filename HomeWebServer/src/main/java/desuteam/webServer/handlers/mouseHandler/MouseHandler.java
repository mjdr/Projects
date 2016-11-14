package desuteam.webServer.handlers.mouseHandler;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import desuteam.webServer.Request;
import desuteam.webServer.handler.WebHandlerAdapter;
import desuteam.webServer.utils.HTTPUtils;
import net.iharder.Base64;

public class MouseHandler extends WebHandlerAdapter {
	
	Robot robot;
	Dimension dimension;
	
	public MouseHandler() {
		super("mouse_handler");
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
	}

	public void handleRequest(Request request) {
		if(request.getMethod().equals("GET"))
			HTTPUtils.responseHTMLFile(request, new File("html/canvas.html"));
		else if(request.getMethod().equals("POST")){
			PrintWriter pw = new PrintWriter(request.getOutputStream());
			
			
			String content = "";
			String p_type = request.getParameters().get("type");
			if(p_type == null);
			else if(p_type.equals("image")){
				
				try {
					Point location = MouseInfo.getPointerInfo().getLocation();
					int x = location.x - dimension.width /4;
					int y = location.y - dimension.height /4;

					int screenX = x;
					int screenY = y;
					if(screenX < 0) screenX = 0;
					if(screenX + dimension.width /2 > dimension.width) screenX = dimension.width /2;
					
					if(screenY < 0) screenY = 0;
					if(screenY + dimension.height /2 > dimension.height) screenY = dimension.height /2;
					
					Rectangle r = new Rectangle(screenX ,screenY , dimension.width /2,dimension.height /2);
				    BufferedImage bi = robot.createScreenCapture(r);
				   
				    ByteArrayOutputStream os = new ByteArrayOutputStream();
				    OutputStream b64 = new Base64.OutputStream(os);
				    ImageIO.write(bi, "png", b64);
				    String result = os.toString("UTF-8");
					content = String.format("{\"imageData\":\"data:image/png;base64, %s\"}", result);
					
				    } catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(p_type.equals("mouse")){
				

				Point location = MouseInfo.getPointerInfo().getLocation();
				
				
				int x = dimension.width /4;
				int y = dimension.height /4;
				
				if(location.x - dimension.width /4 < 0)
					x += location.x - dimension.width /4;
				
				if(location.y - dimension.height /4 < 0)
					y += location.y - dimension.height /4;
				
				if(location.x + dimension.width /4 > dimension.width)
					x -= dimension.width - (location.x + dimension.width /4);
				if(location.y + dimension.height /4 > dimension.height)
					y -= dimension.height - (location.y + dimension.height /4);
				
				content = String.format("{\"x\":%d,\"y\":%d}", x, y);
				
			}
				
				pw.println(HTTPUtils.printStatusCode(200));
				pw.println(HTTPUtils.printHeaders(HTTPUtils.getDefaultHeaders("html/json", content.length())));
				
				pw.print(content);
				
			
		    
			
			pw.close();
		}
	}
	
}
