package desuteam.feederServer.feeders;

import java.io.IOException;

public abstract class AbstractFeeder {
	
	public abstract String getUrl();
	public abstract String getXMLFeed() throws IOException;
	
}
