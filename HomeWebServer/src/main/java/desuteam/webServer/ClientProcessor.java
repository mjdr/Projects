package desuteam.webServer;

import java.io.IOException;
import java.net.Socket;

import desuteam.webServer.handler.HandlerManager;

public class ClientProcessor implements Runnable {
	
	private Socket socket;
	
	public ClientProcessor(Socket socket) {
		this.socket = socket;
		new Thread(this).start();
		
	}
	
	public void run() {
		try {
			
			Request request = Request.parse(socket);
			if(request != null)
				HandlerManager.handleRequest(request);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
			try {
				if(socket.isConnected()){
					socket.getInputStream().close();
					socket.getOutputStream().close();
					socket.close();
				}
			} catch (IOException e) {
			}
		}
	}

}
