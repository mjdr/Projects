package desuteam.feederServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
	private ServerSocket server;
	private static final int PORT = 880;
	
	private Thread worker;
	
	public Server() throws IOException {
		server = new ServerSocket(PORT);
		worker = new Thread(this);
		worker.start();
	}

	public void run() {
		
		Socket client;
		
		while(true){
			try {
				client = server.accept();
				new ClientProcessor(client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		new Server();
	}
	
}
