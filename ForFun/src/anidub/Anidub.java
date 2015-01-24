package anidub;

import anidub.parsers.PageParser;
import anidub.parsers.TrackerPageParser;

public class Anidub 
{	
	public static class Online
	{
		
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
