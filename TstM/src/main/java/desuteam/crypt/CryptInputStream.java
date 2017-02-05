package desuteam.crypt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class CryptInputStream extends InputStream {
	private Random rand;
	private InputStream stream;
	
	public CryptInputStream(InputStream stream, long key) {
		this.stream = stream;
		rand = new Random(key);
	}
	
	
	@Override
	public int read() throws IOException { return stream.read() ^ rand.nextInt(256); }
	@Override
	public void close() throws IOException { stream.close(); }
	@Override
	public int available() throws IOException { return stream.available(); }
	
	
	
}
