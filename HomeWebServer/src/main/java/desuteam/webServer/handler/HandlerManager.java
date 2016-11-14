package desuteam.webServer.handler;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import desuteam.webServer.Request;
import desuteam.webServer.handlers.TestHandler;
import desuteam.webServer.handlers.audio.AudioBrodcast;
import desuteam.webServer.handlers.mouseHandler.MouseHandler;
import desuteam.webServer.utils.HTTPUtils;

public class HandlerManager {
	private static List<WebHandler> webHandlers = new ArrayList<WebHandler>();
	private static DefaultHandler defaultHandler = new DefaultHandler();

	public static void init() {
		webHandlers.add(new TestHandler());
		webHandlers.add(new MouseHandler());
		webHandlers.add(new AudioBrodcast());
	}

	public static void handleRequest(Request request) {
		for (WebHandler handler : webHandlers)
			if (handler.getHandleURL().equals(request.getUrl())) {
				handler.handleRequest(request);
				return;
			}
		defaultHandler.handleRequest(request);

	}

	static class DefaultHandler implements WebHandler {

		public String getHandleURL() {
			return null;
		}

		public void handleRequest(Request request) {
			PrintWriter pw = new PrintWriter(request.getOutputStream());

			Map<String, String> headers = new HashMap<String, String>();

			headers.put("Server", "homeJavaServer");
			headers.put("Content-Type", "text/html");
			headers.put("Content-Length", "0");

			pw.println(HTTPUtils.printStatusCode(404));

			pw.flush();

		}

	}

}
