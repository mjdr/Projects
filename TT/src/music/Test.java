package music;

import java.io.File;
import java.io.IOException;

public class Test 
{
	public static void main(String[] args) throws IOException 
	{
		WavInfo info = Wav.getInfo(new File("/home/jdr/Downloads/music.wav"));
		System.out.println(info.getChunkSize());
	}
}
