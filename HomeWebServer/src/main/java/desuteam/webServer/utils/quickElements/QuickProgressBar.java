package desuteam.webServer.utils.quickElements;

public class QuickProgressBar extends QuickElement {
	private String name;
	private int currentValue;
	private int maxValue;
	
	public QuickProgressBar(String name, int currentValue, int maxValue) {
		super();
		this.name = name;
		this.currentValue = currentValue;
		this.maxValue = maxValue;
	}

	public QuickProgressBar(String name) {
		this(name, 0, 100);
	}
	
	
	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	
	@Override
	public String printJson() {
		return String.format("{\"type\": \"ProgressBar\",\"currentValue\": \"%d\",\"maxValue\" : \"%d\", \"name\" : \"%s\"}", currentValue, maxValue, String.format("%s(%d/%d)", name,currentValue,maxValue));
	}
	
	
	
}
