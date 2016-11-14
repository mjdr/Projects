package desuteam.webServer.handlers.quickuitest;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import desuteam.webServer.Request;
import desuteam.webServer.handler.WebHandlerAdapter;
import desuteam.webServer.utils.HTTPUtils;
import desuteam.webServer.utils.quickElements.QuickElement;
import desuteam.webServer.utils.quickElements.QuickImage;
import desuteam.webServer.utils.quickElements.QuickProgressBar;
import desuteam.webServer.utils.quickElements.QuickTextField;
import desuteam.webServer.utils.quickElements.QuickUtils;

public class QuickUIHandler extends WebHandlerAdapter {

	private List<QuickElement> elements = new ArrayList<QuickElement>();
	
	public QuickUIHandler(String url) {
		super(url);
	}

	public void handleRequest(Request request) {
		if(request.getMethod().equals("GET")){
			HTTPUtils.responseHTMLFile(request, new File("html/quick-template.html"), handleUrl);
		}
		else if(request.getMethod().equals("POST")){
			PrintWriter pw = new PrintWriter(request.getOutputStream());
			String json = QuickUtils.generateJson(elements);
			pw.println(HTTPUtils.printStatusCode(200));
			try {
				pw.println(HTTPUtils.printHeaders(HTTPUtils.getDefaultHeaders("text/json; charset=utf-8", json.getBytes("UTF-8").length)));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pw.print(json);
			
			
			pw.close();
		}
		
	}
	
	protected void addElement(QuickElement element){
		elements.add(element);
	}
	protected void removeElement(QuickElement element){
		elements.remove(element);
	}

}
