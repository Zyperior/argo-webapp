package http.server.response;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import http.server.filehandling.FileStore;

public class HttpResponsePost extends HttpResponse {

	HttpResponsePost(String fileRequested, PrintWriter out, BufferedOutputStream dataOut, ArrayList<String> requestData)
			throws IOException {
		super(fileRequested, out, dataOut, requestData);
		this.body = true;

		sendResponse();
		// Send the bulk of the request and handle it in the filestore class
		// input "" is a placeholder that can be utilized for storage location with parameters
		FileStore.arrToFile(requestData, "");

	}

	public void sendResponse() throws IOException {
		out.println("HTTP/1.1 200 OK");
		standardOutToClient();

	}
}
