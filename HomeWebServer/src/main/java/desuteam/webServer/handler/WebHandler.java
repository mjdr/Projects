package desuteam.webServer.handler;

import desuteam.webServer.Request;

public interface WebHandler {
	String getHandleURL();
	void handleRequest(Request request);
}
