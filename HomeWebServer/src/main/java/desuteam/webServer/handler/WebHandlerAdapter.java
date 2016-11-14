package desuteam.webServer.handler;

public abstract class WebHandlerAdapter implements WebHandler {
	
	protected String handleUrl;
	
	public WebHandlerAdapter(String handleUrl) {
		this.handleUrl = handleUrl;
	}
	
	public String getHandleURL() {
		return handleUrl;
	}
	
	
	
}
