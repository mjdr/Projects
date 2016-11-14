package desuteam.webServer.handlers;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import desuteam.webServer.Request;
import desuteam.webServer.handler.WebHandlerAdapter;
import desuteam.webServer.utils.HTTPUtils;

public class TestHandler extends WebHandlerAdapter {

	public TestHandler() {
		super("test");
	}

	public void handleRequest(Request request) {
		if(request.getMethod().equals("GET"))
			HTTPUtils.responseHTMLFile(request, new File("html/simplePage.html"));
		else if(request.getMethod().equals("POST"))
			handlePostRequest(request);
		
	}
	
	public void handlePostRequest(Request request){
		PrintWriter pw = new PrintWriter(request.getOutputStream());
		
		pw.println(HTTPUtils.printStatusCode(200));
		pw.println(HTTPUtils.printHeaders(HTTPUtils.getDefaultHeaders("text/json", -1)));
		pw.print(Integer.toString(new Random().nextInt(1000)));
		pw.close();
	}
	
}
