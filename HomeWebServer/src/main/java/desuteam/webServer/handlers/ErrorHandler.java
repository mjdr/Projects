package desuteam.webServer.handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import desuteam.webServer.Request;
import desuteam.webServer.handler.WebHandlerAdapter;

public class ErrorHandler extends WebHandlerAdapter {

	private File logFile;

	public ErrorHandler(String fileName) {
		super("error");// bind address http://serverhost.com/error
		this.logFile = new File(fileName);
	}

	public void handleRequest(Request request) {
		if (!request.getMethod().equals("GET"))
			return;
		if (!request.getParameters().containsKey("error"))
			return;

		try {
			PrintWriter printWriter = new PrintWriter(new FileWriter(logFile, true));

			printWriter.printf("[%s]: %s", Calendar.getInstance().getTime().toString(),
					request.getParameters().get("error"));
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
