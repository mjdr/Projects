package desuteam.webServer.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import desuteam.webServer.Request;

public class HTTPUtils {
	
	
	public static String printStatusCode(int code){
		String message = "";
		
		switch (code) {
		case 200:
			message = "OK";
			break;
		case 404:
			message = "Not Found";
			break;

		default:
			throw new RuntimeException("Code : " + code + " not define!");
		}
		
		return String.format("HTTP/1.1 %d %s", code, message);
	}
	
	public static String printHeaders(Map<String,String> headers){
		StringBuffer sb = new StringBuffer();
		for(String key : headers.keySet())
			sb.append(key).append(": ").append(headers.get(key)).append('\n');
		return sb.toString();
	}
	
	public static void responseHTMLFile(Request request, File file,Object...params){
		PrintWriter pw = new PrintWriter(request.getOutputStream());
		
		pw.println(HTTPUtils.printStatusCode(200));

		try {
			String data = String.format(FileUtils.readFile(file),params);
			pw.println(HTTPUtils.printHeaders(getDefaultHeaders("text/html", data.length())));
			pw.print(data);
		} catch (IOException e) {
			e.printStackTrace(pw);
		}
		
		pw.flush();
	}
	
	public static Map<String, String> getDefaultHeaders(String contentType, long contentLength){

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Server", "homeJavaServer");
		headers.put("Content-Type", contentType);
		if(contentLength >= 0)
			headers.put("Content-Length", Long.toString(contentLength));
			
		return headers;
	}
}
