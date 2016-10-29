package desuteam.com.times10;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import desuteam.Freelance.Proxies;

public class App 
{
    public static void main( String[] args ) throws Exception{
    	new App();
    }

    private int timeOutCounter = 0;
    private int goodCounter = 0;
    
    private void timeOut(){
    	timeOutCounter++;
    	if(timeOutCounter >= 3){
    		timeOutCounter -= 3;
    		goodCounter = 0;
    		Proxies.setNextProxy(true);
    	}
    }
    private void good(){
    	goodCounter++;
    	if(goodCounter >= 5){
    		goodCounter = 0;
    		Proxies.setNextProxy(true);
    	}
    }
    
    
    public App() throws IOException {

    	Proxies.initDefault();
    	Proxies.setNextProxy(false);
    	
        
    	
    	PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("data2.csv", true)));
    	
    	
    	
    	for(int page = 44;page < 70;page++){
	    	List<Organizer> organizers;
	    	while(true){
				try {
					organizers = getOrganizers(page);
					timeOutCounter = 0;
					break;
				} catch (IOException e) {
					timeOut();
				}
	    	}
	    	System.out.println("Page: " + page);
		   
	    	if(page == 44){
	    		organizers.remove(0);
	    		organizers.remove(0);
	    		organizers.remove(0);
	    		organizers.remove(0);
	    		organizers.remove(0);
	    		organizers.remove(0);
	    		organizers.remove(0);
	    		organizers.remove(0);
	    	}
	    	
	    	int counter1 = 1;
	    	for(Organizer organizer : organizers){
		    	System.out.printf("[%d / %d] %s\n",counter1++, organizers.size(),organizer.name);
	    		while(true){
			    	try {
						setOrganizerInfo(organizer);
						timeOutCounter = 0;
						break;
					} catch (Exception e) {
						timeOut();
					}
	    		}
	    		setHostedEvents(organizer);
	    		setUpcomingEvents(organizer);
	    		
	    		int counter = 0;
	    		System.out.println("	Get hosted Events info");
	    		
	    		counter = 0;
	    		for(Event event : organizer.hostedEvents){
	    			for(int j = 0;j < 10;j++)
				    	try {
							setEventInfo(organizer, event);
							timeOutCounter = 0;
							System.out.println("	" + counter);
							break;
						} catch (Exception e) {
							timeOut();
						}
	    				counter++;
	    			}
	    		System.out.println();
	    		System.out.println("	Get upcoming Events info");
	    		
	    		counter = 0;
	    		for(Event event : organizer.upcomingEvents){
	    			for(int j = 0;j < 10;j++)
				    	try {
							setEventInfo(organizer, event);
							timeOutCounter = 0;
							System.out.println("	" + (counter));
							break;
						} catch (Exception e) {
							timeOut();
						}
	    			counter++;
	    			}
	    		print(printWriter, organizer);
		    	printWriter.flush();
	    	}
    	}
    	printWriter.close();
	}
    
    
    public void sleep(int time){
    	try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    public void print(PrintWriter pw, Organizer organizer){
    	String res = "";

    	Event host = organizer.hostedEvents.size() > 0 ? organizer.hostedEvents.get(0): new Event("", "");
    	Event upc = organizer.upcomingEvents.size() > 0 ? organizer.upcomingEvents.get(0): new Event("", "");
    	
    	res = String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
    			organizer.name,organizer.info,
    			host.name,host.info,
    			upc.name,upc.info);
    	pw.println(res);
    	
    	for(int i = 1;i < Math.max(organizer.hostedEvents.size(), organizer.upcomingEvents.size());i++){
    		host = organizer.hostedEvents.size() > i ? organizer.hostedEvents.get(i) : new Event("", "");
        	upc = organizer.upcomingEvents.size() > i ? organizer.upcomingEvents.get(i) : new Event("", "");
        	res = String.format("\"\",\"\",\"%s\",\"%s\",\"%s\",\"%s\"",
        			host.name,host.info,
        			upc.name,upc.info);
        	pw.println(res);
    	}
    	pw.println();
    	
    }
    
    
	public void setUpcomingEvents(Organizer organizer){
		System.out.println("	Upcoming Events");
    	organizer.upcomingEvents = getEvents(organizer, "up","up_more");
    }
    public void setHostedEvents(Organizer organizer){
		System.out.println("	Hosted Events");
    	organizer.hostedEvents = getEvents(organizer, "exp","exp_more");
    }
    
    public List<Event> getEvents(Organizer organizer, String type, String nextID){
    	
    	List<Event> events = new ArrayList<Event>();
    	int counter = 1;
    	boolean next = true;
	    System.out.print("		");
    	do{
	    	Connection conn = Jsoup.connect(String.format("%s?page=%d&ajax=1&type=%s", organizer.link, counter++,type));
	    	conn.header("Accept", "*/*");
	    	conn.header("Upgrade-Insecure-Requests", "1");
	    	conn.header("X-Requested-With", "XMLHttpRequest");
	    	conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
	    	conn.header("DNT", "1");
	    	conn.header("Referer", organizer.link);
	    	conn.header("Accept-Encoding", "gzip, deflate, sdch");
	    	conn.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    	conn.header("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
	    	conn.cookie("country", "Ukraine");
	    	conn.cookie("countryCode", "UA");
	    	conn.method(Method.POST);
	    	Response response;
	    	while(true){
	    		try {
					response = exec(conn,String.format("%s?page=%d&ajax=1&type=%s", organizer.link, counter,type));
					timeOutCounter = 0;
					break;
				} catch (IOException e) {timeOut();}
	    		
	    	}
	    	counter++;
	    	Document doc = Jsoup.parseBodyFragment(response.body());
	    	Elements links = doc.select("a[itemprop=\"url\"]");
	    	
	    	for(Element link : links){
	    		Event event = new Event(link.select("span").first().text(), "http://10times.com"+link.attr("href"));
	    		events.add(event);
	    	}
	    	
	    	next = doc.select("#"+nextID).size() == 1;
	    	System.out.printf("%5d",events.size());
    	}while(next);

    	System.out.println();
    	System.out.println("	Size: " + events.size());
    	
    	return events;
    }
    public void setEventInfo(Organizer organizer, Event event) throws Exception {
    	Connection conn = Jsoup.connect(event.link);
    	conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    	conn.header("Upgrade-Insecure-Requests", "1");
    	conn.header("X-Requested-With", "XMLHttpRequest");
    	conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
    	conn.header("DNT", "1");
    	conn.header("Referer", organizer.link);
    	conn.header("Accept-Encoding", "gzip, deflate, sdch");
    	conn.header("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
    	conn.cookie("country", "Ukraine");
    	conn.cookie("countryCode", "UA");
    	
    	Response response = exec(conn,event.link);
    	Document doc = Jsoup.parse(response.body());
    	
    	
    	Element info = doc.select("p.desc").first();
    	event.info = info.text();
    	
    }
    public void setOrganizerInfo(Organizer organizer) throws Exception {
    	Connection conn = Jsoup.connect(organizer.link).timeout(10000);
    	conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    	conn.header("Upgrade-Insecure-Requests", "1");
    	conn.header("X-Requested-With", "XMLHttpRequest");
    	conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
    	conn.header("DNT", "1");
    	conn.header("Referer", "http://10times.com/organizers/china");
    	conn.header("Accept-Encoding", "gzip, deflate, sdch");
    	conn.header("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
    	conn.cookie("country", "Ukraine");
    	conn.cookie("countryCode", "UA");
    	
    	Response response = exec(conn,organizer.link);
    	Document doc = Jsoup.parse(response.body());
    	Element info = doc.select("blockquote.box").first();
    	organizer.info = info == null?"":info.text();
    }
    
    public List<Organizer> getOrganizers(int page) throws IOException{
    	
    	List<Organizer> result = new ArrayList<Organizer>();
    	
    	Connection conn = Jsoup.connect("http://10times.com/organizers/china?ajax=1&page="+page);
    	conn.timeout(10000).method(Method.GET);
    	conn.header("Accept", "*/*");
    	conn.header("X-Requested-With", "XMLHttpRequest");
    	conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
    	conn.header("DNT", "1");
    	conn.header("Referer", "http://10times.com/organizers/china");
    	conn.header("Accept-Encoding", "gzip, deflate, sdch");
    	conn.header("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
    	conn.cookie("country", "Ukraine");
    	conn.cookie("countryCode", "UA");
    	
    	
    	Response response = exec(conn,"http://10times.com/organizers/china?ajax=1&page="+page);
    	
    	
    	Document doc = Jsoup.parseBodyFragment(response.body());
    	
    	Elements divs = doc.select("div.col-md-8");
    	
    	for(Element div : divs){
    		Element link = div.select("h4 a").get(0);
    		result.add(new Organizer(link.text(), "http://10times.com" + link.attr("href")));
    	}
    	
    	return result;
    }
    
    private Response exec(Connection connection,String url) throws IOException{
    	connection.timeout(20000);
    	sleep(5000);
    	try{
    	Response response =  connection.execute();
    	good();
    	return response;
    	}catch(IOException e){
    		throw e;
    	}
    	
    }
}
