package music;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AdvDataInputStream extends DataInputStream
{

	public AdvDataInputStream(InputStream in) 
	{
		super(in);
	}
	
	
	public int readUnsignedInt() throws IOException
	{
		long res = readUnsignedShort();
		
		res = res << 16;
		res |= readUnsignedShort();
		return (int) res;
	}

}
