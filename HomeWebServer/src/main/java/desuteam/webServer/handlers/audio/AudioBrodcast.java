package desuteam.webServer.handlers.audio;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import desuteam.webServer.Request;
import desuteam.webServer.handler.WebHandlerAdapter;
import desuteam.webServer.utils.HTTPUtils;

public class AudioBrodcast extends WebHandlerAdapter {

	public AudioBrodcast() {
		super("audio");
	}

    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    TargetDataLine line;
    public void handleRequest(Request request) {
		
    	 try {
             AudioFormat format = getAudioFormat();
             DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
  
             // checks if system supports the data line
             if (!AudioSystem.isLineSupported(info)) {
                 System.out.println("Line not supported");
                 System.exit(0);
             }
             line = (TargetDataLine) AudioSystem.getLine(info);
             line.open(format);
             line.start();   // start capturing
  
             OutputStream out = request.getOutputStream();
             PrintWriter pw = new PrintWriter(out);

             pw.println(HTTPUtils.printStatusCode(200));
             
             Map<String, String>headers = HTTPUtils.getDefaultHeaders("audio/wave", -1);
             
             headers.put("Content-Disposition", "inline; filename=\"stream.wav\"");
             
             pw.println(HTTPUtils.printHeaders(headers));
             
             int numBytesRead;
             byte[] data = new byte[1024 * 256];
             
             for (int i = 0; i < 5 && request.getSocket().isConnected();i++) {
            	   numBytesRead =  line.read(data, 0, data.length);
            	   try{
	            	   out.write(data, 0, numBytesRead);
	            	   out.flush();
            	   }catch(SocketException e){
            		   break;
            	   }
            	}
             line.close();
  
         } catch (LineUnavailableException ex) {
             ex.printStackTrace();
         } catch (IOException ioe) {
             ioe.printStackTrace();
         }
	}
    
    AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
    
    
    
    
}
