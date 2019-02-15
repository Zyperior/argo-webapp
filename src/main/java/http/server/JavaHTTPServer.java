package http.server;

import java.io.*;
import java.net.Socket;
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
		try {

			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			out = new PrintWriter(connect.getOutputStream());
			dataOut = new BufferedOutputStream(connect.getOutputStream());

			String input = in.readLine();

			// Dont parse nulls!
			if (input != null) {
				StringTokenizer parse = new StringTokenizer(input);

				// Dont request more tokens than we have!
				if (parse.hasMoreTokens()) {
					method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client

					fileRequested = parse.nextToken().toLowerCase();
				}
			}

			if (method.equals("GET")) {
				type = RequestType.GET;
			} else if (method.equals("HEAD")) {
				type = RequestType.HEAD;
			} else if (method.equals("POST")) {
				type = RequestType.POST;
			}

			new HTTPRequest(type, fileRequested, out, dataOut);

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