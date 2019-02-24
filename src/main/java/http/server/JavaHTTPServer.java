package http.server;

import http.server.request.HttpLogger;
import http.server.request.HttpRequest;
import http.server.request.RequestParser;
import http.server.response.HttpResponse;
import http.server.response.StandardResponseHeader;
import http.server.request.RequestType;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class JavaHTTPServer implements Runnable {

	private Socket connect;

	public JavaHTTPServer(Socket c) {
		connect = c;
	}

	public void run() {

		PrintWriter out = null;
		BufferedOutputStream dataOut = null;
		String requestData = "";
		int streamLength = 0;
		InputStream inputStream = null;
		boolean verbose = false;
		byte[] buffer = null;
		try {

			out = new PrintWriter(connect.getOutputStream());
			dataOut = new BufferedOutputStream(connect.getOutputStream());
			inputStream = connect.getInputStream();

			streamLength = inputStream.available();
			buffer = new byte[streamLength + 1000];
			inputStream.read(buffer, 0, streamLength);
			requestData = new String(buffer, 0, buffer.length);

			// use this object to get the data you want from the request
			// methods not starting with get arent intended for external use
			RequestParser n = new RequestParser(requestData, connect.getInetAddress());
			HttpLogger.log(n.toString(true));
			if (verbose) {
				System.out.println();
				System.out.println("CONTENT-LENGTH: " + n.getContentLength());
				System.out.println("POST-PARAMS: " + n.getParams());
				System.out.println("FORMDATA: " + n.getFormData());
				System.out.println("REQUEST: " + n.getRequestType());
				System.out.println("URL: " + n.getFileRequested());
				System.out.println();
			}

			if (n.getFileRequested() != null || n.getRequestType() != RequestType.INVALID) {
				HttpRequest myRequest = new HttpRequest(n);
				HttpResponse myResponse = myRequest.processRequest();
				myResponse.sendResponse(out, dataOut);
			}

		} catch (IOException ioe) {

			System.err.println("Server error : " + ioe);

		} finally {

			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (out != null) {
					out.close();
				}
				if (dataOut != null) {
					dataOut.close();
				}
				connect.close(); // we close socket connection
			} catch (Exception e) {
				System.err.println("Error closing stream : " + e.getMessage());
			}

		}

	}

}