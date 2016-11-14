package desuteam.webServer.utils.quickElements;

public class QuickTextField extends QuickElement {
	
	private String text;
	
	public QuickTextField(String text) {
		this.text = text;
	}
	
	public QuickTextField() {
		this("");
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String printJson() {
		return String.format("{\"type\":\"TextField\",\"text\":\"%s\"}", text);
	}
	
}
