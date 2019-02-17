package http.server;

import http.server.response.HttpResponseFactory;
import http.server.response.RequestType;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class JavaHTTPServer implements Runnable {

	private Socket connect;

	public JavaHTTPServer(Socket c) {
		connect = c;
	}

	public void run() {

		BufferedReader in = null;
		PrintWriter out = null;
		BufferedOutputStream dataOut = null;
		RequestType type = RequestType.INVALID;
		String fileRequested = "";
		String method = "";

		// This arraylist contains the entire http request except the first row
		ArrayList<String> requestData = new ArrayList<String>();

		try {

			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			out = new PrintWriter(connect.getOutputStream());
			dataOut = new BufferedOutputStream(connect.getOutputStream());
			String input = null;

			if (in.ready()) {
				input = in.readLine();
			}
			// Dont parse nulls!
			if (input != null) {
				StringTokenizer parse = new StringTokenizer(input);

				// Dont request more tokens than we have!
				if (parse.hasMoreTokens()) {
					method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client

					fileRequested = parse.nextToken().toLowerCase();
				}
			}
			// Dump the rest of the buffered reader into an array we can use
			// maxLines acts as a safety net to prevent infinite loop due to software errors
			while (in.ready()) {
				requestData.add(in.readLine());
			}

			if (method.equals("GET")) {
				type = RequestType.GET;
			} else if (method.equals("HEAD")) {
				type = RequestType.HEAD;
			} else if (method.equals("POST")) {
				type = RequestType.POST;
			}

			HttpResponseFactory.createResponse(type, fileRequested, out, dataOut, requestData);

		} catch (IOException ioe) {

			System.err.println("Server error : " + ioe);

		} finally {

			try {
				if (in != null) {

					in.close();
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