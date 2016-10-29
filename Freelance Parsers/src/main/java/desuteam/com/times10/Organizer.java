package desuteam.com.times10;

import java.util.List;

public class Organizer {
	String name;
	String link;
	String info;
	List<Event> hostedEvents, upcomingEvents;
	
	public Organizer(String name, String link) {
		this.name = name;
		this.link = link;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
}