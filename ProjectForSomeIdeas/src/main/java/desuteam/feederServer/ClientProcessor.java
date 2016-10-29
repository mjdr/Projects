package desuteam.feederServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.BreakIterator;

public class ClientProcessor implements Runnable {
	
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	private Thread thread;
	
	public ClientProcessor(Socket socket) throws IOException {
		this.socket = socket;
		
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream());
		
		thread = new Thread(this);
		thread.start();
	}
	
	
	public void run() {
		
		
		try {
			
			String firstLine = reader.readLine();
			if(firstLine != null){
				String[] parts = firstLine.split("\\s");
				
				String url = parts[1].substring(1);
				
				System.out.println(url);
				
				//Skip all headers
				for(String line;(line = reader.readLine())!=null && !line.isEmpty(););
				
				
				
				String body;
				String contentType;
				try{
					String xmlFeed = FeederManager.getXMLFeed(url);
					
					if(xmlFeed == null){
						body = "<html><title>Error</title><body><div style=\"border:1px solid black;\">Feeder with url:"+url+" not found!</div></body></html>";
						contentType = "Content-Type: text/html";
					}
					else{
						body = xmlFeed;
						contentType = "application/rss+xml; charset=UTF-8";
					}
				}
				catch (Exception e) {
					body = "<html><title>Error</title><body><div style=\"border:1px solid black;\">Feed create error! <br>"+e.getMessage()+"</div></body></html>";
					contentType = "Content-Type: text/html";
				}
				
				System.out.println(body.length());
				writer.println("HTTP-Version: HTTP/1.1 200 OK");
				writer.println("Content-Length: " + body.length());
				writer.println("Content-Type: " + contentType);
				writer.println("");
				writer.write(body);
				writer.flush();
			}
			
			
		} catch (IOException e) {}
		finally {
			try {
				reader.close();
				writer.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	
}
