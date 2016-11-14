package desuteam.webServer.utils.quickElements;

public class QuickImage extends QuickElement {
	private String src;
	
	public QuickImage(String src) {
		this.src = src;
	}
	public QuickImage() {
		this("");
	}
	
	public void setSrc(String src) {
		this.src = src;
	}
	
	@Override
	public String printJson() {
		return String.format("{\"type\":\"Image\",\"src\":\"%s\"}", src);
	}
	
}
