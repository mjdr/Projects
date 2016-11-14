package desuteam.webServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import desuteam.webServer.handler.HandlerManager;

public class Server implements Runnable {
	
	
	private static int PORT = 80;
	private ServerSocket serverSocket;
	private Thread workThread;
	
	
	public void start() throws IOException{
		
		HandlerManager.init();
		
		
		serverSocket = new ServerSocket(PORT);
		workThread = new Thread(this);
		workThread.start();
	}


	public void run() {
		Socket client;
		while(true){
			try {
				client = serverSocket.accept();
				
				new ClientProcessor(client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}
