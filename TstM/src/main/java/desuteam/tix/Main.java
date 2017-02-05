package desuteam.tix;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.java_websocket.WebSocket;
import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;

public class Main {
	public static void main(String[] args) throws URISyntaxException {
		
		
		
		WebSocketClient client = new WebSocketClient(new URI("wss://tixchat.com/ws/") , new Draft_10()) {
			
			
			@Override
			public void onOpen(ServerHandshake arg0) {
				System.out.println("Open");
				
			}
			
			@Override
			public void onMessage(String message) {
				System.out.println("Message: " + message);
				
			}
			
			@Override
			public void onError(Exception ex) {
				
				
			}
			
			@Override
			public void onClose(int arg0, String arg1, boolean arg2) {
				System.out.println("Close: " + arg1);
				
			}
		};
		
		
		SSLContext sslContext = null;
		try {
		    sslContext = SSLContext.getInstance( "TLS" );
		    sslContext.init( null, null, null ); // will use java's default key and trust store which is sufficient unless you deal with self-signed certificates
		} catch (NoSuchAlgorithmException e) {
		    e.printStackTrace();
		} catch (KeyManagementException e) {
		    e.printStackTrace();
		}
		
		client.setWebSocketFactory( new DefaultSSLWebSocketClientFactory( sslContext ) );
		client.connect();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.send("['version', [2, 'TiXchat web', '5c0e82781ebf']]");
		
		
		
	}
}
