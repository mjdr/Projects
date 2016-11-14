package desuteam.webServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Request {
	private Socket socket;
	private String url;
	private String method;
	private OutputStream outputStream;
	private Map<String,String> headers;
	private Map<String,String> params;
	
	
	
	public Request(Socket socket, String url, String method, OutputStream outputStream, Map<String, String> headers, Map<String, String> params) {
		this.socket = socket;
		this.url = url;
		this.method = method;
		this.outputStream = outputStream;
		this.headers = headers;
		this.params = params;
	}
	
	public Socket getSocket() {
		return socket;
	}
	public OutputStream getOutputStream() {
		return outputStream;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public String getUrl() {
		return url;
	}
	public String getMethod() {
		return method;
	}
	public Map<String, String> getParameters() {
		return params;
	}
	
	public static Request parse(Socket socket) throws IOException{
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		String first = reader.readLine();//GET /someDir/somePage.html HTTP/1.1
		if(first == null) return null;
		String[] parts = first.split("\\s");
		
		String method = parts[0];
		String url = parts[1].substring(1);
		if(url.contains("?"))
			url = url.substring(0, url.indexOf('?'));
		
		Map<String,String> headers = new HashMap<String, String>();
		
		String line;
		while((line = reader.readLine()) != null && !line.isEmpty()){
			String[] datas = line.split("\\:\\s");
			headers.put(datas[0], datas[1]);
		}
		Map<String,String> params = new HashMap<String, String>();
		String contentLength = headers.get("Content-Length");
		if(contentLength != null && Integer.parseInt(contentLength) > 0){
			char[] buff = new char[Integer.parseInt(contentLength)]; 
			reader.read(buff);
			String paramsString = new String(buff);
			String[] paramPair = paramsString.split("\\&");
			for(String pair : paramPair){
				String[] keyVal = pair.split("=");
				params.put(keyVal[0], keyVal[1]);
			}
		}
		
		return new Request(socket, url, method, socket.getOutputStream(), headers, params);
	}
}
