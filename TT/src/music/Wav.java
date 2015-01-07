package music;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Wav 
{
	/**
	 * Get info about wav file;
	 * */
	public static WavInfo getInfo(File wavFile) throws IOException
	{
		
		if(wavFile.isDirectory() || !wavFile.exists())
			throw new IllegalArgumentException("Isn't wav file!");
		
		DataInputStream in = new DataInputStream(new FileInputStream(wavFile));
		
		WavInfo i = getInfo(in);
		in.close();
		return i;
	}
	public static WavInfo getInfo(InputStream input)throws IOException
	{
		int chunkSize;
		int numChannels;
		int sampleRate;
		int byteRate;
		int blockAlign;
		int bitsPerSample;
		int subchunk2Size;
		
		AdvDataInputStream in = new AdvDataInputStream(input);
		if(in.readInt() != 0x52494646)
		{
			System.out.println(0);
			return null;
		}
		chunkSize = Integer.reverse(in.readInt());
		if(in.readInt() != 0x57415645)
		{
			System.out.println(1);
			return null;
		}
		if(in.readInt() != 0x666d7420)
		{
			System.out.println(2);
			return null;
		}
		if(Integer.reverseBytes(in.readInt()) != 16)
		{
			System.out.println(3);
			return null;
		}
		if(Short.reverseBytes(in.readShort()) != 1)
		{
			System.out.println(4);
			return null;
		}
		numChannels = Short.reverseBytes(in.readShort());
		sampleRate =  Integer.reverseBytes(in.readInt());
		byteRate = Integer.reverseBytes(in.readInt());
		blockAlign = in.readShort();
		bitsPerSample = Short.reverseBytes(in.readShort());
		if(Long.reverseBytes(in.readUnsignedInt()) != 0x64617461)
		{
			System.out.println(5);
			return null;
		}
		subchunk2Size = in.readInt();
		 
		return new WavInfo(
				chunkSize,
				numChannels, 
				sampleRate, 
				byteRate, 
				blockAlign, 
				bitsPerSample, 
				subchunk2Size);
	}
}
