package audio;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Main {
	static int frameSample;
	static int timeofFrame;
	static int N;
	static int runTimes;
	static int bps;
	static int channels;
	static double times;
	static int bufSize;
	static int frameSize;
	static int frameRate;
	static long length;

	public static void main(String[] args) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("resource/Doten.wav"));

			int numBytes = ais.available();
			System.out.println("numbytes: " + numBytes);
			byte[] buffer = new byte[numBytes];
			int count = 0;
			while (count != -1) {
				count = ais.read(buffer, 0, numBytes);
			}
			ais.close();

			int[] soundData = new int[buffer.length / 2];

			for (int i = 0; i < soundData.length; i++)
				soundData[i] = Byte.toUnsignedInt(buffer[2 * i]) | (Byte.toUnsignedInt(buffer[2 * i + 1]) << 8);

			PrintWriter pw = new PrintWriter(new FileWriter("resource/soundData.csv"));

			for (int i = 0; i < soundData.length && i < 1000; i++)
				pw.format("%d,%d\n", i + 115000, soundData[i + 115000]);

			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
