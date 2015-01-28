package anidub;

import anidub.parsers.PageParser;
import anidub.parsers.OnlinePageParser;
import anidub.parsers.TrackerPageParser;

public class Anidub 
{	
	public static class Online
	{
		private static OnlinePageParser oparser = new OnlinePageParser();
		public static PageParser getParser()
		{
			return oparser;
		}
	}
	public static class Tracker
	{
		private static TrackerPageParser parser = new TrackerPageParser();
		public static PageParser getParser()
		{
			return parser;
		}
	}
}
