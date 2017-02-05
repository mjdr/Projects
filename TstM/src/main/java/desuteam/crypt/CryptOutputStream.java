package desuteam.crypt;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class CryptOutputStream extends OutputStream {

	private Random rand;
	private OutputStream stream;
	
	public CryptOutputStream(OutputStream stream, long key) {
		this.stream = stream;
		rand = new Random(key);
	}
	
	@Override
	public void write(int b) throws IOException { stream.write(b^rand.nextInt(256)); }
	@Override
	public void close() throws IOException { stream.close(); }
	@Override
	public void flush() throws IOException { stream.flush(); }
}
