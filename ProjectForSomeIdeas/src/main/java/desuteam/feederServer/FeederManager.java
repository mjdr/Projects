package desuteam.feederServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import desuteam.feederServer.feeders.AbstractFeeder;
import desuteam.feederServer.feeders.TestFeeder;

public class FeederManager {
	
	private static Map<String,AbstractFeeder>feeders;
	
	static{
		feeders = new HashMap<String, AbstractFeeder>();
		
		addFeeder(new TestFeeder());
		
	}
	
	private static void addFeeder(AbstractFeeder feeder){
		feeders.put(feeder.getUrl(), feeder);
	}
	
	
	public static String getXMLFeed(String url)throws IOException{
		if(feeders.containsKey(url))
			return feeders.get(url).getXMLFeed();
		return null;
	}
	
	
	
}
