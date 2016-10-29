package desuteam.com.times10;

public class Event {
	String name;
	String info = "";
	String link;
	
	public Event(String name, String link) {
		
		this.name = name;
		this.link = link;
	}
	
	@Override
	public String toString() {
		return String.format("%50s		%s", name,link);
	}
	
	
}